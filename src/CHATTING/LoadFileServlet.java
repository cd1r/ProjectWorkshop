package CHATTING;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoadFileServlet
 */
@WebServlet("/load_file")
public class LoadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectDB connDB = ConnectDB.getConnectDB();
		
		String roomId = request.getParameter("roomId");
		String searchCriteria = request.getParameter("searchCriteria");
		String searchKeyword = request.getParameter("searchKeyword");
		String isList = request.getParameter("isList");
		
		System.out.println("LoadFileServlet Parmas : " + roomId + " " + searchCriteria + " " + searchKeyword + " " + isList);
		
		String result = null; 
		
		if(searchCriteria != null)
			result = connDB.searchFileInfo(roomId, searchCriteria, searchKeyword);
		
		else
			result = connDB.loadFileInfo(roomId, isList);
		
		System.out.println(result);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		
		if(result != null)
		{
			pw.write(result);
			pw.close();
		}
		else
		{
			System.out.println("파일 목록 읽기 실패");
			pw.close();
		}
	}
}
