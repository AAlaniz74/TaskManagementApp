package com.sdproject.app.modeltest;

import com.sdproject.app.model.Task;
import com.sdproject.app.model.TaskStatus;
import com.sdproject.app.model.User;
import com.sdproject.app.model.UserType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {

	private static User basicUser = new User("Name", "Password", UserType.NORMAL);
	private Task testTask = new Task("Test Task", "Test", basicUser.getUserID());

	@Test
	public void testGetTaskName() {
		assertEquals(testTask.getTaskName(), "Test Task");
	}

	@Test
	public void testSetTaskName() {
		testTask.setTaskName("New Name");
		assertEquals(testTask.getTaskName(), "New Name");
	}

	@Test
        public void testGetTaskDesc() {
                assertEquals(testTask.getTaskDesc(), "Test");
        }

        @Test
        public void testSetTaskDesc() {
                testTask.setTaskDesc("New Desc");
                assertEquals(testTask.getTaskDesc(), "New Desc");
        }

}
