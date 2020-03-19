//$Id$
/**
 * 
 */
package com.manik.general.javalite;

import java.util.ArrayList;
import java.util.List;

import com.manik.general.mysql.query.BeanHandler;
import com.manik.general.mysql.query.Join;
import com.manik.project.Helper.Utils;
import com.manik.project.Util.Attributes;
import com.manik.project.Util.Condition;
import com.manik.project.Util.LazyList;

public class FlowList<T extends Model> extends LazyList<T>{

	private static final long serialVersionUID = 1L;
	private final MetaModel metaModel;
	private Class<? extends Model> model;
	private List<Attributes> selectedColumns = new ArrayList<>();
	private int index = 0, range = 100;
	private List<Condition> conditions = new ArrayList<>();
	private List<Join> joins = new ArrayList<>();
	private Attributes groupBy = null, orderBy = null;
	
	
	public FlowList(Class<? extends Model> clazz, MetaModel model, Condition cond){
		this.model = clazz;
		this.metaModel = model;
		this.conditions.add(cond);
	}
	
	public Class<? extends Model> getModelClass(){
		return this.model;
	}
	
	public void setIndex(int index){
		this.index = index;
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public int getRange(){
		return this.range;
	}
	
	public List<Attributes> getSelectedColumns(){
		return this.selectedColumns;
	}
	
	public List<Condition> getCondition(){
		return this.conditions;
	}
	
	public List<Join> getJoinTables(){
		return this.joins;
	}
	
	public Attributes getGroupBy(){
		return this.groupBy;
	}
	
	public Attributes getOrderBy(){
		return this.orderBy;
	}
	
	@SuppressWarnings({ "unchecked"})
	public <E extends Model> FlowList<E> and(Condition cond){
		List<Condition> conds = getCondition();
		if(!conds.isEmpty()){
			cond.setRowCondition(Condition.AND);
		}
		conds.add(cond);
		return (FlowList<E>)this;
	}
	
	@SuppressWarnings({ "unchecked"})
	public <E extends Model> FlowList<E> or(Condition cond){
		List<Condition> conds = getCondition();
		if(!conds.isEmpty()){
			cond.setRowCondition(Condition.OR);
		}
		conds.add(cond);
		return (FlowList<E>)this;
	}
	
	public <E extends Model> FlowList<T> select(Attributes...attributes){
		if(attributes != null && attributes.length > 0){
			for(Attributes a : attributes){
				this.selectedColumns.add(a);
			}
		}
		return (FlowList<T>) this;
	}
	
	public <E extends Model> FlowList<T> groupBy(Attributes attr){
		this.groupBy = attr;
		return (FlowList<T>) this;
	}
	
	public <E extends Model> FlowList<T> orderBy(Attributes attr){
		this.orderBy = attr;
		return (FlowList<T>) this;
	}
	
	public <E extends Model> FlowList<T> range(int startIndex, int endIndex){
		this.index = startIndex;
		this.range = endIndex;
		return (FlowList<T>) this;
	}
	
	public <E extends Model> FlowList<T> range(int endIndex){
		this.range = endIndex;
		return (FlowList<T>) this;
	}
	
	/**
	 * This method will execute query.
	 */
	protected void hydrate(){
		if(hydrated()){
			return;
		}
		delegates = new ArrayList<T>();
		if(!canExecuteQuery()){
			throw new RuntimeException("cannot execute query");//No i18n
		}
		
		changeToHydrated();
		BeanHandler.getReadBean().getDataSet(this.metaModel, this);
	}
	
	public void addDelegates(List<T> list){
		if(this.delegates == null){
			this.delegates = new ArrayList<>();
		}
		if(Utils.isNotNullOrEmpty(list)){
			this.delegates.addAll(list);
		}
	}

}
