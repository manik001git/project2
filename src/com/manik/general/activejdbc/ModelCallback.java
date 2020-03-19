//$Id$
package com.manik.general.activejdbc;

import com.manik.general.javalite.Model;

public interface ModelCallback<T extends Model> {
	
	public void doCallback(T m) throws Exception;

}
