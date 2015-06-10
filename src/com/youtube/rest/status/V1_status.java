package com.youtube.rest.status;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.sql.*;
import com.youtube.dao.*;

@Path("/v1/status")
public class V1_status {
	
	private static final String api_version= "00.01.00";

	@GET
	@Produces(MediaType.TEXT_HTML)
    public String returnTitle(){
		return "<p>Jave Web Service</p>";
	}
	
	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
    public String returnVersion(){
		return "<p>Version:</p>" + api_version;
	}
	
	@Path("/database")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnDatabaseStatus() throws Exception{
		
		PreparedStatement query =null;
		String myString = null;
		String returnString = null;
		Connection conn = null;
		
		try{
			
			conn = MySqlCompany2.MySqlCom2Conn().getConnection();
			query = conn.prepareStatement("select * from department");
			ResultSet rs = query.executeQuery();
			
			while(rs.next())
			{
				myString = rs.getString("dept_name");
			}
			
			query.close(); //close connection
			
			returnString = "<p>Database Status</p>"+ "<p>Depart Name: " + myString + "</p>";
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if (conn != null) conn.close();
		}
		
		return returnString;
	}
	


}
