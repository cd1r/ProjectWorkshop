<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.io.*"%>
<%@ page import="java.text.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>

<%
	request.setCharacterEncoding("utf-8");

//�ʿ��Ѱ� : ���� ����� ���, ������ ����� ���� �̸�
	String roomId = request.getParameter("roomId");
	String fileId = request.getParameter("fileId");
	
	String path = request.getSession().getServletContext().getRealPath("files\\dialog"+roomId); 
	System.out.println("[���� �ٿ�ε� "+fileId+"] "+path);

	String fileName = "����.txt";//file��ȣ�� ���ؼ� ������ ����Ǿ� �ִ� ���� �� (db�� �ҷ�����)
	
	String orgfileName = "test.txt";
	
	
	InputStream in = null;
    OutputStream os = null;
    File file = null;
    boolean skip = false;
    String client = "";
    
    try{
        
    	 
        // ������ �о� ��Ʈ���� ���
        try{
            file = new File(path, fileName);
            in = new FileInputStream(file);
        }catch(FileNotFoundException fe){
            skip = true;
        }
         
        client = request.getHeader("User-Agent");
 
        // ���� �ٿ�ε� ��� ����
        response.reset() ;
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Description", "JSP Generated Data");
 
 
        if(!skip){
 
             
            // IE
            if(client.indexOf("MSIE") != -1){
                response.setHeader ("Content-Disposition", "attachment; filename="+new String(orgfileName.getBytes("KSC5601"),"ISO8859_1"));
 
            }else{
                // �ѱ� ���ϸ� ó��
                orgfileName = new String(orgfileName.getBytes("utf-8"),"iso-8859-1");
 
                response.setHeader("Content-Disposition", "attachment; filename=\"" + orgfileName + "\"");
                response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
            }  
             
            response.setHeader ("Content-Length", ""+file.length() );
 
 
       
            os = response.getOutputStream();
            byte b[] = new byte[(int)file.length()];
            int leng = 0;
             
            while( (leng = in.read(b)) > 0 ){
                os.write(b,0,leng);
            }
 
        }else{
            response.setContentType("text/html;charset=UTF-8");
            out.println("<script language='javascript'>alert('������ ã�� �� �����ϴ�');history.back();</script>");
 
        }
         
        os.flush();
        os.close();
        in.close();
 
    }catch(Exception e){
      e.printStackTrace();
    } 
%>