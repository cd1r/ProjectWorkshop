<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	boolean isSession = false;
	String user_email = null;
	user_email = (String)session.getAttribute("user_email");
	if(user_email != null)
		isSession = true;
	
	System.out.println("세션 ID : " + user_email);
%>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>회원정보수정</title>
</head>
<body>
	<input id="user-email" type="hidden" value="<%=user_email%>">

</body>
</html>