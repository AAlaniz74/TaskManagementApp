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

public class CreateTaskView extends JFrame{

    private DatabaseWrapper db;
    private JPanel panel;
    private JLabel name_label,des_label,duedate_label, subtask_label;
    private JTextField name, duedate;
    private JTextArea description;
    private JCheckBoxList subtask;
    private JButton submit;

    public CreateTaskView(DatabaseWrapper db){
        this.db = db;
        panel = new JPanel(new GridLayout(6,1));
        addNameTextBox();
        addDescription();
        addSubtask();
        addDueDate();
        addSubmitButton();

        add(panel, BorderLayout.CENTER);




        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CreateTask");
        setSize(500, 300);
        setVisible(true);

    }

    public void addNameTextBox(){
        name_label = new JLabel("Enter Name:");
        name = new JTextField("Name",100);

        panel.add(name_label);
        panel.add(name);
    }

    public void addDescription(){
        des_label = new JLabel("Enter Description:");
        description = new JTextArea(10,100 );

        panel.add(des_label);
        panel.add(description);

    }
    public void addSubtask(){
        subtask_label = new JLabel("Select SubTasks");
        subtask = new JCheckBoxList();
        subtask.addCheckbox(new JCheckBox("Test1"));
        panel.add(subtask_label);
        panel.add(subtask);

    }

    public void addDueDate(){
        duedate_label = new JLabel("Due Date: ");
        duedate = new JTextField("MM/DD/YYYY");
        panel.add(duedate_label);
        panel.add(duedate);
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


}
