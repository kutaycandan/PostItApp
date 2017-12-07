package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/**
 * 
 *This is Team class which extends Database class for database connection and has several method for manipulating Team Table
 * @author kutay
 *
 */
public class Team extends Database {
	private int id;
	private String teamname;
	private String creationDate;
	private String updateDate;
	private boolean isDeleted;
	public Team() {
		super();
		this.id = -1;
		this.teamname = "-1";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTeamname() {
		return this.teamname;
	}
	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}
	/**
	 * Delete team and its member and posts with using team id
	 * @param teamID
	 */
	public void deleteTeam(int teamID) {
		try {
		String query = "DELETE FROM Team WHERE id = " + teamID;
		
		this.state.executeUpdate(query);
		
		query = "DELETE FROM TeamMember WHERE teamID = " + teamID;
		this.state.executeUpdate(query);
		query = "DELETE FROM PostIt WHERE teamID = " + teamID;
		this.state.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Get id with using teamname
	 * @param teamname
	 */
	public void getIDwithTeamname(String teamname) {
		try {
			String query= "SELECT * FROM Team WHERE teamname = '"+ teamname+"'";
		
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
	 * returns teamname with using teamid
	 * @param id
	 * @return
	 */
	public String getTeamnamewithTeamID(int id) {
		try {
			String query= "SELECT * FROM Team WHERE id = "+id;
			String s ="";
			System.out.println(query);
			ResultSet result = this.state.executeQuery(query);
			if(result.next()) {
				 s =result.getString("teamname");
			 }
			return s;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * update team with using teamname
	 * @param teamname
	 */
	public void changeTeamwithTeamName(String teamname) {
		try {
			String query = "UPDATE Team SET updateDate =";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("tr"));
			query+="'"+ sdf.format(new Date())+"'";
			if(!this.teamname.equals("-1")) query += ", teamname ='"+this.teamname+"'";
			query += " WHERE teamname = '"+teamname+"'";
			System.out.println(query);
			this.state.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Add team with using class information
	 */
	public void addTeam() {
		try {
			String query = "INSERT INTO Team VALUES(NULL,";
			
			if(!this.teamname.equals("-1")) query += "'"+this.teamname+"',";
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
