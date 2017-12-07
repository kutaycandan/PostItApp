package server;

import java.text.*;
import java.util.*;

import database.*;

import java.sql.*;
public class MainServer {
	public static void main(String[] args) {
		Thread t = new TCPServer();
		t.start();
		//TeamMember teamMember= new TeamMember();
		//teamMember.setTeamID(2);
		//teamMember.setMemberID(3);
		//teamMember.getIDwithMemberID();
		
	}
}
//SimpleDateFormat sdf = new SimpleDateFormat("dd MM YYYY HH:mm:ss ", new Locale("tr"));
		//String date = sdf.format(new Date());
		//System.out.println(date);
		/*Member member =new Member();
		member.setUsername("kutay");
		member.setFirstName("Kutay");
		member.addMember();
		member.deleteMember(1);

		Member member2 = new Member();
		member2.setLastName("Candan");
		member2.setPassword("Kutay123");
		member2.changeMemberwithUsername("kutay");
		member2.getIDwithUsername("kutay");
		System.out.println(member2.getId());
		
		Team team = new Team();
		team.setTeamname("takim1");
		team.addTeam();
		team.deleteTeam(1);
		team.setTeamname("kutaytakim");
		team.changeTeamwithTeamName("takim1");
		team.getIDwithTeamname("kutaytakim");
		System.out.println(team.getId());
		
		TeamMember teamMember= new TeamMember();
		teamMember.setTeamID(2);
		teamMember.setMemberID(3);
		teamMember.addTeamMember();
		teamMember.setAsMaster(2, 3);
		teamMember.getIDwithIDs(2, 3);
		teamMember.deleteTeamMember(teamMember.getId());
		
		PostIt postIt = new PostIt();
		postIt.setText("sa");
		postIt.setPostname("kutsi");
		postIt.setTeamID(2);
		postIt.addPostIt();
		postIt.setText("as");
		postIt.changePostItwithPostName("kutsi");
		postIt.getIDwithPostname("kutsi");
		postIt.deletePostIt(postIt.getId());*/