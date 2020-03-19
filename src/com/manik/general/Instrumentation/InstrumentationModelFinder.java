//$Id$
package com.manik.general.Instrumentation;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;

public class InstrumentationModelFinder {

	private final CtClass modelClass;
    private final List<CtClass> models = new ArrayList<>();
    private final ClassPool cp;
    private String currentDirectoryPath;


    protected InstrumentationModelFinder() throws NotFoundException, ClassNotFoundException {
        cp = ClassPool.getDefault();
        //any simple class will do here, but Model - it causes slf4j to be loaded during instrumentation.
        cp.insertClassPath(new ClassClassPath(this.getClass())); //No I18n
        modelClass = cp.get("com.manik.general.javalite.Model");
    }

    protected void processURL(URL url) throws URISyntaxException, IOException, ClassNotFoundException {
        File f = new File(url.toURI());
        if(f.isFile()){
            processFilePath(f);
        }else{
            processDirectoryPath(f);
        }
    }


    /**
     * Finds and processes property files inside zip or jar files.
     *
     * @param file zip or jar file.
     */
    private void processFilePath(File file) {
        try(ZipFile zip = new ZipFile(file)){
            if(file.getCanonicalPath().toLowerCase().endsWith(".jar")|| file.getCanonicalPath().toLowerCase().endsWith(".zip")){ //No I18n
                Enumeration<? extends ZipEntry> entries = zip.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();

                    if (entry.getName().endsWith("class")) {
                        InputStream zin = zip.getInputStream(entry);
                        tryClass(entry.getName().replace(File.separatorChar, '.').substring(0, entry.getName().length() - 6));
                        zin.close();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException ignore) {
        }
    }

    protected void processDirectoryPath(File directory) throws IOException, ClassNotFoundException {
        currentDirectoryPath = directory.getCanonicalPath();
        processDirectory(directory);
    }

    /**
     * Recursively processes this directory.
     *
     * @param directory - start directory for processing.
     */
    private void processDirectory(File directory) throws IOException, ClassNotFoundException {
        findFiles(directory);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    processDirectory(file);
                }
            }
        }
    }

    /**
     * This will scan directory for class files, non-recursive.
     *
     * @param directory directory to scan.
     * @throws IOException, NotFoundException
     */
    private void findFiles(File directory) throws IOException, ClassNotFoundException {
        File[] files = directory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				 return name.endsWith(".class");
			}    
        });

        if (files != null) {
            for (File file : files) {
                int current = currentDirectoryPath.length();
                String fileName = file.getCanonicalPath().substring(++current);
                String className = fileName.replace(File.separatorChar, '.').substring(0, fileName.length() - 6);
                tryClass(className);
            }
        }
    }

    protected void tryClass(String className) throws IOException, ClassNotFoundException {
        try {
            CtClass clazz = getClazz(className);
            if(isModel(clazz) && !models.contains(clazz)) {
            	models.add(clazz);
            }
        }catch(Exception e){

        }
    }

    protected CtClass getClazz(String className) throws NotFoundException {
        return cp.get(className);
    }

    protected boolean isModel(CtClass clazz) throws NotFoundException {
        return clazz != null && notAbstract(clazz) && clazz.subclassOf(modelClass) && !clazz.equals(modelClass);
    }

    private boolean notAbstract(CtClass clazz) {
        int modifiers = clazz.getModifiers();
        return !(Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers));
    }

    protected List<CtClass> getModels() {
        return models;
    }
}
