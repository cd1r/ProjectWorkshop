package ENTERROOM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import XML.XML;
public class ConnectDB {
private static ConnectDB connectDB = new ConnectDB();
	
	public static ConnectDB getConnectDB(){
		return connectDB;
	}
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	String url = "jdbc:mysql://121.126.233.20:3306/shsydb?useSSL=false";
	String user = "shsy";
	String pass = "shsy08";
	
	//DOMSource source;
	String source;
	
	//email로 자신이 속한 방 찾기
	public String getRoomList(String email){
		XML xml = new XML();
		xml.make_rootElement("root");
		
		try {
	       Class.forName("com.mysql.jdbc.Driver");
	       conn = DriverManager.getConnection(url, user, pass);

	       if (conn == null)
	          throw new Exception("데이터베이스에 연결할 수 없습니다.");
	         
	       pstmt = (PreparedStatement) conn.prepareStatement(
	    		   "Select room.id, room.name, acc.name, room.mem_cnt" +
	    		   " From (tb_memberinfo mem Join tb_roominfo room On mem.room_id=room.id)" + 
	    		   " Join tb_accinfo acc On acc.email=room.manager_email Where mem_email=?");
	       pstmt.setString(1, email);
	       
	       rs = pstmt.executeQuery();
	       
	       while(rs.next()){
	    	   xml.make_element("info");
	    	   xml.make_child("id", rs.getString("id"));
	    	   xml.make_child("room_name", rs.getString("room.name"));
	    	   xml.make_child("manager_name", rs.getString("acc.name"));
	    	   xml.make_child("mem_cnt", rs.getString("room.mem_cnt"));
	       }
	       
	       return xml.make_xml();
           
		} catch (Exception e) {
	         e.printStackTrace();
	         return null;
	    }
	}
}
