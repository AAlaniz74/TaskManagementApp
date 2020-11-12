package com.sdproject.app.view;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.ArrayList;
import com.sdproject.app.model.*;
import com.sdproject.app.database.*;
import java.time.format.DateTimeFormatter;

public class AdminUserView extends JFrame {

  class ListElement {
    String name;
    int ID;

    ListElement(String name, int ID) {
      this.name = name;
      this.ID = ID;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }

  private DatabaseWrapper db;
  private int currentUserID;
  
  private String currentTable;
  private int selectedID;

  private JButton addButton;
  private JButton deleteButton;
  private JButton displayButton;
  private JButton modifyButton;
  private JPanel panel;
  private JComboBox<String> tables;
  private JTextArea textBox;
  private JList<ListElement> list = new JList<>();
  private DefaultListModel<ListElement> model = new DefaultListModel<>();

  public AdminUserView(DatabaseWrapper db, int currentUserID) {
    this.db = db;
    this.currentUserID = currentUserID;
    this.currentTable = "User";
        
    panel = new JPanel();
    setSize(700, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(panel);
    panel.setLayout(null);

    comboBoxes();
    addNewButton();
    displayButton();
    deleteButton();
    modifyButton();
    createJList();
    createTextArea();

    setVisible(true);
  }

  public void comboBoxes() {
    String[] tableList = {"User", "Team", "Task"};

    tables = new JComboBox<String>(tableList);
    tables.setBounds(115, 10, 120 , 20);
    panel.add(tables);
  }

  public void addNewButton() {
    addButton = new JButton(new AbstractAction("Add New"){
      @Override
      public void actionPerformed(ActionEvent e) {
        if (currentTable.equals("User")) {
          CreateUserView t = new CreateUserView(db);
        } else if (currentTable.equals("Task")) {
          CreateTaskView t = new CreateTaskView(db);
        } else if (currentTable.equals("Team")) {
          CreateTeamView t = new CreateTeamView(db);
        }
      }
    });
    addButton.setBounds(10, 420, 90, 20);
    panel.add(addButton);
  }

  public void displayButton() {
    displayButton = new JButton(new AbstractAction("Display"){
      @Override
      public void actionPerformed(ActionEvent e) {
        if (tables.getItemAt(tables.getSelectedIndex()).equals("Task")) {
          currentTable = "Task";
        } else if (tables.getItemAt(tables.getSelectedIndex()).equals("User")) {
          currentTable = "User";
        } else if (tables.getItemAt(tables.getSelectedIndex()).equals("Team")) {
          currentTable = "Team";
        }
          clearJList();
          fillJList();
          textBox.setText("");
      }
    });
    displayButton.setBounds(15, 10, 90, 20);
    panel.add(displayButton);
  }

  public void deleteButton() {
    deleteButton = new JButton(new AbstractAction("Delete Selected"){
      @Override
      public void actionPerformed(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(new JFrame(), "Are you sure?");
        
        if (confirm == JOptionPane.YES_OPTION) {

          if (currentTable.equals("User")) {
            db.query().tableIs(currentTable).userIdIs(selectedID).delete();
          } else if (currentTable.equals("Team")) {
            db.query().tableIs(currentTable).teamIdIs(selectedID).delete();
          } else if (currentTable.equals("Task")) {
            db.query().tableIs(currentTable).taskIdIs(selectedID).delete();
          }
          clearJList();
          fillJList();
          textBox.setText("");
        }
      }
    });
    deleteButton.setBounds(10, 390, 125, 20);
    panel.add(deleteButton);
  }

  public void modifyButton() {
    modifyButton = new JButton("Modify Selected");
    /*
    modifyButton = new JButton(new AbstractAction("Modify Selected"){
      @Override
      public void actionPerformed(ActionEvent e) {
        if(currentTable.equals("User")) {
          User user = db.query().tableIs("User").userNameIs(selectedID).getOne();
          ModifyUserView view = new ModifyUserView(db, user, this);
        } else if(currentTable.equals("Team")) {
        
        } else if(currentTable.equals("Task")) {

        }

        clearJList();
        fillJList();
        textBox.setText("");
      }
    });
    */
    modifyButton.setBounds(140, 390, 125, 20);
    panel.add(modifyButton);
  }

  public void createJList() {
    list.setModel(model);
    fillJList();
    list.setBounds(10, 40, 250, 340);
    panel.add(list);
    list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        selectedID = (list.getSelectedValue() == null) ? 0 : list.getSelectedValue().ID;
        displayText();
      }
    });
  }

  public void createTextArea() {
    textBox = new JTextArea();
    textBox.setBounds(280, 40, 390, 340);
    textBox.setEditable(false);
    panel.add(textBox);
  }

  public void fillJList() {
    if (currentTable.equals("Task")) {
      ArrayList<Task> taskList = db.query().tableIs("Task").get();
      if (taskList != null) {
        for (Task task : taskList)
          model.addElement(new ListElement(task.getTaskName(), task.getTaskId()));
      }
    } else if (currentTable.equals("User")) {
      ArrayList<User> userList = db.query().tableIs("User").get();
      if (userList != null) {
        for (User user : userList)
          model.addElement(new ListElement(user.getUserName(), user.getUserId()));
      }
    } else if (currentTable.equals("Team")) {
      ArrayList<Team> teamList = db.query().tableIs("Team").get();
      if (teamList != null) {
        for (Team team : teamList)
          model.addElement(new ListElement(team.getTeamName(), team.getTeamId()));
      }
    }
  }

  public void displayText() {
    
    if (selectedID == 0) {
      return;
    } else if (currentTable.equals("User")) {
      User selectedUser = db.query().tableIs("User").userIdIs(selectedID).getOne();
      textBox.setText("Name: " + selectedUser.getUserName() +
                      "\nID: " + selectedUser.getUserId() +
                      "\nPassword: " + selectedUser.getUserPass() +
                      "\nUser Type: " + selectedUser.getUserType());
    
    } else if (currentTable.equals("Task")) {
      Task selectedTask = db.query().tableIs("Task").taskIdIs(selectedID).getOne();

      User createdBy = db.query().tableIs("User").userIdIs(selectedTask.getCreatedById()).getOne();

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      String dueDate = (selectedTask.getDueDate() == null) ? "" : selectedTask.getDueDate().format(formatter);
      String recurringDays = (selectedTask.getRecurringDays() == 0) ? "" : Integer.toString(selectedTask.getRecurringDays());

      String text = "Name: " + selectedTask.getTaskName() +
                    "\nTask Description: " + selectedTask.getTaskDesc() +
                    "\nID: " + selectedTask.getTaskId() +
                    "\nTask Status: " + selectedTask.getTaskStatus() +
                    "\nCreated By: " + createdBy.getUserName() + " (" + createdBy.getUserId() + ")" +
                    "\nAssigned To ID: " + selectedTask.getAssignedToId() + 
                    "\nCreated On: " + selectedTask.getCreatedOn().format(formatter) +
                    "\nDue Date: " + dueDate +
                    "\nRecurring Interval: " + recurringDays;

      ArrayList<Integer> subtaskIDs = selectedTask.getSubtaskIDs();
      
      if (subtaskIDs != null) {
        
        text += "\nSubtasks:";

        for (Integer subtaskID : subtaskIDs) {
          Task task = db.query().tableIs("Task").taskIdIs(subtaskID).getOne();
          text += "\n" + task.getTaskName() + " (" + task.getTaskId() + ")";
        }
      }

      textBox.setText(text);
           
    } else if (currentTable.equals("Team")) {
      Team selectedTeam = db.query().tableIs("Team").teamIdIs(selectedID).getOne();
      
      String text = "Team Name: " + selectedTeam.getTeamName() + 
                    "\nTeam Members:";
      ArrayList<User> userlist = db.query().tableIs("Team").teamIdIs(selectedID).getTeamMembers();
      
      for (User user : userlist)
        text += "\n" + user.getUserName();
        
      textBox.setText(text);
    
    }
  }

  public void clearJList(){
    selectedID = 0;
    model.clear();
  }

}
