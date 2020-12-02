package com.sdproject.app.view;

import com.sdproject.app.database.*;
import com.sdproject.app.model.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
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
  private GridBagConstraints gbc;
  private Font font, font2;

  public ModifyUserView(DatabaseWrapper db, UserView view, int selectedID) {
    this.db = db;
    this.view = view;
    this.selectedUser = db.query().tableIs("User").userIdIs(selectedID).getOne();

    topPanel = new JPanel(new FlowLayout());
    centerPanel = new JPanel(new GridBagLayout());
    bottomPanel = new JPanel(new FlowLayout());
    
    gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 0, 10, 20);
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

    setGBC(GridBagConstraints.LINE_END, GridBagConstraints.VERTICAL, 0, 0, .1, .2);
    centerPanel.add(nameLabel, gbc);
    setGBC(GridBagConstraints.LINE_START, GridBagConstraints.BOTH, 1, 0, .9, .2);
    centerPanel.add(nameField, gbc);
  }
    
  private void addPassField() {
    passLabel = new JLabel("Password:");
    passLabel.setFont(font);

    passField = new JPasswordField();
    passField.setFont(font2);
    passField.setText(selectedUser.getUserPass());

    setGBC(GridBagConstraints.LINE_END, GridBagConstraints.VERTICAL, 0, 1, .1, .2);
    centerPanel.add(passLabel, gbc);
    setGBC(GridBagConstraints.LINE_START, GridBagConstraints.BOTH, 1, 1, .9, .2);
    centerPanel.add(passField, gbc);
  }
    
  private void addTypeField() {
    userType = new JLabel();
    userType.setFont(font);
    userType.setText("Select User Type: ");

    typeList = new JComboBox<String>(new String[] {"NORMAL", "ADMIN"});

    setGBC(GridBagConstraints.LINE_END, GridBagConstraints.VERTICAL, 0, 2, .1, .2);
    centerPanel.add(userType, gbc);
    setGBC(GridBagConstraints.LINE_START, GridBagConstraints.NONE, 1, 2, .9, .2);
    centerPanel.add(typeList, gbc);
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

  private void setGBC(int anchor, int fill, int gridx, int gridy, double weightx, double weighty){
    gbc.anchor = anchor;
    gbc.fill = fill;
    gbc.weightx = weightx;
    gbc.weighty = weighty;
    gbc.gridx = gridx;
    gbc.gridy = gridy;
  }
}
