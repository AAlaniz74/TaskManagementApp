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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Font;

import com.sdproject.app.database.DatabaseWrapper;
import static com.sdproject.app.view.GBConstraints.*;

public class CreateUserView extends JFrame {

  private DatabaseWrapper db;
  private UserView view;

  private JPanel topPanel, bottomPanel, centerPanel;
  private JLabel userLabel, passLabel, typeLabel;
  private JTextField userField;
  private JPasswordField passField;
  private JComboBox<String> userType;
  private JButton submit, cancel;
  private Font font, font2;

  public CreateUserView(DatabaseWrapper db, UserView view) {
    this.db = db;
    this.view = view;

    topPanel = new JPanel(new FlowLayout());
    centerPanel = new JPanel(new GridBagLayout());
    bottomPanel = new JPanel(new FlowLayout());
    
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
    centerPanel.add(userLabel, new GBConstraints(0,0).anchor(LINE_END).fill(VERTICAL).weight(.1, .2));
    centerPanel.add(userField, new GBConstraints(1,0).anchor(LINE_START).fill(BOTH).weight(.9, .2));
  }

  public void addPassLabel() {
    passLabel = new JLabel();
    passLabel.setFont(font);
    passLabel.setText("Password: ");
    passField = new JPasswordField();
    passField.setFont(font2);
    centerPanel.add(passLabel, new GBConstraints(0,1).anchor(LINE_END).fill(VERTICAL).weight(.1, .2));
    centerPanel.add(passField, new GBConstraints(1,1).anchor(LINE_START).fill(BOTH).weight(.9, .2));
  }  

  public void addTypeLabel() {
    typeLabel = new JLabel();
    typeLabel.setFont(font);
    typeLabel.setText("Select user type: ");
		
    String[] typeList = new String[] {"NORMAL", "ADMIN"};
    userType = new JComboBox<String>(typeList);
    centerPanel.add(typeLabel, new GBConstraints(0,2).anchor(LINE_END).fill(VERTICAL).weight(.1, .2));
    centerPanel.add(userType, new GBConstraints(1,2).anchor(LINE_START).fill(NONE).weight(.9, .2));
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
}
