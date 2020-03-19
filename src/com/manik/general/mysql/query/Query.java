//$Id$
package com.manik.general.mysql.query;

import java.util.ArrayList;
import java.util.List;

import com.manik.project.Util.Condition;

public class Query {
	 
	private Table table;
	private Range range;
	private OrderBy orderBy;
	private GroupBy groupBy;
	private List<Column> selectCols = new ArrayList<>();
	private List<Condition> conds = new ArrayList<>();
	private List<Join> joins = new ArrayList<>();
	private int type;
	
	public Query(Table table){
		 this.table = table;
	}
	
	public void setRange(Range range){
		this.range = range;
	}
	
	public void setOrderBy(OrderBy or){
		this.orderBy = or;
	}
	
	public void setGroupBy(GroupBy gb){
		this.groupBy = gb;
	}
	
	public void setSelectColumns(List<Column> selectCols){
		this.selectCols = selectCols;
	}
	
	public void addSelectColumns(Column col){
		this.selectCols.add(col);
	}
	
	public void setConditions(List<Condition> conds){
		this.conds = conds;
	}
	
	public void addCondition(Condition cond){
		this.conds.add(cond);
	}
	
	public void setJoins(List<Join> joins){
		this.joins = joins;
	}
	
	public void addCondition(Join join){
		this.joins.add(join);
	}
	
	public void setType(int type){
		this.type = type;
	}
	
	public Table getTable(){
		 return this.table;
	}
	
	public Range getRange(){
		return this.range;
	}
	
	public OrderBy getOrderBy(){
		return this.orderBy;
	}
	
	public GroupBy getGroupBy(){
		return this.groupBy;
	}
	
	public List<Column> getSelectColumns(){
		return this.selectCols;
	}
	
	public List<Condition> getConditions(){
		return this.conds;
	}
	
	public List<Join> getJoins(){
		return this.joins;
	}
	
	public int getType(){
		return this.type;
	}
}
