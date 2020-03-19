//$Id$
package com.manik.general.mysql.query;

import java.util.List;

import com.manik.project.Constants.JoinConstants;
import com.manik.project.Util.Condition;

import java.util.ArrayList;

public class Join implements JoinConstants{
	
	Table lhs, rhs;
	private int type;
	private List<Condition> joinConditions = new ArrayList<>();
	
	public Join(Table lhs, Table rhs, int type){
		this.lhs = lhs;
		this.rhs = rhs;
		this.type = type;
	}
	
	public void setJoinConditions(Condition cond){
		this.joinConditions.add(cond);
	}
	
	public List<Condition> getJoinConditions(){
		return this.joinConditions;
	}
	
	public int getJoinType(){
		return this.type;
	}
	
	public String getJoinTableName(){
		return rhs.getName();
	}
	
	public String getBaseTableName(){
		return lhs!=null?lhs.getName():null;
		
	}
}
