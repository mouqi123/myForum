package com.forum.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forum.domain.Board;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class BoardTest {
	
	@Autowired
	private BoardDao boardDao;

	@Test
	public void test() {
		Board b=new Board();
		b.setBoardName("亚洲无码123");
		b.setBoardDesc("亚洲无码片板块");
		b.setTopicNum(0);
		boardDao.save(b);
		//System.out.println(boardDao.get(1));
		assert true;
	}

}
