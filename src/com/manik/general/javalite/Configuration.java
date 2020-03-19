//$Id$
package com.manik.general.javalite;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.manik.general.Logging.MyLogger;

public class Configuration {

	private static final Logger LOGGER = MyLogger.getLogger(Configuration.class.getName());
	//db vs models.
	private Map<String, List<String>> modelsMap = new HashMap<>();
	
	protected Configuration(){
		try{
			Enumeration<URL> resource = getClass().getClassLoader().getResources("activejdbc_models.properties");
			if(resource != null){
				while(resource.hasMoreElements()){
					URL url = resource.nextElement();
					LOGGER.info("Load models from: {}"+url.toExternalForm());
	                InputStream inputStream = null;
	                InputStreamReader isreader = null;
	                BufferedReader reader = null;
	                try{
	                    inputStream = url.openStream();
	                    isreader = new InputStreamReader(inputStream);
	                    reader = new BufferedReader(isreader);
	                    String line;
	                    while((line = reader.readLine()) != null){
	                        String[] parts = line.split(":");
	                        String modelName = parts[0];
	                        String dbName = parts[1];

	                        List<String> modelNames = modelsMap.get(dbName);
	                        if (modelNames == null) {
	                            modelNames = new ArrayList<>();
	                            modelsMap.put(dbName, modelNames);
	                        }
	                        modelNames.add(modelName);
	                    }
	                }finally{
	                	closeQuietly(reader);
	                    closeQuietly(isreader);
	                    closeQuietly(inputStream);
	                }
				}
		        if(modelsMap.isEmpty()){
		        	LOGGER.info("ActiveJDBC Warning: Cannot locate any models, assuming project without models.");
		            return;
		        }
			}
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, "Error while initializing models", e);
		}
	}
	
	private void closeQuietly(AutoCloseable reader){
		if(reader != null){
			try{
				reader.close();
			}catch(Exception e){
				
			}
		}
	}
	
	public List<String> getModelNames(String dbName) {
        return modelsMap.get(dbName);
    }
	
}
