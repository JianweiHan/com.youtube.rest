package com.youtube.dao;

import java.sql.*;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.util.ToJSON;

public class SchemaMySql extends MySqlCompany2{
	
	public JSONArray queryReturnbrandParts(String brand) throws Exception{
		
		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try{
			conn = mysqlPcPartsConnection();
			query = conn.prepareStatement("select * from department where UPPER(location) = ?");
			query.setString(1, brand.toUpperCase());
			ResultSet rs = query.executeQuery();
			
			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		
		catch (SQLException sqlError){
			sqlError.printStackTrace();
			return json;
		}
		catch (Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return json;
	}

}
