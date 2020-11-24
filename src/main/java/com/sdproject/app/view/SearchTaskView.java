package com.sdproject.app.view;

import javax.swing.*;
import javax.swing.JButton;
import javax.xml.crypto.Data;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Arrays;

import javax.swing.text.MaskFormatter;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;
import java.text.ParseException;

import com.sdproject.app.database.DatabaseWrapper;
import com.sdproject.app.database.Query;
import com.sdproject.app.model.Task;
import com.sdproject.app.model.TaskStatus;
import com.sdproject.app.model.User;
import com.sdproject.app.model.Team;

public class SearchTaskView extends JFrame {

  private DatabaseWrapper db;
  private UserView view;

  private ArrayList<Integer> subtaskIDs;
  private int assignedToID;
  private int createdByID;

  private JPanel panel, subtaskPanel, assignedToPanel, createdByPanel;
  private JLabel nameLabel, subtaskLabel, colorLabel, assignedToLabel, createdByLabel;
  private JTextField nameField;
  private JComboBox<ColorItem> colorType;
  private JButton submitButton, cancelButton;
  private JScrollPane checkBoxScroll, radioBoxScroll, createdByScroll;
  private ButtonGroup assignedButtonGroup, createdByButtonGroup;

  public SearchTaskView(DatabaseWrapper db, UserView view) {
    this.db = db;
    this.view = view;
    subtaskIDs = new ArrayList<Integer>();
    panel = new JPanel(new GridLayout(5,1));
    addNameTextBox();
    addSubtask();
    addAssignedTo();
    addColorHex();
    addSubmitButton();
    addCancelButton();

    add(panel, BorderLayout.CENTER);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setTitle("CreateTask");
    setSize(1000, 500);
    setVisible(true);
  }

  public void addNameTextBox(){
    nameLabel = new JLabel("Enter Name:");
    nameField = new JTextField("", 100);
    panel.add(nameLabel);
    panel.add(nameField);
  }
    
  public void addSubtask(){
    subtaskLabel = new JLabel("Select SubTasks");
    subtaskPanel = new JPanel();
    subtaskPanel.setLayout(new BoxLayout(subtaskPanel, BoxLayout.Y_AXIS));

    ActionListener actionListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JCheckBox checkBox = (JCheckBox) e.getSource();
        int taskID = (int) checkBox.getClientProperty("ID");
        if (checkBox.isSelected()) {
          subtaskIDs.add(taskID);
        } else if (subtaskIDs.contains(taskID)) {
          subtaskIDs.remove(Integer.valueOf(taskID));
        }
      }
    };

    ArrayList<Task> taskList = db.query().tableIs("Task").get();
    for (Task task : taskList) {
      JCheckBox newCheckBox = new JCheckBox(task.getTaskName());
      newCheckBox.addActionListener(actionListener);
      newCheckBox.putClientProperty("ID", task.getTaskId());
      subtaskPanel.add(newCheckBox);
    }

    checkBoxScroll = new JScrollPane(subtaskPanel);
    checkBoxScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    panel.add(subtaskLabel);
    panel.add(checkBoxScroll);
  }

  public void addAssignedTo() {
    assignedToLabel = new JLabel("Assigned to:");
    assignedToPanel = new JPanel();
    assignedToPanel.setLayout(new BoxLayout(assignedToPanel, BoxLayout.Y_AXIS));
    assignedButtonGroup = new ButtonGroup();

    ActionListener actionListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JRadioButton selected = (JRadioButton) e.getSource();
        int selectedID = (int) selected.getClientProperty("ID");
        assignedToID = selectedID; 
      }
    };

    ArrayList<User> userList = db.query().tableIs("User").get();
    for (User user : userList) {
      JRadioButton newButton = new JRadioButton(user.getUserName());
      newButton.addActionListener(actionListener);
      newButton.putClientProperty("ID", user.getUserId()); 
      assignedButtonGroup.add(newButton);
      assignedToPanel.add(newButton);
    }

    ArrayList<Team> teamList = db.query().tableIs("Team").get();
    for (Team team : teamList) {
      JRadioButton newButton = new JRadioButton(team.getTeamName());
      newButton.addActionListener(actionListener);
      newButton.putClientProperty("ID", team.getTeamId());
      assignedButtonGroup.add(newButton);
      assignedToPanel.add(newButton);
    }

    radioBoxScroll = new JScrollPane(assignedToPanel);
    radioBoxScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    panel.add(assignedToLabel);
    panel.add(radioBoxScroll);

  }

  public void addCreatedBy() {
    createdByLabel = new JLabel("Created by:");
    createdByPanel = new JPanel();
    createdByPanel.setLayout(new BoxLayout(createdByPanel, BoxLayout.Y_AXIS));
    createdByButtonGroup = new ButtonGroup();

    ActionListener actionListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JRadioButton selected = (JRadioButton) e.getSource();
        int selectedID = (int) selected.getClientProperty("ID");
        createdByID = selectedID; 
      }
    };

    ArrayList<User> userList = db.query().tableIs("User").get();
    for (User user : userList) {
      JRadioButton newButton = new JRadioButton(user.getUserName());
      newButton.addActionListener(actionListener);
      newButton.putClientProperty("ID", user.getUserId());
      createdByButtonGroup.add(newButton);
      createdByPanel.add(newButton);
    }

    createdByScroll = new JScrollPane(createdByPanel);
    createdByScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    panel.add(createdByLabel);
    panel.add(createdByScroll);
  }

  public void addColorHex() {
    colorLabel = new JLabel("Add color:");
    ColorItem[] colorList = new ColorItem[] { new ColorItem("None", null), new ColorItem("Blue", "#0000ff"), new ColorItem("Red", "#ff0000"), new ColorItem("Green", "#00ff00") };
    colorType = new JComboBox<ColorItem>(colorList);
    panel.add(colorLabel);
    panel.add(colorType);
  }

  public void addSubmitButton(){
    submitButton = new JButton("Submit");
    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        Query q = new Query().tableIs("Task");

        if (!nameField.getText().equals("")) {
          q = q.taskNameIs(nameField.getText());
        } 
        
        if (subtaskIDs.size() != 0) {
          q = q.allSubtasksAre(subtaskIDs);
        }

        ColorItem colorItem = (ColorItem) colorType.getSelectedItem();
        
        if (colorItem.colorHex != null) {
          q = q.colorHexIs(colorItem.colorHex);
        }
 
        if (assignedButtonGroup.getSelection() != null)
          q = q.assignedToIdIs(assignedToID);

        if (createdByButtonGroup.getSelection() != null)
          q = q.createdByIdIs(createdByID);

        view.search(q);
        dispose();        
      }
    });

    panel.add(submitButton);
  }
  
  public void addCancelButton(){
    cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    panel.add(cancelButton);
  }

}
