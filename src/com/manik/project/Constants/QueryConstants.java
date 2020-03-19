//$Id$
package com.manik.project.Constants;

public class QueryConstants {
	
	public static enum QueryType{
		SELECT("SELECT"), INSERT("INSERT INTO"), UPDATE("UPDATE"), DELETE("DELETE");
		public int value;
		public String name;
		private QueryType(String name){
			this.value = ordinal();this.name = name;
		}
	}
}
