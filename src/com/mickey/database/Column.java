//$Id$
package com.mickey.database;

import com.source.exceptions.QueryConstructException;

public class Column {

	private Table table;
	String column;
	
	public Column(Table table, String column) throws QueryConstructException{
		if(table == null || column == null || "".equals(column)){
			throw new QueryConstructException();
		}
		this.table = table;
		this.column = column;
	}
	
	public Table getTable(){
		return this.table;
	}
	
	public String getTableName(){
		return this.table.getName();
	}
	
	public String getColumn(){
		return this.column;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		if("*".equals(this.column)){
			return this.column;
		}
		sb.append(getTableName()).append(".").append(this.column);
		return sb.toString();
	}
}
