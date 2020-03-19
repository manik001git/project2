//$Id$
package com.manik.general.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.manik.general.Logging.MyLogger;
import com.manik.project.Helper.Utils;

public class QueryFormation {

	private static final Logger LOGGER = MyLogger.getLogger(QueryFormation.class.getName());
	
	public static List<String> formInsertQuery(Map<String, List<Map<String, String>>> props){
		List<String> queries = new ArrayList<>();
		try{
			if(Utils.isNotNullOrEmpty(props)){
				for(Map.Entry<String, List<Map<String, String>>> entry : props.entrySet()){
					String table = entry.getKey();
					List<Map<String, String>> list = entry.getValue();//should be unique columns in prepopulate.xml 
					if(Utils.isNotNullOrEmpty(list)){
						StringBuilder str = new StringBuilder();
						List<String> columns = new ArrayList<>(list.get(0).keySet());
						str.append("INSERT INTO "+ table + " (");
						for(int i = 0; i < columns.size(); i++){
							str.append(" "+columns.get(i)).append(i < columns.size()-1 ? ", " : " ) ");
						}
						str.append(" VALUES ");
						String valStr = "";
						for(int i = 0; i < list.size(); i++){
							List<String> v = new ArrayList<>(list.get(i).values());
							valStr += "( " + Utils.ListToString(v) + " ) " + (i < list.size()-1 ? ", " : "");
						}
						str.append(valStr);
						queries.add(str.toString());
					}
				}
			}
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, "Error while Form Insert Query", e);
		}
		return queries;
	}
}
