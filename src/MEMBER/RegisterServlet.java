package MEMBER;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		String name = request.getParameter("name");
		String pw = request.getParameter("pw");
		String email = request.getParameter("email");
		String organization = request.getParameter("organization");
		String phone = request.getParameter("phone");
		String grade = request.getParameter("grade");
		String gender = request.getParameter("gender");
		
		System.out.println(name+" "+pw+" "+email+" "+phone+" "+organization+" "+grade+" "+gender+" ");
		
		result = String.valueOf(connDB.registerAccount(name, pw, email, organization, phone, grade, gender));
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pwrite = response.getWriter();
		
		if(result != null) {
			System.out.println(result);
			pwrite.write(result);
			pwrite.close();
		}
	}

}
