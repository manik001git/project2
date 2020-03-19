//$Id$
package com.manik.general.javalite;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.manik.general.Database.Connection.ConnectionAccess;
import com.manik.general.Logging.MyLogger;



public enum Registry {
	
	INSTANCE;
	
	
	private static final Logger LOGGER = MyLogger.getLogger(Registry.class.getName());

    private final MetaModels metaModels = new MetaModels();
    private final Map<Class<? extends Model>, ModelRegistry> modelRegistries = new HashMap<>();
    private final Configuration config = new Configuration();
    private final Set<String> initedDbs = new HashSet<>();
    
    
    public static Registry getInstance(){
    	return INSTANCE;
    }
    
    public Configuration getConfiguration(){
    	return config;
    }
    
    
    synchronized void init(String dbName){
    	if(dbName != null && !initedDbs.contains(dbName)){
	    	Connection c = null;
	    	try{
	    		ModelFinder.findModels(dbName);
	    		c = ConnectionAccess.getConnection(dbName);
	    		if(c == null){
	    			throw new RuntimeException("Mysql connection not available");
	    		}
	            List<Class<? extends Model>> modelClasses = ModelFinder.getModelsForDb(dbName);
	            registerModels(dbName, modelClasses);
	            DatabaseMetaData metaData = c.getMetaData();
	    		
	            for(Class<? extends Model> clazz : modelClasses){
	            	String table = metaModels.getTableName(clazz);
	            	Map<String, ColumnMetaData> columnMeta = fetchColumnMeta(metaData, table);
	            	registerColumnMetadata(clazz, columnMeta);
	            }
	            initedDbs.add(dbName);
	    	}catch(Exception e){
	    		LOGGER.log(Level.SEVERE, "cannot find models", e);
	    	}
    	}
    }
    
    private void registerModels(String dbName, List<Class<? extends Model>> classList){
    	for(Class<? extends Model> clazz : classList){
    		MetaModel m = new MetaModel(dbName, clazz);
    		metaModels.addMetaModels(clazz, m);
    		LOGGER.log(Level.INFO, "MetaModel added : "+ clazz.getName());
    	}
    }
    
    private Map<String, ColumnMetaData> fetchColumnMeta(DatabaseMetaData metaData, String table){
    	String[] names = table.split("\\.", 3);
        String schema = null, tableName;
        switch(names.length) {
        	case 1	:	tableName = names[0];break;
        	
        	case 2	:	schema = names[0];
        				tableName = names[1];
        				break;
        	
        	default	:	throw new RuntimeException("invalid table name: " + table); //No I18n
        }
        
        ResultSet rs = null;
        Map<String, ColumnMetaData> columns = null;
        try{
        	rs = metaData.getColumns(null, schema, tableName, null);
        	String dbProduct = metaData.getDatabaseProductName().toLowerCase();
 	        columns = getColumns(rs, dbProduct);
 	        rs.close();
 	        
 	        //try upper case table name - Oracle uses upper case
	        if (columns.isEmpty()) {
	            rs = metaData.getColumns(null, schema, tableName.toUpperCase(), null);
	            dbProduct = dbProduct.toLowerCase();
	            columns = getColumns(rs, dbProduct);
	            rs.close();
	        }
	
	        //if upper case not found, try lower case.
	        if (columns.isEmpty()) {
	            rs = metaData.getColumns(null, schema, tableName.toLowerCase(), null);
	            columns = getColumns(rs, dbProduct);
	            rs.close();
	        }
	
	        LOGGER.info(columns.size() > 0 ? "Fetched metadata for table:" + table : "Failed to retrieve metadata for table: '{}'" + table);
        }catch(Exception e){
        	LOGGER.log(Level.SEVERE, "Error while fetchColumnMeta ", e);
        }finally{
        	try{
        		if(rs != null){
        			rs.close();
        		}
        	}catch(Exception e){
        		
        	}
        }
        return columns;
    }
    
    private Map<String, ColumnMetaData> getColumns(ResultSet rs, String db) throws SQLException{
    	Map<String, ColumnMetaData> map = new HashMap<>();
    	while(rs.next()){
    		 if (!"h2".equals(db) || !"INFORMATION_SCHEMA".equals(rs.getString("TABLE_SCHEM"))) {
                 ColumnMetaData cm = new ColumnMetaData(rs.getString("COLUMN_NAME"), rs.getString("TYPE_NAME"), rs.getInt("COLUMN_SIZE"));  //No I18n
                 map.put(cm.getColumnName(), cm);
             }
    	}
    	return map;
    }
    
    private void registerColumnMetadata(Class<? extends Model> clazz, Map<String, ColumnMetaData> metaParams) {
        metaModels.setColumnMetadata(clazz, metaParams);
    }
    
    public MetaModel getMetaModelByTableName(String table) {
        return metaModels.getMetaModelByTableName(table);
    }
    
    public MetaModel getMetaModelByClassName(String className) {
        return metaModels.getMetaModelByClassName(className);
    }

    public MetaModel getMetaModelByClass(Class<? extends Model> clazz) {
    	String dbName = MetaModel.getDBName(clazz);
        init(dbName);
    	return metaModels.getMetaModelByClass(clazz);
    }
    
    public ModelRegistry modelRegistryOf(Class<? extends Model> modelClass) {
        ModelRegistry registry = modelRegistries.get(modelClass);
        if (registry == null) {
            registry = new ModelRegistry();
            modelRegistries.put(modelClass, registry);
        }
        return registry;
    }

}
