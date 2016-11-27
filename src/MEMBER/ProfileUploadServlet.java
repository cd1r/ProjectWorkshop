package MEMBER;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class ProfileUploadServlet
 */
@WebServlet("/profile_upload")
public class ProfileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//String path = "C:\\Users\\hyoseung\\Documents\\dialog12";
		ServletContext context = request.getSession().getServletContext();
		String path = context.getRealPath("images\\profile");
		//path => C:\Users\hyoseung\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\AdvWeb\images\profile
		//절대경로
		
		int sizeLimit = 5*1024*1024;
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pwrite = response.getWriter();
		
		try {
			MultipartRequest multi = new MultipartRequest(request, path, sizeLimit, "utf-8" ,new DefaultFileRenamePolicy());
			
			Enumeration formNames = multi.getFileNames(); // 폼의 이름 반환
			String formName = (String) formNames.nextElement();
			String filename = multi.getFilesystemName(formName);
			path += "\\"+filename;
			System.out.println(path);
			System.out.println("filename : " + filename);
			
		} catch (Exception e) {
			e.printStackTrace();
			pwrite.write("false");
			pwrite.close();
		}
		
		pwrite.write(path);
		pwrite.close();
	}
	

}
