package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 *This is PostIt class which extends Database class for database connection and has several method for manipulating PostIt Table
 * @author kutay
 *
 */
public class PostIt extends Database{
	private int id;
	private String postname;
	private String text;
	private int teamID;
	private String creationDate;
	private String updateDate;
	private boolean isDeleted;
	private boolean isUpdatedNow;
	public PostIt() {
		super();
		this.postname="-1";
		this.text="-1";
		this.teamID=-1;
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPostname() {
		return this.postname;
	}
	public void setPostname(String postname) {
		this.postname=postname;
	}
	public int getTeamID() {
		return this.teamID;
	}
	public void setTeamID(int teamID) {
		this.teamID=teamID;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * gets is updated now boolean with using teamid and postname
	 * @param teamID
	 * @param postname
	 * @return
	 */
	public String getIsUpdatedNow(int teamID, String postname) {
		try {
			String query= "SELECT * FROM PostIt WHERE teamID = " + teamID+" AND postname = '"+postname+"' AND isUpdatedNow = 1";
			
			ResultSet result = this.state.executeQuery(query);
			if(result.next()) {
				 return "-1";
			}
			setIsUpdatedNow( teamID,  postname);
			return "1";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "-1";
	}
	/**
	 * sets is updated now to 1 with using teamid and postname
	 * @param teamID
	 * @param postname
	 */
	public void setIsUpdatedNow(int teamID, String postname) {
		try {
			String query = "UPDATE PostIt SET updateDate = ";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("tr"));
			query+="'"+ sdf.format(new Date())+"'";
			
			query += ", isUpdatedNow = 1 ";
		
			query += " WHERE postname = '"+postname+"' AND teamID = "+teamID;
			System.out.println(query);
			this.state.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * sets is updated not to 0 with using teamid and postname
	 * @param teamID
	 * @param postname
	 */
	public void releaseIsUpdatedNow(int teamID, String postname) {
		try {
			String query = "UPDATE PostIt SET updateDate = ";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("tr"));
			query+="'"+ sdf.format(new Date())+"'";
			
			query += ", isUpdatedNow = 0 ";
		
			query += " WHERE postname = '"+postname+"' AND teamID = "+teamID;
			System.out.println(query);
			this.state.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * delete all postits with using teamid
	 * @param teamID
	 */
	public void deletePostItWithteamID(int teamID) {
		try {
		String query = "DELETE FROM PostIt WHERE teamID = " + teamID;
		
			this.state.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * delete one postit with using teamid and postname
	 * @param teamID
	 * @param postname
	 */
	public void deletePostItWithpostnameAndTeamID(int teamID,String postname) {
		try {
		String query = "DELETE FROM PostIt WHERE teamID = " + teamID+" AND postname = '"+postname+"'";
		
			this.state.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * gets postit id with using postname
	 * @param postname
	 */
	public void getIDwithPostname(String postname) {
		try {
			String query= "SELECT * FROM PostIt WHERE postname = '"+ postname+"'";
		
			
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
	 * get text with using teamid and postname
	 * @param teamID
	 * @param postname
	 * @return
	 */
	public String getTextWithpostnameAndTeamID(int teamID, String postname) {
		try {
			String query= "SELECT * FROM PostIt WHERE teamID = " + teamID+" AND postname = '"+postname+"'";
		
		
			ResultSet result = this.state.executeQuery(query);
			if(result.next()) {
				 return result.getString("text");
			 }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * returns whether postname is created before in team
	 * @param teamID
	 * @param postname
	 * @return
	 */
	public boolean isPostExist(int teamID,String postname) {
		try {
			String query= "SELECT * FROM PostIt WHERE postname = '"+ postname+"' AND teamID = "+teamID;
		
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
	 * gets postnames with using team id for listing
	 * @return
	 */
	public String getPostnameswithTeamID() {
		try {
			String query= "SELECT * FROM PostIt WHERE teamID = "+this.teamID;
			ResultSet result = this.state.executeQuery(query);
			String s ="";
			while(result.next()) {
				String postname=result.getString("postname");
				s+=postname+"/";
				
				
			}
			return s;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * change post text with using postname and team id
	 * @param text
	 */
	public void changePostTextwithPostnameAndTeamID(String text) {
		try {
			String query = "UPDATE PostIt SET updateDate = ";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("tr"));
			query+="'"+ sdf.format(new Date())+"'";
			
			query += ", text ='"+text+"'";
		
			query += " WHERE postname = '"+this.postname+"' AND teamID = "+this.teamID;
			System.out.println(query);
			this.state.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * add posts to it 
	 */
	public void addPostIt() {
		try {
			String query = "INSERT INTO PostIt VALUES(NULL,";
			
			if(!this.postname.equals("-1")) query += "'"+this.postname+"',";
			else query+="NULL,";
			if(!this.text.equals("-1")) query += "'"+this.text+"',";
			else query+="NULL,";
			if(this.teamID!=-1) query += this.teamID+",";
			else query+="NULL,";
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("tr"));
			query+="'"+ sdf.format(new Date())+"'";
			//System.out.println(date);
			query+=",NULL,0,0)";
			System.out.println(query);

			this.state.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
