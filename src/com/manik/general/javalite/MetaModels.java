//$Id$
package com.manik.general.javalite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manik.general.activejdbc.CaseInsensitiveMap;

public class MetaModels {

	//private static final Logger LOGGER = MyLogger.getLogger(MetaModels.class.getName());
	
	private final Map<Class<? extends Model>, MetaModel> metaModelByClass = new HashMap<>();
	private final Map<String, MetaModel> metaModelByTableName = new CaseInsensitiveMap<>();
	private final Map<String, MetaModel> metaModelByClassName = new CaseInsensitiveMap<>();
	
	public void addMetaModels(Class<? extends Model> clazz, MetaModel m){
		metaModelByClass.put(clazz, m);
		metaModelByTableName.put(m.getTable(), m);
		metaModelByClassName.put(clazz.getSimpleName(), m);
	}
	
	public MetaModel getMetaModelByClass(Class<? extends Model> clazz){
		return this.metaModelByClass.get(clazz);
	}
	
	public MetaModel getMetaModelByTableName(String table){
		return this.metaModelByTableName.get(table);
	}
	
	public MetaModel getMetaModelByClassName(String className){
		return this.metaModelByClassName.get(className);
	}
	
	public String getTableName(Class<? extends Model> clazz){
		MetaModel m = this.metaModelByClass.get(clazz);
		return m != null ? m.getTable() : null;
	}
	
	public String[] getTableNames(String dbName){
		List<String> tableNames = new ArrayList<>();
        for (MetaModel metaModel : metaModelByTableName.values()) {
            if (metaModel.getDBName().equals(dbName)){
                tableNames.add(metaModel.getTable());
            }
        }
        return tableNames.toArray(new String[0]);
	}
	
	public void setColumnMetadata(Class<? extends Model> clazz, Map<String, ColumnMetaData> metaParams) {
		metaModelByClass.get(clazz).setColumnMetadata(metaParams);
    }
}
