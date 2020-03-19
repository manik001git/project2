//$Id$
package com.manik.general.activejdbc;

import com.manik.general.javalite.Model;

public interface ModelExpr<T extends Model> {
	
	public boolean checkExpr(T m);
	
}
