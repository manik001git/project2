//$Id$
package com.manik.general.mysql.query;

public class Range {
	private int startIndex, endIndex;
	
	public Range(int start, int end){
		this.startIndex = start;
		this.endIndex = end;
	}
	
	public int getStartIndex(){
		return this.startIndex;
	}
	
	public int getEndIndex(){
		return this.endIndex;
	}
}
