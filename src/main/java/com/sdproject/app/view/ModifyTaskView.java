package com.sdproject.app.view;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import com.sdproject.app.model.*;
import com.sdproject.app.database.*;

import java.util.List;
import java.util.Arrays;

import javax.swing.text.MaskFormatter;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ModifyTaskView extends JFrame {
    
  private DatabaseWrapper db;
  private Task selectedTask;
  private ArrayList<Integer> subtaskIDs;
  private int assignedToID;
  private UserView view;  

  private JPanel panel, subtaskPanel, assignedToPanel;
  private JLabel nameLabel, descLabel, statusLabel, dueDateLabel, subtaskLabel, recurringLabel, colorLabel, assignedToLabel;
  private JTextField nameField;
  private JTextArea descField;
  private JFormattedTextField recurringField, dueDateField;
  private JComboBox<ColorItem> colorType;
  private JComboBox<String> statusType;
  private JButton submitButton, cancelButton;
  private JScrollPane checkBoxScroll, radioBoxScroll;
  private ButtonGroup assignedButtonGroup; 
    
  public ModifyTaskView(DatabaseWrapper db, UserView view, int selectedID) {
    this.db = db;
    this.view = view;
    this.selectedTask = db.query().tableIs("Task").taskIdIs(selectedID).getOne();
    this.subtaskIDs = selectedTask.getSubtaskIDs();    
    
    panel = new JPanel(new GridLayout(10,1));
    addNameTextBox();
    addDescription();
    addTaskStatus();
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
    setSize(1000, 500);
    setVisible(true);
  }
 
  public void addNameTextBox(){
    nameLabel = new JLabel("Name:");
    nameField = new JTextField(selectedTask.getTaskName(), 100);
    panel.add(nameLabel);
    panel.add(nameField);
  }

  public void addDescription() {
    descLabel = new JLabel("Description:");
    descField = new JTextArea(selectedTask.getTaskDesc());
    panel.add(descLabel);
    panel.add(descField);
  }  

  public void addTaskStatus() {
    statusLabel = new JLabel("Task status");
    
    String[] statusList = new String[] { "IN_PROGRESS", "FINISHED" };
    statusType = new JComboBox<String>(statusList);
    panel.add(statusLabel);
    panel.add(statusType);
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
     
      if (subtaskIDs.contains(task.getTaskId()))
        newCheckBox.setSelected(true);

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
      
      if (user.getUserId() == selectedTask.getAssignedToId())
        newButton.setSelected(true);
      
      assignedButtonGroup.add(newButton);
      assignedToPanel.add(newButton);
    }

    ArrayList<Team> teamList = db.query().tableIs("Team").get();
    for (Team team : teamList) {
      JRadioButton newButton = new JRadioButton(team.getTeamName());
      newButton.addActionListener(actionListener);
      newButton.putClientProperty("ID", team.getTeamId());
      
      if (team.getTeamId() == selectedTask.getAssignedToId())
        newButton.setSelected(true);
      
      assignedButtonGroup.add(newButton);
      assignedToPanel.add(newButton);
    }

    radioBoxScroll = new JScrollPane(assignedToPanel);
    radioBoxScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    panel.add(assignedToLabel);
    panel.add(radioBoxScroll);

  }

  public void addDueDate(){
    dueDateLabel = new JLabel("Due date (use format 'yyyy-mm-dd'):");
    try {
      MaskFormatter formatter = new MaskFormatter("####-##-##");
      formatter.setValidCharacters("0123456789");
      formatter.setPlaceholderCharacter('_');
      dueDateField = new JFormattedTextField(formatter);
      if (selectedTask.getDueDate() != null) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dueDateField.setValue(selectedTask.getDueDate().format(df));
      }
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
    recurringField.setText(String.valueOf(selectedTask.getRecurringDays()));
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

        if (nameField.getText().equals("")) {
          JOptionPane.showMessageDialog(null, "Task must have name");
          return;
        } 
       
        int selectedID = selectedTask.getTaskId();
        String newTaskStatus = (String) statusType.getSelectedItem(); 
        Query q = new Query().tableIs("Task").taskIdIs(selectedID).modifyTo().taskNameIs(nameField.getText()).taskDescIs(descField.getText()).taskStatusIs(newTaskStatus);

        if (subtaskIDs.size() != 0)
          q = q.allSubtasksAre(subtaskIDs);
        
        ColorItem colorItem = (ColorItem) colorType.getSelectedItem();
        q = q.colorHexIs(colorItem.colorHex);

        if (dueDateField.isEditValid()) {
          if (!verifyDate(dueDateField.getText())) {
            return;
          }
          
          LocalDate compare = LocalDate.parse(dueDateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

          if (LocalDate.now().isAfter(compare)) {
            JOptionPane.showMessageDialog(null, "Due date must be in the future");
            return;
          } else {
            q = q.dueDateIs(dueDateField.getText());
          }
        }

        q.recurringDaysIs(Integer.parseInt(recurringField.getText()));

        db.modify(q);
        JOptionPane.showMessageDialog(null, "Task modified");
        view.updateJList();
        dispose();        
      }
    });
    panel.add(submitButton);
  }

  public boolean verifyDate(String dateStr) {
    String dateVal[] = dateStr.split("-");
    int[] date = new int[] { Integer.parseInt(dateVal[0]), Integer.parseInt(dateVal[1]), Integer.parseInt(dateVal[2]) }; 

    if (date[1] < 1 || date[1] > 12) {
      JOptionPane.showMessageDialog(null, "Invalid month value");
      return false;
    }

    if (date[2] < 0) {
      JOptionPane.showMessageDialog(null, "Day value must be greater than 1");
      return false;
    }

    Integer[] thirtyArr = new Integer[] { 4, 6, 9, 11 };
    Integer[] thirtyOneArr = new Integer[] { 1, 3, 5, 7, 8, 10, 12 };
    List<Integer> thirtyDays = Arrays.asList(thirtyArr);
    List<Integer> thirtyOneDays = Arrays.asList(thirtyOneArr);

    if (thirtyDays.contains(date[1]) && date[2] > 30) {
      JOptionPane.showMessageDialog(null, "This month has only 30 days");
      return false;
    }

    if (thirtyOneDays.contains(date[1]) && date[2] > 31) {
      JOptionPane.showMessageDialog(null, "This month has only 31 days");
      return false;
    }

    boolean leapYear = (((date[0] % 4 == 0) && (date[0] % 100 != 0)) || (date[0] % 400 == 0));

    if (date[1] == 2 && !leapYear && date[2] > 28) {
      JOptionPane.showMessageDialog(null, "This month has only 28 days");
      return false;
    }

    if (date[1] == 2 && leapYear && date[2] > 29) {
      JOptionPane.showMessageDialog(null, "This month has only 29 days");
      return false;
    }

    return true;
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
