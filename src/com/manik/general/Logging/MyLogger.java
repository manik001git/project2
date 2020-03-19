//$Id$
package com.manik.general.Logging;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MyLogger extends Logger{

	private static final Handler fileHandler;
	static String pattern;
	int count = 25, limit = 200000;
	static boolean append = false;
	static{
		pattern = LogManager.getLogManager().getProperty("file");
		fileHandler = getFileHandler();
	}
	protected MyLogger(String name, String resourceBundleName) {
		super(name, resourceBundleName);
	}
	
	public static Logger getLogger(String name){
		Logger logger = Logger.getLogger(name);
		try{
			//LogManager.getLogManager().readConfiguration(new FileInputStream("mylogging.properties"));
	        if(fileHandler != null){
	        	//fileHandler.setFormatter(new LogFormatter());
	        	logger.addHandler(fileHandler);
			}
		}catch(Exception e){
			
		}
		return logger;
	}
	
	private static Handler getFileHandler(){
		try{
			return new FileHandler("/Users/mani-5690/Desktop/Logs/hello.txt", 2000, 25, true);
		}catch(Exception e){
			
		}
		return null;
	}

	
}
