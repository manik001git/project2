//$Id$
package com.manik.general.mysql.query;

public class Table {
	
	private String table;
	
	public Table(String table){
		this.table = table;
	}
	
	public String getName(){
		return this.table;
	}
}
