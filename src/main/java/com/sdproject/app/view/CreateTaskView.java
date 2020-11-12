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
import com.sdproject.app.database.Query;
import com.sdproject.app.model.Task;
import com.sdproject.app.model.TaskStatus;

public class CreateTaskView extends JFrame {

  private DatabaseWrapper db;

  private JPanel panel;
  private JLabel name_label,des_label,duedate_label, subtask_label;
  private JTextField name, duedate;
  private JTextArea description;
  private JCheckBoxList subtask;
  private JButton submit, cancel;

  public CreateTaskView(DatabaseWrapper db){
    this.db = db;
    panel = new JPanel(new GridLayout(6,1));
    addNameTextBox();
    addDescription();
    addSubtask();
    addDueDate();
    addSubmitButton();
    addCancelButton();

        add(panel, BorderLayout.CENTER);




        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CreateTask");
        setSize(500, 700);
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

        System.out.println("Hello");
        ArrayList<Task> taskList = db.query().tableIs("Task").get();
        for (int i = 0; i < taskList.size(); i++)
            subtask.addCheckbox(new JCheckBox( taskList.get(i).getTaskName()));
        //for(int i = 701; i < 706; i++){
            //System.out.println("InFor");
            //Task send = db.query().tableIs("Task").taskIdIs(i).getOne();

            //subtask.addCheckbox(new JCheckBox(send.getTaskName()));
            //System.out.println(send);
        //}


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

                //System.out.println(duedate.getText());
                //db.query().tableIs("User").userNameIs(newUserName).userPassIs(newUserPass).userTypeIs(newUserType).insert();
                db.query().tableIs("Task").taskNameIs(name.getText()).taskDescIs(description.getText()).taskStatusIs("IN_PROGRESS").insert();
                ListModel test = subtask.getModel();
                for(int i = 0; i < test.getSize(); i++){
                    System.out.println(test.getElementAt(i) + " ");
                }
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


}
