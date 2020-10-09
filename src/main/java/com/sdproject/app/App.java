package com.sdproject.app;

import com.sdproject.app.model.User;
import com.sdproject.app.model.User.UserType;
import com.sdproject.app.model.Team;
import com.sdproject.app.model.Task;
import com.sdproject.app.model.TaskCategory;

public class App
{
    public static void main( String[] args )
    {
      // ******************************* User(s) creation *******************************
      User alex = new User("Alex", UserType.ADMIN);
      User alejandro = new User("Alejandro", UserType.ADMIN);
      User ryan = new User("Ryan", UserType.NORMAL);
      User vyda = new User("Vyda", UserType.NORMAL);

      // *******************************  Team creation *******************************
      Team team = new Team();
      team.addMember(alex);
      team.addMember(alejandro);
      team.addMember(ryan);
      team.addMember(vyda);

      // *******************************  Task(s) creation *******************************
      Date dueDate = new Date();
      dueDate.setDate(9);
      dueDate.setMonth(10);
      dueDate.setYear(2020);

      User createdByAI = new User("AI", UserType.NORMAL);

      Task task = new Task(
                 "Finish F1",
                 "Implement object creation for entities including User, Team, Task, and Task Category.",
                 dueDate,
                 createdByAI
              );



      // *******************************  Task Category creation *******************************
      Task task1 = new Task(
                 "Finish F1",
                 "Implement object creation for entities including User, Team, Task, and Task Category.",
                 dueDate,
                 createdByAI
              );

      Task task2 = new Task(
                 "Finish F5",
                 "Assign a color to each task.",
                 dueDate,
                 createdByAI
              );

      ArrayList<Task> taskList = new ArrayList<Task>();
      taskList.add(task1);
      taskList.add(task2);

      TaskCatagory toDo = new TaskCatagory("TO-DO",
                                          "Tasks to completed by October 9th",
                                          taskList,
                                          createdByAI
                                          );

    }
}
