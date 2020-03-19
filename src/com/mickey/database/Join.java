//$Id$
package com.mickey.database;

import com.source.exceptions.QueryConstructException;

public class Join {

	public static final int LEFT_JOIN = 0;
	public static final int RIGHT_JOIN = 1;
	public static final int INNER_JOIN = 2;
	
	
	private Table baseTable, referenceTable;
	private Criteria criteria;
	private int joinType;
	
	public Join(Table baseTable, Table referenceTable, Criteria criteria, int joinType) throws QueryConstructException {
		if(baseTable == null || referenceTable == null || criteria == null || (joinType != LEFT_JOIN && joinType != INNER_JOIN && joinType != RIGHT_JOIN)){
			throw new QueryConstructException("Primary Entity cannot be null");
		}
		this.baseTable = baseTable;
		this.referenceTable = referenceTable;
		this.criteria = criteria;
		this.joinType = joinType;
	}
	
	public Table getBaseTable(){
		return this.baseTable;
	}
	
	public Table getReferenceTable(){
		return this.referenceTable;
	}
	
	public Criteria getCriteria(){
		return this.criteria;
	}
	
	public int getJoinType(){
		return this.joinType;
	}
	
	public String toString(){
		StringBuilder join = new StringBuilder();
		int joinType = getJoinType();
		
		if(joinType == LEFT_JOIN){
			join.append("LEFT JOIN");
		}else if(joinType == INNER_JOIN){
			join.append("INNER JOIN");
		}else if(joinType == RIGHT_JOIN){
			join.append("RIGHT JOIN");
		}
		
		Table referenceTable = getReferenceTable();
		join.append(" ").append(referenceTable.getName()).append(" ON ");
		
		Criteria criteria = getCriteria();
		join.append(criteria.toString());
		
		return join.toString();
	}

}
