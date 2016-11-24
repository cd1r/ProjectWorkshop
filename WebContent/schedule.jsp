<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>  
<%
	boolean isSession = false;
	String user_email = null;
	user_email = (String)session.getAttribute("user_email");
	String roomId = request.getParameter("roomId");
	System.out.println("Workshopinfo Room ID : " + roomId);
	
	if(user_email != null)
		isSession = true;
	
	System.out.println("채팅방 세션 ID : " + user_email);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link href="./css/chatmaster.css" type="text/css" rel="stylesheet"/>
<link href="./css/schedule.css" type="text/css" rel="stylesheet"/>
<script src="./js/jquery-1.9.1.min.js"></script>
<script src="./js/schedule.js?ver=9"></script>
</head>
<body>
<input id="room-id" type="hidden" value="<%=roomId%>">
<input id="user-email" type="hidden" value="<%=user_email%>">
<table id="chatLayout">
	<tr>
    	<td class="left-side">

            <div class="profile-div">
                <div class="profile-img-div">
                    <img class="profile-img" src="park.png">
                </div>
                <div class="profile-name">박효신</div>
            </div>
            <div class="left-menu">
                <div class="all-menu" id="all-menu-label">전체 메뉴</div>
                <div class="all-menu" id="workshop-info-menu">공작소정보</div>
                <div class="all-menu" id="chat-room-menu">회의실</div>
                <div class="all-menu" id="file-menu">공작소창고보기</div>
                <div class="all-menu" id="schedule-menu">공작소일정보기</div>
            </div>

        </td>
        <td id="schedule-content">
            <table id="calendar" align="center">
                <tr id="control-tr">
                    <td>
                        <input type="button" id="prev-month-btn" value="<">
                        <input type="button" id="next-month-btn" value=">">
                    </td>
                    <td></td>
                    <td colspan="3"><span id="year-span">2016</span>년 <span id="month-span">11</span>월</td>
                    <td><input type="button" value="월별" id="show-cal-btn"></td>
                    <td><input type="button" value="리스트" id="show-list-btn"></td>
                </tr>
                <tr>
                    <td class="day-char">일</td>
                    <td class="day-char">월</td>
                    <td class="day-char">화</td>
                    <td class="day-char">수</td>
                    <td class="day-char">목</td>
                    <td class="day-char">금</td>
                    <td class="day-char">토</td>
                </tr>
        	</table>
            
            <div id="add-schedule-form">
	            <div>
	            	<span>담당자</span>
	            	<select id="worker-select">
	                </select>
	                <input type="button" value="일정추가" id="add-schedule-btn">
	            </div>
	            <div>    
	            	<span>작업기간</span>
	                <input type="date" id="from">
	                <span>~</span>
	                <input type="date" id="to">
	            </div>
	            <div>
	               <span>작업내용</span>
	               <input type="text" id="job">        
	            </div>
    		</div>
        </td>
     </tr>
</table>
</body>
</html>