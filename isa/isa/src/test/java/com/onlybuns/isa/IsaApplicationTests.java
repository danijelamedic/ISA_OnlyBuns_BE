package com.onlybuns.isa;

import com.onlybuns.isa.model.Follower;
import com.onlybuns.isa.model.Role;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.service.FollowerService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.runner.RunWith;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
class IsaApplicationTests {

	@Autowired
	private FollowerService followerService;

	User user1 = new User("johndoe", "John", "Doe", "johndoe@example.com", "123 Elm Street", "password123", Role.REGISTERED_USER);
	User user2 = new User("janedoe", "Jane", "Doe", "janedoe@example.com", "456 Maple Avenue", "securePass!", Role.REGISTERED_USER);
	User user3 = new User("alexsmith", "Alex", "Smith", "alexsmith@example.com", "789 Oak Drive", "qwerty123", Role.REGISTERED_USER);
	User user4 = new User("emilyjones", "Emily", "Jones", "emilyjones@example.com", "321 Birch Lane", "pass1234", Role.REGISTERED_USER);
	User user5 = new User("michaelbrown", "Michael", "Brown", "michaelbrown@example.com", "654 Pine Road", "mypassword", Role.REGISTERED_USER);

	@Before
	public void setUp() throws Exception {
		followerService.follow(new Follower(user1, user2));
		followerService.follow(new Follower(user3, user2));
		followerService.follow(new Follower(user4, user2));
		followerService.follow(new Follower(user2, user1));
		followerService.follow(new Follower(user5, user3));
	}

	//@Test(expected = ObjectOptimisticLockingFailureException.class)

	void contextLoads() {
	}

}
