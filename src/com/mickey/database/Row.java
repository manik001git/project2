//$Id$
package com.mickey.database;

import java.util.HashMap;
import java.util.Map;

import com.source.exceptions.QueryConstructException;

public class Row {

	private Table table;
	private Map<Column, Object> values = new HashMap<>();
	
	public Row(Table table) throws QueryConstructException {
		if(table == null){
			throw new QueryConstructException();
		}
		this.table = table;
	}
	
	public void set(Column column, Object value){
		if(column != null){
			values.put(column, value);
		}
	}
	
	public Object get(Column column){
		return column != null ? this.values.get(column) : null;
	}
	
	public Table getTable(){
		return this.table;
	}

}
