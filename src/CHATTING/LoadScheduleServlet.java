package CHATTING;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoadScheduleServlet
 */
@WebServlet("/load_schedule")
public class LoadScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ConnectDB connDB = ConnectDB.getConnectDB();
		
		String isTerm = request.getParameter("isTerm");
		String roomId = request.getParameter("roomId");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
				
		String result = null;

		System.out.println("Is Term : " + isTerm);

		if(isTerm.equals("True"))
		result = connDB.loadScheduleInfoInTerm(roomId, year, month);

		else
		result = connDB.loadScheduleInfoAll(roomId);

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
			System.out.println("Schedule Load Failed");
			pw.close();
		}
	}

}
