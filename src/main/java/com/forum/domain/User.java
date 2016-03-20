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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author mutou
 *
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name="t_user")
public class User extends BaseDomain {
	/**
	 *  锁定用户对应的状态值
	 */
	public static final int USER_LOCK = 1;
	
	/**
	 *  用户解锁对应的状态值
	 */
	public static final int USER_UNLOCK=0;
	
	/**
	 *  管理员类型
	 */
	public static final int FORUM_ADMIN=2;
	
	/**
	 *  普通用户
	 */
	public static final int NORMAL_USER=1;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	private int userId;
	
	@Column(name="user_name",nullable=false)
	private String userName;
	
	//用户类型，1表示普通用户，2表示板块管理员，3表示论坛管理员。
	@Column(name="user_type",columnDefinition="int not null default 1")
	private int userType=NORMAL_USER;
	
	
	/**
	 *  用户所有的登录日志。
	 */
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy="user")
	private Set<LoginLog> longinLogs=new HashSet<>();
	
	@Column(name="password",nullable=false)
	private String password;
	
	@Column(name="locked",columnDefinition="int not null default 0")
	private int locked=USER_UNLOCK;
	
	@Column(name="credit",columnDefinition="int default 0")
	private int credit;
	
	@ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE},fetch=FetchType.LAZY)
	@JoinTable(name="t_board_manager",joinColumns={@JoinColumn(name="user_id")}, 
		inverseJoinColumns={@JoinColumn(name="board_id")} )
	private Set<Board> manBoards=new HashSet<Board>();
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="user")
	private Set<Topic> topics=new HashSet<>();
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="user")
	private Set<Post> posts=new HashSet<>();

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public Set<LoginLog> getLonginLogs() {
		return longinLogs;
	}

	public void setLonginLogs(Set<LoginLog> longinLogs) {
		this.longinLogs = longinLogs;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLocked() {
		return locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public Set<Board> getManBoards() {
		return manBoards;
	}

	public void setManBoards(Set<Board> manBoards) {
		this.manBoards = manBoards;
	}

	public Set<Topic> getTopics() {
		return topics;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
}
