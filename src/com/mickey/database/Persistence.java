//$Id$
package com.mickey.database;

import java.sql.Connection;

public class Persistence {

	private Connection con = null;
	
	public Persistence getPersistence(long lookupId) {
		return new Persistence();
	}
	
}
