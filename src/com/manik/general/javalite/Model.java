//$Id$
/**
 * 
 */
package com.manik.general.javalite;


import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.manik.general.Logging.MyLogger;
import com.manik.general.activejdbc.CaseInsensitiveMap;
import com.manik.project.Helper.Utils;
import com.manik.project.Util.Attributes;
import com.manik.project.Util.Condition;
import com.manik.project.Util.FlowModel;

public abstract class Model{
	
	private static final Logger LOGGER = MyLogger.getLogger(Model.class.getName());
	
	private Map<String, Object> attributes = new CaseInsensitiveMap<>();
	private Map<String, Object> nonModelAttributes = new CaseInsensitiveMap<>();
	protected MetaModel metaModel;
	
	protected Model(){
		this.metaModel = ModelDelegate.metaModelOf(getClass());
	}
	
	public static <T extends Model> FlowList<T> where(Condition cond){
		return FlowModel.where(Model.<T>modelClass(), cond);
	}
	
	public boolean save(){
		try{
			return true;
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, "ERROR while save model", e);
		}
		return false;
	}
	
	public static boolean delete(Condition cond){
		try{
			return true;
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, "ERROR while delete model", e);
		}
		return false;
	}
	
	
	private static <T extends Model> Class<T> modelClass() {
	    throw new RuntimeException("do instrument");
	}
	
	
	@SuppressWarnings("unchecked")
	public <T extends Model> T set(Attributes attr, Object val){
		if(attr != null){
			String attrname = attr.getName();
			if(this.metaModel.hasTableAttribute(attrname)){
				this.attributes.put(attrname, val);
			}
		}
		return (T)this;
	}
	
	protected void hydrate(Map<String, Object> map, MetaModel metaModel){
		if(Utils.isNotNullOrEmpty(map)){
			Set<String> modelAttrs = metaModel.getAttributeNames();
			for(Map.Entry<String, Object> entry : map.entrySet()){
				String key = entry.getKey();
				if(modelAttrs.contains(key)){
					this.attributes.put(key.toUpperCase(), entry.getValue());
				}else{
					this.nonModelAttributes.put(key.toUpperCase(), entry.getValue());
				}
			}
		}
	}
	
	public String getString(Attributes attr){
		if(attr != null){
			String name = attr.getName();
			Object val = this.attributes.get(name);
			return  val != null ? val.toString() : null;
		}
		return null;
	}
	
	public Long getLong(Attributes attr){
		if(attr != null){
			String name = attr.getName();
			Object val = this.attributes.get(name);
			return val != null && Utils.isNumeric(val.toString()) ? Long.valueOf(val.toString()) : null;
		}
		return null;
	}
	
	public Integer getInteger(Attributes attr){
		if(attr != null){
			String name = attr.getName();
			Object val = this.attributes.get(name);
			return val != null && Utils.isNumeric(val.toString()) ? Integer.valueOf(val.toString()) : null;
		}
		return null;
	}
	
	public Boolean getBoolean(Attributes attr){
		if(attr != null){
			String name = attr.getName();
			Object val = this.attributes.get(name);
			return val != null ? "true".equals(val.toString()) : null;
		}
		return null;
	}
	
	public MetaModel getMetaModelLocal() {
		return metaModel;
	}
	
}
