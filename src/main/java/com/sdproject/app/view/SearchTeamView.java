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
import com.sdproject.app.model.User;

public class SearchTeamView extends JFrame{

  private DatabaseWrapper db;
  private UserView view;

  private ArrayList<Integer> teamMembers;
  
  private JPanel panel, teamlistPanel;
  private JLabel nameLabel, teamlistLabel;
  private JTextField nameField;
  private JButton submitButton, cancelButton;
  private JScrollPane checkBoxScroll;

  public SearchTeamView(DatabaseWrapper db, UserView view) {
    this.db = db;
    this.view = view;
    teamMembers = new ArrayList<Integer>();
    panel = new JPanel(new GridLayout(3,1));
    addNameTextBox();
    addTeamList();
    addSubmitButton();
    addCancelButton();
    add(panel, BorderLayout.CENTER);

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setTitle("Create Task");
    setSize(1000, 300);
    setVisible(true);
  }

  public void addNameTextBox(){
    nameLabel = new JLabel("Name:");
    nameField = new JTextField("",100);
    panel.add(nameLabel);
    panel.add(nameField);
  }

  public void addTeamList(){
    teamlistLabel = new JLabel("Team Members:");
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
      teamlistPanel.add(newCheckBox);
    }

    checkBoxScroll = new JScrollPane(teamlistPanel);
    checkBoxScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    panel.add(teamlistLabel);
    panel.add(checkBoxScroll);
  }


  public void addSubmitButton() {
    submitButton = new JButton("Submit");
    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Query q = new Query().tableIs("Team");

        if (!nameField.getText().equals(""))
          q = q.teamNameIs(nameField.getText());
          
        if (teamMembers.size() > 0)
          q = q.allTeamMembersAre(teamMembers); 

        view.search(q);
        dispose();
      }
    });
    panel.add(submitButton);
  }
    
  public void addCancelButton() {
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
