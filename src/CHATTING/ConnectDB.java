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
	ResultSet rs = null;
	
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
	          throw new Exception("데이터베이스에 연결할 수 없습니다.");
	         
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
	          throw new Exception("데이터베이스에 연결할 수 없습니다.");
	       
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
	          throw new Exception("데이터베이스에 연결할 수 없습니다.");
	         
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
	

	//일반 메시지
	public boolean insertDialog(int roomId, String email, String context){
		String tb_name = "tb_dialog"+String.valueOf(roomId);
		
		try {
		       Class.forName("com.mysql.jdbc.Driver");
		       int rs = 0;
		       conn = DriverManager.getConnection(url, user, pass);

		       if (conn == null)
		          throw new Exception("데이터베이스에 연결할 수 없습니다.");
		         
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

	//파일 메시지
	public boolean insertDialogAndFile(int roomId, String email, String context, String fileId){
		String tb_name = "tb_dialog"+String.valueOf(roomId);
		
		try {
		       Class.forName("com.mysql.jdbc.Driver");
		       int rs = 0;
		       conn = DriverManager.getConnection(url, user, pass);

		       if (conn == null)
		          throw new Exception("데이터베이스에 연결할 수 없습니다.");
		         
		       /*pstmt = (PreparedStatement) conn.prepareStatement(
		    		   "Insert Into tb_dialog8 (speaker, context, datetime)"+
		    		   "Values('"+ email +"', '" + context + "', NOW())");*/
		       
		       pstmt = (PreparedStatement) conn.prepareStatement(
		    		   "Insert Into "+tb_name+" (speaker, context, datetime, file)"+
		    		   "Values('"+ email +"', '" + context + "', NOW(), " + fileId + ")");
		   
		       rs = pstmt.executeUpdate();
		       
		       if(rs > 0) return true;
		       else return false;
		    	              
		} catch (Exception e) {
		         e.printStackTrace();
		         return false;
		}
	}
	
	//서버에 저장된 파일 이름 가져오기
	public String getServerSavedFileName(String fileId){
		String result = null;
		
		try {
		       Class.forName("com.mysql.jdbc.Driver");
		       conn = DriverManager.getConnection(url, user, pass);

		       if (conn == null)
		          throw new Exception("데이터베이스에 연결할 수 없습니다.");
		         
		       pstmt = (PreparedStatement) conn.prepareStatement("Select * From tb_fileinfo Where id="+fileId);
		       
		       rs = pstmt.executeQuery();
		       rs.first();
		       
		       result = rs.getString("file_name");
		    
		       
		} catch (Exception e) {
		         e.printStackTrace();
		         return null;
		}
		
		return result;
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
	          throw new Exception("데이터베이스에 연결할 수 없습니다.");
	         
	       pstmt = (PreparedStatement) conn.prepareStatement("Select manager_email From tb_roominfo Where id="+roomId);
	       rs = pstmt.executeQuery();
	       rs.next();
	       managerEmail = rs.getString("manager_email");
	       
	       
	       pstmt = (PreparedStatement) conn.prepareStatement(
	    		   "Select email, name, gender, phone, univ, grade, photo_url, last_read_dialog"+
	    		   " From tb_memberinfo mem Join tb_accinfo acc On mem.mem_email=acc.email"+
	    		   " Where mem.room_id=?");
	       pstmt.setInt(1, Integer.valueOf(roomId));
	       
	       rs = pstmt.executeQuery();
	       
	       while(rs.next()){
	    	   xml.make_element("info");
	    	   
	    	   if(rs.getString("email").equals(managerEmail))
	    		   xml.make_child("position", "팀장");
	    	   else
	    		   xml.make_child("position", "팀원");
	    	   
	    	   xml.make_child("photo_url", rs.getString("photo_url"));
	    	   
	    	   if(rs.getString("gender").equals("M"))
	    		   xml.make_child("name_gender", rs.getString("name")+" (남)");
	    	   else
	    		   xml.make_child("name_gender", rs.getString("name")+" (여)");
	    	   xml.make_child("univ_grade", rs.getString("univ") + " " + rs.getString("grade") + "학년");
	    	   xml.make_child("email", rs.getString("email"));
	    	   xml.make_child("phone", rs.getString("phone"));
	    	   xml.make_child("last_read_dialog", rs.getString("last_read_dialog"));
	       }
	       System.out.println("Test : " + xml.make_xml() + "\t" + managerEmail);
	       return source = xml.make_xml() + "\t" + managerEmail;
	       
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
	          throw new Exception("데이터베이스에 연결할 수 없습니다.");
	            
	       pstmt = (PreparedStatement) conn.prepareStatement(
	    		   "Select name, create_date, mem_cnt, term_to-CurDate() As dday"+
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
	
	//파일 디비에 저장
	public String fileUpload(String roomId, String email, String name, String type, long size) {
		int dbresult = 0;
		String result="false";
		String path = "dialog"+roomId+"/"+name;
		System.out.println("DB 파일 저장 : "+ email);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		       conn = DriverManager.getConnection(url, user, pass);

		    if (conn == null)
		    	throw new Exception("데이터베이스에 연결할 수 없습니다.");
		         
		    pstmt = (PreparedStatement) conn.prepareStatement("insert into tb_fileinfo (room_id, uploader_email, file_name, extention, file_url, size, upload_date) "
		    		+ "values(" + roomId + ", '" +email + "', '" + name + "', '" + type + "', '"+ path + "', "+ size + ", NOW())");
				
		    dbresult = pstmt.executeUpdate();
		    
		    if(dbresult > 0){
		    	pstmt = null;
				rs = null;
				
				pstmt = (PreparedStatement) conn.prepareStatement(
						"Select * From tb_fileinfo Where room_id=? and uploader_email=? and file_name=?");
				pstmt.setString(1, roomId);
				pstmt.setString(2, email);
				pstmt.setString(3, name);
				
				rs = pstmt.executeQuery();
				rs.first();
				
				result = rs.getString("id");
				System.out.println("파일 번호 얻어옴 " + result);
		    }
		    else {
		    	result="false";
		    }
		    
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{pstmt.close();}catch(SQLException e){}
			try{conn.close();}catch(SQLException e){}
		}
		
		return result;
	}
	
	//파일이 저장될 경로 받아오기
	public String getFileURL(int room_id){
		String result = null;
		
		try {
	       Class.forName("com.mysql.jdbc.Driver");
	       conn = DriverManager.getConnection(url, user, pass);

	       if (conn == null)
	          throw new Exception("데이터베이스에 연결할 수 없습니다.");
	         
	       pstmt = (PreparedStatement) conn.prepareStatement("Select * From tb_roominfo Where id="+room_id);
	       
	       rs = pstmt.executeQuery();
	         
	       rs.first();
	       result = rs.getString("file_url");
	       
		} catch (Exception e) {
	         e.printStackTrace();
	         return result;
	    }finally{
			try{rs.close();}catch(SQLException e){}
			try{pstmt.close();}catch(SQLException e){}
			try{conn.close();}catch(SQLException e){}
		}
		return result;
	}

	public String insertSchedule(String roomId, String email, String job, String color, String from, String to) {
		try {
		       Class.forName("com.mysql.jdbc.Driver");
		       int rs = 0;
		       ResultSet resultSet = null;
		       conn = DriverManager.getConnection(url, user, pass);

		       if (conn == null)
		          throw new Exception("데이터베이스에 연결할 수 없습니다.");
		         
		       pstmt = (PreparedStatement) conn.prepareStatement(
		    		   "Insert Into tb_jobinfo (room_id, mem_email, content, color, term_from, term_to) Values(?, ?, ?, ?, ?, ?)");
		       pstmt.setString(1, roomId);
		       pstmt.setString(2, email);
		       pstmt.setString(3, job);
		       pstmt.setString(4, color);
		       pstmt.setString(5, from);
		       pstmt.setString(6, to);
		       
		       rs = pstmt.executeUpdate();
		       
		       if(rs > 0){
		    	   pstmt = (PreparedStatement) conn.prepareStatement("Select id From tb_jobinfo Order By id Desc Limit 1");
		    	   resultSet = pstmt.executeQuery();
		    	   resultSet.next();
		    	   String lastId = resultSet.getString("id");
		    	   
		    	   return lastId;
		       }
		       else return null;
		    	              
			} catch (Exception e) {
		         e.printStackTrace();
		         return null;
			}
	}

	public String loadScheduleInfo(String roomId, String year, String month) {
		
		XML xml = new XML();
		xml.make_rootElement("root");
		ResultSet rs = null;
		
		try {
	       Class.forName("com.mysql.jdbc.Driver");
	       conn = DriverManager.getConnection(url, user, pass);

	       if (conn == null)
	          throw new Exception("데이터베이스에 연결할 수 없습니다.");
	            
	       pstmt = (PreparedStatement) conn.prepareStatement(
	    		   "Select id, mem_email, acc.name, content, color, term_from, term_to, to_days(CurDate())-to_days(term_to) As dday"+
	    		   " From tb_jobinfo job Join tb_accinfo acc On job.mem_email=acc.email"+
	    		   " Where room_id=? And term_from >= '" + year + "-" + month + "-" + "1" + "' And term_to < '" + year + "-" + (month+1) + "-" + "1" + "'" + 
	    		   " Order By term_from Asc;");
	       pstmt.setInt(1, Integer.valueOf(roomId));
	       //pstmt.setString(2, year + "-" + month + "-" + "1");
	       
	       rs = pstmt.executeQuery();
	       
	       while(rs.next()){
	    	   xml.make_element("info");
	    	   	    	   
	    	   xml.make_child("id", rs.getString("id"));
	    	   xml.make_child("mem_email", rs.getString("mem_email"));
	    	   xml.make_child("name", rs.getString("name"));
	    	   xml.make_child("job", rs.getString("content"));
	    	   xml.make_child("color", rs.getString("color"));
	    	   xml.make_child("from", rs.getString("term_from"));
	    	   xml.make_child("to", rs.getString("term_to"));
	    	   xml.make_child("dday", rs.getString("dday"));
	       }
	       
	       return source = xml.make_xml();
	       
		} catch (Exception e) {
	         e.printStackTrace();
	         return null;
		}
	}

	public boolean insertLastReadDialogId(String email, int roomId) {
		
		try {
		       Class.forName("com.mysql.jdbc.Driver");
		       ResultSet resultSet = null;
		       int rs = 0;
		       int lastId = 0;
		       conn = DriverManager.getConnection(url, user, pass);

		       if (conn == null)
		          throw new Exception("데이터베이스에 연결할 수 없습니다.");
		       
		       pstmt = (PreparedStatement) conn.prepareStatement(
		    		   "Select id From tb_dialog" + roomId + " Order By id Desc Limit 1");
		       resultSet = pstmt.executeQuery();
		       
		       resultSet.first();
		       lastId = resultSet.getInt("id");
		       
		       pstmt = (PreparedStatement) conn.prepareStatement(
		    		   "Update tb_memberinfo Set last_read_dialog=" + lastId +
		    		   " Where mem_email='" + email + "' And room_id='" + roomId + "'");
		       rs = pstmt.executeUpdate();
		       
		       if(rs > 0) return true;
		       else return false;
		    	              
		} catch (Exception e) {
		         e.printStackTrace();
		         return false;
		}
	}

	public String loadDialog(String roomId) {
		XML xml = new XML();
		xml.make_rootElement("root");
		ResultSet rs = null;
		
		try {
	       Class.forName("com.mysql.jdbc.Driver");
	       conn = DriverManager.getConnection(url, user, pass);

	       if (conn == null)
	          throw new Exception("데이터베이스에 연결할 수 없습니다.");
	            
	       pstmt = (PreparedStatement) conn.prepareStatement(
	    		   "Select di.id, di.speaker, di.context, di.datetime, acc.photo_url"+ 
	    		   " From tb_dialog" + roomId + " di Join tb_accinfo acc On di.speaker=acc.email");
	       rs = pstmt.executeQuery();
	       
	       while(rs.next()){
	    	   xml.make_element("dialog");
	    	   	    	   
	    	   xml.make_child("id", rs.getString("di.id"));
	    	   xml.make_child("speaker", rs.getString("di.speaker"));
	    	   xml.make_child("context", rs.getString("di.context"));
	    	   xml.make_child("datetime", rs.getString("di.datetime"));
	    	   xml.make_child("photo_url", rs.getString("acc.photo_url"));
	       }
	       
	       return source = xml.make_xml();
	       
		} catch (Exception e) {
	         e.printStackTrace();
	         return null;
		}
	}
	
	public String downFileInfo (String id){
		String result = null;
		
		try {
	       Class.forName("com.mysql.jdbc.Driver");
	       conn = DriverManager.getConnection(url, user, pass);

	       if (conn == null)
	          throw new Exception("데이터베이스에 연결할 수 없습니다.");
	         
	       pstmt = (PreparedStatement) conn.prepareStatement("Select * From tb_fileinfo Where id="+id);
	       
	       rs = pstmt.executeQuery();
	         
	       if(rs.first()){
	    	   result = rs.getString("file_url");
	       }
	       else result = null;
	                  
		} catch (Exception e) {
	         e.printStackTrace();
	    }finally{
			try{rs.close();}catch(SQLException e){}
			try{pstmt.close();}catch(SQLException e){}
			try{conn.close();}catch(SQLException e){}
		}
		
		return result;
	}
}