package com.forum.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.util.Assert;

public class BaseDao<T> {
	
	private Class<T> entityClass;
	@Autowired
	private HibernateTemplate hibernateTemplate;
	/**
	 *   通过反射获取子类确定的泛型类
	 */
	public BaseDao(){
		Type genType=getClass().getGenericSuperclass();
		Type[] params=((ParameterizedType)genType).getActualTypeArguments();
		entityClass=(Class)params[0];
	}
	
	public T load(Serializable id){
		return (T) hibernateTemplate.load(entityClass, id);
	}
	
	public T get(Serializable id){
		return (T) hibernateTemplate.get(entityClass, id);
	}
	
	public List<T> loadAll(){
		return hibernateTemplate.loadAll(entityClass);
	}
	
	public void save(T entity){
		hibernateTemplate.save(entity);
	}

	public void remove(T entity){
		hibernateTemplate.delete(entity);
	}
	
	public void update(T entity){
		hibernateTemplate.update(entity);
	}
	
	public List<?> find(String hql){
		return hibernateTemplate.find(hql);
	}
	
	public List<?> find(String hql,Object ... params){
		return hibernateTemplate.find(hql, params);
	}
	
	public void initiallize(Object entity){
		hibernateTemplate.initialize(entity);
	}
	
	public Session getSession(){
		return hibernateTemplate.getSessionFactory().getCurrentSession();
	}
	
	/**
	 * 创建一个Query,用给定的values填充‘？’
	 * @param hql
	 * @param values
	 * @return
	 */
	public Query createQuery(String hql,Object...values){
		Assert.hasText(hql);
		Query query=getSession().createQuery(hql);
		for(int i=0;i<values.length;i++){
			query.setParameter(i, values[i]);
		}
		return query;
	}

	public static String removeSelect(String hql){
		Assert.hasText(hql);
		int beginPos=hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos!=-1, "hql: "+hql +" must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	public static String removeOrders(String hql){
		Assert.hasText(hql);
		return hql.replaceAll("order\\s*by[\\w|\\W|\\s|\\S]*", "");
	}
	
	/**
	 *   用给定的页码号和每页的条目数，来获得一个页面。页面的条目由hql决定。 
	 * @param hql
	 * @param pageNo
	 * @param pageSize
	 * @param values
	 * @return
	 */
	public Page pageQuery(String hql,int pageNo,int pageSize,Object... values){
		Assert.hasText(hql);
		Assert.isTrue(pageNo>1,"page should start from 1");
		String countQueryString="select count(*) "+removeSelect(removeOrders(hql));
		List<?> countList=hibernateTemplate.find(countQueryString, values);
		long totalCount=(long) countList.get(0);
		
		if(totalCount<1)
			return new Page();
		int startIndex=Page.getStartOfPage(pageNo, pageSize);
		Query query=createQuery(hql, values);
		List<?> list=query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		
		return new Page(startIndex, totalCount, pageSize, list);
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
