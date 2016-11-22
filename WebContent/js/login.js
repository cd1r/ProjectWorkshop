$(document).ready(function(){
	$("#pw").keyup(function(e) {
		if(e.keyCode == 8)
		{
			if($("#pw").val().length == 0)
				$("#pw").css({"font-family" : "NanumSquare"});
		}
		else
			$("#pw").css({"font-family" : "Gulim"});
	});
});

//회원가입
$(document).on("click", "#registAcc", function() {
	location.href="register.jsp";
});

//로그인 버튼
$(document).on("click", "#loginBtn", function(){
	if($.trim($('#id').val()).length == 0 || 
			$.trim($('#pw').val()).length == 0)
		{
			alert("아이디와 비밀번호 모두 입력해주세요.");
		}
	else login_call();
});

function login_call(){
	$.ajax({
		type: "post",
		url: "login.do",
		data: {userId:$('#id').val(), userPw:$('#pw').val()},
		datatype: "text",
		success: login_result
	});
}

function login_result(data){
	if(data == "true") {
		//alert("로그인 성공");
		location.href="intro.jsp";
	}
	else alert("아이디 또는 비밀번호를 확인해주세요");
}

