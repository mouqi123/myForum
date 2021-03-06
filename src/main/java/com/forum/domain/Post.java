package com.forum.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_post")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="post_type",discriminatorType=DiscriminatorType.STRING)
//1表示主贴，2表示回复贴.
@DiscriminatorValue("2")
public class Post extends BaseDomain {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="post_id")
	private int postId;
	
	@Column(name="post_title",nullable=false)
	private String postTitle;
	
	@Column(name="post_text",nullable=false,length=20000)
	private String postText;
	
	@Column(name="board_id",nullable=false)
	private int boardId;
	
	@Column(name="create_time",nullable=false)
	private Date createTime;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name="topic_id",nullable=false)
	private Topic topic;

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostText() {
		return postText;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
}
