//$Id$
package com.manik.general.Logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter{

	Date dat = new Date();
	private Object[] args = new Object[1];
	
	private StringBuilder sb = new StringBuilder();

	public LogFormatter() {
		
	}

	public synchronized String format(LogRecord record) {
		this.sb.setLength(0);
		
		this.dat.setTime(record.getMillis());
		this.args[0] = this.dat;
		StringBuffer text = new StringBuffer();
		

		
		this.sb.append("\"").append(text).append("\"").append(" ");
		this.sb.append("\"").append(record.getThreadID()).append("\"").append(" ");
		this.sb.append("\"").append(record.getSourceClassName()).append("\"").append(" ");
		this.sb.append("\"").append(record.getSourceMethodName()).append("\"").append(" ");
		this.sb.append("\"").append(record.getLevel().getLocalizedName()).append("\"").append(" ");
		Long startTime = System.currentTimeMillis();
		Long timeInMillis = startTime != null ? record.getMillis() - startTime : -1L;
		this.sb.append("\"").append(timeInMillis).append("\"").append(" ");
		

		String message = this.formatMessage(record);
		String throwable = escapeQuotedString(message);
		this.sb.append("\"").append(throwable).append("\"").append(" ");
		

		if(record.getThrown() != null){
			throwable = formatThrowable(record.getThrown());
			String escapedThrowable = escapeQuotedString(throwable);
			this.sb.append("\"").append(escapedThrowable).append("\"");
		}else{
			this.sb.append("\"\"");
		}

		this.sb.append(" ");
		this.sb.append('\n');
		this.sb.append('\n');
		return this.sb.toString();
	}

	private static String escapeQuotedString(String value) {
		return value.replaceAll("\"", "\"\"");
	}
	
	public static String formatThrowable(Throwable t) {
		if(t != null){
			StringWriter sw = null;
			PrintWriter pw = null;
			try{
				sw = new StringWriter(1024);
				pw = new PrintWriter(sw);
				t.printStackTrace(pw);
			}finally {
				if(pw != null){
					pw.close();
				}
			}
			return String.valueOf(sw);
		}
		return null;
	}


}
