//$Id$
package com.manik.general.javalite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelFinder {
	
	private static final Map<String, List<Class<? extends Model>>> MODELCLASSES = new HashMap<>();
    private static final List<String> MODELCLASSNAMES = new ArrayList<>();
    private static final Map<String, Class<? extends Model>> MODELCLASSMAP = new HashMap<>();

	
    protected static void findModels(String dbName) throws IOException, ClassNotFoundException {
    	List<String> models = Registry.getInstance().getConfiguration().getModelNames(dbName);
    	if(models != null && !models.isEmpty()){
    		for(String model : models){
    			modelFound(model);
    		}
    	}else{
    		throw new RuntimeException("No models found. please do instrumentation.");
    	}
    }
    
    public static void modelFound(String modelClassName){
        synchronized (MODELCLASSNAMES){
            if(!MODELCLASSNAMES.contains(modelClassName)){
                MODELCLASSNAMES.add(modelClassName);
            }
        }
    }
    
    protected static List<Class<? extends Model>> getModelsForDb(String dbName) throws IOException, ClassNotFoundException {
        synchronized (MODELCLASSNAMES){
            if(!MODELCLASSES.containsKey(dbName)){
                for (String className : MODELCLASSNAMES) {
                    registerModelClass(className);
                }
            }
            return MODELCLASSES.get(dbName);
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void registerModelClass(String className) throws IOException, ClassNotFoundException {
        Class clazz = Class.forName(className);
        if (Model.class.isAssignableFrom(clazz) && clazz != null && !clazz.equals(Model.class)) {
            String dbName = MetaModel.getDBName(clazz);
            MODELCLASSES.computeIfAbsent(dbName,k->MODELCLASSES.put(dbName, new ArrayList<Class<? extends Model>>()));
            if(!MODELCLASSES.get(dbName).contains(clazz)){
                MODELCLASSES.get(dbName).add(clazz);
                MODELCLASSMAP.put(className, clazz);
            }
        }
    }
}
