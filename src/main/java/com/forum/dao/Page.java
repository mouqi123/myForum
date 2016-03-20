package com.forum.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page implements Serializable {
	
	private static int DEFAULT_PAGE_SIZE=20;
	private int pageSize=DEFAULT_PAGE_SIZE;  //每页的记录数
	private long start;	//当前页第一条数据在数据库中的位置,从0开始
	private List<?> data; 	//当前页中存放的记录，类型一般为List
	private long totalCount; //总记录数
	
	public Page(){
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList<>());
	}
	
	/**
	 * @param start 本页数据在数据库中的起始位置
	 * @param totalSize 数据库中总记录
	 * @param pageSize 本页容量
	 * @param data 本页包含的数据
	 */
	public Page(long start, long totalSize,  int pageSize, List<?> data){
		this.start=start;
		this.totalCount=totalSize;
		this.pageSize=pageSize;
		this.data=data;
	}
	
	public long getTotalCount(){
		return totalCount;
	}
	
	public long getTotalPageCount(){
		if(totalCount%pageSize==0)
			return totalCount/pageSize;
		else return totalCount/pageSize+1;
	}
	
	public int getPageSize(){
		return pageSize;
	}
	
	public List<?> getResult(){
		return data;
	}
	
	public long getCurrentPageNo(){
		return start/pageSize+1;
	}
	
	public boolean hasNextPage(){
		return getCurrentPageNo()<getTotalPageCount();
	}
	
	public boolean hasPreviousPage(){
		return getCurrentPageNo()>1;
	}
	
	protected static int getStartOfPage(int pageNo){
		return getStartOfPage(pageNo,DEFAULT_PAGE_SIZE);
	}
	
	public static int getStartOfPage(int pageNo,int pageSize){
		return pageSize*(pageNo-1);
	}
}
