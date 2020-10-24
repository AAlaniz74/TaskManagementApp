package com.sdproject.app.view;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.ArrayList;
import com.sdproject.app.model.*;
import com.sdproject.app.database.*;

public class mainView extends JFrame
{
    private DatabaseWrapper db;
    private JButton addButton;
    private JButton deleteButton;
    private JButton display;
    private JPanel panel;
    private JComboBox actions;
    private JComboBox currentDisplay;
    private JTextArea textBox;
    private String currentJList;
    private String select;
    private JList<String> list = new JList<>();
    private DefaultListModel<String> model = new DefaultListModel<>();

    public mainView(DatabaseWrapper db)
    {
        this.db = db;
        currentJList = "User";
        panel = new JPanel();
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        panel.setLayout(null);

        comboBoxes();
        addNewButton();
        displayButton();
        deleteButton();
        createJList();
        createTextArea();

        setVisible(true);
    }

    public void comboBoxes(){
        String[] actionList = {"Task Catagory", "Task", "User", "Team"};

        actions = new JComboBox<String>(actionList);
        actions.setBounds(325, 400, 120 , 20);
        panel.add(actions);

        currentDisplay = new JComboBox<String>(actionList);
        currentDisplay.setBounds(115, 10, 120 , 20);
        panel.add(currentDisplay);
    }

    public void addNewButton(){
        addButton = new JButton(new AbstractAction("Add New"){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(actions.getItemAt(actions.getSelectedIndex()).equals("Task"))
                {
                    CreateTaskView t = new CreateTaskView(db);
                    dispose();
                }
                if(actions.getItemAt(actions.getSelectedIndex()).equals("User"))
                {
                    CreateUserView t = new CreateUserView(db);
                }
                if(actions.getItemAt(actions.getSelectedIndex()).equals("Team"))
                {
                    CreateTeamView t = new CreateTeamView(db);
                    dispose();
                }
            }
        });
        addButton.setBounds(230, 400, 90, 20);
        panel.add(addButton);

    }

    public void displayButton(){
        display = new JButton(new AbstractAction("Display"){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                model.clear();
                if(currentDisplay.getItemAt(currentDisplay.getSelectedIndex()).equals("Task"))
                {
                    createTaskList();
                }
                if(currentDisplay.getItemAt(currentDisplay.getSelectedIndex()).equals("User"))
                {
                    createUserList();
                }
                if(currentDisplay.getItemAt(currentDisplay.getSelectedIndex()).equals("Team"))
                {
                    createTeamList();
                }
            }
        });
        display.setBounds(15, 10, 90, 20);
        panel.add(display);
    }

    public void deleteButton(){
        deleteButton = new JButton(new AbstractAction("Delete Selected"){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                model.clear();
                if(currentJList.equals("User"))
                {
                    //delete user query
                    //createUserList();
                }
                if(currentJList.equals("Team"))
                {
                    //delete team query
                    //createTeamList();
                }
                if(currentJList.equals("Task"))
                {
                    //delete task query
                    //createTaskList();
                }
            }
        });
        deleteButton.setBounds(40, 400, 130, 20);
        panel.add(deleteButton);
    }

    public void createJList(){
        list.setModel(model);
        createUserList();
        list.setBounds(10, 40, 250, 340);
        panel.add(list);
        list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                select = list.getSelectedValue();
                //displayText();
            }
        } );
    }

    public void createTextArea(){
        textBox = new JTextArea();
        textBox.setBounds(280, 40, 390, 340);
        textBox.setEditable(false);
        panel.add(textBox);
    }

    // creation funtions for each object for the JList
    public void createTaskList()
    {
        ArrayList<Task> taskList = db.query().tableIs("Task").get();
        currentJList = "Task";
        for(int i = 0; i < taskList.size(); i++)
        {
            model.addElement(taskList.get(i).getTaskName());
        }
    }
    public void createUserList()
    {
        ArrayList<User> userList = db.query().tableIs("User").get();
        currentJList = "User";
        for(int i = 0; i < userList.size(); i++)
        {
            model.addElement(userList.get(i).getUserName());
        }
    }
    public void createTeamList()
    {
        ArrayList<Team> teamList = db.query().tableIs("Team").get();
        currentJList = "Team";
        for(int i = 0; i < teamList.size(); i++)
        {
            model.addElement(teamList.get(i).getTeamName());
        }
    }

    void displayText()
    {
        if(currentJList.equals("User"))
        {
            User temp = db.query().tableIs("User").userNameIs(select).getOne();
            textBox.setText("ID: " + temp.getUserId() +
            "\nPassword: " + temp.getUserPass() +
            "\nUser Type: " + temp.getUserType());
        }
        if(currentJList.equals("Team"))
        {
            String text = "Team Members";
            Team temp = db.query().tableIs("Team").teamNameIs(select).getOne();
            ArrayList<User> userlist = temp.getTeamMembers();
            for(int i = 0; i < userlist.size(); i++)
                text = text + "\n" + userlist.get(i);
            textBox.setText(text);
        }
        if(currentJList.equals("Task"))
        {
            Task temp = db.query().tableIs("Task").taskNameIs(select).getOne();

            textBox.setText("ID: " + temp.getTaskId() +
            "\nName: " + temp.getTaskName() +
            "\nTask Status: " + temp.getTaskStatus() +
            "\nSub-Tasks: ");

            ArrayList<Integer> subTaskIDs = temp.getSubtaskIDs();
            for(int i = 0; i < subTaskIDs.size(); i++)
            {
                Task task = db.query().tableIs("Task").taskIdIs(subTaskIDs.get(i)).getOne();
                textBox.append(task.getTaskName() + " ");
            }

            textBox.append("\nassignedToID: " + temp.getAssignedToId() +
            "\nCreated BY: " + db.query().tableIs("User").userIdIs(temp.getCreatedById()).getOne() +
            "\nCreated On: " + temp.getCreatedOn() +
            "\nDue Date: " + temp.getDueDate() +
            "\nDescription: " + temp.getTaskDesc());

        }
    }

}
