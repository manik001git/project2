//$Id$
package com.manik.general.Instrumentation;

public class Main {
	
//	public static void main(String[] args) {
//		Instrumentation instrumentation = new Instrumentation(System.getProperty("activejdbc_properties_dir"));
//	    instrumentation.instrument();
//	}
	
	public static void doInstrument(){
		Instrumentation instrumentation = new Instrumentation(System.getProperty("activejdbc_properties_dir"));
	    instrumentation.instrument();
	}
	
}
