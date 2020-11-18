package com.sdproject.app.view;

import com.sdproject.app.database.*;
import com.sdproject.app.model.*;
import java.awt.event.*;
import javax.swing.*;

public class ModifyUserView extends JFrame{
    
  private static final long serialVersionUID = 1L;
  private DatabaseWrapper db;
  private User selectedUser;

  private JPanel panel;
  private JLabel nameLabel, passLabel, userType;
  private JTextField nameField;
  private JPasswordField passField;
  private JComboBox<String> typeList;
  private JButton submitButton;
  private JButton cancelButton;

  public ModifyUserView(DatabaseWrapper db, int selectedID) {
    this.db = db;
    this.selectedUser = db.query().tableIs("User").userIdIs(selectedID).getOne();
    panel = new JPanel();
    setSize(300, 250);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    add(panel);
    panel.setLayout(null);

    addNameField();
    addPassField();
    addTypeField();       
    submitButton();
    cancelButton();
    setVisible(true);
  }
    
  private void addNameField() {
    nameLabel = new JLabel("Username:");
    nameLabel.setBounds(10, 20, 90, 25);
        
    nameField = new JTextField();
    nameField.setText(selectedUser.getUserName());
    nameField.setBounds(90, 20, 140, 25);

    panel.add(nameLabel);
    panel.add(nameField);
  }
    
  private void addPassField() {
    passLabel = new JLabel("Password:");
    passLabel.setBounds(10, 50, 90, 25);

    passField = new JPasswordField();
    passField.setText(selectedUser.getUserPass());
    passField.setBounds(90, 50, 140, 25);

    panel.add(passLabel);
    panel.add(passField);
  }
    
  private void addTypeField() {
    userType = new JLabel();
    userType.setText("User Type: ");
    userType.setBounds(10, 80, 90, 25);

    typeList = new JComboBox<String>(new String[] {"NORMAL", "ADMIN"});
    typeList.setBounds(90, 80, 140, 25);

    panel.add(userType);
    panel.add(typeList);
  }
    
  public void submitButton() {
    submitButton = new JButton("Submit");
    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String pass = passField.getText();
        String userType = (String) typeList.getSelectedItem();
        boolean nameTest = db.query().tableIs("User").userNameIs(name).get().size() != 0;

        if (name.equals("")) {
          JOptionPane.showMessageDialog(null, "Name cannot be blank");
          return;
        } else if (pass.equals("")) {
          JOptionPane.showMessageDialog(null, "Password cannot be blank");
          return;
        } else if (nameTest) {
          JOptionPane.showMessageDialog(null, "User name already taken");
          return;
        } else {
          db.query().tableIs("User").userIdIs(selectedUser.getUserId()).modifyTo().userNameIs(name).userPassIs(pass).userTypeIs(userType).modify();
          JOptionPane.showMessageDialog(null, "User modified!");
          dispose();
        }
        
      }
    });
    submitButton.setBounds(80, 120, 90, 25);
    panel.add(submitButton);
  }

  public void cancelButton(){
    cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    cancelButton.setBounds(80, 155, 90, 25);
    panel.add(cancelButton);
  }
}
