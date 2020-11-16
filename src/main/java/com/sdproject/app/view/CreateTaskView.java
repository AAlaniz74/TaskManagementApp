package com.sdproject.app.view;

import javax.swing.*;
import javax.swing.JButton;
import javax.xml.crypto.Data;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

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

public class CreateTaskView extends JFrame {

  class ColorItem {
    String colorName;
    String colorHex;

    ColorItem(String colorName, String colorHex) {
      this.colorName = colorName;
      this.colorHex = colorHex;
    }

    @Override
    public String toString() {
      return this.colorName;
    }
  }

  private DatabaseWrapper db;
  private int currentUserID;

  private ArrayList<Integer> subtaskIDs;
  private int assignedToID;

  private JPanel panel, subtaskPanel, assignedToPanel;
  private JLabel nameLabel, descLabel, dueDateLabel, subtaskLabel, recurringLabel, colorLabel, assignedToLabel;
  private JTextField nameField;
  private JTextArea descField;
  private JFormattedTextField recurringField, dueDateField;
  private JComboBox<ColorItem> colorType;
  private JButton submitButton, cancelButton;
  private JScrollPane checkBoxScroll;
  private ButtonGroup assignedButtonGroup;

  public CreateTaskView(DatabaseWrapper db, int currentUserID){
    this.db = db;
    this.currentUserID = currentUserID;
    this.assignedToID = currentUserID;
    subtaskIDs = new ArrayList<Integer>();
    panel = new JPanel(new GridLayout(8,1));
    addNameTextBox();
    addDescription();
    addSubtask();
    addAssignedTo();
    addDueDate();
    addRecurringDays();
    addColorHex();
    addSubmitButton();
    addCancelButton();

    add(panel, BorderLayout.CENTER);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setTitle("CreateTask");
    setSize(500, 700);
    setVisible(true);
  }

  public void addNameTextBox(){
    nameLabel = new JLabel("Enter Name:");
    nameField = new JTextField("Name",100);
    panel.add(nameLabel);
    panel.add(nameField);
  }

  public void addDescription(){
    descLabel = new JLabel("Enter Description:");
    descField = new JTextArea(10,100 );
    panel.add(descLabel);
    panel.add(descField);
  }
    
  public void addSubtask(){
    subtaskLabel = new JLabel("Select SubTasks");
    subtaskPanel = new JPanel();
    checkBoxScroll = new JScrollPane(subtaskPanel);

    ActionListener actionListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JCheckBox checkBox = (JCheckBox) e.getSource();
        int taskID = (int) checkBox.getClientProperty("ID");
        if (checkBox.isSelected()) {
          subtaskIDs.add(taskID);
        } else if (subtaskIDs.contains(taskID)) {
          subtaskIDs.remove(taskID);
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

    panel.add(subtaskLabel);
    panel.add(checkBoxScroll);
  }

  public void addAssignedTo() {
    assignedToLabel = new JLabel("Assigned to:");
    assignedToPanel = new JPanel();
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

    panel.add(assignedToLabel);
    panel.add(new JScrollPane(assignedToPanel));

  }

  public void addDueDate(){
    dueDateLabel = new JLabel("Due date (use format 'yyyy-mm-dd'):");
    try {
      MaskFormatter formatter = new MaskFormatter("####-##-##");
      formatter.setValidCharacters("0123456789");
      formatter.setPlaceholderCharacter('_');
      dueDateField = new JFormattedTextField(formatter);
    } catch (ParseException e) {
      System.err.println("Mask formatter error: " + e.getMessage());
    }

    panel.add(dueDateLabel);
    panel.add(dueDateField);  
  }

  public void addRecurringDays() {
    recurringLabel = new JLabel("If recurring task, how many days should it recur? (Zero means non-recurring)");
    NumberFormat format = NumberFormat.getInstance();
    format.setGroupingUsed(false);
    NumberFormatter dayFormatter = new NumberFormatter(format);
    dayFormatter.setValueClass(Integer.class);
    dayFormatter.setMinimum(0);
    dayFormatter.setMaximum(Integer.MAX_VALUE);
    dayFormatter.setAllowsInvalid(false);
    dayFormatter.setCommitsOnValidEdit(true);

    recurringField = new JFormattedTextField(dayFormatter);
    recurringField.setText("0");
    panel.add(recurringLabel);
    panel.add(recurringField);
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

        if (nameField.getText().equals("")) {
          JOptionPane.showMessageDialog(null, "Task must have name");
          return;
        } 
        
        q = q.taskNameIs(nameField.getText()).taskDescIs(descField.getText()).createdByIdIs(currentUserID);

        if (subtaskIDs.size() != 0)
          q = q.allSubtasksAre(subtaskIDs);
        
        ColorItem colorItem = (ColorItem) colorType.getSelectedItem();
        q = q.colorHexIs(colorItem.colorHex);

        if (!dueDateField.getText().equals(""))
          q = q.dueDateIs(dueDateField.getText());

        q.recurringDaysIs(Integer.parseInt(recurringField.getText()));

        db.insert(q);
        JOptionPane.showMessageDialog(null, "New task created!");
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
