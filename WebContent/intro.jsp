<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
<meta charset="utf-8">
<link href="./css/master.css" type="text/css" rel="stylesheet"/>
<link href="./css/intro.css?ver=1" type="text/css" rel="stylesheet"/>
<link href="./carousel/owl.carousel.css" rel="stylesheet">
<link href="./carousel/owl.theme.css" rel="stylesheet">
<link href="./carousel/google-code-prettify/prettify.css" rel="stylesheet">

<script src="./js/jquery-1.9.1.min.js"></script> 
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script src="./carousel/owl.carousel.js"></script>
<script src="./js/master.js"></script>
<script src="./js/intro.js?ver=1"></script>
<title>프로젝트공작소</title>
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
    
	<div id="main-img">
        <div class="container">
          <div class="row">
            <div class="span12">
              <div id="owl-demo" class="owl-carousel">
                <div class="item"><img src="./images/pic1_1.png"></div>
                <div class="item"><img src="./images/pic2_1.png"></div>
                <div class="item"><img src="./images/pic3_1.png"></div>
              </div>
            </div>
          </div>
        </div>
    </div>    
    
    <script>
    
    </script>

    <div class="start-menu">
		프로젝트 공작소 이용하기
    </div>
    
    <table id="menu-table" align="center" cellpadding="20">
    	<tr>
        	<td>
            	<div class="menu" id="first-menu">
                    <img class="menu-img" src="./images/img1.png">
                    <div class="menu-name">프로젝트 성공법</div>
                </div>
            </td>
            <td id="second-td">
            	<div class="menu" id="second-menu">
                    <img class="menu-img" src="./images/img2.png">
                    <br>
                    <div class="menu-name">공작소 만들기</div>
                </div>
            </td>
            <td>
            	<div class="menu" id="third-menu">
                    <img class="menu-img" src="./images/img3.png"><br>
                    <div class="menu-name">공작소 들어가기</div>
                </div>
            </td>
        </tr>
    </table>
   
    <div class="footer">
    	<div class="footer-con">Developed By Hyoseung Seo &amp; Seyoon Hwang</div>
    </div>
</body>
</html>