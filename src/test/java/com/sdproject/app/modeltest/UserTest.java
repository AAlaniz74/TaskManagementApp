package com.sdproject.app.modeltest;

import com.sdproject.app.model.User;
import com.sdproject.app.model.User.UserType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

	private static User basicUser = new User("Name", UserType.NORMAL);	
	private static User adminUser = new User("Admin", UserType.ADMIN);

	@Test
	public void testBasicName() {
		assertEquals("Name", basicUser.getName());
	}

	@Test
	public void testBasicUserType() {
		assertEquals(basicUser.getType(), UserType.NORMAL);
	}

	@Test
	public void testAdminUserType() {
		assertEquals(adminUser.getType(), UserType.ADMIN);
	}
}

