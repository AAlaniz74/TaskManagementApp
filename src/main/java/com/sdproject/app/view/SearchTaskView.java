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
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Font;

import com.sdproject.app.database.DatabaseWrapper;
import com.sdproject.app.database.Query;
import com.sdproject.app.model.Task;
import com.sdproject.app.model.TaskStatus;
import com.sdproject.app.model.User;
import com.sdproject.app.model.Team;
import static com.sdproject.app.view.GBConstraints.*;

public class SearchTaskView extends JFrame {

  private DatabaseWrapper db;
  private UserView view;

  private ArrayList<Integer> subtaskIDs;
  private int assignedToID;
  private int createdByID;

  private JPanel topPanel, bottomPanel, centerPanel, subtaskPanel, assignedToPanel, createdByPanel;
  private JLabel nameLabel, subtaskLabel, colorLabel, assignedToLabel, createdByLabel;
  private JTextField nameField;
  private JComboBox<ColorItem> colorType;
  private JButton submitButton, cancelButton;
  private JScrollPane checkBoxScroll, radioBoxScroll, createdByScroll;
  private ButtonGroup assignedButtonGroup, createdByButtonGroup;
  private Font font, font2;

  public SearchTaskView(DatabaseWrapper db, UserView view) {
    this.db = db;
    this.view = view;
    subtaskIDs = new ArrayList<Integer>();

    topPanel = new JPanel(new FlowLayout());
    centerPanel = new JPanel(new GridBagLayout());
    bottomPanel = new JPanel(new FlowLayout());

    font = new Font("Ariel", Font.BOLD, 13);
    font2 = new Font("Ariel", Font.PLAIN, 15);
    JLabel label = new JLabel("Search for a Task");
    label.setFont(font);
    topPanel.add(label);

    addNameTextBox();
    addSubtask();
    addAssignedTo();
    addCreatedBy();
    addColorHex();
    addSubmitButton();
    addCancelButton();

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    add(topPanel, BorderLayout.NORTH);
    add(centerPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);
    setTitle("SearchTask");
    setPreferredSize(new Dimension(740, 470));
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public void addNameTextBox(){
    nameLabel = new JLabel("Enter Name:");
    nameLabel.setFont(font);
    nameField = new JTextField("");
    nameField.setFont(font2);
    centerPanel.add(nameLabel, new GBConstraints(0,0).anchor(LINE_START).fill(NONE).insets(20, 20, 0, 5));
    centerPanel.add(nameField, new GBConstraints(0,1).anchor(LINE_START).fill(HORIZONTAL).weight(.3,0).insets(0, 20, 20, 5));
  }
    
  public void addSubtask(){
    subtaskLabel = new JLabel("Select SubTasks");
    subtaskLabel.setFont(font);
    subtaskPanel = new JPanel();
    subtaskPanel.setLayout(new BoxLayout(subtaskPanel, BoxLayout.Y_AXIS));

    ActionListener actionListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JCheckBox checkBox = (JCheckBox) e.getSource();
        checkBox.setFont(font);
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
      newCheckBox.setFont(font);
      newCheckBox.addActionListener(actionListener);
      newCheckBox.putClientProperty("ID", task.getTaskId());
      subtaskPanel.add(newCheckBox);
    }

    checkBoxScroll = new JScrollPane(subtaskPanel);
    checkBoxScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    centerPanel.add(subtaskLabel , new GBConstraints(0,2).anchor(LINE_START).fill(NONE).insets(0, 20, 0, 5));
    centerPanel.add(checkBoxScroll, new GBConstraints(0,3).anchor(LINE_START).fill(BOTH).insets(0, 20, 0, 5));
  }

  public void addAssignedTo() {
    assignedToLabel = new JLabel("Assigned to:");
    assignedToLabel.setFont(font);
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
      newButton.setFont(font);
      newButton.addActionListener(actionListener);
      newButton.putClientProperty("ID", user.getUserId()); 
      assignedButtonGroup.add(newButton);
      assignedToPanel.add(newButton);
    }

    ArrayList<Team> teamList = db.query().tableIs("Team").get();
    for (Team team : teamList) {
      JRadioButton newButton = new JRadioButton(team.getTeamName());
      newButton.setFont(font);
      newButton.addActionListener(actionListener);
      newButton.putClientProperty("ID", team.getTeamId());
      assignedButtonGroup.add(newButton);
      assignedToPanel.add(newButton);
    }

    radioBoxScroll = new JScrollPane(assignedToPanel);
    radioBoxScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    centerPanel.add(assignedToLabel, new GBConstraints(1,2).anchor(LINE_START).fill(NONE).insets(0, 20, 0, 5));
    centerPanel.add(radioBoxScroll, new GBConstraints(1,3).anchor(LINE_START).fill(BOTH).weight(.3,.5).insets(0, 20, 0, 5));

  }

  public void addCreatedBy() {
    createdByLabel = new JLabel("Created by:");
    createdByLabel.setFont(font);
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
      newButton.setFont(font);
      newButton.addActionListener(actionListener);
      newButton.putClientProperty("ID", user.getUserId());
      createdByButtonGroup.add(newButton);
      createdByPanel.add(newButton);
    }

    createdByScroll = new JScrollPane(createdByPanel);
    createdByScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    centerPanel.add(createdByLabel, new GBConstraints(2,2).anchor(LINE_START).fill(NONE).insets(0, 20, 0, 5));
    centerPanel.add(createdByScroll, new GBConstraints(2,3).anchor(LINE_START).fill(BOTH).weight(.3,0).insets(0, 20, 0, 5));
  }

  public void addColorHex() {
    colorLabel = new JLabel("Add color:");
    colorLabel.setFont(font);
    ColorItem[] colorList = new ColorItem[] { new ColorItem("None", null), new ColorItem("Blue", "#bae1ff"), new ColorItem("Red", "#ffb3ba"), new ColorItem("Orange", "#ffdfba"), new ColorItem("Green", "#baffc9"), new ColorItem("Purple", "#e0bbe4") };
    colorType = new JComboBox<ColorItem>(colorList);
    centerPanel.add(colorLabel, new GBConstraints(1,0).anchor(LINE_START).fill(NONE).insets(20, 20, 0, 5));
    centerPanel.add(colorType, new GBConstraints(1,1).anchor(LINE_START).fill(HORIZONTAL).insets(0, 20, 20, 5));
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

    bottomPanel.add(submitButton);
  }
  
  public void addCancelButton(){
    cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    bottomPanel.add(cancelButton);
  }

}
