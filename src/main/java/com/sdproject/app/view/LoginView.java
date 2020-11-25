package com.sdproject.app.view;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import com.sdproject.app.database.DatabaseWrapper;
import com.sdproject.app.database.Query;
import com.sdproject.app.model.User;

public class LoginView extends JFrame implements UserView {

  private DatabaseWrapper db;

  private JPanel panel;
  private JLabel userLabel, passLabel;
  private JTextField userText;
  private JPasswordField passText;
  private JButton submit, newUserButton;

  public LoginView(DatabaseWrapper db) {
    this.db = db;
    panel = new JPanel(new GridLayout(4, 1));
    addUserLabel();
    addPassLabel();
    addSubmitButton();
    addNewUserButton();
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        db.serializeAll();
        System.exit(0);
      }
    });
    
    add(panel, BorderLayout.CENTER);
    setTitle("Login to TaskApp");
    setSize(500, 300);
    setVisible(true);
  }

  private void addUserLabel() {
    userLabel = new JLabel();
    userLabel.setText("User Name: ");
    userText = new JTextField();
    panel.add(userLabel);
    panel.add(userText);
  }

  private void addPassLabel() {
    passLabel = new JLabel();
    passLabel.setText("Password: ");
    passText = new JPasswordField();
    panel.add(passLabel);
    panel.add(passText);
  }

  private void addSubmitButton() {
    submit = new JButton("Submit");
    submit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String username = userText.getText();
        String password = passText.getText();
        User user = db.query().tableIs("User").userNameIs(username).userPassIs(password).getOne();
	      
        if (user == null) {
          JOptionPane.showMessageDialog(null, "Invalid username/password");
        } else {
          JOptionPane.showMessageDialog(null, "Logged in\nUser: " + user.getUserName());
          
          if (user.getUserType().toString().equals("ADMIN")) {
            AdminUserView adminView = new AdminUserView(db, user.getUserId());
          } else {
            NormalUserView normalView = new NormalUserView(db, user.getUserId());
          }
          dispose();
        }		
      }
    });
    panel.add(submit);
  }
  
  private void addNewUserButton() {
    newUserButton = new JButton("Create New User");
    newUserButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        CreateUserView createView = new CreateUserView(db, LoginView.this);
      }
    });
    panel.add(newUserButton);
  }

  public void updateJList() {}
  public void search(Query q) {}
}
