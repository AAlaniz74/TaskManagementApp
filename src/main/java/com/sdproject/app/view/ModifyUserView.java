package com.sdproject.app.view;

import com.sdproject.app.database.*;
import com.sdproject.app.model.*;
import static com.sdproject.app.view.GBConstraints.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Font;

public class ModifyUserView extends JFrame{
    
  private static final long serialVersionUID = 1L;
  private DatabaseWrapper db;
  private User selectedUser;
  private UserView view;

  private JPanel topPanel, bottomPanel, centerPanel;
  private JLabel nameLabel, passLabel, userType;
  private JTextField nameField;
  private JPasswordField passField;
  private JComboBox<String> typeList;
  private JButton submitButton;
  private JButton cancelButton;
  private Font font, font2;

  public ModifyUserView(DatabaseWrapper db, UserView view, int selectedID) {
    this.db = db;
    this.view = view;
    this.selectedUser = db.query().tableIs("User").userIdIs(selectedID).getOne();

    topPanel = new JPanel(new FlowLayout());
    centerPanel = new JPanel(new GridBagLayout());
    bottomPanel = new JPanel(new FlowLayout());
    
    font = new Font("Ariel", Font.BOLD, 13);
    font2 = new Font("Ariel", Font.PLAIN, 15);
    JLabel label = new JLabel("Modify the User");
    label.setFont(font);
    topPanel.add(label);

    addNameField();
    addPassField();
    addTypeField();       
    submitButton();
    cancelButton();

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    add(topPanel, BorderLayout.NORTH);
    add(centerPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);
    setTitle("ModifyUser");
    setPreferredSize(new Dimension(500, 270));
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }
    
  private void addNameField() {
    nameLabel = new JLabel("Username:");
    nameLabel.setFont(font);
        
    nameField = new JTextField();
    nameField.setFont(font2);
    nameField.setText(selectedUser.getUserName());

    centerPanel.add(nameLabel, new GBConstraints(0,0).anchor(LINE_END).fill(VERTICAL).weight(.1, .2));
    centerPanel.add(nameField, new GBConstraints(1,0).anchor(LINE_START).fill(BOTH).weight(.9, .2));
  }
    
  private void addPassField() {
    passLabel = new JLabel("Password:");
    passLabel.setFont(font);

    passField = new JPasswordField();
    passField.setFont(font2);
    passField.setText(selectedUser.getUserPass());

    centerPanel.add(passLabel, new GBConstraints(0,1).anchor(LINE_END).fill(VERTICAL).weight(.1, .2));
    centerPanel.add(passField, new GBConstraints(1,1).anchor(LINE_START).fill(BOTH).weight(.9, .2));
  }
    
  private void addTypeField() {
    userType = new JLabel();
    userType.setFont(font);
    userType.setText("Select User Type: ");

    typeList = new JComboBox<String>(new String[] {"NORMAL", "ADMIN"});

    centerPanel.add(userType, new GBConstraints(0,2).anchor(LINE_END).fill(VERTICAL).weight(.1, .2));
    centerPanel.add(typeList, new GBConstraints(1,2).anchor(LINE_START).fill(NONE).weight(.9, .2));
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
          view.updateJList();
          dispose();
        }
        
      }
    });
    submitButton.setBounds(80, 120, 90, 25);
    bottomPanel.add(submitButton);
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
    bottomPanel.add(cancelButton);
  }
}
