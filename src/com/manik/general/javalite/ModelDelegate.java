//$Id$
package com.manik.general.javalite;

import java.util.Map;

public class ModelDelegate {

//	private static final Logger LOGGER = MyLogger.getLogger(ModelDelegate.class.getName());
	
	@SuppressWarnings("unchecked")
	public static <T extends Model> T instance(Map<String, Object> map, MetaModel metaModel){
		return (T) instance(map, metaModel, metaModel.getModelClass());
	}
	
	public static <T extends Model> T instance(Map<String, Object> map, MetaModel metaModel, Class<T> clazz){
		try{
			T t = clazz.newInstance();
			t.hydrate(map, metaModel);
			return t;
		}catch(InstantiationException ie){
			throw new RuntimeException("Error while initialize model with map : " + clazz);
		}catch(IllegalAccessException iae){
			throw new RuntimeException(iae.getMessage(), iae);
		}
	}
	
	public static MetaModel metaModelOf(Class<? extends Model> clazz) {
        return Registry.getInstance().getMetaModelByClass(clazz);
    }

	
}
