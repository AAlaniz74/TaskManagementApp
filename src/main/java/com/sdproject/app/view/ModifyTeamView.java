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
import com.sdproject.app.model.Team;
import com.sdproject.app.model.User;

public class ModifyTeamView extends JFrame{

  private DatabaseWrapper db;
  private Team selectedTeam;
  private ArrayList<Integer> teamMembers;

  private JPanel panel, teamlistPanel;
  private JLabel nameLabel, teamlistLabel;
  private JTextField nameField;
  private JButton submitButton, cancelButton;
  private JScrollPane checkBoxScroll;

  public ModifyTeamView(DatabaseWrapper db, int selectedID) {
    this.db = db;
    this.selectedTeam = db.query().tableIs("Team").teamIdIs(selectedID).getOne();
    this.teamMembers = selectedTeam.getTeamMemberIDs();
    panel = new JPanel(new GridLayout(3,1));
    
    addNameTextBox();
    addTeamList();
    addSubmitButton();
    addCancelButton();
    add(panel, BorderLayout.CENTER);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("CreateTask");
    setSize(500, 300);
    setVisible(true);
  }

  public void addNameTextBox() {
    nameLabel = new JLabel("Team Name:");
    nameField = new JTextField(selectedTeam.getTeamName(), 100);
    panel.add(nameLabel);
    panel.add(nameField);
  }

  public void addTeamList(){
    teamlistLabel = new JLabel("Select All Team Members to add to team");
    teamlistPanel = new JPanel();
    teamlistPanel.setLayout(new BoxLayout(teamlistPanel, BoxLayout.Y_AXIS));

    ActionListener actionListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JCheckBox checkBox = (JCheckBox) e.getSource();
        int userID = (int) checkBox.getClientProperty("ID");
        if (checkBox.isSelected()) {
          teamMembers.add(userID);
        } else if (teamMembers.contains(userID)) {
          teamMembers.remove(Integer.valueOf(userID));
        }
      }
    };

    ArrayList<User> userList = db.query().tableIs("User").get();
    for (User user : userList) {
      JCheckBox newCheckBox = new JCheckBox(user.getUserName());
      newCheckBox.addActionListener(actionListener);
      newCheckBox.putClientProperty("ID", user.getUserId());

      if (teamMembers.contains(user.getUserId()))
        newCheckBox.setSelected(true);

      teamlistPanel.add(newCheckBox);
    }

    checkBoxScroll = new JScrollPane(teamlistPanel);
    checkBoxScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    panel.add(teamlistLabel);
    panel.add(checkBoxScroll);
  }

  public void addSubmitButton(){
    submitButton = new JButton("Submit");
    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (nameField.getText().equals("")) {
          JOptionPane.showMessageDialog(null, "Team must have a name");
        } else if (teamMembers.size() < 2) {
          JOptionPane.showMessageDialog(null, "Team must have at least 2 members");
        } else {
          db.query().tableIs("Team").teamIdIs(selectedTeam.getTeamId()).modifyTo().teamNameIs(nameField.getText()).allTeamMembersAre(teamMembers).modify();
          JOptionPane.showMessageDialog(null, "Team modified");
          dispose();
        }
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
