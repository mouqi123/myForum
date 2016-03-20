package com.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forum.dao.BoardDao;
import com.forum.dao.PostDao;
import com.forum.dao.TopicDao;
import com.forum.dao.UserDao;
import com.forum.domain.Board;
import com.forum.domain.Topic;

@Service
public class ForumService {
	@Autowired
	private TopicDao topicDao;
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PostDao postDao;
	
	public void addTopic(Topic topic){
		Board board=(Board) boardDao.get(topic.getBoardId());
		board.setTopicNum(board.getTopicNum()+1);
		topicDao.save(topic);
		
	}
}
