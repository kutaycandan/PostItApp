package database;

import java.sql.*;

public class Database {
	private String db = "jdbc:mysql://127.0.0.1:3306/Post-It";
	private String db_user="root";
	private String db_password="";
	Connection conn;
	Statement state;
	public Database() {
		try {
			 this.conn = DriverManager.getConnection(this.db, this.db_user, this.db_password);
			 this.state = conn.createStatement();
			 /*String query ="Select * From Member"; 
			 ResultSet result = state.executeQuery(query);
			 while(result.next()) {
				 //Read data
				 result.getString("username");
			 }*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
