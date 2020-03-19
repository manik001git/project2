//$Id$
package com.manik.general.activejdbc.validation;

import com.manik.general.javalite.Model;

public interface Validator {
	
	public void validate(Model m);
	
	public void setMessage(String msg);
}
