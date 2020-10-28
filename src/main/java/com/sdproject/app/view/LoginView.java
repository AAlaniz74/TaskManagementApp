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
import java.util.ArrayList;

import com.sdproject.app.database.DatabaseWrapper;
import com.sdproject.app.database.Query;
import com.sdproject.app.model.User;

public class LoginView extends JFrame {

  private DatabaseWrapper db;

  private JPanel panel;
  private JLabel user_label, pass_label;
  private JTextField user_text;
  private JPasswordField pass_text;
  private JButton submit, newUserButton;

  public LoginView(DatabaseWrapper db) {
    this.db = db;
    panel = new JPanel(new GridLayout(4, 1));
    addUserLabel();
    addPassLabel();
    addSubmitButton();
    addNewUserButton();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(panel, BorderLayout.CENTER);
    setTitle("Login to TaskApp");
    setSize(500, 300);
    setVisible(true);
  }

  private void addUserLabel() {
    user_label = new JLabel();
    user_label.setText("User Name: ");
    user_text = new JTextField();
    panel.add(user_label);
    panel.add(user_text);
  }

  private void addPassLabel() {
    pass_label = new JLabel();
    pass_label.setText("Password: ");
    pass_text = new JPasswordField();
    panel.add(pass_label);
    panel.add(pass_text);
  }

  private void addSubmitButton() {
    submit = new JButton("Submit");
    submit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String username = user_text.getText();
        String password = pass_text.getText();
        ArrayList<User> user = db.query().tableIs("User").userNameIs(username).userPassIs(password).get();
	if (user.size() != 1) {
          JOptionPane.showMessageDialog(null, "Invalid username/password");
        } else {
          JOptionPane.showMessageDialog(null, "Logged in\nUser: " + user.get(0).getUserName() + "\nPass: " + user.get(0).getUserPass());
          mainView view = new mainView(db);
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
        CreateUserView createView = new CreateUserView(db);
        dispose();	
      }
    });
    panel.add(newUserButton);
  }

}
