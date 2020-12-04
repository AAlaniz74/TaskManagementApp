package com.sdproject.app.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import com.sdproject.app.model.*;
import com.sdproject.app.database.*;
import java.util.List;
import java.util.Arrays;
import javax.swing.text.MaskFormatter;
import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Font;

import static com.sdproject.app.view.GBConstraints.*;

public class ModifyTaskView extends JFrame {
    
  private DatabaseWrapper db;
  private Task selectedTask;
  private ArrayList<Integer> subtaskIDs;
  private int assignedToID;
  private UserView view;  

  private JPanel topPanel, bottomPanel, leftCenterPanel, upperleftPanel, subtaskPanel, assignedToPanel;
  private JLabel nameLabel, descLabel, statusLabel, dueDateLabel, subtaskLabel, recurringLabel, colorLabel, assignedToLabel;
  private JTextField nameField;
  private JTextArea descField;
  private JFormattedTextField recurringField, dueDateField;
  private JComboBox<ColorItem> colorType;
  private JComboBox<String> statusType;
  private JButton submitButton, cancelButton;
  private JScrollPane checkBoxScroll, radioBoxScroll;
  private ButtonGroup assignedButtonGroup;
  private Font font, font2;
    
  public ModifyTaskView(DatabaseWrapper db, UserView view, int selectedID) {
    this.db = db;
    this.view = view;
    this.selectedTask = db.query().tableIs("Task").taskIdIs(selectedID).getOne();
    this.subtaskIDs = selectedTask.getSubtaskIDs();    
    
    topPanel = new JPanel(new FlowLayout());
    leftCenterPanel = new JPanel(new GridBagLayout());
    upperleftPanel = new JPanel(new GridBagLayout());
    bottomPanel = new JPanel(new FlowLayout());

    font = new Font("Ariel", Font.BOLD, 13);
    font2 = new Font("Ariel", Font.PLAIN, 15);
    JLabel label = new JLabel("Modify the Task");
    label.setFont(font);
    topPanel.add(label);

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

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    add(topPanel, BorderLayout.NORTH);
    leftCenterPanel.add(upperleftPanel, new GBConstraints(0,1).fill(BOTH).insets(0, 20, 0, 0).weight(0, .1));
    add(leftCenterPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);
    setTitle("ModifyTask");
    setPreferredSize(new Dimension(740, 470));
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }
 
  public void addNameTextBox(){
    nameLabel = new JLabel("Name:");
    nameLabel.setFont(font);
    nameField = new JTextField(selectedTask.getTaskName());
    nameField.setFont(font2);
    upperleftPanel.add(nameLabel, new GBConstraints(0,0).anchor(LINE_END).fill(VERTICAL).insets(0, 0, 0, 5));
    upperleftPanel.add(nameField, new GBConstraints(1,0).anchor(LINE_START).fill(BOTH).weight(1,.3).insets(0, 0, 10, 0));
  }

  public void addDescription() {
    descLabel = new JLabel("Description:");
    descLabel.setFont(font);
    descField = new JTextArea(selectedTask.getTaskDesc());
    descField.setFont(font2);
    leftCenterPanel.add(descLabel, new GBConstraints(0,3).anchor(LINE_START).fill(NONE).insets(0, 20, 0, 5));
    leftCenterPanel.add(descField, new GBConstraints(0,4).anchor(LINE_START).fill(BOTH).weight(.5,.5).insets(0, 20, 0, 0));
  }  

  public void addTaskStatus() {
    statusLabel = new JLabel("Task status:");
    statusLabel.setFont(font);
    String[] statusList = new String[] { "IN_PROGRESS", "FINISHED" };
    statusType = new JComboBox<String>(statusList);
    upperleftPanel.add(statusLabel, new GBConstraints(0,4).anchor(LINE_END).fill(VERTICAL).insets(0, 0, 0, 5));
    upperleftPanel.add(statusType, new GBConstraints(1,4).anchor(LINE_START).fill(BOTH).weight(0,.1).insets(0, 0, 0, 0));
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
     
      if (subtaskIDs.contains(task.getTaskId()))
        newCheckBox.setSelected(true);

      subtaskPanel.add(newCheckBox);
    }

    checkBoxScroll = new JScrollPane(subtaskPanel);
    checkBoxScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    leftCenterPanel.add(subtaskLabel, new GBConstraints(1,0).anchor(LINE_START).fill(NONE).insets(0, 20, 0, 20));
    leftCenterPanel.add(checkBoxScroll, new GBConstraints(1,1).anchor(LINE_START).fill(BOTH).weight(.3,0).insets(0, 20, 0, 20));
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
      
