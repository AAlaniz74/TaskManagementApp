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

public class SearchView extends JFrame {
    private DatabaseWrapper db;
    private JPanel panel;
    private JLabel name_label,des_label,duedate_label, teamlist_label;
    private JTextField name, duedate;
    private JTextArea description;

    private JCheckBoxList teamlist;
    private JButton submit,cancel;

    public SearchView(DatabaseWrapper db){
        this.db = db;
        panel = new JPanel(new GridLayout(3,1));

        //addTeamList();
        addNameSearch();
        addDescriptionSearch();
        addSubmitButton();
        addCancelButton();

        add(panel, BorderLayout.CENTER);




        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CreateTask");
        setSize(500, 300);
        setVisible(true);

    }

    public void addSubmitButton(){

        submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                JOptionPane.showMessageDialog(null, "New Task Created.");
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
    private void addNameSearch(){

        name_label = new JLabel("Enter Name:");
        name = new JTextField("",100);

        panel.add(name_label);
        panel.add(name);
    }
    private void addDescriptionSearch(){
        des_label = new JLabel("Enter Description:");
        description = new JTextArea(10,100 );
        panel.add(des_label);
        panel.add(description);

    }


}
