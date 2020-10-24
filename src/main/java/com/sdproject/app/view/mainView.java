package com.sdproject.app.view;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.ArrayList;
import com.sdproject.app.model.*;
import com.sdproject.app.database.*;



class mainView extends JFrame
{
    private DatabaseWrapper db;
    private JButton addButton;
    private JButton deleteButton;
    private JButton display;
    private JPanel panel;
    private JComboBox actions;
    private JComboBox currentDisplay;
    private JTextArea textBox;
    private String currentJList = "User";
    private String select;
    private String[] actionList = {"Task Catagory", "Task", "User", "Team"};
    private JList<String> list = new JList<>();
    private DefaultListModel<String> model = new DefaultListModel<>();

    mainView(DatabaseWrapper db)
    {
        this.db = db;
        panel = new JPanel();
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        panel.setLayout(null);

        actions = new JComboBox<String>(actionList);
        actions.setBounds(325, 400, 120 , 20);
        panel.add(actions);

        currentDisplay = new JComboBox<String>(actionList);
        currentDisplay.setBounds(115, 10, 120 , 20);
        panel.add(currentDisplay);

        /*addButton = new JButton(new AbstractAction("Add New"){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(actions.getItemAt(actions.getSelectedIndex()).equals("Task Catagory"))
                {
                    //open create Task catagory panel
                }
                if(actions.getItemAt(actions.getSelectedIndex()).equals("Task"))
                {
                    //open createtask panel
                }
                if(actions.getItemAt(actions.getSelectedIndex()).equals("User"))
                {
                    //open createUser panel
                }
                if(actions.getItemAt(actions.getSelectedIndex()).equals("Team"))
                {
                    CreateTeamView t = new CreateTeamView(db);
                    dispose();
                }
            }
        });
        addButton.setBounds(230, 400, 90, 20);
        panel.add(addButton);*/
        addNewButton();

        display = new JButton(new AbstractAction("Display"){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                model.clear();
                if(currentDisplay.getItemAt(currentDisplay.getSelectedIndex()).equals("Task Catagory"))
                {
                    //createTaskCatagoryList();
                }
                if(currentDisplay.getItemAt(currentDisplay.getSelectedIndex()).equals("Task"))
                {
                    //createTaskList();
                }
                if(currentDisplay.getItemAt(currentDisplay.getSelectedIndex()).equals("User"))
                {
                    //createUserList();
                }
                if(currentDisplay.getItemAt(currentDisplay.getSelectedIndex()).equals("Team"))
                {
                    //createTeamList();
                }
            }
        });
        display.setBounds(15, 10, 90, 20);
        panel.add(display);

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
                if(currentJList.equals("Task Catagory"))
                {
                    //delete task catagory query
                    //createTaskCatagoryList();
                }
            }
        });
        deleteButton.setBounds(40, 400, 130, 20);
        panel.add(deleteButton);

        list.setModel(model);
        //createUserList();
        list.setBounds(10, 40, 250, 340);
        panel.add(list);
        list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                select = list.getSelectedValue();
                //displayText();
            }
        } );

        textBox = new JTextArea();
        textBox.setBounds(280, 40, 390, 340);
        textBox.setEditable(false);
        panel.add(textBox);

        setVisible(true);
    }

    public void addNewButton(){
        addButton = new JButton(new AbstractAction("Add New"){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(actions.getItemAt(actions.getSelectedIndex()).equals("Task Catagory"))
                {
                    //open create Task catagory panel
                }
                if(actions.getItemAt(actions.getSelectedIndex()).equals("Task"))
                {
                    CreateTaskView t = new CreateTaskView(db);
                    dispose();
                }
                if(actions.getItemAt(actions.getSelectedIndex()).equals("User"))
                {
                    //open createUser panel
                }
                if(actions.getItemAt(actions.getSelectedIndex()).equals("Team"))
                {

                    //System.out.println("hello?");
                    CreateTeamView t = new CreateTeamView(db);
                    dispose();
                }
            }
        });
        addButton.setBounds(230, 400, 90, 20);
        panel.add(addButton);

    }


    // creation funtions for each object for the JList
    /*public void createTaskCatagoryList()
    {
        ArrayList<TaskCatagory> list = db.query().tableIs("Task Catagory").get();
        currentJList = "Task Catagory";
        for(int i = 0; i < list.length; i++)
        {
            model.addElement(list[i].getCatagoryName());
        }
    }
    public void createTaskList()
    {
        ArrayList<Task> list = db.query().tableIs("Task").get();
        currentJList = "Task";
        for(int i = 0; i < list.length; i++)
        {
            model.addElement(list[i].getTaskName());
        }
    }
    public void createUserList()
    {
        ArrayList<User> list = db.query().tableIs("User").get();
        currentJList = "User";
        for(int i = 0; i < list.length; i++)
        {
            model.addElement(user.getUserName());
        }
    }
    public void createTeamList()
    {
        ArrayList<Team> list = db.query().tableIs("Team").get();
        currentJList = "Team";
        for(int i = 0; i < list.length; i++)
        {
            model.addElement(list[i].getTeamName());
        }
    } */

   /* void displayText()
    {
        if(currentJList.equals("User"))
        {
            User temp = db.query().tableIs("User").userNameIs(select).get();
            textBox.setText("ID: " + temp.getUserID() +
            "\nPassword: " + temp.getUserPass() +
            "\nUser Type: " + temp.getUserType());
        }
        if(currentJList.equals("Team"))
        {
            String text = "Team Members";
            Team temp = db.query().tableIs("Team").teamNameIs(select).get();
            ArrayList<Users> userlist = Team.getTeamMembers();
            for(int i = 0; i < userlist.length; i++)
                text = text + "\n" + userlist[i];
            textBox.setText(text);
        }
        if(currentJList.equals("Task"))
        {
            Task temp = db.query().tableIs("Task").taskNameIs(select).get();

            textBox.setText("ID: " + temp.getTaskID() +
            "\nName: " + temp.getTaskName() +
            "\nTask Status: " + temp.getTaskStatus() +
            "\nSub-Tasks: ");

            ArrayList<Integer> subTaskIDs = Task.getSubtaskIDs();
            for(int i = 0; i < subTaskIDs.length; i++)
            {
                textBox.append(db.query().tableIs("Task").taskIDis(subTaskIDs[i]).get().getTaskName() + " ");
            }

            textBox.append("\nassignedToID: " + temp.getAssignedToID() +
            "\nCreated BY: " + db.query().tableIs("User").userIDIs(getCreatedByID()).get() +
            "\nCreated On: " + temp.getCreatedOn() +
            "\nDue Date: " + temp.getDueDate() +
            "\nDescription: " + temp.getTaskDesc());

        }
        if(currentJList.equals("Task Catagory"))
        {
            TaskCatagory temp = db.query().tableIs("Task Catagory").catagoryNameIs(select).get();
        }
    } */

}
