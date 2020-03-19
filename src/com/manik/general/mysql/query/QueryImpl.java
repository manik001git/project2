//$Id$
package com.manik.general.mysql.query;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.manik.general.Logging.MyLogger;
import com.manik.project.Constants.ComparatorConstants.Comparators;
import com.manik.project.Constants.JoinConstants.JoinType;
import com.manik.project.Constants.QueryConstants.QueryType;
import com.manik.project.Helper.Utils;
import com.manik.project.Util.Attributes;
import com.manik.project.Util.Condition;

public class QueryImpl {

	private static final Logger LOGGER = MyLogger.getLogger(SelectQueryImpl.class.getName());
	
	public static String formQuery(Query qry){
		try{
			if(qry != null){
				int type = qry.getType();
				StringBuilder qryString = new StringBuilder();
				
				if(type == QueryType.SELECT.value){
					StringBuilder columns = new StringBuilder();
					List<Column> cols = qry.getSelectColumns();
					for(int i = 0; i< cols.size(); i++){
						Column col = cols.get(i);
						if(i != 0){
							columns.append(", ");
						}
						columns.append(columnToString(col));
					}
					
					StringBuilder joinStr = new StringBuilder();
					List<Join> joins = qry.getJoins();
					for(int i = 0; i< joins.size(); i++){
						Join join = joins.get(i);
						joinStr.append(JoinType.getJoinType(join.getJoinType())).append(" ").append(join.getJoinTableName()).append(" on ");
						joinStr.append(getConditions(join.getJoinConditions()));
					}
					
					StringBuilder whereStr = new StringBuilder().append("where ");
					whereStr.append(getConditions(qry.getConditions()));
					
					StringBuilder obStr = new StringBuilder();
					OrderBy orderBy = qry.getOrderBy();
					if(orderBy != null){
						obStr.append("order by ");
						obStr.append(columnToString(new Column(orderBy.getOrderByColumn())));
					}
					
					StringBuilder gbStr = new StringBuilder();
					GroupBy groupBy = qry.getGroupBy();
					if(groupBy != null){
						gbStr.append("group by ");
						gbStr.append(columnToString(new Column(groupBy.getGroupByColumn())));
					}
					
					StringBuilder rangeStr = new StringBuilder();
					Range range = qry.getRange();
					if(range != null){
						rangeStr.append("limit ");
						rangeStr.append(range.getStartIndex()).append(", ").append(range.getEndIndex());
					}
					
					qryString.append(QueryType.SELECT.name + " ").append(columns.toString()).append(" FROM " + qry.getTable().getName() + " ");
					if(Utils.isNotNullOrEmpty(joinStr)){
						qryString.append(joinStr.toString() + " ");
					}
					if(Utils.isNotNullOrEmpty(whereStr)){
						qryString.append(whereStr.toString() + " ");
					}
					if(Utils.isNotNullOrEmpty(obStr)){
						qryString.append(obStr.toString() + " ");
					}
					if(Utils.isNotNullOrEmpty(gbStr)){
						qryString.append(gbStr.toString() + " ");
					}
					if(Utils.isNotNullOrEmpty(rangeStr)){
						qryString.append(rangeStr.toString());
					}
					
					LOGGER.info("Formed Query : " + qryString.toString());
					return qryString.toString();
				}
				else if(type == QueryType.DELETE.value){
					qryString.append(QueryType.DELETE.name + " ").append(" FROM " + qry.getTable().getName() + " ");
					
					StringBuilder whereStr = new StringBuilder().append("where ");
					whereStr.append(getConditions(qry.getConditions()));
					if(Utils.isNotNullOrEmpty(whereStr)){
						qryString.append(whereStr);
					}
					
					return qryString.toString();
				}
			}
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, "Error while query formation, type = " + qry.getType() + " table = " + qry.getTable().getName(), e);
		}
		return null;
	}
	
	
	
	
	
	private static String getConditions(List<Condition> conds){
		StringBuilder condStr = new StringBuilder();
		for(Condition cond : conds){
			Column col = new Column(cond.getLeftColumn());
			condStr.append(columnToString(col)).append(" ");
			
			int comparator = cond.getComparator();
			condStr.append(Comparators.getType(comparator)).append(" ");
			
			Attributes rhs = cond.getRightColumn();
			if(rhs != null){
				condStr.append(columnToString(new Column(rhs)));
			}else{
				//TODO:check values.
				Object values = cond.getValues();
				condStr.append(Utils.objectToString(values));
			}
			condStr.append(" ");
		}
		return condStr.toString();
	}
	
	private static String columnToString(Column a){
		return "*".equals(a.getName()) ? "*" : "`"+a.getTable()+"`.`"+a.getName()+"`";
	}
	
}
