//$Id$
package com.manik.general.Initialize;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.manik.general.Database.Database;
import com.manik.general.Logging.MyLogger;
import com.manik.general.Security.SecurityFilter;

public class InitializeAppFilter implements Filter {

	private static final Logger LOGGER = MyLogger.getLogger(SecurityFilter.class.getName());
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {
		try{
			//Database.initializeService();
			fc.doFilter(request, response);
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, "ERROR while initialize service", e);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
