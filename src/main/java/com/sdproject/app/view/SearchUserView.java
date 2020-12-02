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
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Font;

import com.sdproject.app.model.User;
import com.sdproject.app.model.UserType;
import com.sdproject.app.database.DatabaseWrapper;
import com.sdproject.app.database.Query;

import com.sdproject.app.view.LoginView;

public class SearchUserView extends JFrame {

  private DatabaseWrapper db;
  private UserView view;

  private JPanel topPanel, bottomPanel, centerPanel;
  private JLabel userLabel, passLabel, typeLabel;
  private JTextField userField;
  private JPasswordField passField;
  private JComboBox<String> userType;
  private JButton submitButton;
  private GridBagConstraints gbc;
  private Font font, font2;

  public SearchUserView(DatabaseWrapper db, UserView view) {
    this.db = db;
    this.view = view;

    topPanel = new JPanel(new FlowLayout());
    centerPanel = new JPanel(new GridBagLayout());
    bottomPanel = new JPanel(new FlowLayout());
    
    gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 0, 10, 20);
    font = new Font("Ariel", Font.BOLD, 13);
    font2 = new Font("Ariel", Font.PLAIN, 15);
    JLabel label = new JLabel("Find User");
    label.setFont(font);
    topPanel.add(label);

    addUserLabel();
    addTypeLabel();
    addSubmitButton();

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    add(topPanel, BorderLayout.NORTH);
    add(centerPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);
    setTitle("SearchUser");
    setPreferredSize(new Dimension(500, 220));
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
    setGBC(GridBagConstraints.LINE_END, GridBagConstraints.VERTICAL, 0, 0, .1, .2);
    centerPanel.add(userLabel, gbc);
    setGBC(GridBagConstraints.LINE_START, GridBagConstraints.BOTH, 1, 0, .9, .2);
    centerPanel.add(userField, gbc);
  }

  public void addTypeLabel() {
    typeLabel = new JLabel();
    typeLabel.setText("User type: ");
    typeLabel.setFont(font);
		
    String[] typeList = new String[] {"", "NORMAL", "ADMIN"};
    userType = new JComboBox<String>(typeList);
    setGBC(GridBagConstraints.LINE_END, GridBagConstraints.VERTICAL, 0, 2, .1, .2);
    centerPanel.add(typeLabel, gbc);
    setGBC(GridBagConstraints.LINE_START, GridBagConstraints.NONE, 1, 2, .9, .2);
    centerPanel.add(userType, gbc);
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
    bottomPanel.add(submitButton);
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
