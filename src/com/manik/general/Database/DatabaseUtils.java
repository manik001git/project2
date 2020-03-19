//$Id$
package com.manik.general.Database;



import java.util.Map;

import com.manik.project.Database.Utils.DatabaseConstants;
import com.manik.project.Helper.Utils;

public class DatabaseUtils implements DatabaseConstants{
	
	public static String getColumnPropertyString(Map<String, String> props){
		if(props != null){
			String name = props.get(NAME), dataType = props.get(DATA_TYPE), maxlen = props.get(MAX_LEN), nullable = props.get(NULLABLE), defVal = props.get(DEFAULT_VALUE);
			if(isValidName(name) && isValidDataType(dataType)){
				StringBuilder s = new StringBuilder();
				String modefiedType = getModifiedDataType(dataType, Utils.isNumeric(maxlen) ? Integer.valueOf(maxlen) : null);
				s.append(name.toUpperCase()).append(" ").append(modefiedType);
				
				if(nullable != null && "false".equals(nullable)){
					s.append(" NOT NULL ");
				}
				if(defVal != null && isValidDefaultValue(modefiedType, defVal)){
					s.append(" DEFAULT " + (isStringDataType(modefiedType) ? "'"+defVal+"'" : defVal));
				}
				return s.toString();
			}
		}
		return null;
	}
	
	public static boolean isValidName(String name){
		return Utils.isNotNullOrEmpty(name) && Utils.matches(name, "[A-Za-z0-9_-]+");
	}
	
	public static boolean isValidDataType(String type){
		return Utils.isNotNullOrEmpty(type) && DatabaseConstants.DataTypeConstants.ALLOWED_DATATYPES.contains(type.toUpperCase());
	}
	
	public static boolean isValidDefaultValue(String dataType, String defaultValue){
		if(isNumericDataType(dataType)){
			return Utils.isNumeric(defaultValue);
		}
		return true;
	}
	
	private static boolean isStringDataType(String type){
		return type != null && (type.contains(DataTypeConstants.TEXT) || type.contains(DatabaseConstants.DataTypeConstants.VARCHAR)); 
	}
	
	private static boolean isNumericDataType(String type){
		return type != null && (type.contains(DataTypeConstants.BIGINT) || type.contains(DataTypeConstants.TINYINT)); 
	}
	
	private static String getModifiedDataType(String type, Integer len){
		if(type != null){
			len = len != null ? len : 255;
			switch(type){
				case DataTypeConstants.VARCHAR		:	type = type+"("+len+")";break;
				
				case DataTypeConstants.ENCRYPTED	:	type = DatabaseConstants.DataTypeConstants.TEXT+"("+len+")";break;
				
				default								:	break;
			}
		}
		return type;
	}
	
	
}
