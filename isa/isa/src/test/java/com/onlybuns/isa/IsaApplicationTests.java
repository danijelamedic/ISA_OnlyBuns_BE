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

	//@Test(expected = ObjectOptimisticLockingFailureException.class)

	void contextLoads() {
	}

}
