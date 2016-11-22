package CHATTING;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import XML.XML;

public class ConnectDB {
	private static ConnectDB connectDB = new ConnectDB();
	
	public static ConnectDB getConnectDB(){
		return connectDB;
	}
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	
	String url = "jdbc:mysql://121.126.233.20:3306/shsydb?useSSL=false";
	String user = "shsy";
	String pass = "shsy08";
	
	//DOMSource source;
	String source;
	
	public boolean addClient(String session, String email, int roomId){
		try {
	       Class.forName("com.mysql.jdbc.Driver");
	       int rs = 0;
	       conn = DriverManager.getConnection(url, user, pass);

	       if (conn == null)
	          throw new Exception("�����ͺ��̽��� ������ �� �����ϴ�.");
	         
	       pstmt = (PreparedStatement) conn.prepareStatement("Insert Into tb_session Values(?, ?, ?, ?)");
	       pstmt.setString(1, session);
	       pstmt.setString(2, email);
	       pstmt.setInt(3, roomId);
	       pstmt.setString(4, null);
	       
	       rs = pstmt.executeUpdate();
	       
	       if(rs > 0) return true;
	       else return false;
	    	              
		} catch (Exception e) {
	         e.printStackTrace();
	         return false;
		}
	}
	
	public boolean deleteClient(String email, int roomId){
		
		try {
	       Class.forName("com.mysql.jdbc.Driver");
	       int rs = 0;
	       conn = DriverManager.getConnection(url, user, pass);

	       if (conn == null)
	          throw new Exception("�����ͺ��̽��� ������ �� �����ϴ�.");
	       
	       System.out.println("deleteClient() email : " + email);
	       pstmt = (PreparedStatement) conn.prepareStatement("Delete From tb_session Where email=? And room_id=?");
	       pstmt.setString(1, email);
	       pstmt.setInt(2, roomId);
	       
	       rs = pstmt.executeUpdate();
	       
	       if(rs > 0) return true;
	       else return false;
	    	              
		} catch (Exception e) {
	         e.printStackTrace();
	         return false;
		}
	}
	
	public String getReceiverClient(int roomId){
		XML xml = new XML();
		xml.make_rootElement("root");
		ResultSet rs = null;
		try {
	       Class.forName("com.mysql.jdbc.Driver");
	       conn = DriverManager.getConnection(url, user, pass);

	       if (conn == null)
	          throw new Exception("�����ͺ��̽��� ������ �� �����ϴ�.");
	         
	       pstmt = (PreparedStatement) conn.prepareStatement("Select session From tb_session Where room_id=?");
	       pstmt.setInt(1, roomId);
	       
	       rs = pstmt.executeQuery();
	       
	       while(rs.next()){
	    	   xml.make_element("sessions");
	    	   xml.make_child("session", rs.getString("session"));
	       }
	       
	       return source = xml.make_xml();
	       
		} catch (Exception e) {
	         e.printStackTrace();
	         return null;
		}
	}
	

	public boolean insertDialog(int roomId, String email, String context){
		String tb_name = "tb_dialog"+String.valueOf(roomId);
		
		try {
		       Class.forName("com.mysql.jdbc.Driver");
		       int rs = 0;
		       conn = DriverManager.getConnection(url, user, pass);

		       if (conn == null)
		          throw new Exception("�����ͺ��̽��� ������ �� �����ϴ�.");
		         
		       /*pstmt = (PreparedStatement) conn.prepareStatement(
		    		   "Insert Into tb_dialog8 (speaker, context, datetime)"+
		    		   "Values('"+ email +"', '" + context + "', NOW())");*/
		       
		       pstmt = (PreparedStatement) conn.prepareStatement(
		    		   "Insert Into "+tb_name+" (speaker, context, datetime)"+
		    		   "Values('"+ email +"', '" + context + "', NOW())");
		   
		       rs = pstmt.executeUpdate();
		       
		       if(rs > 0) return true;
		       else return false;
		    	              
			} catch (Exception e) {
		         e.printStackTrace();
		         return false;
			}
	}

	public String loadWorkshopMemeberInfo(String roomId) {

		XML xml = new XML();
		xml.make_rootElement("root");
		ResultSet rs = null;
		String managerEmail = null;
		try {
	       Class.forName("com.mysql.jdbc.Driver");
	       conn = DriverManager.getConnection(url, user, pass);

	       if (conn == null)
	          throw new Exception("�����ͺ��̽��� ������ �� �����ϴ�.");
	         
	       pstmt = (PreparedStatement) conn.prepareStatement("Select manager_email From tb_roominfo Where id=?");
	       pstmt.setInt(1, Integer.valueOf(roomId));
	       rs = pstmt.executeQuery();
	       rs.next();
	       managerEmail = rs.getString("manager_email");
	       
	       
	       pstmt = (PreparedStatement) conn.prepareStatement(
	    		   "Select email, name, gender, phone, univ, grade, photo_url"+
	    		   " From tb_memberinfo mem Join tb_accinfo acc On mem.mem_email=acc.email"+
	    		   " Where mem.room_id=?");
	       pstmt.setInt(1, Integer.valueOf(roomId));
	       
	       rs = pstmt.executeQuery();
	       
	       while(rs.next()){
	    	   xml.make_element("info");
	    	   
	    	   if(rs.getString("email").equals(managerEmail))
	    		   xml.make_child("position", "����");
	    	   else
	    		   xml.make_child("position", "����");
	    	   
	    	   xml.make_child("photo_url", rs.getString("photo_url"));
	    	   
	    	   if(rs.getString("gender").equals("M"))
	    		   xml.make_child("name_gender", rs.getString("name")+" (��)");
	    	   else
	    		   xml.make_child("name_gender", rs.getString("name")+" (��)");
	    	   xml.make_child("univ_grade", rs.getString("univ") + " " + rs.getString("grade") + "�г�");
	    	   xml.make_child("email", rs.getString("email"));
	    	   xml.make_child("phone", rs.getString("phone"));
	       }
	       
	       return source = xml.make_xml();
	       
		} catch (Exception e) {
	         e.printStackTrace();
	         return null;
		}
	}

	public String loadWorkshopInfo(String roomId) {
		
		XML xml = new XML();
		xml.make_rootElement("root");
		ResultSet rs = null;
		
		try {
	       Class.forName("com.mysql.jdbc.Driver");
	       conn = DriverManager.getConnection(url, user, pass);

	       if (conn == null)
	          throw new Exception("�����ͺ��̽��� ������ �� �����ϴ�.");
	            
	       pstmt = (PreparedStatement) conn.prepareStatement(
	    		   "Select name, create_date, mem_cnt, term_to-term_from As dday"+
	    		   " From tb_roominfo Where id=?");
	       pstmt.setInt(1, Integer.valueOf(roomId));
	       
	       rs = pstmt.executeQuery();
	       
	       while(rs.next()){
	    	   xml.make_element("info");
	    	   	    	   
	    	   xml.make_child("workshop_name", rs.getString("name"));
	    	   xml.make_child("create_date", rs.getString("create_date"));
	    	   xml.make_child("mem_cnt", rs.getString("mem_cnt"));
	    	   xml.make_child("dday", rs.getString("dday"));
	       }
	       
	       return source = xml.make_xml();
	       
		} catch (Exception e) {
	         e.printStackTrace();
	         return null;
		}
	}
}