//$Id$
package com.manik.general.Database.Connection;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConnectionAccess {
	private static final ThreadLocal<HashMap<String, Connection>> CONNECTIONSTL = new ThreadLocal<>();
    
    public static Map<String, Connection> getConnectionMap(){
        if (CONNECTIONSTL.get() == null){
            CONNECTIONSTL.set(new HashMap<String, Connection>());
        }
        return CONNECTIONSTL.get();
    }
    
    public static Connection getConnection(String dbName){
        return getConnectionMap().get(dbName);
    }

    public static void attach(String dbName, Connection connection) {
        if(ConnectionAccess.getConnectionMap().get(dbName) != null){
            throw new RuntimeException("You are opening a connection " + dbName + " without closing a previous one. Check your logic. Connection still remains on thread: " + ConnectionAccess.getConnectionMap().get(dbName)); //No I18n
        }
        ConnectionAccess.getConnectionMap().put(dbName, connection);
    }

    public static void detach(String dbName){
        getConnectionMap().remove(dbName);
    }


    public static List<Connection> getAllConnections(){
        return new ArrayList<>(getConnectionMap().values());
    }
    
    public static void closeConnections() throws Exception{
		List<Connection> cons = getAllConnections();
		if(cons != null && !cons.isEmpty()){
			for(Connection con : cons){
				if(con != null){
					con.close();
				}
			}
		}
    }
}
