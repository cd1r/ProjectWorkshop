package MEMBER;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	public LoginServlet() {
		super();
        // TODO Auto-generated constructor stub
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		ConnectDB connDB = ConnectDB.getConnectDB();
		
		String result;
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		System.out.println("아이디 : " + userId + " 비밀번호: " + userPw);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		
		result = connDB.loginConfirm(userId, userPw); // 로그인 성공시 이름과 사진 경로를 리턴해줌 - 세윤
		
		if(result != null){
			System.out.println(result);
			HttpSession Session = request.getSession();
			Session.setAttribute("user_email", userId);
			Session.setAttribute("user_name", result.split("\t")[0]);
			Session.setAttribute("photo_url", result.split("\t")[1]);
			Session.setAttribute("login_type", "basic");
			
			pw.write("true");
			pw.close();
		}
		
	}

}
