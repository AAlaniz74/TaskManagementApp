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
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Font;

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
  private GridBagConstraints gbc;
  private Font font, font2;

  public LoginView(DatabaseWrapper db) {
    this.db = db;
    panel = new JPanel(new GridBagLayout());
    gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 0, 10, 20);
    font = new Font("Ariel", Font.BOLD, 13);
    font2 = new Font("Ariel", Font.PLAIN, 15);

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
    
    add(panel);
    setTitle("Login to TaskApp");
    setPreferredSize(new Dimension(500, 230));
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void setGBC(int anchor, int fill, int gridx, int gridy, double weightx, double weighty){
    gbc.anchor = anchor;
    gbc.fill = fill;
    gbc.weightx = weightx;
    gbc.weighty = weighty;
    gbc.gridx = gridx;
    gbc.gridy = gridy;
  }

  private void addUserLabel() {
    userLabel = new JLabel();
    userLabel.setFont(font);
    userLabel.setText("User Name: ");
    userText = new JTextField();
    userText.setFont(font2);
    setGBC(GridBagConstraints.LINE_END, GridBagConstraints.VERTICAL, 0, 0, .1, .2);
    panel.add(userLabel, gbc);
    setGBC(GridBagConstraints.LINE_START, GridBagConstraints.BOTH, 1, 0, .9, .2);
    panel.add(userText, gbc);
  }

  private void addPassLabel() {
    passLabel = new JLabel();
    passLabel.setFont(font);
    passLabel.setText("Password: ");
    passText = new JPasswordField();
    passText.setFont(font2);
    setGBC(GridBagConstraints.LINE_END, GridBagConstraints.VERTICAL, 0, 1, .1, .2);
    panel.add(passLabel, gbc);
    setGBC(GridBagConstraints.LINE_START, GridBagConstraints.BOTH, 1, 1, .9, .2);
    panel.add(passText, gbc);
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
    setGBC(GridBagConstraints.BASELINE_LEADING, GridBagConstraints.CENTER, 1, 2, 1, 1);
    panel.add(submit, gbc);
  }
  
  private void addNewUserButton() {
    newUserButton = new JButton("Create New User");
    newUserButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        CreateUserView createView = new CreateUserView(db, LoginView.this);
      }
    });
    setGBC(GridBagConstraints.BASELINE, GridBagConstraints.NONE, 1, 2, .5, 1);
    panel.add(newUserButton, gbc);
  }

  public void updateJList() {}
  public void search(Query q) {}
}
