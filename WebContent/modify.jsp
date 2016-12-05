<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="EUC-KR"%>
<%
	boolean isSession = false;
	String user_email = null;
	String login_type = null;
	user_email = (String)session.getAttribute("user_email");
	login_type = (String)session.getAttribute("login_type"); 
	if(user_email != null)
		isSession = true;
	
	System.out.println("세션 ID : " + user_email + " " + login_type);
%>    
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>회원정보수정</title>
<link href="./css/master.css" type="text/css" rel="stylesheet"/>
<link href="./css/modify.css" type="text/css" rel="stylesheet"/>
<script src="./js/jquery-1.9.1.min.js"></script>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script src="./js/master.js"></script>
<script src="./js/modify.js"></script>
</head>
<body>
	<input id="user-email" type="hidden" value="<%=user_email%>">
	<input id="login-type" type="hidden" value="<%=login_type%>">
	
	<div class="boxA">
		<div class="top-back">
    	</div>
		<div class="title-div">
	    	<p class="top-big-p">프로젝트<span id="big-p-span">공작소</span></p>
	        <p class="top-small-p">Project Workshop</p>
	    </div>
	    <div class="login">
		  	<a id="logout-label">로그아웃</a>
		</div>
	    <div class="regist">
	    	<a href="modify.jsp">정보수정</a>
		</div>
    </div>
    
    <table id="modify-form" align="center">
    	<tr>
        	<td rowspan="9">
            	<div class="profile-div">
                    <div class="profile-photo">프로필사진</div>
                    <div class="profile-photo"><img id="profile-image" src="./images/null_profile.png"></div>
                <form id="file_form" enctype="multipart/form-data" method="post">
                    <div class="profile-photo"><input type="file" id="photo-path"></div>
                </form>
                </div>
            </td>
            <td class="td-input">	
            	<input id="modify_name">
            </td>
        </tr>
        <tr>
            <td class="td-input">
            	<input id="modify_email">
            </td>

        </tr>

        <tr>
            <td class="td-input">
            	<input type="password" id="modify_passwd" placeholder="비밀번호" maxlength="15"><span id="ok-sign"></span>
            	<div id="passwd_info">영어,숫자를 포함한 6~15자</div>
            </td>
        </tr>
        <tr>
            <td class="td-input">
            	<input type="password" id="modify_passwd_confirm" placeholder="비밀번호 확인" maxlength="15"><span id="ok-sign-confirm"></span>
            </td>
        </tr>
        <tr>
            <td class="td-input"><input id="modify_phone" placeholder="핸드폰 번호" onkeydown="return showKeyCode(event)" maxlength="11"></td>
        </tr>
        <tr> 	
            <td class="td-input"><input id="modify_organization" placeholder="소속"></td>
        </tr>
        <tr>
            <td class="td-etc">
            <label>학년</label>
            <select id="modify_grade_selector">
                <option value="1">1학년</option>
                <option value="2">2학년</option>
                <option value="3">3학년</option>
                <option value="4">4학년</option>
            </select></td>
        </tr>
         <tr>
            <td class="td-input">
              <input id="modify_gender">
            </td>
        </tr>
        <tr>
        	<td>
            	<input type="button" id="modify_cancel" value="취소">
                <input type="button" id="modify_confirm" value="확인">
            </td>
        </tr>
    </table>
	
</body>
</html>