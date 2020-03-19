//$Id$
package com.manik.general.mysql.query;



import com.manik.general.javalite.FlowList;
import com.manik.general.javalite.MetaModel;


public interface ReadBean {

	@SuppressWarnings("rawtypes")
	public void getDataSet(MetaModel model, FlowList list);
	
	
}
