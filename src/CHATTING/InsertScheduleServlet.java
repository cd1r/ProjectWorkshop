package CHATTING;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InsertScheduleServlet
 */
@WebServlet("/insert_schedule")
public class InsertScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectDB connDB = ConnectDB.getConnectDB();
		
		String roomId = request.getParameter("roomId");
		String email = request.getParameter("email");
		String job = request.getParameter("job");
		String color = request.getParameter("color");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String lastId = null;
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		
		lastId = connDB.insertSchedule(roomId, email, job, color, from, to);
		System.out.println("lastId : " + lastId);
		if(lastId != null)
		{
			pw.write("true " + lastId);
			pw.close();
		}
		else{
			pw.write("false");
			pw.close();
		}
	}

}
