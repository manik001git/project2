//$Id$
package com.mickey.database;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestClass {
	
	private static Logger LOGGER;

	static {
		System.setProperty("java.util.logging.config.file","/Users/mani-5690/Desktop/Eclipse 3/eclipse/Eclipse.app/Contents/MacOS/ZIDE/My Passwords/webapps/WEB-INF/conf/logging.properties");
		LOGGER = Logger.getLogger(TestClass.class.getName());
	}
	
	public static void main(String[] args) {
		try{
		 
			LOGGER.log(Level.SEVERE, "Test Message.");
			Table base = new Table("BPJOBS");
			Table ref = new Table("BPJOBFIELDSVALUE");
			
			SelectQueryImpl query = new SelectQueryImpl(base);
			
			Criteria joinCri = new Criteria(new Column(base, "JOB_ID"), new Column(ref, "JOB_ID"), QueryConstants.EQUAL);
			Join join = new Join(base, ref, joinCri, Join.INNER_JOIN);
			query.addJoin(join);
			
			Criteria cri = new Criteria(new Column(base, "JOB_ID"), "123456789", QueryConstants.EQUAL);
			query.setCriteria(cri);
			
			query.addSelectColumn(new Column(base, "JOB_ID"));
			query.addSelectColumn(new Column(ref, "JOB_ID"));
			
			String s = query.getQuery();
			System.out.print(s);
			
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, "Error", e);
		}

	}

}
