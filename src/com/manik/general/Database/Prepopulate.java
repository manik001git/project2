//$Id$
package com.manik.general.Database;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.manik.general.Logging.MyLogger;
import com.manik.project.Helper.Utils;

public class Prepopulate {

	private static final Logger LOGGER = MyLogger.getLogger(Prepopulate.class.getName());
	
	private static Map<String, List<Map<String, String>>> getData(){
		Map<String, List<Map<String, String>>> tablepropsmap = new LinkedHashMap<>();
		try{
			File file = new File(System.getProperty("app.config.dir")+"/prepoulate.xml");
			if(file.exists()){
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document d = db.parse(file);
				
				if(Utils.isNotNull(d)){
					List<Node> projects = XMLNode.getChildNodes(d.getElementsByTagName("project"), null);//expect only one project tag.
					if(Utils.isNotNullOrEmpty(projects)){
						for(Node projectTag : projects){
							List<Node> tables = XMLNode.getChildNodes(projectTag.getChildNodes(), null);
							if(Utils.isNotNullOrEmpty(tables)){
								for(Node table : tables){
									String tableName = table.getNodeName();
									XMLNode n = new XMLNode(table);
									if(tablepropsmap.containsKey(tableName)){
										List<Map<String, String>> list = tablepropsmap.get(tableName);
										list.add(n.getAllAttributes());
									}else{
										List<Map<String, String>> list = new ArrayList<>();
										list.add(n.getAllAttributes());
										tablepropsmap.put(tableName, list);
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, "Error while parse prepopulate.xml", e);
		}
		return tablepropsmap;
	}
	
	public static List<String> populateEntities(){	
		//return QueryFormation.formInsertQuery(getData());
		LOGGER.log(Level.SEVERE, "Success Message");
		return null;
	}
}
