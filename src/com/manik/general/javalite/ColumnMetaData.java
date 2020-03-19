//$Id$
package com.manik.general.javalite;

public class ColumnMetaData {
	private final String columnName;
    private final String typeName;
    private final int columnSize;

    public ColumnMetaData(String columnName, String  typeName, int columnSize) {
        this.columnName = columnName;
        this.typeName = typeName;
        this.columnSize = columnSize;
    }
    
    public String getColumnName() {
        return columnName;
    }
    
    public int getColumnSize() {
        return columnSize;
    }
    
    public String getTypeName() {
        return typeName;
    }
    
    @Override
    public String toString() {
        return "[ columnName=" + columnName + ", typeName=" + typeName + ", columnSize=" + columnSize + "]";
    }
}
