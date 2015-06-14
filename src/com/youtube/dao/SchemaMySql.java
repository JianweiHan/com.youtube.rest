package com.youtube.dao;

import java.sql.*;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.util.ToJSON;

public class SchemaMySql extends MySqlCompany2 {

	public int insertIntoPC_PARTS(String DEPT_NO, String DEPT_NAME,
			String LOCATION)
			throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		try {
			/*
			 * If this was a real application, you should do data validation
			 * here before starting to insert data into the database.
			 * 
			 * Important: The primary key on PC_PARTS table will auto increment.
			 * That means the PC_PARTS_PK column does not need to be apart of
			 * the SQL insert query below.
			 */
			conn = mysqlPcPartsConnection();
			query = conn
					.prepareStatement("insert into department "
							+ "(DEPT_NO, DEPT_NAME, LOCATION) "
							+ "VALUES ( ?, ?, ? ) ");

			query.setString(1, DEPT_NO);
			query.setString(2, DEPT_NAME);
			query.setString(3, LOCATION);
/*
			// PC_PARTS_AVAIL is a number column, so we need to convert the
			// String into a integer
			int avilInt = Integer.parseInt(PC_PARTS_AVAIL);
			query.setInt(4, avilInt);

			query.setString(5, PC_PARTS_DESC);
			
*/
			query.executeUpdate(); // note the new command for insert statement

		} catch (Exception e) {
			e.printStackTrace();
			return 500; // if a error occurs, return a 500
		} finally {
			if (conn != null)
				conn.close();
		}

		return 200;
	}

	public JSONArray queryReturnbrandParts(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = mysqlPcPartsConnection();
			query = conn
					.prepareStatement("select * from department where UPPER(location) = ?");
			query.setString(1, brand.toUpperCase());
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); // close connection
		}

		catch (SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return json;
		} finally {
			if (conn != null)
				conn.close();
		}

		return json;
	}

}
