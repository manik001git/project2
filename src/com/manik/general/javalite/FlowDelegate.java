//$Id$
package com.manik.general.javalite;

import java.util.logging.Logger;

import com.manik.general.Logging.MyLogger;
import com.manik.general.mysql.query.BeanHandler;
import com.manik.project.Constants.AttributeConstants;
import com.manik.project.Util.Attributes;
import com.manik.project.Util.Condition;

public class FlowDelegate extends ModelDelegate {
	
	private static final Logger LOGGER = MyLogger.getLogger(FlowDelegate.class.getName());
	
	public static <T extends Model> T findById(Long id, Class<? extends Model> clazz, Attributes...attributes){
		if(id != null){
			MetaModel meta = metaModelOf(clazz);
			
			Condition cond =  new Condition(meta.getTable(), new Attributes(meta.getTable(), meta.getPrimaryKey()), id, AttributeConstants.EQUAL);
			FlowList<T> list = new FlowList<>(clazz, meta, cond);
			if(attributes != null && attributes.length > 0){
				list.select(attributes);
			}
			
			return !list.isEmpty() ? list.get(0) : null;
		}
		return null;
	}
	
	public static <T extends Model> boolean delete(Class<T> clazz, Long id) throws Exception{
		MetaModel meta = metaModelOf(clazz);
		Condition cond = new Condition(meta.getTable(), new Attributes(meta.getTable(), meta.getPrimaryKey()), id, AttributeConstants.EQUAL);
		return delete(clazz, cond);
	}
	
	public static <T extends Model> boolean delete(Class<T> clazz, Condition cond) throws Exception{
		//MetaModel meta = metaModelOf(clazz);
		if(cond != null){
			//String table = meta.getTable();
			return BeanHandler.getWriteBean().delete(cond);
		}
		return false;
	}
}
