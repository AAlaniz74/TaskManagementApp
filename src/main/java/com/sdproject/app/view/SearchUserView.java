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

public class SearchUserView extends JFrame {

  private DatabaseWrapper db;
  private UserView view;

  private JPanel panel;
  private JLabel userLabel, passLabel, typeLabel;
  private JTextField userField;
  private JPasswordField passField;
  private JComboBox<String> userType;
  private JButton submitButton;

  public SearchUserView(DatabaseWrapper db, UserView view) {
    this.db = db;
    this.view = view;
    panel = new JPanel(new GridLayout(3, 1));
    addUserLabel();
    addTypeLabel();
    addSubmitButton();

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

  public void addTypeLabel() {
    typeLabel = new JLabel();
    typeLabel.setText("User type: ");
		
    String[] typeList = new String[] {"", "NORMAL", "ADMIN"};
    userType = new JComboBox<String>(typeList);
    panel.add(typeLabel);
    panel.add(userType);
  }

  public void addSubmitButton() {
    submitButton = new JButton("Search"); 
    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Query q = new Query().tableIs("User");

        if (!userField.getText().equals(""))
          q = q.userNameIs(userField.getText());
        
        String typeString = (String) userType.getSelectedItem();
        if (!typeString.equals(""))
          q = q.userTypeIs(typeString);

        view.search(q);
        dispose();
      }
    });
    panel.add(submitButton);
  }

}
