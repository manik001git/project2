//$Id$
package com.manik.general.Database.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.manik.general.Logging.MyLogger;

public class MySQLConnection {
	
	private static final Logger LOGGER = MyLogger.getLogger(MySQLConnection.class.getName());
	
	public static Connection getConnection(){
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
		}catch(ClassNotFoundException ene){
			LOGGER.log(Level.SEVERE, "Error While get Connection from mysql" + ene);
		}catch(SQLException sqe){
			LOGGER.log(Level.SEVERE, "SQLException While get Connection from mysql" + sqe);
		}
		return con;
	}
}
