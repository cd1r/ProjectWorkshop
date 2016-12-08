package ENTERROOM;

import java.io.File;
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
	
	//email�� �ڽ��� ���� �� ã��
	public String getRoomList(String email){
		XML xml = new XML();
		xml.make_rootElement("root");
		
		try {
	       Class.forName("com.mysql.jdbc.Driver");
	       conn = DriverManager.getConnection(url, user, pass);

	       if (conn == null)
	          throw new Exception("�����ͺ��̽��� ������ �� �����ϴ�.");
	         
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
	
	//���� ����ڰ� �����Ϸ��� ���� �������� �������� �˾Ƴ��� �Լ�
	public String getEmailPosition(String roomId, String email) {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pass);

			if (conn == null)
				throw new Exception("�����ͺ��̽��� ������ �� �����ϴ�.");

			pstmt = (PreparedStatement) conn.prepareStatement("Select * From tb_roominfo Where id=" + roomId);

			rs = pstmt.executeQuery();

			rs.first();
			String manager = rs.getString("manager_email");

			if (manager.equals(email)) return "����";
			else return "����";

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			try{rs.close();}catch(SQLException e){}
			try{pstmt.close();}catch(SQLException e){}
			try{conn.close();}catch(SQLException e){}
		}
	}
	
	//���� �����
	public boolean deleteRoomMember(String roomId, String email){
		try{
			Class.forName("com.mysql.jdbc.Driver"); 
			conn = DriverManager.getConnection(url, user, pass);
			
			if (conn == null)
				throw new Exception("�����ͺ��̽��� ������ �� �����ϴ�.");
			
			pstmt = conn.prepareStatement("delete from tb_memberinfo "
					+ "where room_id="+roomId+" and mem_email='"+email+"'");
			int result = pstmt.executeUpdate();
		
			if(result == 0) return false;
			
			System.out.println("���� ����");
			return true;
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try{pstmt.close();}catch(SQLException e){}
			try{conn.close();}catch(SQLException e){}
		}
		return false;
	}
	
	//���� �����
	public boolean deleteRoom(String roomId, String email, String path){
		try{
			Class.forName("com.mysql.jdbc.Driver"); 
			conn = DriverManager.getConnection(url, user, pass);
			
			if (conn == null)
				throw new Exception("�����ͺ��̽��� ������ �� �����ϴ�.");
			
			pstmt = conn.prepareStatement("delete from tb_roominfo where id="+roomId);
			int result = pstmt.executeUpdate();
		
			if(result == 0) return false;
			System.out.println("�� ����");
			
			
			pstmt = conn.prepareStatement("drop table tb_dialog"+roomId);
			pstmt.executeUpdate();
			
			System.out.println("�� ���̺�(dialog) ����");
			
			
			//���丮 �����
			try{
				File dir = new File(path+"\\","dialog"+roomId);
				File[] files = dir.listFiles();
				
				for(int i=0; i<files.length; i++)
					files[i].delete();
				dir.delete();
				System.out.println("���丮 ����");
			}catch(Exception e){}
			
			return true;
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try{pstmt.close();}catch(SQLException e){}
			try{conn.close();}catch(SQLException e){}
		}
		return false;
	}
}
