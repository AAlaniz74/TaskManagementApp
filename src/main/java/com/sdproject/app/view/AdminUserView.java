package com.sdproject.app.view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.BorderLayout;
import com.sdproject.app.model.*;
import com.sdproject.app.database.*;
import java.time.format.DateTimeFormatter;
import java.awt.Component;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;

public class AdminUserView extends JFrame implements UserView {

  private DatabaseWrapper db;
  private int currentUserID;
  
  private String currentTable;
  private int selectedID;

  private JButton addButton, deleteButton, searchButton, modifyButton, logoutButton;
  private JPanel topPanel, bottomPanel;
  private JComboBox<String> tables;
  private JTextArea textBox;
  private JList<ListElement> list = new JList<>();
  private DefaultListModel<ListElement> model = new DefaultListModel<>();
  private JScrollPane scrollPane = new JScrollPane();

  public AdminUserView(DatabaseWrapper db, int currentUserID) {
    this.db = db;
    this.currentUserID = currentUserID;
    this.currentTable = "User";
        
    setTitle("Admin Menu");
    setPreferredSize(new Dimension(800, 600));
    getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        db.serializeAll();
        System.exit(0);
      }
    });
    
    topPanel = new JPanel();
    bottomPanel = new JPanel();
    topPanel.setLayout(new FlowLayout());
    bottomPanel.setLayout(new FlowLayout());
    add(topPanel, BorderLayout.NORTH);
    add(bottomPanel, BorderLayout.SOUTH);

    comboBoxes();
    addNewButton();
    deleteButton();
    modifyButton();
    searchButton();
    addLogoutButton();
    createJList();
    createTextArea();

    pack();
    setVisible(true);
  }

  public void comboBoxes() {
    String[] tableList = {"User", "Task", "Team"};

    tables = new JComboBox<String>(tableList);
    tables.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent event) {
        if (event.getStateChange() == ItemEvent.SELECTED) {
          currentTable = (String) event.getItem();
          updateJList();
        }
      }
    });
    topPanel.add(tables);
  }

  public void addNewButton() {
    addButton = new JButton(new AbstractAction("Add"){
      @Override
      public void actionPerformed(ActionEvent e) {
        if (currentTable.equals("User")) {
          CreateUserView t = new CreateUserView(db, AdminUserView.this);
        } else if (currentTable.equals("Task")) {
          CreateTaskView t = new CreateTaskView(db, AdminUserView.this, currentUserID);
        } else if (currentTable.equals("Team")) {
          CreateTeamView t = new CreateTeamView(db, AdminUserView.this);
        }
      }
    });
    bottomPanel.add(addButton);
  }
    
  public void deleteButton() {
    deleteButton = new JButton(new AbstractAction("Delete"){
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
          updateJList();
        }
      }
    });
    bottomPanel.add(deleteButton);
  }

  public void modifyButton() {
    modifyButton = new JButton(new AbstractAction("Modify"){
      @Override
      public void actionPerformed(ActionEvent e) {
        if(currentTable.equals("User")) {
          ModifyUserView view = new ModifyUserView(db, AdminUserView.this, selectedID);
        } else if(currentTable.equals("Team")) {
          ModifyTeamView view = new ModifyTeamView(db, AdminUserView.this, selectedID);          
        } else if(currentTable.equals("Task")) {
          ModifyTaskView view = new ModifyTaskView(db, AdminUserView.this, selectedID);
        }
        updateJList();
      }
    });
    bottomPanel.add(modifyButton);
  }

  public void searchButton() {
    searchButton = new JButton(new AbstractAction("Search"){
      @Override
      public void actionPerformed(ActionEvent e) {
        if (currentTable.equals("User")) {
          SearchUserView t = new SearchUserView(db, AdminUserView.this);
        } else if (currentTable.equals("Task")) {
          SearchTaskView t = new SearchTaskView(db, AdminUserView.this);
        } else if (currentTable.equals("Team")) {
          SearchTeamView t = new SearchTeamView(db, AdminUserView.this);
        }
      }
    });
    bottomPanel.add(searchButton);
  }

  public void addLogoutButton() {
    logoutButton = new JButton(new AbstractAction("Logout") {
      @Override
      public void actionPerformed(ActionEvent e) {
        int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout?", JOptionPane.YES_NO_OPTION);
        
        if (reply == JOptionPane.YES_OPTION) {
          LoginView t = new LoginView(db);
          dispose();
        }
      }
    });
    bottomPanel.add(logoutButton);
  }

  public void createJList() {
    list.setModel(model);
    fillJList();
    list.setFont(list.getFont().deriveFont(15.0f));
    list.setLayoutOrientation(JList.VERTICAL);
    list.setFixedCellWidth(250);
    list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        selectedID = (list.getSelectedValue() == null) ? 0 : list.getSelectedValue().ID;
        displayText();
      }
    });

    list.setCellRenderer(new DefaultListCellRenderer() {
      @Override
      public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (currentTable.equals("Task")) {
          ListElement elem = (ListElement) value;
          Task selectedTask = db.query().tableIs(currentTable).taskIdIs(elem.ID).getOne();
          String colorHex = selectedTask.getColorHex();
           
          if (colorHex != null && !colorHex.equals("")) {  
            c.setBackground(Color.decode(colorHex));
          }
             
          if (isSelected) {
            c.setBackground(c.getBackground().darker());
          }
        }
        return c;
      } 
    });
    scrollPane.setViewportView(list);
    scrollPane.setBorder(new LineBorder(Color.black, 3));
    add(scrollPane, BorderLayout.WEST);
  }

  public void createTextArea() {
    textBox = new JTextArea();
    Font font = new Font("Serif", Font.BOLD, 15);
    textBox.setFont(font);
    textBox.setBorder(new LineBorder(Color.black, 3));
    textBox.setEditable(false);
    add(textBox, BorderLayout.CENTER);
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
                      "\nUser Productivity: " + selectedUser.getUserProductivity() +
                      "\nUser Type: " + selectedUser.getUserType());
    
    } else if (currentTable.equals("Task")) {
      Task selectedTask = db.query().tableIs("Task").taskIdIs(selectedID).getOne();

      User createdBy = db.query().tableIs("User").userIdIs(selectedTask.getCreatedById()).getOne();

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      String dueDate = (selectedTask.getDueDate() == null) ? "" : selectedTask.getDueDate().format(formatter);
      String recurringDays = (selectedTask.getRecurringDays() == 0) ? "" : Integer.toString(selectedTask.getRecurringDays());
      String completeOnDate = (selectedTask.getCompletedOn() == null) ? "" : selectedTask.getCompletedOn().format(formatter);

      String text = "Name: " + selectedTask.getTaskName() +
                    "\nTask Description: " + selectedTask.getTaskDesc() +
                    "\nID: " + selectedTask.getTaskId() +
                    "\nTask Status: " + selectedTask.getTaskStatus() +
                    "\nCreated By: " + createdBy.getUserName() + " (" + createdBy.getUserId() + ")" +
                    "\nAssigned To ID: " + selectedTask.getAssignedToId() + 
                    "\nCreated On: " + selectedTask.getCreatedOn().format(formatter) +
                    "\nDue Date: " + dueDate +
                    "\nComplete On: " + completeOnDate +
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

  public void clearJList() {
    selectedID = 0;
    model.clear();
  }

  public void updateJList() {
    clearJList();
    fillJList();
    textBox.setText("");
  }

  public void search(Query q) {
    clearJList();
    
    if (currentTable.equals("Task")) {
      ArrayList<Task> taskList = db.get(q);
      if (taskList != null) {
        for (Task task : taskList)
          model.addElement(new ListElement(task.getTaskName(), task.getTaskId()));
      }
    } else if (currentTable.equals("User")) {
      ArrayList<User> userList = db.get(q);
      if (userList != null) {
        for (User user : userList)
          model.addElement(new ListElement(user.getUserName(), user.getUserId()));
      }
    } else if (currentTable.equals("Team")) {
      ArrayList<Team> teamList = db.get(q);
      if (teamList != null) {
        for (Team team : teamList)
          model.addElement(new ListElement(team.getTeamName(), team.getTeamId()));
      }
    }
    textBox.setText("");
  }
}
