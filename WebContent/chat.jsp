<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	boolean isSession = false;

	String user_email = (String)session.getAttribute("user_email");
	String user_name = (String)session.getAttribute("user_name");
	String photo_url = (String)session.getAttribute("photo_url");
	
	String roomId = request.getParameter("roomId");
	String manager_email = (String)session.getAttribute("room" + roomId + "_manager_email");
	
	System.out.println("Chat.jsp Room ID : " + roomId + " / ManagerEmail : " + manager_email);
	if(user_email != null)
		isSession = true;
	
	System.out.println("채팅방 세션 ID : " + user_email);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link href="./css/chatmaster.css?ver=1" type="text/css" rel="stylesheet"/>
<link href="./css/chat.css" type="text/css" rel="stylesheet"/>
<script src="./js/jquery-1.9.1.min.js"></script>
<script src="./js/chatting.js?ver=1"></script>
<title><%=roomId%>번방</title>
</head>

<body>
<input id="user-email" type="hidden" value="<%=user_email%>">
<input id="user-name" type="hidden" value="<%=user_name%>">
<input id="photo_url" type="hidden" value="<%=photo_url%>">
<input id="room-id" type="hidden" value="<%=roomId%>">
<input id="manager-email" type="hidden" value="<%=manager_email%>">
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
            	<img src="./images/chat.png">
            </div>

        </td>
        <td colspan="2" class="dialog-content">
        	<div id="dialog-div">
	        	<ul class="dialog-ul"> 	
	            </ul>
            </div>
        </td>
        <td rowspan="2" class="right-side">
        	<table id="right-menu-table">
            	<tr>
                	<td id="tab1-td" class="tab-td">구<br>성<br>원</td>
                    <td id="tab-content-td" rowspan="5">
                    	<div id="tab1-div">
                    		<div id="member-label">공작소 구성원</div>
                    		<div id="member-cnt-label">총 인원 : 0명</div>
                        	<ul id="right-member-ul">
                            </ul>
                        </div>
                        <!--  <div id="tab2-div">tab2
                        	<form enctype="multipart/form-data">
                        	<input type="file" id="file_upload"> 
                        	<input type="button" id="upload-btn" value="파일 업로드"><br/>
                        	<input type="button" id="download-btn" value="파일 다운로드">
                        	</form>
                        </div>-->
                        <div id="tab2-div">
                        	
                        </div>
                        <div id="tab3-div">
							<div id="worklist-label">내 할일 목록</div>
                        	<div id="work-cnt-label">총 일정 : 0개</div>
                        	<div id="work-order-criteria">
                        		<select id="criteria-category">
                        			<option value="due-date">마감일</option>
                        			<option value="job-name">작업이름</option>
                        		</select>
                        		<select id="criteria-order">
                        			<option>오름차순</option>
                        			<option>내림차순</option>
                        		</select>
                        		<input type="button" id="order-confirm-btn" value="적용">
                        	</div>
                        	<div id="worklist-ul-div">
                        		<ul id="worklist-ul">
                        		</ul>
                        	</div>
						</div>
                    </td>
                </tr>
            	<!-- <tr>
	                <td id="tab2-td" class="tab-td">파<br>일<br>보<br>내<br>기</td>
                </tr> -->
            	<tr>
   	                <td id="tab2-td" class="tab-td">파<br>일<br>보<br>기</td>
                </tr>
            	<tr>
                	<td id="tab3-td" class="tab-td">내<br>할<br>일</td>
                </tr>
                <tr id="last-empty-tr"></tr>
            </table>
        </td>
    </tr>
    <tr>
        <td class="dialog-enter-td"><textarea id="dialog-enter" placeholder="대화내용을 입력해주세요"></textarea></td>
        <td class="send-btn-td"><input type="button" id="send-btn" value="보내기"></td>
    </tr>
</table>
</body>
</html>