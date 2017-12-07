package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import database.*;
/**
 * This is server thread and this sends and receives messages to/from client
 * @author kutay
 *
 */
public class ServerThread extends Thread {
	Socket socket;
	Scanner inFromClient;
	PrintStream outToClient;
	String clientSentence; 
	String capitalizedSentence;
	BinarySemaphore mutex;
	//Scanner scan;
	boolean kill;
	public ServerThread(Socket socket,BinarySemaphore m) {
		this.socket=socket;
		this.kill=false;
		this.mutex = m;
	}
	/**
	 * Run works at all time until client is kill the connection
	 * run gets messages with the tuple "process_name-args0-..-argn"
	 */
	public void run() {
		
		while(!this.kill) {
			try {
			inFromClient = new Scanner(socket.getInputStream());
			outToClient =new PrintStream(socket.getOutputStream());
			while(inFromClient.hasNextLine()) {
				clientSentence = inFromClient.nextLine();
				System.out.println(clientSentence);
				
				
				if(clientSentence.startsWith("log_in")) {//Done
					String id= log_in(clientSentence);
					System.out.println(id);
					outToClient.println(id);
				}else if(clientSentence.startsWith("sign_up")) {//Done
					String id =sign_up(clientSentence);
					System.out.println(id);
					outToClient.println(id);
				}else if(clientSentence.startsWith("get_groups")) {//Done
					String groups= get_groups(clientSentence);
					System.out.println(groups);
					outToClient.println(groups);
				}else if(clientSentence.startsWith("add_group")) {//Done
					String id=add_group(clientSentence);
					System.out.println(id);
					outToClient.println(id);
				}else if(clientSentence.startsWith("join_group")) {//Done
					String id=join_group(clientSentence);
					System.out.println(id);
					outToClient.println(id);
				}else if(clientSentence.startsWith("check_master")) {//Done
					String id = check_master(clientSentence);
					System.out.println(id);
					outToClient.println(id);
				}else if(clientSentence.startsWith("delete_group")) {//Done
					delete_group(clientSentence);
				}else if(clientSentence.startsWith("get_posts")) {//Done
					String groups= get_posts(clientSentence);
					System.out.println(groups);
					outToClient.println(groups);
				}else if(clientSentence.startsWith("add_post")) {//Done
					String id=add_post(clientSentence);
					System.out.println(id);
					outToClient.println(id);
				}else if(clientSentence.startsWith("update_post")) {//Done
					update_post(clientSentence);
				}
				else if(clientSentence.startsWith("delete_post")) {//Done
					delete_post(clientSentence);
				}else if(clientSentence.startsWith("release_post_update")) {//Done
					release_post_update(clientSentence);
				}else if(clientSentence.startsWith("read_post")) {//Done
					String text=read_post(clientSentence);
					System.out.println(text);
					outToClient.println(text);
				}else if(clientSentence.startsWith("request_post_update")) {//Done
					String id=request_post_update(clientSentence);
					System.out.println(id);
					outToClient.println(id);
				}
			}
			
			}
			 catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
	}
	
	public synchronized String sign_up(String s) {
		mutex.P();
		String[] arr = s.split("-");
		Member m = new Member();
		m.setUsername(arr[1]);
		m.setMail(arr[2]);
		m.setPassword(arr[3]);
		if(m.isMailExist()) {
			mutex.V();
			return "-1";
		}
		if(m.isUsernameExist()) {
			mutex.V();
			return "-1";
		}
		
		m.addMember();
		mutex.V();
		return "1";
		
	}
	public String log_in(String s) {
		mutex.P();
		String[] arr = s.split("-");
		Member m = new Member();
		String ret="";
		m.setUsername(arr[1]);
		m.setPassword(arr[2]);
		m.getIDwithUsernameAndPassword();
		int id = m.getId();
		mutex.V();
		return ret+ id;
	}
	public String get_groups(String s) {
		mutex.P();
		String[] arr = s.split("-");
		TeamMember tm = new TeamMember();
		tm.setMemberID(Integer.parseInt(arr[1]));
		String ss=tm.getIDwithMemberID();
		mutex.V();
		return ss;
		
	}
	public String get_posts(String s) {
		mutex.P();
		String[] arr = s.split("-");
		Team t = new Team();
		t.getIDwithTeamname(arr[1]);
		int teamID = t.getId();
		PostIt p = new PostIt();
		p.setTeamID(teamID);
		String ss=p.getPostnameswithTeamID();
		mutex.V();
		return ss;
	}
	public  String add_post(String s) {
		mutex.P();
		String[] arr = s.split("-");
		Team t = new Team();
		t.getIDwithTeamname(arr[1]);
		int teamID=t.getId();
		PostIt p = new PostIt();
		if(p.isPostExist(teamID,arr[2])){
			mutex.V();
			return "-1";
		}
		p = new PostIt();
		p.setPostname(arr[2]);
		p.setTeamID(teamID);
		p.setText(arr[3]);
		
		p.addPostIt();
		mutex.V();
		return "1";
		
	}
	public  void update_post(String s) {
		mutex.P();
		String[] arr = s.split("-");
		Team t = new Team();
		t.getIDwithTeamname(arr[1]);
		int teamID=t.getId();
		PostIt p = new PostIt();
		p.setPostname(arr[2]);
		p.setTeamID(teamID);
		p.changePostTextwithPostnameAndTeamID(arr[3]);
		p.releaseIsUpdatedNow(teamID, arr[2]);
		mutex.V();
	}
	public void delete_post(String s) {
		mutex.P();
		String[] arr = s.split("-");
		Team t = new Team();
		t.getIDwithTeamname(arr[1]);
		int teamID=t.getId();
		PostIt p = new PostIt();
		p.deletePostItWithpostnameAndTeamID(teamID, arr[2]);
		mutex.V();
	}
	public String read_post(String s) {
		mutex.P();
		String[] arr = s.split("-");
		Team t = new Team();
		t.getIDwithTeamname(arr[1]);
		int teamID=t.getId();
		PostIt p = new PostIt();
		String ss =p.getTextWithpostnameAndTeamID(teamID, arr[2]);
		mutex.V();
		return ss;
	}
	public String request_post_update(String s) {
		mutex.P();
		String[] arr = s.split("-");
		Team t = new Team();
		t.getIDwithTeamname(arr[1]);
		int teamID=t.getId();
		PostIt p = new PostIt();
		String ss =p.getIsUpdatedNow(teamID, arr[2]);
		mutex.V();
		return ss;
	}
	public void release_post_update(String s) {
		mutex.P();
		String[] arr = s.split("-");
		Team t = new Team();
		t.getIDwithTeamname(arr[1]);
		int teamID=t.getId();
		PostIt p = new PostIt();
		p.releaseIsUpdatedNow(teamID, arr[2]);
		mutex.V();
	}
	public synchronized String add_group(String s) {
		mutex.P();
		//1.UserID-2.groupname
		String[] arr = s.split("-");
		Team t = new Team();
		t.setTeamname(arr[2]);
		t.getIDwithTeamname(arr[2]);
		if(t.getId()>0) {
			mutex.V();
			return "-1";
		}
		t.addTeam();
		t.getIDwithTeamname(arr[2]);
		int teamid = t.getId();
		TeamMember tm = new TeamMember();
		tm.setTeamID(teamid);
		tm.setMemberID(Integer.parseInt(arr[1]));
		tm.addTeamMember();
		tm.setAsMaster();
		mutex.V();
		return "1";
	}
	public synchronized String join_group(String s) {
		mutex.P();
		String[] arr = s.split("-");
		String ss="";
		Team t = new Team();
		t.setTeamname(arr[2]);
		t.getIDwithTeamname(arr[2]);
		if(t.getId()==-1) {
			mutex.V();
			return "-1";
		}
		int teamid = t.getId();
		TeamMember tm = new TeamMember();
		tm.setTeamID(teamid);
		tm.setMemberID(Integer.parseInt(arr[1]));
		if(tm.isTeamMemberExist()){
			mutex.V();
			return "-2";
		}
		tm.addTeamMember();
		mutex.V();
		return "1";
	}
	public synchronized void delete_group(String s) {
		mutex.P();
		String[] arr = s.split("-");//1-user_id-groupname
		String ss="";
		Team t = new Team();
		t.setTeamname(arr[2]);
		t.getIDwithTeamname(arr[2]);
		int teamid = t.getId();
		TeamMember tm = new TeamMember();
		tm.setTeamID(teamid);
		tm.setMemberID(Integer.parseInt(arr[1]));
		if(tm.getIsMaster()) {
			t.deleteTeam(teamid);
		}
		tm.getIDwithIDs(teamid, tm.getMemberID());
		tm.deleteTeamMember(tm.getId());
		mutex.V();
	}
	public String check_master(String s) {
		mutex.P();
		String[] arr = s.split("-");//1-user_id-groupname
		Team t = new Team();
		t.getIDwithTeamname(arr[2]);
		int teamid=t.getId();
		TeamMember tm = new TeamMember();
		tm.setTeamID(teamid);
		tm.setMemberID(Integer.parseInt(arr[1]));
		if(tm.getIsMaster()) { 
			mutex.V();
			return "1";
		}
		mutex.V();
		return "-1";
	}
	
	
	
}
