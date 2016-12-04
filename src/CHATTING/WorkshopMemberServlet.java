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
 * Servlet implementation class WorkshopInfoServlet
 */
@WebServlet("/workshop_member")
public class WorkshopMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WorkshopMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ConnectDB connDB = ConnectDB.getConnectDB();
		
		String roomId = request.getParameter("roomId");
		
		String result = connDB.loadWorkshopMemberInfo(roomId);
		System.out.println(result);
		
		HttpSession Session = request.getSession();
		Session.setAttribute("room" + roomId+"_manager_email", result.split("\t")[1]);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		
		if(result != null)
		{
			pw.write(result.split("\t")[0]);
			pw.close();
		}
		else
		{
			System.out.println("공작소 멤버 읽기 실패");
			pw.close();
		}
	}

}
