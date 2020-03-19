//$Id$
package com.manik.project.Constants;

public class ComparatorConstants {
	
	public static enum Comparators{
		EQUAL(1, "="), NOT_EQUAL(2, "!="), CONTAINS(3, "like"), IN(4, "in"), NOT_IN(5, "not in"), GREATER_THAN(6, ">"),GREATER_OR_EQUAL(7,">="), LESS_THAN(8, "<"), LESS_OR_EQUAL(9, "<=");
		private int value;
		private String type;
		private Comparators(int val, String type){
			this.value = val;this.type = type;
		}
		
		public static String getType(int val){
			for(Comparators c : Comparators.values()){
				if(c.value == val){
					return c.type;
				}
			}
			return Comparators.EQUAL.type;
		}
	}
}

