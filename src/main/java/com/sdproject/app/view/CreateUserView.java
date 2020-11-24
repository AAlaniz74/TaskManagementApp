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

import com.sdproject.app.model.User;
import com.sdproject.app.model.UserType;
import com.sdproject.app.database.DatabaseWrapper;
import com.sdproject.app.database.Query;

import com.sdproject.app.view.LoginView;

public class CreateUserView extends JFrame {

  private DatabaseWrapper db;
  private UserView view;

  private JPanel panel;
  private JLabel userLabel, passLabel, typeLabel;
  private JTextField userField;
  private JPasswordField passField;
  private JComboBox<String> userType;
  private JButton submit, cancel;

  public CreateUserView(DatabaseWrapper db, UserView view) {
    this.db = db;
    this.view = view;
    panel = new JPanel(new GridLayout(5, 2));
    panel.add(new JLabel("New user creation"));
    panel.add(new JLabel(""));
    addUserLabel();
    addPassLabel();
    addTypeLabel();
    addSubmitButton();
    addCancelButton();

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    add(panel, BorderLayout.CENTER);
    setTitle("New User creation");
    setSize(1000, 600);
    setVisible(true);
  }

  public void addUserLabel() {
    userLabel = new JLabel();
    userLabel.setText("Username: ");
    userField = new JTextField();
    panel.add(userLabel);
    panel.add(userField);
  }

  public void addPassLabel() {
    passLabel = new JLabel();
    passLabel.setText("Password: ");
    passField = new JPasswordField();
    panel.add(passLabel);
    panel.add(passField);
  }  

  public void addTypeLabel() {
    typeLabel = new JLabel();
    typeLabel.setText("Select user type: ");
		
    String[] typeList = new String[] {"NORMAL", "ADMIN"};
    userType = new JComboBox<String>(typeList);
    panel.add(typeLabel);
    panel.add(userType);
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
    panel.add(submit);
  }

  private void addCancelButton() {
    cancel = new JButton("CANCEL");
    cancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    panel.add(cancel);
  }

}
