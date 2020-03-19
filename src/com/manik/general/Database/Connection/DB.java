//$Id$
package com.manik.general.Database.Connection;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;

public class DB implements Closeable{

	public static final String DEFAULT_NAME = "manik_passwords";
	private final String name;
	
	public DB(String name){
		this.name = name;
	}
	
	public DB(){
		this.name = DEFAULT_NAME;
	}
	
	public void open(Connection con){
		try{
			ConnectionAccess.attach(this.name, con);
		}catch(Exception e){
			throw new RuntimeException("Database Failed to connect");
		}
	}
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
