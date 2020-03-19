//$Id$
package com.manik.general.mysql.query;

import com.manik.general.Database.Database;
import com.manik.general.Database.QueryExecution;
import com.manik.project.Helper.Utils;
import com.manik.project.Util.Condition;

public class WriteBeanImpl implements WriteBean{

	
	public boolean delete(Condition condition) {
		boolean isDeleted = false;
		if(condition != null){
			String table = condition.getTableName();
			if(table != null){
				StringBuilder qry = new StringBuilder();
				qry.append("DELETE FROM ").append(table);
				
				String condStr = Condition.getConditions(condition);
				if(Utils.isNotNullOrEmpty(condStr)){
					qry.append(" WHERE ").append(condStr);
					isDeleted = QueryExecution.executeQuery(qry.toString(), Database.getCurrentDatabase());
				}
			}
		}
		return isDeleted;
	}

}
