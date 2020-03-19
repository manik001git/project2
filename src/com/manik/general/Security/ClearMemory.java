//$Id$
package com.manik.general.Security;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

import com.manik.general.Database.Connection.ConnectionAccess;
import com.manik.general.Logging.MyLogger;

@WebListener
public class ClearMemory implements ServletRequestListener{

	private static final Logger LOGGER = MyLogger.getLogger(ClearMemory.class.getName());
	
	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		try{
			ConnectionAccess.closeConnections();
		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, "ERROR while close mysql connections.");
		}
	}

	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	


}
