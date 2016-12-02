package CHATTING;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ModifyDeleteScheduleServlet
 */
@WebServlet("/modify_delete_schedule")
public class ModifyDeleteScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyDeleteScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ConnectDB connDB = ConnectDB.getConnectDB();
		boolean result = false;
		
		String methodType = request.getParameter("methodType");
		String id = request.getParameter("id");
		
		System.out.println(methodType + " " + id);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		
		if(methodType.equals("Delete")){
			result = connDB.deleteSchedule(id);
		}
		
		else if(methodType.equals("Modify")){
			String email = request.getParameter("email");
			String job = request.getParameter("job");
			String color = request.getParameter("color");
			String from = request.getParameter("from");
			String to = request.getParameter("to");
			
			result = connDB.modifySchedule(id, email, job, color, from, to);
		}
		
		pw.write(String.valueOf(result));
		pw.close();
		
	}

}
