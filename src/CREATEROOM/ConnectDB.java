package CREATEROOM;

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
	
	//email로 사람찾기
	public boolean get_email_accinfo(String email){
		XML xml = new XML();
		xml.make_rootElement("root");
		
		try {
	       Class.forName("com.mysql.jdbc.Driver");
	       conn = DriverManager.getConnection(url, user, pass);

	       if (conn == null)
	          throw new Exception("데이터베이스에 연결할 수 없습니다.");
	         
	       pstmt = (PreparedStatement) conn.prepareStatement("Select * From tb_accinfo Where email=?");
	       pstmt.setString(1, email);
	       
	       rs = pstmt.executeQuery();
	       
	       if(rs.next() == false) return false;
	       else{
	    	   xml.make_element("info");
	    	   xml.make_child("email", rs.getString("email"));
	    	   xml.make_child("name", rs.getString("name"));
	    	   xml.make_child("univ", rs.getString("univ"));
	       }
           
		} catch (Exception e) {
	         e.printStackTrace();
	    }finally{
			try{rs.close();}catch(SQLException e){}
			try{pstmt.close();}catch(SQLException e){}
			try{conn.close();}catch(SQLException e){}
		}
		
		source = xml.make_xml();
		
		return true;
	}
	
	//name으로 사람찾기
	public boolean get_name_accinfo(String name){
		XML xml = new XML();
		xml.make_rootElement("root");
		
		try {
	       Class.forName("com.mysql.jdbc.Driver");
	       conn = DriverManager.getConnection(url, user, pass);

	       if (conn == null)
	          throw new Exception("데이터베이스에 연결할 수 없습니다.");
	        
	       pstmt = (PreparedStatement) conn.prepareStatement("Select * From tb_accinfo Where name=?");
	       pstmt.setString(1, name);
	       
	       rs = pstmt.executeQuery();

	       rs.last();
	       if(rs.getRow() <= 0) return false;
	       rs.beforeFirst();
	       
	       while(rs.next()){
	    	   xml.make_element("info");
	    	   xml.make_child("email", rs.getString("email"));
	    	   xml.make_child("name", rs.getString("name"));
	    	   xml.make_child("univ", rs.getString("univ"));
	       }
	              
		} catch (Exception e) {
	         e.printStackTrace();
	    }finally{
			try{rs.close();}catch(SQLException e){}
			try{pstmt.close();}catch(SQLException e){}
			try{conn.close();}catch(SQLException e){}
		}
		
		source = xml.make_xml();
		
		return true;
	}
	
	//Document to Stirng 값 전달
	public String getSource(){ return source; }
		
	// 공작소 만들기
	public boolean makeRoom(String name, String from_date, String to_date, String member) {
		// member email (팀장은 첫번째)
		XML xml = new XML();
		Document doc;

		ArrayList<String> memArray = new ArrayList<String>();
		int result = 0, mem_count = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new java.util.Date());

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pass);

			if (conn == null)
				throw new Exception("데이터베이스에 연결할 수 없습니다.");

			// 디비가 연결되었다면 xml에 있는 member email을 arraylist에 옮기기
			doc = xml.stringToDocument(member);
			Element root = doc.getDocumentElement();
			NodeList mem = root.getElementsByTagName("member");

			mem_count = mem.getLength();

			for (int i = 0; i < mem.getLength(); i++) {
				Node text = mem.item(i).getChildNodes().item(0);
				memArray.add(text.getNodeValue());
			}

			// tb_roominfo
			pstmt = (PreparedStatement) conn.prepareStatement(
					"insert into tb_roominfo(name, manager_email, term_from, term_to, dialog_url, create_date, mem_cnt) "
							+ "values(?,?,?,?,?,?,?)");
			pstmt.setString(1, name);
			pstmt.setString(2, memArray.get(0)); // 팀장 이메일 doc의 첫번째 element
			pstmt.setString(3, from_date);
			pstmt.setString(4, to_date);
			pstmt.setString(5, ""); // dialog_url
			pstmt.setString(6, date);
			pstmt.setInt(7, memArray.size());

			result = pstmt.executeUpdate();

			if (result > 0) {
				// 만들어진 room의 id값 얻어오기
				pstmt = null;
				rs = null;

				pstmt = (PreparedStatement) conn.prepareStatement(
						"Select * From tb_roominfo Where name=? and manager_email=? and term_from=? and term_to=? and dialog_url=? and create_date=? and mem_cnt=?");
				pstmt.setString(1, name);
				pstmt.setString(2, memArray.get(0));
				pstmt.setString(3, from_date);
				pstmt.setString(4, to_date);
				pstmt.setString(5, ""); // dialog_url
				pstmt.setString(6, date);
				pstmt.setInt(7, memArray.size());
				
				rs = pstmt.executeQuery();
				rs.first();

				int room_id = rs.getInt("id");
				System.out.println("방번호 얻어옴 " + room_id);

				// tb_memberinfo
				for (int i = 0; i < memArray.size(); i++) {
					pstmt = (PreparedStatement) conn
							.prepareStatement("insert into tb_memberinfo(mem_email, room_id) values(?,?)");
					pstmt.setString(1, memArray.get(i));
					pstmt.setInt(2, room_id); // 방 번호 이름(수정하기)
					result = pstmt.executeUpdate();
				}
				
				//공작소 당 테이블 한개씩 생성
				boolean r = makeRoomTable(room_id);
				if(r==false) return false;  //만약 false라면 위에 생성한 튜플들 다 삭제해야하나?!
				
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
			}
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}

		return false;
	}

	public boolean makeRoomTable(int room_id){
		String tb_name = "tb_dialog"+String.valueOf(room_id);
		String query = "CREATE TABLE "+tb_name+" ("
				+ "id int(11) NOT NULL AUTO_INCREMENT,"
				+ "speaker varchar(30) character set utf8 collate utf8_general_ci,"
				+ "context varchar(100) character set utf8 collate utf8_general_ci,"
				+ "datetime datetime, "
				+ "PRIMARY KEY (id));";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
		       Class.forName("com.mysql.jdbc.Driver");
		       conn = DriverManager.getConnection(url, user, pass);

		       if (conn == null)
		          throw new Exception("데이터베이스에 연결할 수 없습니다.");
		         
		       pstmt = (PreparedStatement) conn.prepareStatement(query);
		       pstmt.executeUpdate();
		                  
		       return true;
			} catch (Exception e) {
		         e.printStackTrace();
		    }finally{
				try{pstmt.close();}catch(SQLException e){}
				try{conn.close();}catch(SQLException e){}
			}
		return false;
	}
	

}
