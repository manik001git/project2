//$Id$
package com.manik.general.mysql.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.manik.general.Logging.MyLogger;
import com.manik.general.javalite.FlowList;
import com.manik.general.javalite.MetaModel;
import com.manik.general.javalite.Model;
import com.manik.project.Constants.JoinConstants.JoinType;
import com.manik.project.Constants.QueryConstants.QueryType;
import com.manik.project.Helper.Utils;
import com.manik.project.Util.Attributes;
import com.manik.project.Util.Condition;

public class SelectQueryImpl {
	
	private static final Logger LOGGER = MyLogger.getLogger(SelectQueryImpl.class.getName());
	
	public static String formSelectQuery(MetaModel model, FlowList<? extends Model> list){
		try{
			String baseTable = model.getTable();
			
			Query qry = new Query(new Table(baseTable));
			qry.setType(QueryType.SELECT.value);
			
			//join tables
			List<Join> joins = list.getJoinTables();
			if(Utils.isNotNullOrEmpty(joins)){
				qry.setJoins(joins);
			}
			
			List<Condition> conds = list.getCondition();
			if(Utils.isNotNullOrEmpty(conds)){
				qry.setConditions(conds);
			}
			
			//selected Columns
			List<Attributes> selectedCols = list.getSelectedColumns();
			List<Column> cols = new ArrayList<>();
			if(Utils.isNotNullOrEmpty(selectedCols)){
				List<String> pkIds = new ArrayList<>(Arrays.asList(model.getPKIds()));
				for(Attributes a : selectedCols){
					String col = a.getName();
					if(pkIds.contains(col)){
						pkIds.remove(col);
					}else{
						cols.add(new Column(a));
					}
				}
				if(!pkIds.isEmpty()){
					for(String col : pkIds){
						Attributes a = new Attributes(baseTable, col);
						cols.add(new Column(a));
					}
				}
			}else{
				cols.add(new Column(new Attributes(baseTable, "*")));
				if(joins != null && !joins.isEmpty()){
					for(Join join : joins){
						cols.add(new Column(new Attributes(join.getJoinTableName(), "*")));
					}
				}
			}
			qry.setSelectColumns(cols);
			
			Attributes grby = list.getGroupBy();
			if(grby != null){
				GroupBy gb = new GroupBy(grby);
				qry.setGroupBy(gb);
			}
			
			Attributes orby = list.getOrderBy();
			if(orby != null){
				OrderBy ob = new OrderBy(orby);
				qry.setOrderBy(ob);
			}
			
			Range range = new Range(list.getIndex(), list.getRange());
			qry.setRange(range);
			
			return formSelectQuery(qry);
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, "Error while query construction", e);
		}
		return null;
	}
	
	private static String formSelectQuery(Query qry){
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
						columns.append(col.toString());
					}
					
					StringBuilder joinStr = new StringBuilder();
					List<Join> joins = qry.getJoins();
					for(int i = 0; i< joins.size(); i++){
						Join join = joins.get(i);
						joinStr.append(JoinType.getJoinType(join.getJoinType())).append(" ").append(join.getJoinTableName()).append(" on ");
						joinStr.append(Condition.getConditions(join.getJoinConditions()));
					}
					
					StringBuilder whereStr = new StringBuilder().append("where ");
					whereStr.append(Condition.getConditions(qry.getConditions()));
					
					StringBuilder obStr = new StringBuilder();
					OrderBy orderBy = qry.getOrderBy();
					if(orderBy != null){
						obStr.append("order by ");
						obStr.append(new Column(orderBy.getOrderByColumn()).toString());
					}
					
					StringBuilder gbStr = new StringBuilder();
					GroupBy groupBy = qry.getGroupBy();
					if(groupBy != null){
						gbStr.append("group by ");
						gbStr.append(new Column(groupBy.getGroupByColumn()).toString());
					}
					
					StringBuilder rangeStr = new StringBuilder();
					Range range = qry.getRange();
					if(range != null){
						rangeStr.append("limit ");
						rangeStr.append(range.getStartIndex()).append(", ").append(range.getEndIndex());
					}
					
					qryString.append(QueryType.SELECT.name() + " ").append(columns.toString()).append(" FROM " + qry.getTable().getName() + " ");
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
			}
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, "Error while query execution", e);
		}
		return null;
	}
	
}
