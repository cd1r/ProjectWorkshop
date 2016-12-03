<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link href="./css/master.css" type="text/css" rel="stylesheet"/>
<link href="./css/beforeregister.css" type="text/css" rel="stylesheet"/>
<title>회원가입</title>
<script src="./js/jquery-1.9.1.min.js"></script>
<script src="./js/beforeregister.js"></script>
<script src="./js/master.js"></script>

<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script src="./js/kakao.js"></script>
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
    
<div id="register-type-form">
	<div id="own-type-label">프로젝트 공작소 간편 회원가입</div>
 	<div><input type="button" id="own-type" value="회원가입"></div>
 	<div id="other-type-label">외부 계정으로 회원가입</div>
 	<div id="regist-kakao"><img src="./images/regist_kakao.png"></div>
</div>
    
    
</body>
</html>