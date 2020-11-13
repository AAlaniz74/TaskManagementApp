package com.sdproject.app.view;

import javax.swing.*;
import javax.swing.JButton;
import javax.xml.crypto.Data;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import com.sdproject.app.database.DatabaseWrapper;
import com.sdproject.app.database.Query;
import com.sdproject.app.model.Task;
import com.sdproject.app.model.TaskStatus;

public class CreateTaskView extends JFrame {

  private DatabaseWrapper db;
  private int currentUserID;

  private ArrayList<Integer> subtaskIDs;

  private JPanel panel;
  private JLabel nameLabel,descLabel, dueDateLabel, subtaskLabel, recurringLabel;
  private JTextField nameField;
  private JTextArea descField;
  private JFormattedTextField recurringField, dueDateField;
  private JButton submitButton, cancelButton;

  public CreateTaskView(DatabaseWrapper db, int currentUserID){
    this.db = db;
    this.currentUserID = currentUserID;
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

  }

  public void addDueDate(){
    dueDateLabel = new JLabel("Due date:");
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    dueDateField = new JFormattedTextField(format);
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

  }

  public void addSubmitButton(){
    submitButton = new JButton("Submit");
    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

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
