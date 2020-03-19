//$Id$
package com.mickey.database;

import com.source.exceptions.QueryConstructException;

public class Table {
	
	private String name;
	
	public Table(String name) throws QueryConstructException{
		if(name == null || "".equals(name)){
			throw new QueryConstructException();
		}
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String toString(){
		return this.name;
	}
}
