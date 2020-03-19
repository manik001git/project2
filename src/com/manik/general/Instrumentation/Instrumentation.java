//$Id$
package com.manik.general.Instrumentation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javassist.CtClass;
import javassist.NotFoundException;

import com.manik.general.Database.Connection.DB;
import com.manik.general.Logging.MyLogger;

public class Instrumentation {
	
	private static final Logger LOGGER = MyLogger.getLogger(Instrumentation.class.getName());
	private String outputDirectory;
	
	public Instrumentation(String outputDirectory){
		this.outputDirectory = outputDirectory;
	}
	
	public void instrument(){
		if(outputDirectory == null){
			throw new RuntimeException("Output Directory not set. Instrumentation failed");
		}
		FileOutputStream fout = null;
        try {
        	LOGGER.info("**************************** START INSTRUMENTATION ****************************");
            InstrumentationModelFinder mf = new InstrumentationModelFinder();
            File target = new File(outputDirectory);
            mf.processDirectoryPath(target);
            ModelInstrumentation mi = new ModelInstrumentation();
            

            for (CtClass clazz : mf.getModels()) {
                byte[] bytecode = mi.instrument(clazz);
                String fileName = getFullFilePath(clazz);
                fout = new FileOutputStream(fileName);
                fout.write(bytecode);
                fout.flush();
                fout.close();
                LOGGER.info("Instrumented class: " + fileName );
            }

            generateModelsFile(mf.getModels());
            LOGGER.info("**************************** END INSTRUMENTATION ****************************");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
        	if(fout!=null){
        		try {
					fout.close();
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, null, e);
				}
        	}
        }
		
	}
	
	private String getFullFilePath(CtClass modelClass) throws NotFoundException, URISyntaxException {
        return modelClass.getURL().toURI().getPath();
    }
	
	private static void generateModelsFile(List<CtClass> models) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    	String currPath = System.getProperty("activejdbc_properties_dir");
    	FileOutputStream fout = null;
    	try{
    		fout = new FileOutputStream(new File(currPath, "activejdbc_models.properties"));
	        for (CtClass model : models) {
	            fout.write((model.getName() + ":" + getDatabaseName(model) + "\n").getBytes());
	        }
    	}catch(Exception e){
    		LOGGER.log(Level.SEVERE, "", e);
    	}finally{
    		if(fout!=null){
    			fout.close();
    		}
    	}
    }
	
	protected static String getDatabaseName(CtClass model) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return DB.DEFAULT_NAME;
    }

}
