<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	boolean isSession = false;
	String user_email = null;
	user_email = (String)session.getAttribute("user_email");
	if(user_email != null)
		isSession = true;
	System.out.println("세션 ID : " + user_email);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>공작소 만들기</title>
<link href="./css/master.css" type="text/css" rel="stylesheet"/>
<link href="./css/createroom.css" type="text/css" rel="stylesheet"/>
<script src="./js/jquery-1.9.1.min.js"></script>
<script src="./js/createroom.js"></script>
<script src="./js/master.js"></script> 

</head>

<body>
<input id="user-email" type="hidden" value="<%=user_email%>">
<div class="boxA">
		<div class="top-back">
    	</div>
		<div class="title-div">
	    	<p class="top-big-p">프로젝트<span id="big-p-span">공작소</span></p>
	        <p class="top-small-p">Project Workshop</p>
	    </div>
	    <div class="login">
        	<%if(!isSession){%>
        		<a id="login-label">로그인</a>
        	<%} else{%>
        		<a id="logout-label">로그아웃</a>
        	<%}%>
	    </div>
        <div class="regist">
        	<%if(!isSession){%>
        		<a id="regist-label">회원가입</a>
        	<%} else{%>
        		<a id="modify-label">정보수정</a>
        	<%}%>
	    </div>
    </div>

<table id="create-room-form" align="center">
	<tr>
    	<td id="table-left" rowspan="10">
        	<div class="left-big-name">공작소 건설 현장</div>
            <div><img class="create-logo" src="./images/img2.png"></div>
        </td>
    </tr>
    <tr>
    	<td id="first">
        	<div class="content-name">공작소 이름</div>
            <div><input type="text" id="room-name"></div>
        </td>
    </tr>
    <tr>
    	<td id="second">
        	<div class="content-name">공작소 운영 기간</div>
            <div>
            	<select id="from-year-selector">
                </select>
                <select id="from-month-selector">
                </select>
                <select id="from-day-selector">
                </select>
                <span>~</span>
                <select id="to-year-selector">
                </select>
                <select id="to-month-selector">
                </select>
                <select id="to-day-selector">
                </select>
            </div>
        </td>
    </tr>
    <tr>
    	<td id="third">
        	<div class="content-name">팀원 추가</div>
            <div id="search-form">
            	<select id="search-criteria-selector">
                	<option>이메일</option>
                    <option>이름</option>
                </select>
                <input type="text" id="search-keyword">
                <input type="button" id="search-btn" value="검색">
            </div>
                <div class="content-small-name">추가된 팀원</div>
                <div id="added-person-div">
                	<table id="added-person">
                    	<tr>
                        	<th class="name-th">이름</th>
                            <th class="email-th">이메일</th>
                            <th class="org-th">소속</th>
                            <th class="btn-th">삭제</th>
                        </tr>
                    </table>
                </div>
                
                <div class="content-small-name" id="search-result-label">검색결과</div>
                <div id="search-person-div">
                	<table id="search-result-person">
                    	<tr>
                        	<th class="name-th">이름</th>
                            <th class="email-th">이메일</th>
                            <th class="org-th">소속</th>
                            <th class="btn-th">추가</th>
                        </tr>
                    </table>
                </div>
                
                
            </div>
        </td>
    </tr>
</table>
<div class="last-btn-div">
	<input type="button" id="cr-cancel-btn" value="취소">
   	<input type="button" id="cr-confirm-btn"  value="만들기">
</div>
<div id="wrap"></div>
</body>
</html>
