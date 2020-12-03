package com.sdproject.app.view;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Font;

import com.sdproject.app.database.DatabaseWrapper;
import com.sdproject.app.database.Query;
import static com.sdproject.app.view.GBConstraints.*;

public class SearchUserView extends JFrame {

  private DatabaseWrapper db;
  private UserView view;

  private JPanel topPanel, bottomPanel, centerPanel;
  private JLabel userLabel, typeLabel;
  private JTextField userField;
  private JComboBox<String> userType;
  private JButton submitButton;
  private Font font, font2;

  public SearchUserView(DatabaseWrapper db, UserView view) {
    this.db = db;
    this.view = view;

    topPanel = new JPanel(new FlowLayout());
    centerPanel = new JPanel(new GridBagLayout());
    bottomPanel = new JPanel(new FlowLayout());
    
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
    centerPanel.add(userLabel, new GBConstraints(0,0).anchor(LINE_END).fill(VERTICAL).weight(.1, .2));
    centerPanel.add(userField, new GBConstraints(1,0).anchor(LINE_START).fill(BOTH).weight(.9, .2));
  }

  public void addTypeLabel() {
    typeLabel = new JLabel();
    typeLabel.setText("User type: ");
    typeLabel.setFont(font);
		
    String[] typeList = new String[] {"", "NORMAL", "ADMIN"};
    userType = new JComboBox<String>(typeList);
    centerPanel.add(typeLabel, new GBConstraints(0,1).anchor(LINE_END).fill(VERTICAL).weight(.1, .2));
    centerPanel.add(userType, new GBConstraints(1,1).anchor(LINE_START).fill(NONE).weight(.9, .2));
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
}
