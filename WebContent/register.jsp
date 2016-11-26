<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link href="./css/master.css" type="text/css" rel="stylesheet"/>
<link href="./css/register.css" type="text/css" rel="stylesheet"/>
<script src="./js/jquery-1.9.1.min.js"></script>
<script src="./js/master.js"></script>
<script src="./js/register.js"></script>
<title>회원가입</title>
</head>
<body>
	<div class="boxA">
		<div class="top-back">
    	</div>
		<div class="title-div">
	    	<p class="top-big-p">프로젝트<span id="big-p-span">공작소</span></p>
	        <p class="top-small-p">Project Workshop</p>
	    </div>
	    <div class="login">
		  	<a href="login.jsp">로그인</a>
		</div>
	    <div class="regist">
	    	<a href="register.jsp">회원가입</a>
		</div>
    </div>
    
    <table id="regist-form" align="center">
    	<tr>
        	<td rowspan="9">
            	<div class="profile-div">
                    <div class="profile-photo">프로필사진</div>
                    <div class="profile-photo"><img src="./images/null_profile.png"></div>
                    <div class="profile-photo"><input type="file" id="photo-path"></div>
                    <div class="profile-photo"><input type="button" id="load-photo-btn" value="사진 불러오기"></div>
                </div>
            </td>
            <td class="td-input"><input id="reg_name" placeholder="이름"></td>
        </tr>
        <tr>
            <td class="td-input">
            	<input id="reg_email_id" placeholder="이메일">
                <label id="at">@</label>
                <input id="reg_email_company" placeholder="이메일 주소">
                <select id="reg_email_selector">
                	<option value="0">직접입력</option>
                    <option value="naver.com">네이버</option>
                    <option value="daum.net">다음</option>
                    <option value="gmail.com">구글</option>
                    <option value="hanmail.net">한메일</option>
                    <option value="paran.com">파란</option>
                    <option value="nate.com">네이트</option>
                </select>
                <div id="email-check-div"><input type="button" id="check_email" value="중복확인"><span id="email-check-span">로그인할 때 이메일을 사용합니다.</span></div>
            </td>

        </tr>

        <tr>
            <td class="td-input">
            	<input type="password" id="reg_passwd" placeholder="비밀번호" maxlength="15"><span id="ok-sign"></span>
            	<div id="passwd_info">영어,숫자를 포함한 6~15자</div>
            </td>
        </tr>
        <tr>
            <td class="td-input">
            	<input type="password" id="reg_passwd_confirm" placeholder="비밀번호 확인" maxlength="15"><span id="ok-sign-confirm"></span>
            </td>
        </tr>
        <tr>
            <td class="td-input"><input id="reg_phone" placeholder="핸드폰 번호" onkeydown="return showKeyCode(event)" maxlength="11"></td>
        </tr>
        <tr> 	
            <td class="td-input"><input id="reg_organization" placeholder="소속"></td>
        </tr>
        <tr>
            <td class="td-etc">
            <label>학년</label>
            <select id="reg_grade_selector">
                <option value="1">1학년</option>
                <option value="2">2학년</option>
                <option value="3">3학년</option>
                <option value="4">4학년</option>
            </select></td>
        </tr>
         <tr>
            <td class="td-etc">
           	  <label>성별</label><input type="radio" name="gender" id="reg_gender_m" value="M">남
              <input type="radio" name="gender" id="reg_gender_f" value="F">여
            </td>
        </tr>
        <tr>
        	<td>
            	<input type="button" id="reg_cancel" value="취소">
                <input type="button" id="reg_confirm" value="확인">
            </td>
        </tr>
    </table>
</body>
</html>
