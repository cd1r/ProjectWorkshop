package MEMBER;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

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
	
	//로그인
	public boolean loginConfirm(String id, String pw){
		boolean result = false;
		
		try {
	       Class.forName("com.mysql.jdbc.Driver");
	       conn = DriverManager.getConnection(url, user, pass);

	       if (conn == null)
	          throw new Exception("데이터베이스에 연결할 수 없습니다.");
	         
	       pstmt = (PreparedStatement) conn.prepareStatement("Select * From tb_accinfo Where email=? and pw=?");
	       pstmt.setString(1, id);
	       pstmt.setString(2, pw);
	       
	       rs = pstmt.executeQuery();
	         
	       if(rs.first()){
	    	   //로그인 가능
	    	   if(rs.getString("pw").equals(pw)) result = true;
	    	   else result = false; //비밀번호 틀림
	       }
	       else result = false;
	                  
		} catch (Exception e) {
	         e.printStackTrace();
	    }finally{
			try{rs.close();}catch(SQLException e){}
			try{pstmt.close();}catch(SQLException e){}
			try{conn.close();}catch(SQLException e){}
		}
		
		return result;
	}
	
	//회원가입
	public boolean registerAccount(String name, String pw, String email, String organization,
									String phone, String grade, String gender){
		int result = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new java.util.Date());
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		    conn = DriverManager.getConnection(url, user, pass);

		    if (conn == null)
		    	throw new Exception("데이터베이스에 연결할 수 없습니다.");
		         
		    pstmt = (PreparedStatement) conn.prepareStatement("insert into tb_accinfo values(?,?,?,?,?,?,?,?,?)");
		    pstmt.setString(1, email);
		    pstmt.setString(2, pw);
		    pstmt.setString(3, name);
		    pstmt.setString(4, gender);
		    pstmt.setString(5, phone);
		    pstmt.setString(6, organization);
		    pstmt.setString(7, grade);
		    pstmt.setString(8, null);
		    pstmt.setString(9, date);
				
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
	
	//아이디(이메일) 중복체크
	public boolean checkEmailRedundancy(String email){
		boolean result = false;
		
		try{
			Class.forName("com.mysql.jdbc.Driver"); 
			conn = DriverManager.getConnection(url, user, pass);
			pstmt = conn.prepareStatement("select email from tb_accinfo where email=?");
			pstmt.setString(1, email);
			
			rs=pstmt.executeQuery();
				
			if(rs.next()) result=false; //아이디 존재
			else result=true; //아이디 사용가능
				
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try{rs.close();}catch(SQLException e){}
			try{pstmt.close();}catch(SQLException e){}
			try{conn.close();}catch(SQLException e){}
		}
		return result;
	}
}
