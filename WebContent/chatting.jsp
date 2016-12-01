<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<%
	boolean isSession = false;
	String user_email = null;
	user_email = (String)session.getAttribute("user_email");
	if(user_email != null)
		isSession = true;
	
	System.out.println("채팅방 세션 ID : " + user_email);
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>채팅창</title>
<script src="./js/jquery-1.9.1.min.js"></script>
<script src="./js/chatting.js"></script>
</head>
<body>
	<input id="user-email" type="hidden" value="<%=user_email%>">
    <form>
        <!-- 송신 메시지 작성하는 창 -->
        <input id="textMessage" type="text">
        <!-- 송신 버튼 -->
        <input type="button" id="send" value="Send">
    </form>
    <br/>
    
    <!-- 결과 메시지 보여주는 창 -->
    <textarea id="messageTextArea" rows="10" cols="50"></textarea>

    </body>
</html>