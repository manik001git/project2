//$Id$
/**
 * 
 */
package com.manik.project.Util;

import com.manik.project.Constants.AttributeConstants;

/**
 * @author mani-5690
 *
 */
public class Attributes implements AttributeConstants{
	
	private String table = null, name = null;
	
	public Attributes(String table, String name){
		this.table = table;
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getTableName(){
		return this.table;
	}
	
	public Condition equal(Attributes rightCol){
		return new Condition(this.table, this, rightCol, EQUAL);
	}
	
	public Condition equal(Object value){
		return new Condition(this.table, this, value, EQUAL);
	}
	
	public Condition notequal(Attributes rightCol){
		return new Condition(this.table, this, rightCol, NOT_EQUAL);
	}
	
	public Condition notequal(Object value){
		return new Condition(this.table, this, value, NOT_EQUAL);
	}
}
