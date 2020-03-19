//$Id$
package com.manik.general.javalite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manik.general.activejdbc.ModelCallback;
import com.manik.general.activejdbc.ModelExpr;
import com.manik.general.activejdbc.validation.Validator;

public class ModelRegistry {

	private final Map<ModelExpr<? extends Model>, ModelCallback<? extends Model>> afterSaveCallbacks = new HashMap<>();
	
	public List<Validator> validators = new ArrayList<>();
	
	
	//start callbacks
	public void doAfterSave(ModelCallback<? extends Model> asc, String... methods){
    	List<String> methodList = Arrays.asList(methods);
    	ModelExpr<? extends Model> fe = model -> {
    		return (methodList==null || methodList.isEmpty());
    	};
    	afterSaveCallbacksWithCondition(fe, asc);
    }
	
	public void afterSaveCallbacksWithCondition(ModelExpr<? extends Model> fe,ModelCallback<? extends Model> fc){
		if(fe==null){
			fe = t -> true;
		}
		this.afterSaveCallbacks.put(fe, fc);
	}
	    
	 public Map<ModelExpr<? extends Model>, ModelCallback<? extends Model>> afterSaveCallbacks(){
		 return afterSaveCallbacks;
	 }
	 
	 
	 //validators start.
	
}
