//$Id$
package com.manik.general.Logging;


import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

public class LogHandler extends FileHandler{
	
	private static String handlerName = LogHandler.class.getSimpleName();
	
	static String pattern = "application_logs%g.txt";
	static int count = 25, limit = 200000;
	static boolean append = false;
	static{
		pattern = LogManager.getLogManager().getProperty(handlerName + ".filename") != null ? LogManager.getLogManager().getProperty(handlerName + ".filename") : pattern;
		count = LogManager.getLogManager().getProperty(handlerName + ".count") != null ? Integer.valueOf(LogManager.getLogManager().getProperty(handlerName + ".count")) : count;
		limit = LogManager.getLogManager().getProperty(handlerName + ".limit") != null ? Integer.valueOf(LogManager.getLogManager().getProperty(handlerName + ".filename")) : limit;
		append = LogManager.getLogManager().getProperty(handlerName + ".filename") != null ? Boolean.valueOf(LogManager.getLogManager().getProperty(handlerName + ".filename")) : append;
	}
	
	public LogHandler() throws Exception {
		super(pattern, limit, count, append);
		this.setFormatter(new LogFormatter());
	}
	
	public synchronized void publish(LogRecord record) {
		super.publish(record);
	}
}
