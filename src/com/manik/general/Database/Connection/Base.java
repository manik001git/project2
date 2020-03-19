//$Id$
package com.manik.general.Database.Connection;

import java.sql.Connection;

public class Base {

	@SuppressWarnings("resource")//No i18n
	public static void open(String db, Connection con){
		new DB(db).open(con);
	}
}
