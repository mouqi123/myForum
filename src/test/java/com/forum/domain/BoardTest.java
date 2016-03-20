package com.forum.domain;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class BoardTest {
	Configuration cfg=new Configuration().configure("hibernate.cfg.xml");
	SessionFactory sessionFactory=cfg.buildSessionFactory();

	@Test
	public void test() {
		Board board=new Board();
		board.setBoardId(0);
		board.setBoardName("性爱论坛");
		board.setBoardDesc("哈哈");
		board.setTopicNum(1);
		User user=new User();
		user.setUserName("mouqi");
		user.setPassword("12345");
		user.setUserType(1);
		user.getManBoards().add(board);
		Session session=sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		try{
			session.save(board);
			session.save(user);
			tx.commit();
		}catch(Exception e){
			if(tx!=null) tx.rollback();
		}finally {
			session.close();
		}
		 assert true;
	}

}