      if (user.getUserId() == selectedTask.getAssignedToId())
        newButton.setSelected(true);
      
      assignedButtonGroup.add(newButton);
      assignedToPanel.add(newButton);
    }

    ArrayList<Team> teamList = db.query().tableIs("Team").get();
    for (Team team : teamList) {
      JRadioButton newButton = new JRadioButton(team.getTeamName());
      newButton.setFont(font);
      newButton.addActionListener(actionListener);
      newButton.putClientProperty("ID", team.getTeamId());
      
      if (team.getTeamId() == selectedTask.getAssignedToId())
        newButton.setSelected(true);
      
      assignedButtonGroup.add(newButton);
      assignedToPanel.add(newButton);
    }

    radioBoxScroll = new JScrollPane(assignedToPanel);
    radioBoxScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    leftCenterPanel.add(assignedToLabel, new GBConstraints(1,3).anchor(LINE_START).fill(NONE).insets(0, 20, 0, 20));
    leftCenterPanel.add(radioBoxScroll, new GBConstraints(1,4).anchor(LINE_START).fill(BOTH).insets(0, 20, 0, 20));

  }

  public void addDueDate(){
    dueDateLabel = new JLabel("Due date (use format 'yyyy-mm-dd'):");
    dueDateLabel.setFont(font);
    try {
      MaskFormatter formatter = new MaskFormatter("####-##-##");
      formatter.setValidCharacters("0123456789");
      formatter.setPlaceholderCharacter('_');
      dueDateField = new JFormattedTextField(formatter);
      dueDateField.setFont(font2);
      if (selectedTask.getDueDate() != null) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dueDateField.setValue(selectedTask.getDueDate().format(df));
      }
    } catch (ParseException e) {
      System.err.println("Mask formatter error: " + e.getMessage());
    }

    upperleftPanel.add(dueDateLabel, new GBConstraints(0,1).anchor(LINE_END).fill(VERTICAL).insets(0, 0, 0, 5));
    upperleftPanel.add(dueDateField, new GBConstraints(1,1).anchor(LINE_START).fill(VERTICAL).weight(0,.3).insets(0, 0, 10, 0));    
  }

  public void addRecurringDays() {
    recurringLabel = new JLabel("Days of Task recurring(Zero means none):");
    recurringLabel.setFont(font);
    NumberFormat format = NumberFormat.getInstance();
    format.setGroupingUsed(false);
    NumberFormatter dayFormatter = new NumberFormatter(format);
    dayFormatter.setValueClass(Integer.class);
    dayFormatter.setMinimum(0);
    dayFormatter.setMaximum(Integer.MAX_VALUE);
    dayFormatter.setAllowsInvalid(false);
    dayFormatter.setOverwriteMode(true);
    dayFormatter.setCommitsOnValidEdit(true);

    recurringField = new JFormattedTextField(dayFormatter);
    recurringField.setFont(font2);
    recurringField.setText(String.valueOf(selectedTask.getRecurringDays()));
    upperleftPanel.add(recurringLabel, new GBConstraints(0,2).anchor(LINE_END).fill(VERTICAL).insets(0, 0, 0, 5));
    upperleftPanel.add(recurringField, new GBConstraints(1,2).anchor(LINE_START).fill(BOTH).weight(0,.1).insets(0, 0, 10, 0));
  }

  public void addColorHex() {
    colorLabel = new JLabel("Add color:");
    colorLabel.setFont(font);
    ColorItem[] colorList = new ColorItem[] { new ColorItem("None", null), new ColorItem("Blue", "#bae1ff"), new ColorItem("Red", "#ffb3ba"), new ColorItem("Orange", "#ffdfba"), new ColorItem("Green", "#baffc9"), new ColorItem("Purple", "#e0bbe4") };
    colorType = new JComboBox<ColorItem>(colorList);
    upperleftPanel.add(colorLabel, new GBConstraints(0,3).anchor(LINE_END).fill(VERTICAL).insets(0, 0, 0, 5));
    upperleftPanel.add(colorType, new GBConstraints(1,3).anchor(LINE_START).fill(BOTH).weight(0,.1).insets(0, 0, 10, 0));
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

        if (newTaskStatus.equals("FINISHED") && !selectedTask.getTaskStatus().equals("FINISHED"))
          selectedTask.setCompletedOn(LocalDate.now());

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
    bottomPanel.add(submitButton);
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
    bottomPanel.add(cancelButton);
  }

}
