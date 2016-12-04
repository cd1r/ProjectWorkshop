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
	
	System.out.println("���� ID : " + user_email + " " + login_type);
%>    
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ȸ����������</title>
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
	    	<p class="top-big-p">������Ʈ<span id="big-p-span">���ۼ�</span></p>
	        <p class="top-small-p">Project Workshop</p>
	    </div>
	    <div class="login">
		  	<a id="logout-label">�α׾ƿ�</a>
		</div>
	    <div class="regist">
	    	<a href="modify.jsp">��������</a>
		</div>
    </div>
    
    <table id="modify-form" align="center">
    	<tr>
        	<td rowspan="9">
            	<div class="profile-div">
                    <div class="profile-photo">�����ʻ���</div>
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
            	<input type="password" id="modify_passwd" placeholder="��й�ȣ" maxlength="15"><span id="ok-sign"></span>
            	<div id="passwd_info">����,���ڸ� ������ 6~15��</div>
            </td>
        </tr>
        <tr>
            <td class="td-input">
            	<input type="password" id="modify_passwd_confirm" placeholder="��й�ȣ Ȯ��" maxlength="15"><span id="ok-sign-confirm"></span>
            </td>
        </tr>
        <tr>
            <td class="td-input"><input id="modify_phone" placeholder="�ڵ��� ��ȣ" onkeydown="return showKeyCode(event)" maxlength="11"></td>
        </tr>
        <tr> 	
            <td class="td-input"><input id="modify_organization" placeholder="�Ҽ�"></td>
        </tr>
        <tr>
            <td class="td-etc">
            <label>�г�</label>
            <select id="modify_grade_selector">
                <option value="1">1�г�</option>
                <option value="2">2�г�</option>
                <option value="3">3�г�</option>
                <option value="4">4�г�</option>
            </select></td>
        </tr>
         <tr>
            <td class="td-input">
              <input id="modify_gender">
            </td>
        </tr>
        <tr>
        	<td>
            	<input type="button" id="modify_cancel" value="���">
                <input type="button" id="modify_confirm" value="Ȯ��">
            </td>
        </tr>
    </table>
	
</body>
</html>