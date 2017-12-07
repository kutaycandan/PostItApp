package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/**
 * 
 *This is TeamMember class which extends Database class for database connection and has several method for manipulating TeamMember Table
 * @author kutay
 *
 */
public class TeamMember extends Database {
	private int id;
	private int teamID;
	private int memberID;
	private boolean isMaster;
	private String creationDate;
	private String updateDate;
	private boolean isDeleted;
	public TeamMember() {
		super();
		this.isMaster=false;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTeamID() {
		return this.teamID;
	}
	public void setTeamID(int teamID) {
		this.teamID = teamID;
	}
	public int getMemberID() {
		return memberID;
	}
	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}
	/**
	 * returns member is master of team or not
	 * @return
	 */
	public boolean getIsMaster() {
		try {
			String query= "SELECT * FROM TeamMember WHERE isMaster = 1 AND memberID = "+this.memberID+" AND teamID = "+this.teamID;
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
	public void setIsMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}
	/**
	 * delete teammember with using teammember id
	 * @param id
	 */
	public void deleteTeamMember(int id) {
		try {
		String query = "DELETE FROM TeamMember WHERE id = " + id;
		
			this.state.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * gets teammember id with using member id
	 * @return
	 */
	public String getIDwithMemberID() {
		try {
			String query= "SELECT * FROM TeamMember WHERE isDeleted = 0 AND memberID = "+this.memberID;
			ResultSet result = this.state.executeQuery(query);
			String s ="";
			while(result.next()) {
				int teamID=result.getInt("teamID");
				//s+=teamID+"-";
				Team t=new Team();
				s+=t.getTeamnamewithTeamID(teamID)+"/";
			}
			return s;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * get teammember id  with using team id and member id
	 * @param teamID
	 * @param memberID
	 */
	public void getIDwithIDs(int teamID,int memberID) {
		try {
			String query= "SELECT * FROM TeamMember WHERE teamID = "+ teamID + " AND memberID = "+memberID;
		
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
	 *  set member to master
	 */
	public void setAsMaster() {
		try {
			String query = "UPDATE TeamMember SET updateDate =";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("tr"));
			query+="'"+ sdf.format(new Date())+"'";
			query += ", isMaster = 1";
			query += " WHERE teamID = "+ this.teamID + " AND memberID = "+this.memberID;
			System.out.println(query);
			this.state.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * add teammember with using class information
	 */
	public void addTeamMember() {
		try {
			String query = "INSERT INTO TeamMember VALUES(NULL,";
			
			if(this.teamID!=-1) query += this.teamID+",";
			else query+="NULL,";
			if(this.memberID!=-1) query += this.memberID+",";
			else query+="NULL,";
			
			if(this.isMaster) query += "1,";
			else query+="0,";
			
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
	/**
	 * returns whether teammember is exist or not
	 * @return
	 */
	public boolean isTeamMemberExist() {
		try {
			String query= "SELECT * FROM TeamMember WHERE memberID = "+ this.memberID+" AND teamID = "+this.teamID;
		
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
	
}
