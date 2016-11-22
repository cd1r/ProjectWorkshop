package CREATEROOM;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateRoomServlet
 */
@WebServlet("/create_room")
public class CreateRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateRoomServlet() {
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
		String from_date = request.getParameter("from_date");
		String to_date = request.getParameter("to_date");
		String member = request.getParameter("member");
		
		System.out.println("°øÀÛ¼Ò : " + name +" " + from_date +" "+to_date);
		System.out.println(member);
		
		result = String.valueOf(connDB.makeRoom(name, from_date, to_date, member));
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pwrite = response.getWriter();

		if(result != null) {
			System.out.println(result);
			pwrite.write(result);
			pwrite.close();
		}
		
	}

}
