package com.forum.dao;

import org.springframework.stereotype.Repository;

import com.forum.domain.Board;

@Repository
public class BoardDao extends BaseDao<Board> {
	
	protected final String GET_BOARD_NUM="select count(f.boardId) from Board f";
	
	public long getBoardNum(){
		return (Long)getHibernateTemplate().iterate(GET_BOARD_NUM).next();
	}

}
