<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.text.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="CHATTING.ConnectDB" %>

<%
	request.setCharacterEncoding("utf-8");

	ConnectDB connDB = ConnectDB.getConnectDB();

	String roomId = request.getParameter("roomId");
	String fileId = request.getParameter("fileId");
	
	String path = request.getSession().getServletContext().getRealPath("files\\dialog"+roomId); 
	System.out.println("[파일 다운로드 "+fileId+"] "+path);

	
	String fileName = connDB.getServerSavedFileName(fileId);//"참고.txt";//file번호를 통해서 서버에 저장되어 있는 파일 명 (db로 불러오기)
	String type = fileName.substring(fileName.indexOf(".")+1).toLowerCase();
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String date = sdf.format(new java.util.Date());
	
	String orgfileName = "ProjectWorkshop" + date + "."+type;
	
	
	InputStream in = null;
    OutputStream os = null;
    File file = null;
    boolean skip = false;
    String client = "";
    
    try{
    	 
        // 파일을 읽어 스트림에 담기
        try{
            file = new File(path, fileName);
            in = new FileInputStream(file);
        }catch(FileNotFoundException fe){
            skip = true;
        }
         
        client = request.getHeader("User-Agent");
 
        // 파일 다운로드 헤더 지정
        response.reset() ;
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Description", "JSP Generated Data");
 
 
        if(!skip){
             
            // IE
            if(client.indexOf("MSIE") != -1){
                response.setHeader ("Content-Disposition", "attachment; filename="+new String(orgfileName.getBytes("KSC5601"),"ISO8859_1"));
 
            }else{
                // 한글 파일명 처리
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
            out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다');history.back();</script>");
 
        }
        os.flush();
        os.close();
        in.close();
 
    }catch(Exception e){
      e.printStackTrace();
    } 
%>
