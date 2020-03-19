//$Id$
package com.mickey.database;

import java.util.ArrayList;
import java.util.List;

import com.source.exceptions.QueryConstructException;

public class SelectQueryImpl {

	private Table table;
	private List<Column> selectedColumns = new ArrayList<>();
	private List<Join> joins = new ArrayList<>();
	private Criteria criteria;
	private Range range;
	
	public SelectQueryImpl(Table table) throws Exception{
		if(table == null){
			throw new QueryConstructException("Table cannot be null");
		}
		this.table = table;
	}
	
	public void addSelectColumn(Column column){
		if(column != null && !selectedColumns.contains(column)){
			selectedColumns.add(column);
		}
	}
	
	public void addJoin(Join join){
		if(join != null && !joins.contains(join)){
			joins.add(join);
		}
	}
	
	public void setCriteria(Criteria criteria){
		this.criteria = criteria;
	}
	
	public void setRange(Range range){
		this.range = range;
	}
	
	public String getQuery() throws Exception{
		StringBuilder sb = new StringBuilder();
		try{
			Table table = this.table;
			sb.append("SELECT ");
			
			List<Column> columns = this.selectedColumns;
			if(columns != null && !columns.isEmpty()){
				for(int i = 0; i < columns.size(); i++){
					sb.append(columns.get(i).toString()).append(i != columns.size()-1 ? ", " : " ");
				}
			}
			sb.append(" FROM ").append(table.getName());
			
			List<Join> joins = this.joins;
			if(joins != null && !joins.isEmpty()){
				for(int i = 0; i < joins.size(); i++){
					sb.append(" ").append(joins.get(i).toString());
				}
			}
			
			if(this.criteria != null){
				sb.append(" WHERE ").append(this.criteria.toString());
			}
			
			if(this.range != null){
				sb.append(" LIMIT ").append(this.range.getIndex()).append(", ").append(this.range.getRange());
			}
			
		}catch(Exception e){
			throw new QueryConstructException(e);
		}
		return sb.toString().replaceAll("\\s+", " ");
	}

}
