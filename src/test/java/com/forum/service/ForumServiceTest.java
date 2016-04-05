package com.forum.service;

import static org.junit.Assert.*;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.forum.dao.UserDao;
import com.forum.domain.User;
import com.forum.exception.UserExistException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ForumServiceTest {

	@Autowired
	private UserService userService;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private UserDao userDao;
	
	@Test
	
	public void test() throws UserExistException{
		User user=new User();
		user.setCredit(0);
		user.setLastIp("127.0.0.1");
		user.setPassword("mouqi123");
		user.setUserName("mackie");
		user.setUserType(0);
		user.setLocked(0);
		userService.register(user);
		assert true;
	}

}
