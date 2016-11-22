package CREATEROOM;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchKeywordServlet
 */
@WebServlet("/search_keyword")
public class SearchKeywordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchKeywordServlet() {
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
		String select = request.getParameter("select");
		String keyword = request.getParameter("keyword");
		System.out.println("찾고자하는" + select +" "+keyword);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		
		if(select.equals("이메일"))
			result = String.valueOf(connDB.get_email_accinfo(keyword));
		else //이름
			result = String.valueOf(connDB.get_name_accinfo(keyword));
		
		if(result.equals("false")) {
			System.out.println("정보없음");
			pw.write(result);
		}
		else{
			System.out.println(connDB.getSource());
			pw.write(connDB.getSource());
		}
	}

}
