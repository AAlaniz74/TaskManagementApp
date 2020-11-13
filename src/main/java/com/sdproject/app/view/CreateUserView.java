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

  private JPanel panel;
  private JLabel user_label, pass_label, type_label;
  private JTextField user_text;
  private JPasswordField pass_text;
  private JComboBox<String> user_type;
  private JButton submit;

  public CreateUserView(DatabaseWrapper db) {
    this.db = db;
    panel = new JPanel(new GridLayout(5, 1));
    panel.add(new JLabel("New user creation"));
    panel.add(new JLabel(""));
    addUserLabel();
    addPassLabel();
    addTypeLabel();
    addSubmitButton();

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    add(panel, BorderLayout.CENTER);
    setTitle("New User creation");
    setSize(600, 600);
    setVisible(true);
  }

  public void addUserLabel() {
    user_label = new JLabel();
    user_label.setText("Username: ");
    user_text = new JTextField();
    panel.add(user_label);
    panel.add(user_text);
  }

  public void addPassLabel() {
    pass_label = new JLabel();
    pass_label.setText("Password: ");
    pass_text = new JPasswordField();
    panel.add(pass_label);
    panel.add(pass_text);
  }  

  public void addTypeLabel() {
    type_label = new JLabel();
    type_label.setText("Select user type: ");
		
    String[] typeList = new String[] {"NORMAL", "ADMIN"};
    user_type = new JComboBox<String>(typeList);
    panel.add(type_label);
    panel.add(user_type);
  }

  public void addSubmitButton() {
    submit = new JButton("SUBMIT");
    submit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String newUserName = user_text.getText();
	      String newUserPass = pass_text.getText();
        String newUserType = (String) user_type.getSelectedItem();
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
	        dispose();
	      }
      }
    });
    panel.add(submit);
  }

}
