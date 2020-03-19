//$Id$
package com.mickey.database;

public class Range {

	private int index = 1, range = 100;
	
	public Range() {
		
	}
	
	public Range(int index, int range) {
		this.index = index;
		this.range = range;
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public int getRange(){
		return this.range;
	}

}
