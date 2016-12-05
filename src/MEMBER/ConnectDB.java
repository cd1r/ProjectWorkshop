package MEMBER;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

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
	
	//�α���
	public String loginConfirm(String id, String pw){
		
		String result = null;
		
		try {
	       Class.forName("com.mysql.jdbc.Driver");
	       conn = DriverManager.getConnection(url, user, pass);

	       if (conn == null)
	          throw new Exception("�����ͺ��̽��� ������ �� �����ϴ�.");
	         
	       pstmt = (PreparedStatement) conn.prepareStatement("Select * From tb_accinfo Where email=? and pw=?");
	       pstmt.setString(1, id);
	       pstmt.setString(2, pw);
	       
	       rs = pstmt.executeQuery();
	         
	       if(rs.first()){
	    	   //�α��� ����
	    	   if(rs.getString("pw").equals(pw))
	    		   result = rs.getString("name") + "\t" + rs.getString("photo_url");
	    	   else 
	    		   result = null; //��й�ȣ Ʋ��
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
	
	//īī�� �α���
	public String loginKakaoConfirm(String id){
		
		String result = null;
		
		try {
	       Class.forName("com.mysql.jdbc.Driver");
	       conn = DriverManager.getConnection(url, user, pass);

	       if (conn == null)
	          throw new Exception("�����ͺ��̽��� ������ �� �����ϴ�.");
	         
	       pstmt = (PreparedStatement) conn.prepareStatement("Select * From tb_accinfo Where reg_type=?");
	       pstmt.setInt(1, Integer.parseInt(id));
	       
	       rs = pstmt.executeQuery();
	         
	       if(rs.next())
	    	   result = rs.getString("email") + "\t" + rs.getString("name") + "\t" + rs.getString("photo_url");
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
	
	//ȸ������
	public boolean registerAccount(String type, String name, String pw, String email, String organization,
									String phone, String grade, String gender, String photo_url){
		int result = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new java.util.Date());
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		    conn = DriverManager.getConnection(url, user, pass);

		    if (conn == null)
		    	throw new Exception("�����ͺ��̽��� ������ �� �����ϴ�.");
		         
		    pstmt = (PreparedStatement) conn.prepareStatement("insert into tb_accinfo values(?,?,?,?,?,?,?,?,?,?)");
		    pstmt.setString(1, email);
		    pstmt.setString(2, pw);
		    pstmt.setString(3, name);
		    pstmt.setString(4, gender);
		    pstmt.setString(5, phone);
		    pstmt.setString(6, organization);
		    pstmt.setString(7, grade);
		    pstmt.setString(8, photo_url);
		    pstmt.setString(9, date);
		    pstmt.setInt(10, Integer.parseInt(type));
				
		    result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{pstmt.close();}catch(SQLException e){}
			try{conn.close();}catch(SQLException e){}
		}
		
		if(result > 0) return true;
		else return false;
	}
	
	//���̵�(�̸���) �ߺ�üũ
	public boolean checkEmailRedundancy(String email){
		boolean result = false;
		
		try{
			Class.forName("com.mysql.jdbc.Driver"); 
			conn = DriverManager.getConnection(url, user, pass);
			pstmt = conn.prepareStatement("select email from tb_accinfo where email=?");
			pstmt.setString(1, email);
			
			rs=pstmt.executeQuery();
				
			if(rs.next()) result=false; //���̵� ����
			else result=true; //���̵� ��밡��
				
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try{rs.close();}catch(SQLException e){}
			try{pstmt.close();}catch(SQLException e){}
			try{conn.close();}catch(SQLException e){}
		}
		return result;
	}
	
	public String getInfo(String email){
		XML xml = new XML();
		xml.make_rootElement("root");
		String result = null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver"); 
			conn = DriverManager.getConnection(url, user, pass);
			pstmt = conn.prepareStatement("select * from tb_accinfo where email=?");
			pstmt.setString(1, email);
			
			rs=pstmt.executeQuery();
				
			if(rs.next()) {
				xml.make_element("info");
				xml.make_child("name", rs.getString("name"));
				xml.make_child("gender", rs.getString("gender"));
				xml.make_child("phone", rs.getString("phone"));
				xml.make_child("univ", rs.getString("univ"));
				xml.make_child("grade", rs.getString("grade"));
			}
				
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try{rs.close();}catch(SQLException e){}
			try{pstmt.close();}catch(SQLException e){}
			try{conn.close();}catch(SQLException e){}
		}
		
		result = xml.make_xml();
		return result;
	}

	// �Ϲ�ȸ�� ��������
	public boolean modifyAccount(String email, String pw, String organization, String phone,
														String grade, String photo_url) {
		int result = 0;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pass);

			if (conn == null)
				throw new Exception("�����ͺ��̽��� ������ �� �����ϴ�.");

			if(!photo_url.equals("")){
				System.out.println("�Ϲ�ȸ������+���� : "+pw+" "+phone+" "+organization+" "+grade+" "+photo_url);
				pstmt = (PreparedStatement) conn.prepareStatement("update tb_accinfo set pw=?, phone=?, univ=?, "
																+ "grade=?, photo_url=? where email=?");
				pstmt.setString(1, pw);
				pstmt.setString(2, phone);
				pstmt.setString(3, organization);
				pstmt.setString(4, grade);
				pstmt.setString(5, photo_url);
				pstmt.setString(6, email);
			}
			else {
				System.out.println("�Ϲ�ȸ������ : "+pw+" "+phone+" "+organization+" "+grade);
				pstmt = (PreparedStatement) conn.prepareStatement("update tb_accinfo set pw=?, phone=?, univ=?, "
																+ "grade=? where email=?");
				pstmt.setString(1, pw);
				pstmt.setString(2, phone);
				pstmt.setString(3, organization);
				pstmt.setString(4, grade);
				pstmt.setString(5, email);
			}
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException e) {}
			try {conn.close();} catch (SQLException e) {}
		}

		return true;
	}
	
	// īī��ȸ�� ��������
	public boolean modifyKakaoAccount(String email, String organization, String phone,
												String grade, String photo_url) {
		int result = 0;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pass);

			if (conn == null)
				throw new Exception("�����ͺ��̽��� ������ �� �����ϴ�.");

			if(!photo_url.equals("")){
				System.out.println("īī��ȸ������+���� : "+phone+" "+organization+" "+grade+" "+photo_url);
				pstmt = (PreparedStatement) conn.prepareStatement("update tb_accinfo set phone=?, univ=?, "
																+ "grade=?, photo_url=? where email=?");
				pstmt.setString(1, phone);
				pstmt.setString(2, organization);
				pstmt.setString(3, grade);
				pstmt.setString(4, photo_url);
				pstmt.setString(5, email);
			}
			else {
				System.out.println("īī��ȸ������ : "+phone+" "+organization+" "+grade);
				pstmt = (PreparedStatement) conn.prepareStatement("update tb_accinfo set phone=?, univ=?, "
																+ "grade=? where email=?");
				pstmt.setString(1, phone);
				pstmt.setString(2, organization);
				pstmt.setString(3, grade);
				pstmt.setString(4, email);
			}
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException e) {}
			try {conn.close();} catch (SQLException e) {}
		}

		return true;
	}
	
}
