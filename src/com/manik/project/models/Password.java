//$Id$
/**
 * 
 */
package com.manik.project.models;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.manik.general.activejdbc.PrimaryKey;
import com.manik.general.activejdbc.Table;
import com.manik.general.javalite.FlowList;
import com.manik.general.javalite.Model;
import com.manik.project.Attributes.PasswordAttributes;

@Table("Passwords")
@PrimaryKey("PASSWORD_ID")
public class Password extends Model implements PasswordAttributes{
	
	private static final Logger LOGGER = Logger.getLogger(Password.class.getName());
	
	public static boolean createPassword(String type, String value){
		return true;
	}
	
	public static FlowList<Password> updatePassword(Long passId){
		FlowList<Password> pass = Password.where(PASSWORD_ID.equal(passId));
		if(pass.size() > 0){
			return pass;
		}
		return pass;
	}
	
	public static boolean getPassword(){
		LOGGER.log(Level.SEVERE, "Success");
		return true;
	}
	
	public static boolean deletePassword(){
		return true;
	}
}
