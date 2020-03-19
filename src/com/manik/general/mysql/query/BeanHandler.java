//$Id$
package com.manik.general.mysql.query;

public class BeanHandler {
	
	private static ReadBean readBean = null;
	private static WriteBean writeBean = null;
	
	public static ReadBean getReadBean(){
		if(readBean == null){
			readBean = new ReadBeanImpl();
		}
		return readBean;
	}
	
	public static WriteBean getWriteBean(){
		if(writeBean == null){
			writeBean = new WriteBeanImpl();
		}
		return writeBean;
	}
}
