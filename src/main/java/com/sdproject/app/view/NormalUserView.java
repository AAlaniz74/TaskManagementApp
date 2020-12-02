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

public class NormalUserView extends JFrame implements UserView {

  private DatabaseWrapper db;
  private int currentUserID;
  
  private String currentTable;
  private int selectedID;

  private JButton addButton;
  private JButton deleteButton;
  private JButton searchButton;
  private JButton modifyButton;
  private JButton logoutButton;
  private JPanel topPanel, bottomPanel;
  private JComboBox<String> tables;
  private JTextArea textBox;
  private JList<ListElement> list = new JList<>();
  private DefaultListModel<ListElement> model = new DefaultListModel<>();
  private JScrollPane scrollPane = new JScrollPane();

  public NormalUserView(DatabaseWrapper db, int currentUserID) {
    this.db = db;
    this.currentUserID = currentUserID;
    this.currentTable = "Assigned Tasks";
    setTitle("User Menu");
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
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public void comboBoxes() {
    String[] tableList = {"Assigned Tasks", "Created Tasks", "Team"};

    tables = new JComboBox<String>(tableList);
    tables.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent event) {
        if (event.getStateChange() == ItemEvent.SELECTED) {
          currentTable = (String) event.getItem();
          if(currentTable.equals("Created Tasks"))
          {
            deleteButton.setVisible(true);
            modifyButton.setVisible(true);
            addButton.setVisible(true);
          }
          else
          {
            deleteButton.setVisible(false);
            modifyButton.setVisible(false);
            addButton.setVisible(false);
          }
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
        CreateTaskView t = new CreateTaskView(db, NormalUserView.this, currentUserID);
      }
    });
    bottomPanel.add(addButton);
    addButton.setVisible(false);
  }

  public void searchButton() {
    searchButton = new JButton(new AbstractAction("Search"){
      @Override
      public void actionPerformed(ActionEvent e) {
        if (currentTable.equals("User")) {
          SearchUserView t = new SearchUserView(db, NormalUserView.this);
        } else if (currentTable.equals("Task")) {
          SearchTaskView t = new SearchTaskView(db, NormalUserView.this);
        } else if (currentTable.equals("Team")) {
          SearchTeamView t = new SearchTeamView(db, NormalUserView.this);
        }
      }
    });
    bottomPanel.add(searchButton);
  }

  public void deleteButton() {
    deleteButton = new JButton(new AbstractAction("Delete"){
      @Override
      public void actionPerformed(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(new JFrame(), "Are you sure?");
        
        if (confirm == JOptionPane.YES_OPTION) {
            db.query().tableIs("Task").taskIdIs(selectedID).delete();
          }
          updateJList();
        }
    });
    bottomPanel.add(deleteButton);
    deleteButton.setVisible(false);
  }

  public void modifyButton() {

    modifyButton = new JButton(new AbstractAction("Modify"){
      @Override
      public void actionPerformed(ActionEvent e) {
        //ModifyTaskView t = new ModifyTaskView(db, taskName);
        updateJList();
      }
    });
    bottomPanel.add(modifyButton);
    modifyButton.setVisible(false);
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

    class SelectedListCellRenderer extends DefaultListCellRenderer {
      @Override
      public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
         Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
         if (currentTable.equals("Task")) {
           Task selectedTask = db.query().tableIs(currentTable).taskIdIs(selectedID).getOne();
           String colorHex = selectedTask.getColorHex();
           if (isSelected && colorHex != null) {  
             c.setBackground(Color.decode(colorHex));
           }
         }
         return c;
     }
    };
    list.setCellRenderer(new SelectedListCellRenderer());
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

