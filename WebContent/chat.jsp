<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	boolean isSession = false;
	String user_email = null;
	user_email = (String)session.getAttribute("user_email");
	String roomId = request.getParameter("roomId");
	System.out.println("Chat.jsp Room ID : " + roomId);
	if(user_email != null)
		isSession = true;
	
	System.out.println("채팅방 세션 ID : " + user_email);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link href="./css/chatmaster.css" type="text/css" rel="stylesheet"/>
<link href="./css/chat.css" type="text/css" rel="stylesheet"/>
<script src="./js/jquery-1.9.1.min.js"></script>
<script src="./js/chatting.js"></script>
<title>ChatLayout</title>
</head>

<body>
<input id="user-email" type="hidden" value="<%=user_email%>">
<input id="room-id" type="hidden" value="<%=roomId%>">
<table id="chatLayout">
	<tr>
    	<td rowspan="2" class="left-side">

            <div class="profile-div">
                <div class="profile-img-div">
                    <img class="profile-img" src="./images/park.png">
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
        <td colspan="2" class="dialog-content">
        	<ul class="dialog-ul"> 	
            </ul>
        </td>
        <td rowspan="2" class="right-side">
        	<table id="right-menu-table">
            	<tr>
                	<td id="tab1-td" class="tab-td">구<br>성<br>원</td>
                    <td id="tab-content-td" rowspan="5">
                    	<div id="tab1-div">
                        	<ul id="right-member-ul">
                            	<li class="right-member-li">
                                	<table class="right-profile-table">
                                    	<tr>
                                        	<td class="profile-img-td" rowspan="2"><div><img class="right-mini-profile" src="./images/park.png"></div></td>
                                            <td class="member-name-td">박효신</td>
                                        </tr>
                                    	<tr>
                                            <td class="member-email-td">park@naver.com</td>
                                        </tr>                                        
                                    </table>
                                </li>
                                <li class="right-member-li">
                                	<table class="right-profile-table">
                                    	<tr>
                                        	<td class="profile-img-td" rowspan="2"><div><img class="right-mini-profile" src="./images/hwang.jpg"></div></td>
                                            <td class="member-name-td">황세윤</td>
                                        </tr>
                                    	<tr>
                                            <td class="member-email-td">cd1r@naver.com</td>
                                        </tr>                                        
                                    </table>
                                </li>
                            </ul>
                        </div>
                        <div id="tab2-div">tab2
                        	<input type="file" id="file_upload"> 
                        	<input type="button" id="upload-btn" value="파일 업로드"><br/>
                  			<input type="button" id="btn" value="파일 다운로드">
                        	<input type="button" id="download-btn" value="파일 다운로드">
                        </div>
                        <div id="tab3-div">tab3</div>
                        <div id="tab4-div">tab4</div>
                    </td>
                </tr>
            	<tr>
	                <td id="tab2-td" class="tab-td">파<br>일<br>보<br>내<br>기</td>
                </tr>
            	<tr>
   	                <td id="tab3-td" class="tab-td">파<br>일<br>저<br>장</td>
                </tr>
            	<tr>
                	<td id="tab4-td" class="tab-td">내<br>할<br>일</td>
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