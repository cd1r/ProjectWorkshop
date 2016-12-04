<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	boolean isSession = false;
	String user_email = (String)session.getAttribute("user_email");
	String roomId = request.getParameter("roomId");
	String user_name = (String)session.getAttribute("user_name");
	String photo_url = (String)session.getAttribute("photo_url");
	System.out.println("AllFile Room ID : " + roomId);
	
	if(user_email != null)
		isSession = true;
	
	System.out.println("채팅방 세션 ID : " + user_email);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link href="./css/chatmaster.css" type="text/css" rel="stylesheet"/>
<link href="./css/allfile.css" type="text/css" rel="stylesheet"/>
<script src="./js/jquery-1.9.1.min.js"></script>
<script src="./js/allfile.js?ver=1"></script>
</head>
<body>
<input id="user-name" type="hidden" value="<%=user_name%>">
<input id="photo_url" type="hidden" value="<%=photo_url%>">
<input id="room-id" type="hidden" value="<%=roomId%>">
<table id="chatLayout">
	<tr>
    	<td rowspan="2" class="left-side">
            <div class="profile-div">
                <div class="profile-img-div">
                    <img class="profile-img" src="<%=photo_url%>">
                </div>
                <div class="profile-name"><%=user_name%></div>
            </div>
            <div class="left-menu">
                <div class="all-menu" id="all-menu-label">전체 메뉴</div>
                <div class="all-menu" id="workshop-info-menu">공작소정보</div>
                <div class="all-menu" id="chat-room-menu">회의실</div>
                <div class="all-menu" id="file-menu">공작소창고보기</div>
                <div class="all-menu" id="schedule-menu">공작소일정보기</div>
            </div>
            <div id="menu-icon">
            	<img src="./images/file.png">
            </div>
        </td>
        <td id="allfile-info-content">
        	<div id="search-form">
        		<select id="search-criteria">
        			<option value="file-name">파일명</option>
        			<option value="extension">확장자</option>
        			<option value="uploader">게시자</option>
        		</select>
        		<input type="text" id="search-keyword" placeholder="검색어를 입력해주세요.">
        		<input type="button" id="search-btn" value="검색">
        		<input type="button" id="refresh-btn" value="새로고침">
        	</div>
        	<table id="content-table">
            </table>
        </td>
     </tr>
</table>
</body>
</html>