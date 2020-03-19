//$Id$
package com.manik.project.Util;

import com.manik.general.javalite.FlowList;
import com.manik.general.javalite.MetaModel;
import com.manik.general.javalite.Model;
import com.manik.general.javalite.ModelDelegate;
import com.manik.general.javalite.Registry;

public class FlowModel extends ModelDelegate{
	
	public static <T extends Model> FlowList<T> where(Class<? extends Model> clazz, Condition cond){
		FlowList<T> list = new FlowList<>(clazz, metaModelOf(clazz), cond);
		return list;
	}
	
	public static MetaModel metaModelOf(Class<? extends Model> clazz){
		return Registry.getInstance().getMetaModelByClass(clazz);
	}
}
