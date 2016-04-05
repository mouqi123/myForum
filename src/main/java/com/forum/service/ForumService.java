package com.forum.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forum.dao.BoardDao;
import com.forum.dao.Page;
import com.forum.dao.PostDao;
import com.forum.dao.TopicDao;
import com.forum.dao.UserDao;
import com.forum.domain.Board;
import com.forum.domain.MainPost;
import com.forum.domain.Post;
import com.forum.domain.Topic;
import com.forum.domain.User;

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

	public void addTopic(Topic topic) {
		Board board = (Board) boardDao.get(topic.getBoardId());
		board.setTopicNum(board.getTopicNum() + 1);
		topicDao.save(topic);

		MainPost mainPost = topic.getMainPost();
		mainPost.setTopic(topic);
		mainPost.setBoardId(topic.getBoardId());
		mainPost.setCreateTime(new Date());
		mainPost.setPostTitle(topic.getTopicTitle());
		mainPost.setUser(topic.getUser());
		postDao.save(mainPost);

		User user = topic.getUser();
		user.setCredit(user.getCredit() + 10);
		userDao.update(user);
	}

	public void removeTopic(int topicId) {
		Topic topic = topicDao.get(topicId);
		Board board = boardDao.get(topic.getBoardId());
		board.setTopicNum(board.getTopicNum() - 1);
		//board处于受管状态，无需显示更新。
		
		User user = topic.getUser();
		user.setCredit(user.getCredit() - 50);
		topicDao.remove(topic);
		postDao.deleteTopicPosts(topicId);
	}

	public void addPost(Post post) {
		postDao.save(post);
		User user = post.getUser();
		user.setCredit(user.getCredit() + 5);
		Topic topic = topicDao.get(post.getTopic().getTopicId());
		topic.setReplies(topic.getReplies() + 1);
		topic.setLastPost(new Date());
		//topic处于受管状态，无需显示更新。
	}
	
	public void removePost(int postId){
		Post post=postDao.get(postId);
		postDao.remove(post);
		
		Topic topic=topicDao.get(post.getTopic().getTopicId());
		topic.setReplies(topic.getReplies()-1);
		
		User user=post.getUser();
		user.setCredit(user.getCredit()-20);  //处于受管状态，无需显式刷新
	}
	
	public void addBoard(Board board){
		boardDao.save(board);
	}
	
	public void removeBoard(int boardId){
		Board board=boardDao.get(boardId);
		boardDao.remove(board);
	}
	
	/**
	 * 将帖子置为精华主题帖
	 * @param topicId 操作的目标主题帖ID
	 * @param digest 精华级别 可选的值为1，2，3
	 */
	public void makeDigestTopic(int topicId){
		Topic topic = topicDao.get(topicId);
		topic.setDigest(Topic.DIGEST_TOPIC);
		User user = topic.getUser();
		user.setCredit(user.getCredit() + 100);
		//topic 处于Hibernate受管状态，无须显示更新
		//topicDao.update(topic);
		//userDao.update(user);
	}
	
    /**
     * 获取所有的论坛版块
     * @return
     */
    public List<Board> getAllBoards(){
        return boardDao.loadAll();
    }	
	
	/**
	 * 获取论坛版块某一页主题帖，以最后回复时间降序排列
	 * @param boardId
	 * @return
	 */
    public Page getPagedTopics(int boardId,int pageNo,int pageSize){
		return topicDao.getPagedTopics(boardId,pageNo,pageSize);
    }
    
    /**
     * 获取同主题每一页帖子，以最后回复时间降序排列
     * @param boardId
     * @return
     */
    public Page getPagedPosts(int topicId,int pageNo,int pageSize){
        return postDao.getPagedPosts(topicId,pageNo,pageSize);
    }    
    

	/**
	 * 查找出所有包括标题包含title的主题帖
	 * 
	 * @param title
	 *            标题查询条件
	 * @return 标题包含title的主题帖
	 */
	public Page queryTopicByTitle(String title,int pageNo,int pageSize) {
		return topicDao.queryTopicByTitle(title,pageNo,pageSize);
	}
	
	/**
	 * 根据boardId获取Board对象
	 * 
	 * @param boardId
	 */
	public Board getBoardById(int boardId) {
		return boardDao.get(boardId);
	}

	/**
	 * 根据topicId获取Topic对象
	 * @param topicId
	 * @return Topic
	 */
	public Topic getTopicByTopicId(int topicId) {
		return topicDao.get(topicId);
	}
	
	/**
	 * 获取回复帖子的对象
	 * @param postId
	 * @return 回复帖子的对象
	 */
	public Post getPostByPostId(int postId){
		return postDao.get(postId);
	}
    
	/**
	 * 将用户设为论坛版块的管理员
	 * @param boardId  论坛版块ID
	 * @param userName 设为论坛管理的用户名
	 */
	public void addBoardManager(int boardId,String userName){
	   	User user = userDao.getUserByUserName(userName);
	   	if(user == null){
	   		throw new RuntimeException("用户名为"+userName+"的用户不存在。");
	   	}else{
            Board board = boardDao.get(boardId);
            user.getManBoards().add(board);
            userDao.update(user);
	   	}
	}
	
	/**
	 * 更改主题帖
	 * @param topic
	 */
	public void updateTopic(Topic topic){
		topicDao.update(topic);
	}
	
	/**
	 * 更改回复帖子的内容
	 * @param post
	 */
	public void updatePost(Post post){
		postDao.update(post);
	}
	
}
