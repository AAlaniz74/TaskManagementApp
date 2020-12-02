package com.sdproject.app.view;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Font;

import com.sdproject.app.model.User;
import com.sdproject.app.model.UserType;
import com.sdproject.app.database.DatabaseWrapper;
import com.sdproject.app.database.Query;

import com.sdproject.app.view.LoginView;

public class CreateUserView extends JFrame {

  private DatabaseWrapper db;
  private UserView view;

  private JPanel topPanel, bottomPanel, centerPanel;
  private JLabel userLabel, passLabel, typeLabel;
  private JTextField userField;
  private JPasswordField passField;
  private JComboBox<String> userType;
  private JButton submit, cancel;
  private GridBagConstraints gbc;
  private Font font, font2;

  public CreateUserView(DatabaseWrapper db, UserView view) {
    this.db = db;
    this.view = view;

    topPanel = new JPanel(new FlowLayout());
    centerPanel = new JPanel(new GridBagLayout());
    bottomPanel = new JPanel(new FlowLayout());
    
    gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 0, 10, 20);
    font = new Font("Ariel", Font.BOLD, 13);
    font2 = new Font("Ariel", Font.PLAIN, 15);
    JLabel label = new JLabel("New User Creation");
    label.setFont(font);
    topPanel.add(label);

    addUserLabel();
    addPassLabel();
    addTypeLabel();
    addSubmitButton();
    addCancelButton();

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    add(topPanel, BorderLayout.NORTH);
    add(centerPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);
    setTitle("New User creation");
    setPreferredSize(new Dimension(500, 270));
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public void addUserLabel() {
    userLabel = new JLabel();
    userLabel.setFont(font);
    userLabel.setText("Username: ");
    userField = new JTextField();
    userField.setFont(font2);
    setGBC(GridBagConstraints.LINE_END, GridBagConstraints.VERTICAL, 0, 0, .1, .2);
    centerPanel.add(userLabel, gbc);
    setGBC(GridBagConstraints.LINE_START, GridBagConstraints.BOTH, 1, 0, .9, .2);
    centerPanel.add(userField, gbc);
  }

  public void addPassLabel() {
    passLabel = new JLabel();
    passLabel.setFont(font);
    passLabel.setText("Password: ");
    passField = new JPasswordField();
    passField.setFont(font2);
    setGBC(GridBagConstraints.LINE_END, GridBagConstraints.VERTICAL, 0, 1, .1, .2);
    centerPanel.add(passLabel, gbc);
    setGBC(GridBagConstraints.LINE_START, GridBagConstraints.BOTH, 1, 1, .9, .2);
    centerPanel.add(passField, gbc);
  }  

  public void addTypeLabel() {
    typeLabel = new JLabel();
    typeLabel.setFont(font);
    typeLabel.setText("Select user type: ");
		
    String[] typeList = new String[] {"NORMAL", "ADMIN"};
    userType = new JComboBox<String>(typeList);
    setGBC(GridBagConstraints.LINE_END, GridBagConstraints.VERTICAL, 0, 2, .1, .2);
    centerPanel.add(typeLabel, gbc);
    setGBC(GridBagConstraints.LINE_START, GridBagConstraints.NONE, 1, 2, .9, .2);
    centerPanel.add(userType, gbc);
  }

  public void addSubmitButton() {
    submit = new JButton("SUBMIT");
    submit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String newUserName = userField.getText();
	      String newUserPass = passField.getText();
        String newUserType = (String) userType.getSelectedItem();
        boolean nameTest = db.query().tableIs("User").userNameIs(newUserName).get().size() != 0;

        if (newUserName.equals("")) {
          JOptionPane.showMessageDialog(null, "User must have a user name");
        } else if (newUserPass.equals("")) {
          JOptionPane.showMessageDialog(null, "User must have a password");
        } else if (nameTest) {
          JOptionPane.showMessageDialog(null, "User name already taken.");
	      } else {
          db.query().tableIs("User").userNameIs(newUserName).userPassIs(newUserPass).userTypeIs(newUserType).insert();
          JOptionPane.showMessageDialog(null, "New user created.");
          view.updateJList();
          dispose();
	      }
      }
    });
    bottomPanel.add(submit);
  }

  private void addCancelButton() {
    cancel = new JButton("CANCEL");
    cancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    bottomPanel.add(cancel);
  }

  private void setGBC(int anchor, int fill, int gridx, int gridy, double weightx, double weighty){
    gbc.anchor = anchor;
    gbc.fill = fill;
    gbc.weightx = weightx;
    gbc.weighty = weighty;
    gbc.gridx = gridx;
    gbc.gridy = gridy;
  }

}
