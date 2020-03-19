//$Id$
package com.manik.general.javalite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.manik.general.Database.Connection.DB;
import com.manik.general.activejdbc.CaseInsensitiveMap;
import com.manik.general.activejdbc.CompositePK;
import com.manik.general.activejdbc.CustomAttribute;
import com.manik.general.activejdbc.CustomAttributes;
import com.manik.general.activejdbc.PrimaryKey;
import com.manik.general.activejdbc.Table;

public class MetaModel{
	
	private Class<? extends Model> modelClass;
	private String primaryKey, table, dbName;
	private String[] compositeKeys, pkIds;
	private Map<String, Map<String,String>> customAttributes;
	private Map<String, ColumnMetaData> columnMetadata;
	
	protected MetaModel(String dbName, Class<? extends Model> clazz){
		this.dbName = dbName;
		this.modelClass = clazz;
		this.table = getTableName(clazz);
		this.primaryKey = getPrimaryKey(clazz);
		this.compositeKeys = getCompositePKs(clazz);
		this.pkIds = this.compositeKeys != null ? this.compositeKeys : new String[]{this.primaryKey};
		this.customAttributes = getCustomAttributes(clazz);
	}
	
	public String getPrimaryKey(){
		return this.primaryKey;
	}
	
	public Class<? extends Model> getModelClass(){
		return this.modelClass;
	}
	
	public String getDBName(){
		return this.dbName;
	}
	
	private String getPrimaryKey(Class<? extends Model> clazz){
		PrimaryKey key = clazz.getAnnotation(PrimaryKey.class);
		return key != null ? key.value() : null;
	}
	
	private String getTableName(Class<? extends Model> clazz){
		Table key = clazz.getAnnotation(Table.class);
		return key != null ? key.value() : null;
	}
	
	private String[] getCompositePKs(Class<? extends Model> clazz){
		CompositePK key = clazz.getAnnotation(CompositePK.class);
		return key != null ? key.value() : null;
	}
	
	private Map<String, Map<String, String>> getCustomAttributes(Class<? extends Model> clazz){
		Map<String, Map<String, String>> map = null;
		CustomAttributes key = clazz.getAnnotation(CustomAttributes.class);
		CustomAttribute[] caList = key != null ? key.value() : null;
		if(caList != null && caList.length > 0){
			map = new CaseInsensitiveMap<>();
			for(CustomAttribute ca : caList){
				Map<String, String> child = new HashMap<>();
				String name = ca.name(), column = ca.column(), colType = ca.columnType(), type = ca.type(), cacheKey = ca.cacheKey();
				child.put("name", name);
				child.put("column", column);
				child.put("columnType", colType != null && "".equals(colType) ? colType : null);
				child.put("type", type != null && "".equals(type) ? type : null);
				child.put("cacheKey", cacheKey != null && "".equals(cacheKey) ? cacheKey : null);
				map.put(name, child);
			}
		}
		return map;
	}
	
	public String getTable(){
		return this.table;
	}
	
	public String[] getPKIds(){
		return this.pkIds;
	}
	
	public List<String> getCustomAttributes(){
		if(this.customAttributes != null && !this.customAttributes.isEmpty()){
			return new ArrayList<>(this.customAttributes.keySet());
		}
		return null;
	}
	
	public static String getDBName(Class<? extends Model> clazz){
		return DB.DEFAULT_NAME;
	}
	
	public void setColumnMetadata(Map<String, ColumnMetaData> columnMetadata){
        this.columnMetadata = columnMetadata;
    }
	
	public Set<String> getAttributeNames() {
        if(columnMetadata == null || columnMetadata.isEmpty()){
        	throw new RuntimeException("Failed to find table: " + getTable());
        }
        return Collections.unmodifiableSet(columnMetadata.keySet());
    }
	
	public boolean hasTableAttribute(String attribute) {
        return columnMetadata != null && attribute != null && columnMetadata.containsKey(attribute);
    }
}
