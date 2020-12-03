package com.sdproject.app.view;

import javax.swing.*;
import javax.swing.JButton;
import javax.xml.crypto.Data;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Font;

import com.sdproject.app.database.DatabaseWrapper;
import com.sdproject.app.model.Task;
import com.sdproject.app.model.User;
import static com.sdproject.app.view.GBConstraints.*;

public class CreateTeamView extends JFrame{

  private DatabaseWrapper db;
  private UserView view;

  private ArrayList<Integer> teamMembers;
  
  private JPanel teamlistPanel,topPanel, bottomPanel, centerPanel;
  private JLabel nameLabel, teamlistLabel;
  private JTextField nameField;
  private JButton submitButton, cancelButton;
  private JScrollPane checkBoxScroll;
  private Font font, font2;

  public CreateTeamView(DatabaseWrapper db, UserView view) {
    this.db = db;
    this.view = view;
    teamMembers = new ArrayList<Integer>();
    topPanel = new JPanel(new FlowLayout());
    centerPanel = new JPanel(new GridBagLayout());
    bottomPanel = new JPanel(new FlowLayout());   

    font = new Font("Ariel", Font.BOLD, 13);
    font2 = new Font("Ariel", Font.PLAIN, 15);
    JLabel label = new JLabel("Create A Team");
    label.setFont(font);
    topPanel.add(label);

    addNameTextBox();
    addTeamList();
    addSubmitButton();
    addCancelButton();

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    add(topPanel, BorderLayout.NORTH);
    add(centerPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);
    setTitle("New Team");
    setPreferredSize(new Dimension(300, 450));
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public void addNameTextBox(){
    nameLabel = new JLabel("Enter Name:");
    nameLabel.setFont(font);
    nameField = new JTextField("Name",100);
    nameField.setFont(font2);
    centerPanel.add(nameLabel, new GBConstraints(0,0).anchor(LINE_START).fill(VERTICAL).weight(0,0).insets(10, 20, 0, 20));
    centerPanel.add(nameField, new GBConstraints(0,1).anchor(LINE_START).fill(BOTH).weight(0,.05).insets(0, 20, 10, 20));
  }

  public void addTeamList(){
    teamlistLabel = new JLabel("Select All Team Members to add to team");
    teamlistLabel.setFont(font);
    teamlistPanel = new JPanel();
    teamlistPanel.setLayout(new BoxLayout(teamlistPanel, BoxLayout.Y_AXIS));

    ActionListener actionListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JCheckBox checkBox = (JCheckBox) e.getSource();
        checkBox.setFont(font);
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
      newCheckBox.setFont(font);
      newCheckBox.addActionListener(actionListener);
      newCheckBox.putClientProperty("ID", user.getUserId());
      teamlistPanel.add(newCheckBox);
    }

    checkBoxScroll = new JScrollPane(teamlistPanel);
    checkBoxScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    centerPanel.add(teamlistLabel, new GBConstraints(0,2).anchor(LINE_START).fill(VERTICAL).weight(0,.1).insets(20, 20, 0, 20));
    centerPanel.add(checkBoxScroll, new GBConstraints(0,3).anchor(LINE_START).fill(BOTH).weight(0, .8).insets(0, 20, 10, 20));
  }


  public void addSubmitButton() {
    submitButton = new JButton("Submit");
    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (nameField.getText().equals("")) {
          JOptionPane.showMessageDialog(null, "Team must have a name");
        } else if (teamMembers.size() < 2) {
          JOptionPane.showMessageDialog(null, "Team must have at least 2 members");
        } else {
          db.query().tableIs("Team").teamNameIs(nameField.getText()).allTeamMembersAre(teamMembers).insert();
          view.updateJList();
          dispose();
        }
        
      }
    });
    bottomPanel.add(submitButton);
  }
    
  public void addCancelButton() {
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
