package ENTERROOM;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DeleteRoomServlet
 */
@WebServlet("/delete_room")
public class DeleteRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteRoomServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ConnectDB connDB = ConnectDB.getConnectDB();
		
		String room_id = request.getParameter("roomId");
		
		HttpSession session = request.getSession();
		String email = (String)session.getAttribute("user_email");
		
		String position = connDB.getEmailPosition(room_id, email);
		System.out.println(room_id+"  "+email+"  "+position);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pwrite = response.getWriter();
		
		if(position.equals("팀장")){
			ServletContext context = request.getSession().getServletContext();
			String path = context.getRealPath("files");
			
			if(connDB.deleteRoom(room_id, email, path)){
				System.out.println("방지우기 성공");
				pwrite.write("true");
				pwrite.close();
				return;
			}
		}
		else if(position.equals("팀원")){
			if(connDB.deleteRoomMember(room_id, email)){
				System.out.println("팀원을 방에서 지우기 성공");
				pwrite.write("true");
				pwrite.close();
				return;
			}
		}
		
		System.out.println("방지우기 실패");
		pwrite.write("flase");
		pwrite.close();
	}

}
