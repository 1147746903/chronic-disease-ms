package com.comvee.cdms.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author 李左河
 *
 */
public class FormatSqlTool {
	
	private final static Logger log = LoggerFactory.getLogger(FormatSqlTool.class);
	
	/**
	 * 格式化sql字符串
	 * @param sql
	 * @param list
	 * @return
	 */
	public static String formatSqlByList(String sql, List list) {
		int i =0 ;
		String str = "?";
		while (sql.indexOf(str)!=-1) {
			Object obj = list.get(i);
			if (obj instanceof Integer) {
				sql = sql.replaceFirst("\\?", obj.toString());
			}else if (obj instanceof Long) {
				sql = sql.replaceFirst("\\?", obj.toString());
			}else {
				if (obj == null ) {
					obj ="";
				}
				sql = sql.replaceFirst("\\?", "'"+obj.toString()+"'");
			}
			i++;
		}
		return sql;
	}
	
	/**
	 * 不定参数格式化sql字符串
	 * @param sql
	 * @param args
	 * @return
	 */
	public static String formatSql(String sql,Object...args){
		try {
			int i =0 ;
			String str = "?";
			while (sql.indexOf(str)!=-1) {
				Object obj = args[i];
				if (obj instanceof Integer) {
					sql = sql.replaceFirst("\\?", obj.toString());
				}else if (obj instanceof Long) {
					sql = sql.replaceFirst("\\?", obj.toString());
				}else {
					
					if (obj == null ) {
						obj ="";
					}
					sql = sql.replaceFirst("\\?", "'"+obj.toString()+"'");
				}
				i++;
			}
		} catch (Exception e) {
			log.error("format sql error!sql:" + sql, e);
		}
		return sql;
	}
	
	public static void main(String[] args){
		String sql = "select * from thp_good where id=? and cd=? and ii=?";
		List<Object> listParam = new ArrayList<Object>();
		listParam.add(1);
		listParam.add(2);
		listParam.add(null);
		FormatSqlTool.formatSql(sql, listParam);
	}
}
