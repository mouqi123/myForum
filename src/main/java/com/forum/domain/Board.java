package com.forum.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name="t_board")
public class Board extends BaseDomain {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="board_id")
	private int boardId;
	
	@Column(name="board_name")
	private String boardName;
	
	@Column(name="board_desc")
	private String boardDesc;
	
	@Column(name="topic_num",columnDefinition="int not null default 0")
	private int topicNum;
	
	//与板块关联的用户，即板块或整个论坛的管理员。
	@ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE},mappedBy="manBoards",fetch=FetchType.LAZY)
	private Set<User> users=new HashSet<User>();

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getBoardDesc() {
		return boardDesc;
	}

	public void setBoardDesc(String boardDesc) {
		this.boardDesc = boardDesc;
	}

	public int getTopicNum() {
		return topicNum;
	}

	public void setTopicNum(int topicNum) {
		this.topicNum = topicNum;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
