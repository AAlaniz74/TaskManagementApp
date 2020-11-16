package com.sdproject.app.view;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.ArrayList;
import com.sdproject.app.model.*;
import com.sdproject.app.database.*;
import java.time.format.DateTimeFormatter;

public class NormalUserView extends JFrame {

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

  public NormalUserView(DatabaseWrapper db, int currentUserID) {
    this.db = db;
    this.currentUserID = currentUserID;
    this.currentTable = "Assigned Tasks";
        
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
    String[] tableList = {"Assigned Tasks", "Created Tasks", "Team"};

    tables = new JComboBox<String>(tableList);
    tables.setBounds(115, 10, 120 , 20);
    panel.add(tables);
  }

  public void addNewButton() {
    addButton = new JButton(new AbstractAction("New Task"){
      @Override
      public void actionPerformed(ActionEvent e) {
          CreateTaskView t = new CreateTaskView(db, currentUserID);
      }
    });
    addButton.setBounds(10, 420, 90, 20);
    panel.add(addButton);
  }

  public void displayButton() {
    displayButton = new JButton(new AbstractAction("Display"){
      @Override
      public void actionPerformed(ActionEvent e) {
        if (tables.getItemAt(tables.getSelectedIndex()).equals("Assigned Tasks")) {
          currentTable = "Assigned Tasks";
          deleteButton.setVisible(false);
          modifyButton.setVisible(false);
        } else if (tables.getItemAt(tables.getSelectedIndex()).equals("Created Tasks")) {
          currentTable = "Created Tasks";
          deleteButton.setVisible(true);
          modifyButton.setVisible(true);
        } else if (tables.getItemAt(tables.getSelectedIndex()).equals("Team")) {
          currentTable = "Team";
          deleteButton.setVisible(false);
          modifyButton.setVisible(false);
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
    deleteButton = new JButton(new AbstractAction("Delete Task"){
      @Override
      public void actionPerformed(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(new JFrame(), "Are you sure?");
        
        if (confirm == JOptionPane.YES_OPTION) {
            db.query().tableIs("Task").taskIdIs(selectedID).delete();
          }
          clearJList();
          fillJList();
          textBox.setText("");
        }
    });
    deleteButton.setBounds(10, 390, 125, 20);
    panel.add(deleteButton);
    deleteButton.setVisible(false);
  }

  public void modifyButton() {

    modifyButton = new JButton(new AbstractAction("Modify Task"){
      @Override
      public void actionPerformed(ActionEvent e) {
        //ModifyTaskView t = new ModifyTaskView(db, taskName);
        clearJList();
        fillJList();
        textBox.setText("");
      }
    });

    modifyButton.setBounds(140, 390, 125, 20);
    panel.add(modifyButton);
    modifyButton.setVisible(false);
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
    if (currentTable.equals("Created Tasks")) {
      ArrayList<Task> taskList = db.query().tableIs("Task").get();
      if (taskList != null) {
        for (Task task : taskList)
        {
            if(task.getCreatedById() == currentUserID)
                model.addElement(new ListElement(task.getTaskName(), task.getTaskId()));
        }
      }
    } else if (currentTable.equals("Assigned Tasks")) {
        ArrayList<Task> taskList = db.query().tableIs("Task").get();
        if (taskList != null) {
          for (Task task : taskList)
          {
              if(task.getAssignedToId() == currentUserID)
                  model.addElement(new ListElement(task.getTaskName(), task.getTaskId()));
          }
        }
    } else if (currentTable.equals("Team")) {
        boolean foundTeam = false;
      ArrayList<Team> teamList = db.query().tableIs("Team").get();
      if (teamList != null) {
        for (Team team : teamList)
        {
            ArrayList<Integer> TeamMemberIDs = team.getTeamMemberIDs();
            for(int id : TeamMemberIDs)
            {
                if(id == currentUserID)
                {
                    foundTeam = true;
                    break;
                }
            }
            if(foundTeam)
            {
                for(int id : TeamMemberIDs)
                {
                    User user = db.query().tableIs("User").userIdIs(id).getOne();
                    model.addElement(new ListElement(user.getUserName(), id));
                }
                break;
            }
        }
      }
    }
  }

  public void displayText() {
    
    if (selectedID == 0) {
      return;
    }    
    else if (currentTable.equals("Created Tasks") || currentTable.equals("Assigned Tasks")) {
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
           
    } 
  }

  public void clearJList(){
    selectedID = 0;
    model.clear();
  }

}

