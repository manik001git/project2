//$Id$
/**
 * 
 */
package com.manik.project.Util;


import com.manik.general.javalite.Model;
import com.manik.project.Constants.LazyListConstants;


/**
 * @author mani-5690
 *
 */
public class LazyList<T extends Model> extends AbstractLazyList<T> implements LazyListConstants{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int state = START;
	
	@Override
	protected void hydrate(){
		
	}
	
	public boolean hydrated(){
		return delegates != null;
	}
	
	public boolean canExecuteQuery(){
		return this.state == START;
	}
	
	public void changeToHydrated(){
		this.state = HYDRATED;
	}
}
