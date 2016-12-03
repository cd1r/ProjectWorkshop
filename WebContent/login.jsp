<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link href="./css/master.css" type="text/css" rel="stylesheet"/>
<link href="./css/login.css" type="text/css" rel="stylesheet"/>
<title>Login</title>
<script src="./js/jquery-1.9.1.min.js"></script>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>

<script src="./js/login.js?ver=20161203"></script>
<script src="./js/master.js"></script>

<script src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.2.js" charset="utf-8"></script>
<script src="./js/naver.js"></script>
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
	    	<a href="beforeregister.jsp">회원가입</a>
		</div>
    </div>
    
<div class="login-form">
 	<div> <input type="text" id="id" placeholder="아이디"> </div>
	<div> <input type="password" id="pw" placeholder="비밀번호"> </div>
	<div class="login-btn-div"> <input type="button" id="loginBtn" value="로 그 인"> </div>
	<div class="sns-acc-div"><img src="images/login_naver.png"></div>
	<!-- <div class="sns-acc-div" id="kakao-login-btn"><img src="images/login_kakao.png"></div> -->
 	<a class="sns-acc-div" id="kakao-login-btn" href="javascript:loginWithKakao()">
	<img src="images/login_kakao.png">
	</a> 
	<div class="login-etc-form">
    	<div id="findAcc">아이디/비밀번호 찾기</div><div id="registAcc">회원가입</div>
	</div> 
</div>
    
    
</body>
</html>
