package com.forum.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_topic")
public class Topic extends BaseDomain {
	/**
	 *   精华主题贴
	 */
	public static final int DIGEST_TOPIC=1;
	
	/**
	 *   普通主题贴
	 */
	public static final int NOT_DIGEST_TOPIC=0;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="topic_id")
	private int topicId;
	
	@Column(name="topic_title",nullable=false)
	private int topicTitle;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@Column(name="board_id",nullable=false)
	private int boardId;
	
	@Transient
	private MainPost mainPost=new MainPost();
	
	@Column(name="last_post",nullable=false)
	private Date lastPost=new Date();
	
	@Column(name="create_time",nullable=false)
	private Date createTime=new Date();
	
	@Column(name="topic_views",columnDefinition="int not null default 0")
	private int views;
	
	@Column(name="topic_replies",nullable=false)
	private int replies;
	
	private int digest=NOT_DIGEST_TOPIC;

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public int getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(int topicTitle) {
		this.topicTitle = topicTitle;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public MainPost getMainPost() {
		return mainPost;
	}

	public void setMainPost(MainPost mainPost) {
		this.mainPost = mainPost;
	}

	public Date getLastPost() {
		return lastPost;
	}

	public void setLastPost(Date lastPost) {
		this.lastPost = lastPost;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getReplies() {
		return replies;
	}

	public void setReplies(int replies) {
		this.replies = replies;
	}

	public int getDigest() {
		return digest;
	}

	public void setDigest(int digest) {
		this.digest = digest;
	}
}
