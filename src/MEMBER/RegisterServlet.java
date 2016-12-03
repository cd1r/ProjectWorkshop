package MEMBER;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

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
		String type = request.getParameter("type");//일반회원가입인지 카카오톡회원가입인지 구별해줌
		String name = request.getParameter("name");
		String pw = request.getParameter("pw");
		String email = request.getParameter("email");
		String organization = request.getParameter("organization");
		String phone = request.getParameter("phone");
		String grade = request.getParameter("grade");
		String gender = request.getParameter("gender");
		String profile_url = request.getParameter("profile_url");
		String extention = request.getParameter("extention");
		
		String profile_save = "";
		
		System.out.println("넘어온 값 "+type+" "+name+" "+pw+" "+email+" "+phone+" "+organization+" "+grade+" "+gender+" "+extention);
		System.out.println("넘어온 값 "+profile_url);
		
		if(!profile_url.equals("./images/null_profile.png")){
			try {
				
				File file=new File(profile_url); //원본파일부르기
				profile_url = profile_url.substring(0, profile_url.lastIndexOf("\\"));
				String profile_name = email.substring(0, email.indexOf(".")+1);
				file.renameTo(new File(profile_url+"\\"+profile_name+extention)); //파일이름변경
				profile_save = "./images/profile/"+profile_name+extention;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else profile_save = "./images/null_profile.png";
		
		result = String.valueOf(connDB.registerAccount(type, name, pw, email, organization, phone, grade, gender, profile_save));
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pwrite = response.getWriter();
		
		if(result != null) {
			System.out.println(result);
			pwrite.write(result);
			pwrite.close();
		}
	}

}
