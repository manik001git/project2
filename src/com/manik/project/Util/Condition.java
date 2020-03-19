//$Id$
package com.manik.project.Util;

import java.util.List;

import com.manik.general.mysql.query.Column;
import com.manik.project.Constants.ComparatorConstants.Comparators;
import com.manik.project.Helper.Utils;

/**
 * 
 * @author mani-5690
 *
 */
public class Condition {

	public static final String AND = "and";
	public static final String OR = "or";
	
	private String rowCondition = null, modelName = null;
	private Attributes leftColumn = null, rightColumn = null;
	private Object values = null;
	private int comparator = 0;
	
	
	public Condition(String model,Attributes name,Attributes field,int comparator){
		this.modelName = model;
		this.leftColumn = name;
		this.rightColumn = field;
		this.comparator = comparator;
	}
	
	public Condition(String model,Attributes name,Object value,int comparator){
		this.modelName = model;
		this.leftColumn = name;
		this.comparator = comparator;
		this.values = value;
	}
	
	public String getTableName(){
		return this.modelName;
	}
	
	public Attributes getLeftColumn(){
		return this.leftColumn;
	}
	
	public Attributes getRightColumn(){
		return this.rightColumn;
	}
	
	public Object getValues(){
		return this.values;
	}
	
	public int getComparator(){
		return this.comparator;
	}
	
	public void setRowCondition(String cond){
		this.rowCondition = cond;
	}
	
	public String getRowCondition(){
		return this.rowCondition;
	}
	
	
	public static String getConditions(List<Condition> conds){
		StringBuilder condStr = new StringBuilder();
		for(Condition cond : conds){
			condStr.append(getConditions(cond)).append(" ");
		}
		return condStr.toString();
	}
	
	
	public static String getConditions(Condition cond){
		StringBuilder condStr = new StringBuilder();
		Column col = new Column(cond.getLeftColumn());
		condStr.append(col.toString()).append(" ");
		
		int comparator = cond.getComparator();
		condStr.append(Comparators.getType(comparator)).append(" ");
		
		Attributes rhs = cond.getRightColumn();
		if(rhs != null){
			condStr.append(new Column(rhs).toString());
		}else{
			//TODO:check values.
			Object values = cond.getValues();
			condStr.append(Utils.objectToString(values));
		}
		return condStr.toString();
	}
}
