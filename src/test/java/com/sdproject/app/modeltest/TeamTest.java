package com.sdproject.app.modeltest;

import com.sdproject.app.model.Team;
import com.sdproject.app.model.User;
import com.sdproject.app.model.UserType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.ArrayList;

public class TeamTest {

  //TODO: Tests need to be rewritten. Need to remove global variables

  /*
	public static User user1 = new User("t1", "", UserType.NORMAL);
	public static User user2 = new User("t2", "", UserType.NORMAL);
	public ArrayList<User> list = new ArrayList<User>(Arrays.asList(user1, user2));
	public Team team = new Team("Team");
	public static User newUser = new User("t3", "", UserType.NORMAL);

	@Test
	public void testTeamMembers() {
    team.setTeamMemberIDs(list);
		assertEquals(list, team.getTeamMemberIDs());
	}

	@Test
	public void testAddMember() {
		list.add(newUser);
		team.addMember(newUser);
		assertEquals(list, team.getTeamMembers());
	}

	@Test
	public void testRemoveMember() {
		list.remove(newUser);
		team.removeMember(newUser);	
		assertEquals(list, team.getTeamMembers());
	}

  */
}
