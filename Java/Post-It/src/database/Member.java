package database;


import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.sql.*;


/**
 * 
 *This is Member class which extends Database class for database connection and has several method for manipulating Member Table
 * @author kutay
 *
 */
public class Member extends Database {
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String mail;
	private String creationDate;
	private String updateDate;
	private boolean isDeleted;
	public Member(){
		super();
		this.username="-1";
		this.password="-1";
		this.firstName="-1";
		this.lastName="-1";
		this.mail="-1";
	}
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return this.firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return this.lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMail() {
		return this.mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
	/**
	 * delete member with using member id 
	 * @param MemberID
	 */
	public void deleteMember(int MemberID) {
		try {
		String query = "UPDATE Member SET isDeleted = 1 WHERE id = " + MemberID;
		
			this.state.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * check member is exist with checking username and mail
	 * @return
	 */
	public boolean isMemberExist() {
		try {
			String query= "SELECT * FROM Member WHERE username = '"+ this.username+"' AND mail = '"+this.mail+"'";
		
			System.out.println(query);
			ResultSet result = this.state.executeQuery(query);
			if(result.next()) {
				 return true;
			 }
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * returns whether user name exist
	 * @return
	 */
	public boolean isUsernameExist() {
		try {
			String query= "SELECT * FROM Member WHERE username = '"+ this.username+"'";
		
			System.out.println(query);
			ResultSet result = this.state.executeQuery(query);
			if(result.next()) {
				 return true;
			 }
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * returns whether mail exist
	 * @return
	 */
	public boolean isMailExist() {
		try {
			String query= "SELECT * FROM Member WHERE mail = '"+ this.mail+"'";
		
			System.out.println(query);
			ResultSet result = this.state.executeQuery(query);
			if(result.next()) {
				 return true;
			 }
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * gets id with using username and password for log in
	 */
	public void getIDwithUsernameAndPassword() {
		try {
			String query= "SELECT * FROM Member WHERE username = '"+ this.username+"' AND password = '"+this.password+"'";
		
			System.out.println(query);
			ResultSet result = this.state.executeQuery(query);
			if(result.next()) {
				 this.id=result.getInt("id");
			 }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * update member information with using username
	 * @param username
	 */
	public void changeMemberwithUsername(String username) {
		try {
			String query = "UPDATE Member SET updateDate =";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("tr"));
			query+="'"+ sdf.format(new Date())+"'";
			if(!this.username.equals("-1")) query += ", username ='"+this.username+"'";
			if(!this.password.equals("-1")) query += ", password ='"+this.password+"'";
			if(!this.mail.equals("-1")) query += ", mail ='"+this.mail+"'";
			if(!this.firstName.equals("-1")) query += ", firstName ='"+this.firstName+"'";
			if(!this.lastName.equals("-1")) query += ", lastName ='"+this.lastName+"'";
			query += " WHERE username = '"+username+"'";
			System.out.println(query);
			this.state.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * add member with using class information
	 */
	public void addMember() {
		try {
			String query = "INSERT INTO Member VALUES(NULL,";
			
			if(!this.username.equals("-1")) query += "'"+this.username+"',";
			else query+="NULL,";
			
			if(!this.password.equals("-1")) query += "'"+this.password+"',";
			else query+="NULL,";
			
			if(!this.mail.equals("-1")) query += "'"+this.mail+"',";
			else query+="NULL,";
			
			if(!this.firstName.equals("-1")) query += "'"+this.firstName+"',";
			else query+="NULL,";
			
			if(!this.lastName.equals("-1")) query += "'"+this.lastName+"',";
			else query+="NULL,";
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("tr"));
			query+="'"+ sdf.format(new Date())+"'";
			//System.out.println(date);
			query+=",NULL,0)";
			System.out.println(query);

			this.state.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
