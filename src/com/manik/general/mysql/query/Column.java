//$Id$
package com.manik.general.mysql.query;

import com.manik.project.Util.Attributes;

public class Column {
	
	private String table = null, columnName = null, columnAlias = null;
	
	public Column(Attributes column){
		this.table = column.getTableName();
		this.columnName = column.getName();
	}
	
	public Column(Attributes column, String alias){
		this.table = column.getTableName();
		this.columnName = column.getName();
		this.columnAlias = alias;
	}
	
	public String getTable(){
		return this.table;
	}
	
	public String getName(){
		return this.columnName;
	}
	
	public String getAlias(){
		return this.columnAlias;
	}
	
	@Override
	public String toString(){
		return "*".equals(this.getName()) ? "*" : "`"+this.getTable()+"`.`"+this.getName()+"`";
	}
}
