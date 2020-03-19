//$Id$
package com.manik.project.Database.Utils;

import java.util.Arrays;
import java.util.List;

public interface DatabaseConstants {
	
	public static final String NAME = "name";
	public static final String DATA_TYPE = "data-type";
	public static final String NULLABLE = "nullable";
	public static final String DEFAULT_VALUE = "default-value";
	public static final String MAX_LEN = "max-len";
	public static final String TABLES = "tables";
	public static final String TABLE = "table";
	public static final String COLUMN = "column";
	public static final String PRIMARY_KEY = "primary-key";
	public static final String PRIMARY_KEY_COLUMN = "primary-key-column";
	
	public static interface DataTypeConstants {
		public static final String TINYINT = "TINYINT";
		public static final String BIGINT = "BIGINT";
		public static final String VARCHAR = "VARCHAR";
		public static final String TEXT = "TEXT";
		public static final String ENCRYPTED = "ENCRYPTED";
		public static List<String> ALLOWED_DATATYPES = Arrays.asList(TINYINT,BIGINT,VARCHAR,TEXT,ENCRYPTED);
	}
}
