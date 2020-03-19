//$Id$
package com.manik.general.Security;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.manik.general.Logging.MyLogger;

public class SecurityFilter implements Filter{
	
	private static final Logger LOGGER = MyLogger.getLogger(SecurityFilter.class.getName());
	
	public void init(FilterConfig fc){

	}
	
	public void doFilter(ServletRequest request,ServletResponse response, FilterChain fc){
		try{
			if(hasPermission()){
				fc.doFilter(request, response);
			}else{
				throw new RuntimeException("Permission denied");
			}
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, "ERROR while passing security.", e);
		}
	}
	
	public void destroy(){
		
	}
	
	private boolean hasPermission(){
		return true;
	}
}
