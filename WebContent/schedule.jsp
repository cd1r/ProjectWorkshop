<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>  
<%
	boolean isSession = false;
	String user_email = (String)session.getAttribute("user_email");
	String roomId = request.getParameter("roomId");
	String user_name = (String)session.getAttribute("user_name");
	String photo_url = (String)session.getAttribute("photo_url");
	String manager_email = (String)session.getAttribute("room" + roomId + "_manager_email");
	
	System.out.println("Schedule Room ID : " + roomId + " / ManagerEmail : " + manager_email);
	
	if(user_email != null)
		isSession = true;
	
	System.out.println("채팅방 세션 ID : " + user_email);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link href="./css/chatmaster.css" type="text/css" rel="stylesheet"/>
<link href="./css/schedule.css?ver=1" type="text/css" rel="stylesheet"/>
<script src="./js/jquery-1.9.1.min.js"></script>
<script src="./js/schedule.js?ver=2"></script>
</head>
<body>
<input id="user-name" type="hidden" value="<%=user_name%>">
<input id="photo_url" type="hidden" value="<%=photo_url%>">
<input id="room-id" type="hidden" value="<%=roomId%>">
<input id="user-email" type="hidden" value="<%=user_email%>">
<input id="manager-email" type="hidden" value="<%=manager_email%>">
<table id="chatLayout">
	<tr>
    	<td class="left-side">

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

        </td>
        <td id="schedule-content">
            <table id="calendar" align="center">
                <tr id="control-tr">
                    <td>
                        <input type="button" id="prev-month-btn" value="<">
                        <input type="button" id="next-month-btn" value=">">
                    </td>
                    <td></td>
                    <td colspan="3"><span id="year-span">2016</span>년 <span id="month-span">12</span>월</td>
                    <!-- <td><input type="button" value="월별" id="show-cal-btn"></td>
                    <td><input type="button" value="리스트" id="show-list-btn"></td> -->
                    <td></td>
                    <td></td>
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
        	<div id="follow" style="position:absolute;">
        		<div id="float-name"></div>
        		<div id="float-dday"></div>
        		<div id="float-term"><span id="float-from"></span><span> ~ </span><span id="float-to"></span></div>
        	</div>
            <div id="add-schedule-form">
	            <div>
	            	<span class="left-label">담당자</span>
	            	<select id="worker-select">
	                </select>
	                <span class="mid-label" id="color-span">색깔</span>
	                <select id="color" style="color:#f14a2a">
	                	<option value="#f14a2a" style="color:#f14a2a">귤</option>
	                	<option value="#ffba30" style="color:#ffba30">바나나</option>
	                	<option value="#027d3a" style="color:#027d3a">바질</option>
	                	<option value="#23c073" style="color:#23c073">세이지</option>
	                	<option value="#3547b7" style="color:#3547b7">블루베리</option>
	                	<option value="#9324a4" style="color:#9324a4">포도</option>
	                	<option value="#ec7770" style="color:#ec7770">홍학</option>
	                	<option value="#606060" style="color:#606060">흑연</option>
	                </select>
	                <input type="button" value="수정" id="modify-schedule-btn">
	                <input type="button" value="일정추가" id="add-schedule-btn">
	            </div>
	            <div>    
	            	<span class="left-label">작업기간</span>
	                <input type="date" id="from">
	                <span class="mid-label">~</span>
	                <input type="date" id="to">
	            </div>
	            <div>
	               <span class="left-label">작업내용</span>
	               <input type="text" id="job">        
	            </div>
    		</div>
        </td>
     </tr>
</table>
</body>
</html>