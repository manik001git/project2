//$Id$
package com.source.exceptions;

public class QueryConstructException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public static final String MESSAGE = "Error in construct query."; // no i18n

	public QueryConstructException(){
		super(MESSAGE);
	}

	public QueryConstructException(String msg) {
		super(msg);
	}
	
	public QueryConstructException(Exception e) {
		super(e);
	}

}
