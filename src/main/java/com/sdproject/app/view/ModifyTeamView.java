package com.sdproject.app.view;

import javax.swing.*;
import javax.swing.JButton;
import javax.xml.crypto.Data;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import com.sdproject.app.database.DatabaseWrapper;
import com.sdproject.app.model.Task;

public class ModifyTeamView extends JFrame{

    private DatabaseWrapper db;
    private JPanel panel;
    private JLabel name_label,des_label,duedate_label, teamlist_label;
    private JTextField name, duedate;
    private JTextArea description;
    private JCheckBoxList teamlist;
    private JButton submit,cancel;

    public ModifyTeamView(DatabaseWrapper db){
        this.db = db;
        panel = new JPanel(new GridLayout(2,1));

        addTeamList();

        addSubmitButton();
        addCancelButton();

        add(panel, BorderLayout.CENTER);




        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CreateTask");
        setSize(500, 300);
        setVisible(true);

    }




    public void addTeamList(){
        teamlist_label = new JLabel("Select All Team Members to add to team");
        teamlist = new JCheckBoxList();
        teamlist.addCheckbox(new JCheckBox("Test1"));
        // use getOne to access the team and check that have been checked
        panel.add(teamlist_label);
        panel.add(teamlist);

    }


    public void addSubmitButton(){
        submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView t = new mainView(db);
                dispose();
            }
        });

        panel.add(submit);


    }

    public void addCancelButton(){

        cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //System.out.println(duedate.getText());
                //db.query().tableIs("User").userNameIs(newUserName).userPassIs(newUserPass).userTypeIs(newUserType).insert();

                JOptionPane.showMessageDialog(null, "Creation Cancled");
                mainView t = new mainView(db);
                dispose();
            }
        });

        panel.add(cancel);



    }



}
