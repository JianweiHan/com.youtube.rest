package com.youtube.dao;

import java.sql.Connection;
import javax.naming.*;
import javax.sql.*;

public class MySqlCompany2 {
	
	private static DataSource MySqlCom2 = null;
	private static Context context = null;
	
	public static DataSource MySqlCom2Conn() throws Exception {
		
		if(MySqlCom2 != null){
			return MySqlCom2;
		}
		
		try{
			
			if(context == null)
			{
				context = new InitialContext();
			}
			
			MySqlCom2 = (DataSource) context.lookup("jdbc/mysql_ds");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return MySqlCom2;
	}
	
	protected static Connection mysqlPcPartsConnection() {
		Connection conn = null;
		try {
			conn = MySqlCompany2.MySqlCom2Conn().getConnection();
			return conn;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}
