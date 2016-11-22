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
		System.out.println("ã�����ϴ�" + select +" "+keyword);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		
		if(select.equals("�̸���"))
			result = String.valueOf(connDB.get_email_accinfo(keyword));
		else //�̸�
			result = String.valueOf(connDB.get_name_accinfo(keyword));
		
		if(result.equals("false")) {
			System.out.println("��������");
			pw.write(result);
		}
		else{
			System.out.println(connDB.getSource());
			pw.write(connDB.getSource());
		}
	}

}
