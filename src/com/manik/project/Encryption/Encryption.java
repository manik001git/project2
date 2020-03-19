//$Id$
package com.manik.project.Encryption;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.manik.general.Logging.MyLogger;
import com.manik.project.Helper.Utils;

public class Encryption extends EncryptUtils{

	private static final Logger LOGGER = MyLogger.getLogger(Encryption.class.getName());
	
	public static String encrypt(String value){
		try{
			if(Utils.isNotNullOrEmpty(value)){
				String key = getEncryptionKey();
			}
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, "Error while Encrypt value", e);
		}
		return null;
	}
	
	public static String decrypt(String value){
		try{
			if(Utils.isNotNullOrEmpty(value)){
				String key = getEncryptionKey();
			}
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, "Error while Decrypt value", e);
		}
		return null;
	}
	
}
