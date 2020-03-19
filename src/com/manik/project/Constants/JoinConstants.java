//$Id$
package com.manik.project.Constants;

public interface JoinConstants {
	
	public static enum JoinType{
		INNER_JOIN("inner join"), LEFT_JOIN("left join"), RIGHT_JOIN("right join");
		public String value;
		public int type;
		private JoinType(String join){
			this.value = join;this.type = ordinal();
		}
		
		public static String getJoinType(int type){
			for(JoinType jt : JoinType.values()){
				if(jt.type == type){
					return jt.value;
				}
			}
			return JoinType.LEFT_JOIN.value;
		}
	}
}
