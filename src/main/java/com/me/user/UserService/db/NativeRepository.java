package com.me.user.UserService.db;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Component;



@Component
public class NativeRepository {

	
//	@Autowired 
 //   private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired 
	QueryResult queryResult;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
//	@Autowired
//	 @Qualifier("jdbcTemplate2")
//	 private JdbcTemplate jdbcTemplate2;
	
	  public QueryResult executeQueries(String queryData){    
		  QueryResult queryResult =new QueryResult();
		  // System.out.println("queryData---->"+queryData);
	        MapSqlParameterSource parameters = new MapSqlParameterSource();
	        List<Object> columnList=new ArrayList<Object>();
	        List<Object> columnDataType=new ArrayList<Object>();
	      //  String sql = "select * from pgi_ms_reportname";
	        List<Map<String, Object>> rows = jdbcTemplate.queryForList(queryData);
//	        // System.out.println("rows values--->"+rows);
	        SqlRowSet rs = jdbcTemplate.queryForRowSet(queryData);
	        SqlRowSetMetaData rsmd = rs.getMetaData();
	        int columnNo = rsmd.getColumnCount();
	        for ( int i = 1; i <= columnNo; i++ )
	        {
	         columnList.add(rsmd.getColumnLabel(i));
	         columnDataType.add(rsmd.getColumnTypeName(i));
	       //  // System.out.println("column Name--->"+rsmd.getColumnLabel(i));
//	         // System.out.println("column Type--->"+rsmd.getColumnTypeName(i));
	         
	        }
	        queryResult.setColumnName(columnList);
	        queryResult.setRowValue(rows);
	        queryResult.setColumnDataType(columnDataType);
//	        // System.out.println("queryResult--->"+queryResult);
			return queryResult;
	    }
	  
	  public int updateQueries(String queryData){  
//		  // System.out.println("Update Query--->"+queryData);
		  try {
	      return  jdbcTemplate.update(queryData);
		  }catch(Exception ex) {
			  ex.printStackTrace();
			  return 0;
		  }
			
	    }
	  
	  
	  
	  
	  
	  
}