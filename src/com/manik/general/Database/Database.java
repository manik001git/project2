//$Id$
package com.manik.general.Database;

import java.io.File;
import java.sql.Connection;
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

import com.manik.general.Database.Connection.Base;
import com.manik.general.Database.Connection.ConnectionAccess;
import com.manik.general.Database.Connection.DB;
import com.manik.general.Database.Connection.MySQLConnection;
import com.manik.general.Logging.MyLogger;
import com.manik.project.Database.Utils.DatabaseConstants;
import com.manik.project.Helper.Utils;


public class Database implements DatabaseConstants{

	private static final Logger LOGGER = MyLogger.getLogger(Database.class.getName());
	
	
	public static String getCurrentDatabase(){
		return DB.DEFAULT_NAME;
	}
	
	public static void constructDatabase(){	
		try{
			File file = new File(System.getProperty("app.config.dir")+"/database.xml");
			if(file.exists()){
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document d = db.parse(file);
				
				if(Utils.isNotNull(d)){
					List<Node> tablesTags = XMLNode.getChildNodes(d.getElementsByTagName(TABLES), null);
					if(Utils.isNotNullOrEmpty(tablesTags)){
						for(Node tablesTag : tablesTags){
							List<Node> tables = XMLNode.getChildNodes(tablesTag.getChildNodes(), TABLE);
							if(Utils.isNotNullOrEmpty(tables)){
								for(Node table : tables){
									Map<String, String> tableProps = getTableProperties(table);
									List<Map<String, String>> columnProps = getColumnProperties(table);
									Map<String, String> pkProps = getPrimaryKeyProperties(table);
									constructSQLQuery(tableProps, columnProps, pkProps);
								}
							}
						}
					}
				}
			}
		}catch(Exception exe){
			LOGGER.log(Level.SEVERE, "Error while construct Database.", exe);
		}
	}
	
	private static boolean isTable(Node node){
		return node != null && TABLE.equals(node.getNodeName());
	}
	
	private static Map<String, String> getTableProperties(Node table){
		Map<String, String> props = new LinkedHashMap<>();
		if(isTable(table)){
			XMLNode node = new XMLNode(table);
			props = node.getAllAttributes();
		}
		return props;
	}
	
	private static List<Map<String, String>> getColumnProperties(Node table){
		List<Map<String, String>> columnProps = new ArrayList<>();
		if(isTable(table)){
			XMLNode node = new XMLNode(table);
			List<Node> columns = node.getChildren(COLUMN);
			if(Utils.isNotNullOrEmpty(columns)){
				for(Node col : columns){
					XMLNode column = new XMLNode(col);
					List<Node> props = column.getAllChildrens();
					Map<String, String> map = new LinkedHashMap<>();
					if(props != null && !props.isEmpty()){
						for(Node prop : props){
							map.put(prop.getNodeName(), prop.getTextContent());
						}
					}
					columnProps.add(map);
				}
			}
		}
		return columnProps;
	}
	
	private static Map<String, String> getPrimaryKeyProperties(Node table){
		Map<String, String> pkMap = new LinkedHashMap<>();
		if(isTable(table)){
			XMLNode node = new XMLNode(table);
			Node primaryKey = node.getFirstChildren(PRIMARY_KEY);
			if(primaryKey != null){
				XMLNode pkNode = new XMLNode(primaryKey);
				Node pkCol = pkNode.getFirstChildren(PRIMARY_KEY_COLUMN);
				if(pkCol != null){
					pkMap.put(COLUMN, pkCol.getTextContent());
				}
				if(pkNode.hasAttribute(NAME)){
					pkMap.put(NAME, pkNode.getAttribute(NAME));
				}
			}
		}
		return pkMap;
	}
	
	private static void constructSQLQuery(Map<String, String> tProps,List<Map<String, String>> cProps, Map<String, String> pkProps){
		if(Utils.isNotNullOrEmpty(tProps) && Utils.isMapContains(tProps, NAME)){
			String qry = "CREATE TABLE IF NOT EXISTS "+ (tProps.get(NAME));
			if(Utils.isNotNullOrEmpty(cProps)){
				StringBuilder colStr = new StringBuilder();
				for(int i = 0; i < cProps.size(); i++){
					Map<String, String> prop = cProps.get(i);
					if(prop.containsKey(NAME)){
						String str = DatabaseUtils.getColumnPropertyString(prop);
						if(str != null){
							colStr.append(str).append(i < cProps.size() - 1 ? "," : "");
						}
					}
				}
				if(Utils.isNotNullOrEmpty(pkProps) && pkProps.containsKey(COLUMN)){
					colStr.append(",").append("PRIMARY KEY ( ").append(pkProps.get(COLUMN)).append(" )");
				}
				qry += " ( " + colStr.toString() + " ) ";
			}
			//TODO: execute default & customer databases
			QueryExecution.executeQuery(qry, Database.getCurrentDatabase());
		}
	}
	
	
	
	public static void initializeService(){
		try{
			String db = getCurrentDatabase();
			Connection con = ConnectionAccess.getConnection(db);
			con = con == null ? MySQLConnection.getConnection() : con;
			boolean isDBExists = QueryExecution.isDatabaseExists(con,db), isTablesExists = isDBExists;
			if(!isDBExists){
				isDBExists = QueryExecution.createDatabase(con, db);
			}
			if(isDBExists){
				con.setCatalog(db);
				Base.open(db, con);
				if(!isTablesExists){
					constructDatabase();
				}
			}
			
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, "error while initialize service "+ e);
		}
	}
	

}
