//$Id$
package com.manik.general.listeners;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppServletListener implements ServletContextListener {

	private static final Logger LOGGER = Logger.getLogger(AppServletListener.class.getName());
	private static final String propFilePath = "/WEB-INF/conf/";
	
	static{
//		InputStream ins = 
//		LogManager.getLogManager().readConfiguration(ins);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		String appContextPath = sc.getRealPath("");
		System.setProperty("app.home",appContextPath);
		System.setProperty("java.util.logging.config.file","/Users/mani-5690/Desktop/log%.log");
		try{
			load(sc, "app.properties");
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, "Error While loading property files." + e);
		}
	}
	
	private static void load(InputStream is) throws IOException{
		if(is != null){
			try{
				Properties props = new Properties();
				props.load(is);
				Enumeration<?> elements = props.propertyNames();
				while(elements.hasMoreElements()) {
					String key = (String)elements.nextElement();
					String val = props.getProperty(key);
					if(val.contains("home/")){
						val = val.replace("home", System.getProperty("app.home"));
					}
					System.setProperty(key, val);
				}
				LOGGER.log(Level.INFO, "Property file has been loaded.");
			}finally{
				try{
					is.close();
				}catch(Exception e){
					
				}
			}
		}
	}
	
	private static void load(ServletContext sc, String propFileName) throws IOException {
		LOGGER.log(Level.INFO, "Going to load : " + propFileName);
		InputStream is = sc.getResourceAsStream(propFilePath + propFileName);
		load(is);
	}

}
