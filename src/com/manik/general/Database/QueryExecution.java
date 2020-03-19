//$Id$
package com.manik.general.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.manik.general.Database.Connection.ConnectionAccess;
import com.manik.general.Logging.MyLogger;
import com.manik.project.Helper.Utils;

public class QueryExecution {
	
	private static final Logger LOGGER = MyLogger.getLogger(QueryExecution.class.getName());
	
	public static boolean createDatabase(Connection con, String db){
		try{
			if(con != null && db != null && !"".equals(db)){
				if(!isDatabaseExists(con, db)){
					Statement st = con.createStatement();
					st.executeUpdate("CREATE DATABASE IF NOT EXISTS "+db);
				}
				con.setCatalog(db);
				LOGGER.info("DB CREATED : " + db);
				return true;
			}
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, "Error while create DATABASE "+ db + e);
		}
		return false;
	}
	
	public static boolean isDatabaseExists(Connection con, String db) throws Exception{
		boolean isExists = false;
		if(con != null && db != null && !"".equals(db)){
			ResultSet rs = con.getMetaData().getCatalogs();
			if(rs != null){
				while(rs.next()){
					String dbname = rs.getString(1);
					if(db.equals(dbname)){
						isExists = true;
						break;
					}
				}
			}
		}
		return isExists;
	}
	
	/**
	 * Used for UPDATE, DELETE, INSERT Operations
	 * @param query
	 */
	public static boolean executeQuery(String query, String db){
		if(Utils.isNotNullOrEmpty(query)){
			try{
				Connection con = ConnectionAccess.getConnection(db);
				if(con != null){
					Statement st = con.createStatement();
					int status = st.executeUpdate(query);
					LOGGER.info("Query Executed : " + query);
					return status != 0;
				}
			}catch(Exception e){
				LOGGER.log(Level.SEVERE, "SQL Exception while execute query : " + query , e);
			}
		}
		return false;
	}
	
	/**
	 * Used for select query.
	 * @param query
	 */
	public static ResultSet executeSelectQuery(String query, String db){
		if(Utils.isNotNullOrEmpty(query)){
			Connection con = null;
			try{
				con = ConnectionAccess.getConnection(db);
				if(con != null){
					Statement st = con.createStatement();
					return st.executeQuery(query);
				}
				LOGGER.info("Query Executed : " + query);
			}catch(Exception e){
				LOGGER.log(Level.SEVERE, "SQL Exception while execute query : " + query , e);
			}
		}
		return null;
	}
}
