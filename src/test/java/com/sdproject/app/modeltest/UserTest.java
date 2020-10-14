package com.sdproject.app.modeltest;

import com.sdproject.app.model.User;
import com.sdproject.app.model.UserType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

	private static User basicUser = new User("Name", "Password", UserType.NORMAL);	
	private static User adminUser = new User("Admin", "Pass", UserType.ADMIN);

	@Test
	public void testBasicName() {
		assertEquals(basicUser.getUserName(), "Name");
	}

	@Test
	public void testBasicUserType() {
		assertEquals(basicUser.getUserType(), UserType.NORMAL);
	}

	@Test
	public void testAdminUserType() {
		assertEquals(adminUser.getUserType(), UserType.ADMIN);
	}

	@Test
	public void testPassword() {
		assertEquals(basicUser.getUserPass(), "Password");
	}
}

