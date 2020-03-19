//$Id$
package com.manik.general.mysql.query;

import com.manik.project.Util.Attributes;

public class OrderBy {
	
	private Attributes orderBy;
	
	public OrderBy(Attributes attr){
		this.orderBy = attr;
	}
	
	public Attributes getOrderByColumn(){
		return this.orderBy;
	}
}
