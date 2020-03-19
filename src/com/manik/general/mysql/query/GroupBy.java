//$Id$
package com.manik.general.mysql.query;

import com.manik.project.Util.Attributes;

public class GroupBy {
	
	private Attributes groupBy;
	
	public GroupBy(Attributes groupBy){
		this.groupBy = groupBy;
	}
	
	public Attributes getGroupByColumn(){
		return this.groupBy;
	}
}
