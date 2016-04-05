package com.forum.domain;

import java.util.Date;
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
	
	@Column(name="password",nullable=false)
	private String password;
	
	@Column(name="locked",columnDefinition="int not null default 0")
	private int locked=USER_UNLOCK;
	
    @Column(name = "last_ip")
	private String lastIp;
    
	@Column(name="credit",columnDefinition="int default 0")
	private int credit;
	
	@Column(name = "last_visit")
	private Date lastVisit;
	
	@ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE},fetch=FetchType.LAZY)
	@JoinTable(name="t_board_manager",joinColumns={@JoinColumn(name="user_id")}, 
		inverseJoinColumns={@JoinColumn(name="board_id")} )
	private Set<Board> manBoards=new HashSet<Board>();

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

	public String getLastIp() {
		return lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	public Date getLastVisit() {
		return lastVisit;
	}

	public void setLastVisit(Date lastVisit) {
		this.lastVisit = lastVisit;
	}
}
