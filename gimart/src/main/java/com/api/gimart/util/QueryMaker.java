package com.api.gimart.util;


public class QueryMaker {
		
	public static String createQuery(String spname,int parameterscount) {
		String questionmarks=makeQuery(parameterscount);
		String finalquery="{call" + " " + spname + "(" + questionmarks + ")}";
		return finalquery;
		}
	
	public static String makeQuery(int queNumber) {
		String query="?,";
		for (int i = 0; i < queNumber-1; i++) {			
			query+="?,";		
		}	
		query=removeLastChar(query);
		//System.out.print(query);
		return query;
	}
	
	private static String removeLastChar(String str) {
	    return str.substring(0, str.length() - 1);
	}
	
	
	public static String getTableData(String tableName) {
		String finalquery="select * from "+tableName+" where isactive=1";		
		return finalquery;
	}
	
	public static String getDeletedTableData(String tableName) {
		String finalquery="select * from "+tableName+" where isactive=0";		
		return finalquery;
	}
	
	public static String getTableData(String tableName,String columnName,int columnId) {
		String finalquery="select * from "+tableName+" where "+columnName+"=" + columnId + " and " + "isactive=1";		
		return finalquery;
	}
	
	public static String getAssignmentsDates(String classname) {
		String finalquery="select assignment_date from assignments where class='"+classname+"' and isactive=1";		
		return finalquery;
	}
	
	public static String rowCount(String tableName) {
		String finalquery="select COUNT(*) from "+tableName+" where isactive=1";		
		return finalquery;
	}
	
	public static String rowCount(String tableName,String columnName,int columnId) {
		String finalquery="select COUNT(*) from "+tableName+" where "+columnName+"=" + columnId + " and " + "isactive=1";		
		return finalquery;
	}
	
	public static String todayData(String tableName) {
		String finalquery="select * from "+tableName+" where DATE(created)=" + java.time.LocalDate.now() + " and " + "isactive=1";		
		return finalquery;
	}
	
	//System.out.println(java.time.LocalDate.now());
	
}
