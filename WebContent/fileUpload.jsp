<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.text.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="CHATTING.ConnectDB" %>

<%
	request.setCharacterEncoding("utf-8");

	ConnectDB connDB = ConnectDB.getConnectDB();
	
	String roomId = request.getParameter("roomId");
	String email = request.getParameter("email");
	String filename = "";
	long filesize = 0;
	File file;

	String path = request.getSession().getServletContext().getRealPath("files\\dialog"+roomId); 
	System.out.println("[파일 업로드 "+roomId+"  "+email+"] "+path);

	int sizeLimit = 20*1024*1024;
	
	response.setContentType("text/html;charset=utf-8");
	PrintWriter pwrite = response.getWriter();

	try {
		MultipartRequest multi = new MultipartRequest(request, path, sizeLimit, "utf-8" ,new DefaultFileRenamePolicy());
		
		Enumeration formNames = multi.getFileNames(); // 폼의 이름 반환
		String formName = (String) formNames.nextElement();
		filename = multi.getFilesystemName(formName);
		file = multi.getFile(formName);
		filesize = file.length();
		path += "\\"+filename;
		
		System.out.println(path);
		System.out.println("filename : " + filename);
		
	} catch (Exception e) {
		e.printStackTrace();
		pwrite.write("false");
		pwrite.close();
	}
	
	String name = filename.substring(0, filename.indexOf("."));
	String fileType = filename.substring(filename.indexOf(".")+1).toLowerCase();
	//double sizekbyte = filesize/1024;
	System.out.println("db에 넣기전  : "+name + "  "+fileType+"  "+filesize);
	String result = connDB.fileUpload(roomId, email, filename, fileType, filesize);
	
	pwrite.write(result);
	pwrite.close();
%>