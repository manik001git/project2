//$Id$
package com.mickey.database;

import com.source.exceptions.QueryConstructException;

public class DeleteQueryImpl {

	private Table table;
	private Criteria criteria;
	
	public DeleteQueryImpl(Table table) throws QueryConstructException {
		if(table == null){
			throw new QueryConstructException("table can't be null.");
		}
		this.table = table;
	}

	public DeleteQueryImpl(Table table, Criteria criteria) throws QueryConstructException {
		if(table == null || criteria == null){
			throw new QueryConstructException("table && criteria can't be null.");
		}
		this.table = table;
		this.criteria = criteria;
	}
	
	public void setCriteria(Criteria criteria){
		if(this.criteria != null){
			this.criteria = criteria;
		}
	}
	
	public String getQuery() throws QueryConstructException{
		if(table == null || criteria == null){
			throw new QueryConstructException("table && criteria can't be null.");
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		
		sb.append(this.table.getName());
		
		sb.append(" WHERE ").append(this.criteria.toString());
		
		return sb.toString();
	}
}
