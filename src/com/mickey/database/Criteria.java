//$Id$
package com.mickey.database;

import com.source.exceptions.QueryConstructException;

public class Criteria implements QueryConstants{

	public static final int AND = 0;
	public static final int OR = 1;
	
	private String leftCriteria, rightCriteria;
	private int operator = AND;
	
	public Criteria(Column column, Object value, int comparator) throws QueryConstructException {
		if(column == null){
			throw new QueryConstructException("Column cannot be null");
		}
		this.rightCriteria = null;
		this.leftCriteria = process(column, value, comparator);
	}
	
	private Criteria(String left, String right, int operator) throws QueryConstructException {
		this.leftCriteria = left;
		this.rightCriteria = right;
		this.operator = operator;
	}
	
	public Criteria and(Criteria criteria) throws QueryConstructException{
		return new Criteria(this.toString(), criteria != null ? criteria.toString() : null, AND);
	}
	
	public Criteria or(Criteria criteria) throws QueryConstructException{
		return new Criteria(this.toString(), criteria != null ? criteria.toString() : null, OR);
	}
	
	private String process(Column column, Object value, int comparator) throws QueryConstructException{
		if(column == null){
			throw new QueryConstructException("Criteria Column cannot be null");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("( ").append(column.toString());
		switch(comparator){
			case EQUAL		:	if(value == null){
									sb.append(" IS NULL ");
								}else{
									sb.append(" = ");
								}
								break;
								
			case NOT_EQUAL	:	if(value == null){
									sb.append(" IS NOT NULL ");
								}else{
									sb.append(" != ");
								}
								break;
		}
		if(value != null){
			if(value instanceof Column){
				sb.append(value.toString());
			}else{
				sb.append(value+"");
			}
		}
		sb.append(" )");
		return sb.toString();
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if(leftCriteria != null){
			if(rightCriteria != null){
				sb.append("( ").append(leftCriteria);
				if(operator == AND){
					sb.append(" AND ");
				}else if(operator == OR){
					sb.append(" OR ");
				}
				sb.append(rightCriteria).append(" )");
			}else{
				sb.append(leftCriteria);
			}
		}
		return sb.toString();
	}
	
}
