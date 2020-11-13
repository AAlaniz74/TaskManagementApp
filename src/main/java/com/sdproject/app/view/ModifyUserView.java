package com.sdproject.app.view;

import com.sdproject.app.database.*;
import com.sdproject.app.model.*;
import java.awt.event.*;
import javax.swing.*;

public class ModifyUserView extends JFrame{
    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private DatabaseWrapper db;
    private AdminUserView view;
    private JLabel name, password, userType;
    private JTextField nameField;
    private JPasswordField passField;
    private JComboBox<String> type;
    private JButton submit;
    private JButton cancel;
    private User user;

    public ModifyUserView(DatabaseWrapper db, User user, AdminUserView main)
    {
        this.db = db;
        this.user = user;
        this.view = main;
        panel = new JPanel();
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        panel.setLayout(null);

        addNameField();
        addPassField();
        addTypeField();       
        submitButton();
        cancelButton();

        setVisible(true);
    }
    private void addNameField()
    {
        name = new JLabel();
        name.setText("Username: ");
        name.setBounds(10, 20, 90, 25);

        nameField = new JTextField();
        nameField.setText(user.getUserName());
        nameField.setBounds(90, 20, 140, 25);

        panel.add(name);
        panel.add(nameField);
    }
    private void addPassField()
    {
        password = new JLabel();
        password.setText("Password: ");
        password.setBounds(10, 50, 90, 25);

        passField = new JPasswordField();
        passField.setText(user.getUserPass());
        passField.setBounds(90, 50, 140, 25);

        panel.add(password);
        panel.add(passField);
    }
    private void addTypeField()
    {
        userType = new JLabel();
        userType.setText("User Type: ");
        userType.setBounds(10, 80, 90, 25);

        type = new JComboBox<String>(new String[] {"NORMAL", "ADMIN"});
        type.setBounds(90, 80, 140, 25);

        panel.add(userType);
        panel.add(type);
    }
    public void submitButton(){
        submit = new JButton(new AbstractAction("Submit"){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String name = nameField.getText();
                String pass = passField.getText();
                String userType = type.getItemAt(type.getSelectedIndex());
                db.query().tableIs("User").userNameIs(name).userPassIs(pass).userTypeIs(userType).modify();
                view.clearJList();
                view.fillJList();
                dispose();
            }
        });
        submit.setBounds(80, 120, 90, 25);
        panel.add(submit);
    }

    public void cancelButton(){
        cancel = new JButton(new AbstractAction("cancel"){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });
        cancel.setBounds(80, 155, 90, 25);
        panel.add(cancel);
    }
}
