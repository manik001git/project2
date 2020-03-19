//$Id$
package com.manik.general.mysql.query;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.manik.general.Database.Database;
import com.manik.general.Database.QueryExecution;
import com.manik.general.Logging.MyLogger;
import com.manik.general.javalite.FlowList;
import com.manik.general.javalite.MetaModel;
import com.manik.general.javalite.Model;
import com.manik.general.javalite.ModelDelegate;
import com.manik.project.Helper.Utils;

public class ReadBeanImpl implements ReadBean{

	private static final Logger LOGGER = MyLogger.getLogger(ReadBeanImpl.class.getName()); 
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getDataSet(MetaModel model, FlowList list){
		try{
			String qry = SelectQueryImpl.formSelectQuery(model, list);
			if(Utils.isNotNullOrEmpty(qry)){
				ResultSet rs = QueryExecution.executeSelectQuery(qry,Database.getCurrentDatabase());					
				List<Model> models = getModelLists(rs, model);
				list.addDelegates(models);
			}
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, "Error while get Data Set", e);
		}
	}
	
	private static List<Model> getModelLists(ResultSet rs, MetaModel metaModel) throws SQLException{
		List<Model> models = new ArrayList<>();
		if(rs != null){
			ResultSetMetaData rsm = rs.getMetaData();
			while(rs.next()){
				Map<String, Object> modelMap = getModelMap(rsm, rs);
				Model model = ModelDelegate.<Model>instance(modelMap, metaModel);
				models.add(model);
			}
		}
		return models;
	}
	
	private static Map<String, Object> getModelMap(ResultSetMetaData rsm, ResultSet rs) throws SQLException{
		if(rs != null && rsm != null){
			Map<String, Object> model = new HashMap<>();
			int colCount = rsm.getColumnCount();boolean hasNoValue = true;
			for(int i = 1; i <= colCount ; i++){
				String colName = rsm.getColumnName(i);
				Object val = rs.getObject(i); // TODO: work with values.
				if(colName != null && val != null){
					model.put(colName, val);
					hasNoValue = false;
				}
			}
			return hasNoValue ? null : model; 
		}
		return null;
	}
}
