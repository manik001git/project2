//$Id$
package com.manik.project.Helper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.manik.general.Logging.MyLogger;

public class Utils {
	
	private static final Logger LOGGER = MyLogger.getLogger(Utils.class.getName());
	
	public static boolean isNotNullOrEmpty(String[] array){
		return array != null && array.length > 0;
	}
	
	public static boolean isNotNullOrEmpty(String str){
		return str != null && !"".equals(str.trim());
	}
	
	public static boolean isNotNullOrEmpty(StringBuilder str){
		return str != null && !"".equals(str.toString().trim());
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isNotNullOrEmpty(List list){
		return list != null && !list.isEmpty();
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isNotNullOrEmpty(Map map){
		return map != null && !map.isEmpty();
	}
	
	public static boolean isNotNull(Object obj){
		return obj != null;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isMapContains(Map map, String key){
		return map != null && key != null && map.containsKey(key);
	}
	
	public static boolean isNumeric(String val){
		if(isNotNullOrEmpty(val)){
			String regExp = "^(-?)[0-9\\$]{0,100}$";
			Pattern p1 = Pattern.compile(regExp);
			Matcher m1 = p1.matcher(val);
			return m1.matches();
		}
		return false;
	}
	
	public static boolean matches(String val, String regExp){
		if(isNotNullOrEmpty(val) && isNotNullOrEmpty(regExp)){
			try{
				Pattern p1 = Pattern.compile(regExp);
				Matcher m1 = p1.matcher(val);
				return m1.matches();
			}catch(Exception e){
				LOGGER.log(Level.CONFIG, "Utils method Error matches ", e);
			}
		}
		return false;
	}
	
	public static String objectToString(Object o){
		if(o != null){
			Class clazz = o.getClass();
			if(clazz == String[].class || clazz == Long[].class || clazz == Integer[].class){
				String[] s = (String[])o;
				return Arrays.toString(s);
			}else if(clazz == String.class || clazz == Long.class || clazz == Integer.class){
				return o.toString();
			}
		}
		return "";
	}
	
	public static <T> String ListToString(List<T> list){
		if(isNotNullOrEmpty(list)){
			StringBuilder s = new StringBuilder();
			for(int i = 0; i < list.size(); i++){
				s.append(list.get(i)).append(i < list.size()-1 ? ", " : "");
			}
			return s.toString();
		}
		return null;
	}
}
