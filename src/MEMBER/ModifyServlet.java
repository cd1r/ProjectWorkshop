package MEMBER;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ModifyServlet
 */
@WebServlet("/modify")
public class ModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyServlet() {
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
		String type = request.getParameter("type");
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		String organization = request.getParameter("organization");
		String phone = request.getParameter("phone");
		String grade = request.getParameter("grade");
		String profile_url = request.getParameter("profile_url");
		String extention = "jpg";
		
		String profile_save = "";
		
		System.out.println(type+" "+email+" "+pw+" "+organization+" "+phone+" "+grade+" "+profile_url+" "+extention);
		
		if(!profile_url.equals("")){
			try {
				
				File file=new File(profile_url); //원본파일부르기
				profile_url = profile_url.substring(0, profile_url.lastIndexOf("\\"));
				String profile_name = email.substring(0, email.indexOf(".")+1);
				
				File existFile = new File(profile_url+"\\"+profile_name+extention);
				if(existFile.isFile()){
					System.out.println("파일 존재");
					existFile.delete();
				}
				
				file.renameTo(new File(profile_url+"\\"+profile_name+extention)); //파일이름변경
				profile_save = "./images/profile/"+profile_name+extention;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else profile_save = "";
		
		if(type.equals("basic"))
			result = String.valueOf(connDB.modifyAccount(email, pw, organization, phone, grade, profile_save));
		else
			result = String.valueOf(connDB.modifyKakaoAccount(email, organization, phone, grade, profile_save));
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pwrite = response.getWriter();
		
		if(result != null) {
			System.out.println(result);
			pwrite.write(result);
			pwrite.close();
		}
		
	}

}
