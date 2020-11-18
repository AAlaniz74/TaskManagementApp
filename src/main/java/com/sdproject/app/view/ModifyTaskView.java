package com.sdproject.app.view;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import com.sdproject.app.model.*;
import com.sdproject.app.database.*;


public class ModifyTaskView extends JFrame{
    
    private DatabaseWrapper db;
    
    private String taskName;
    private Task tempTask;
    private JPanel panel;
    private JLabel name_label,des_label,duedate_label, subtask_label;
    private JTextField name, duedate;
    private JTextArea description;
    private JCheckBoxList subtask;
    private JButton submit,cancel;
    public ModifyTaskView(DatabaseWrapper db, String taskName){
        this.db = db;
        this.taskName = taskName;
        tempTask = db.query().tableIs("Task").taskNameIs(taskName).getOne();
        panel = new JPanel(new GridLayout(6,1));
        addNameTextBox();
        addDescription();
        addSubtask();
        addDueDate();
        addSubmitButton();
        addCancelButton();

        add(panel, BorderLayout.CENTER);




        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ModifyTask:" + tempTask.getTaskName() );
        setSize(500, 700);
        setVisible(true);

    }
    public void addNameTextBox(){
        name_label = new JLabel("Enter Name:");
        name = new JTextField(tempTask.getTaskName(),100);

        panel.add(name_label);
        panel.add(name);
    }

    public void addDescription(){
        des_label = new JLabel("Enter Description:");
        description = new JTextArea(10,100 );
        description.insert(tempTask.getTaskDesc(), 0);
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
        //Ouputing all tasks in a list.
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
        duedate = new JTextField(/*Use getOne to get the text im*/);
        panel.add(duedate_label);
        panel.add(duedate);
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
}
