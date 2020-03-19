//$Id$
package com.manik.general.Instrumentation;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.manik.general.Logging.MyLogger;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassMap;
import javassist.ClassPool;
import javassist.CodeConverter;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.SignatureAttribute;

public class ModelInstrumentation {
	private static final Logger LOGGER =  MyLogger.getLogger(ModelInstrumentation.class.getName());

    private final CtClass modelClass;

    public ModelInstrumentation() throws NotFoundException {
        ClassPool cp = ClassPool.getDefault();
        cp.insertClassPath(new ClassClassPath(this.getClass()));
        this.modelClass = cp.get("com.manik.general.javalite.Model");
    }

    public byte[] instrument(CtClass target) throws Exception {
        try{
            doInstrument(target);
            target.detach();
            return target.toBytecode();
        }catch(Exception e){
            throw e;
        }
    }

    private void doInstrument(CtClass target) throws NotFoundException, CannotCompileException{
        CtMethod[] modelMethods = modelClass.getDeclaredMethods();
        CtMethod[] targetMethods = target.getDeclaredMethods();

        CtMethod modelGetClass = modelClass.getDeclaredMethod("modelClass"); //No I18n
        CtMethod newGetClass = CtNewMethod.copy(modelGetClass, target, null);
        newGetClass.setBody("{ return " + target.getName() + ".class; }"); //No I18n

        // do not convert Model class to Target class in methods
        ClassMap classMap = new ClassMap();
        classMap.fix(modelClass);

        // convert Model.getDaClass() calls to Target.getDaClass() calls
        CodeConverter conv = new CodeConverter();
        conv.redirectMethodCall(modelGetClass, newGetClass);

        for (CtMethod method : modelMethods) {
            int modifiers = method.getModifiers();
            if (Modifier.isStatic(modifiers)) {
                if (targetHasMethod(targetMethods, method)) {
                    LOGGER.log(Level.SEVERE, "Detected method: " + method.getName() + ", skipping delegate.");
                } else {
                    CtMethod newMethod;
                    if (Modifier.isProtected(modifiers) || Modifier.isPublic(modifiers)) {
                        newMethod = CtNewMethod.copy(method, target, classMap);
                        newMethod.instrument(conv);
                    } else if ("modelClass".equals(method.getName())) { //No I18n
                        newMethod = newGetClass;
                    } else {
                        newMethod = CtNewMethod.delegator(method, target);
                    }

                    // Include the generic signature
                    for (Object attr : method.getMethodInfo().getAttributes()) {
                        if (attr instanceof SignatureAttribute) {
                            newMethod.getMethodInfo().addAttribute((SignatureAttribute) attr);
                        }
                    }
                    target.addMethod(newMethod);
                }
            }
        }
    }

    private boolean targetHasMethod(CtMethod[] targetMethods, CtMethod delegate) {
        for(CtMethod targetMethod : targetMethods){
            if(targetMethod.equals(delegate)){
                return true;
            }
        }
        return false;
    }

}
