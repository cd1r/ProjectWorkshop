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
 * Servlet implementation class IsOnlineServlet
 */
@WebServlet("/Is_online")
public class IsOnlineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IsOnlineServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectDB connDB = ConnectDB.getConnectDB();
		
		String roomId = request.getParameter("roomId");
		
		String result = connDB.loadIsLogin(roomId);
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
			System.out.println("접속 여부 확인 실패");
			pw.close();
		}
	}

}
