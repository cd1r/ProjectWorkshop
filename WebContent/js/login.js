Kakao.init('7030fc67b3b2c71443851314f53085e2');

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
	location.href="beforeregister.jsp";
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
		success: function login_result(data){
			if(data == "true") {
				alert("환영합니다.");
				location.href="intro.jsp";
			}
			else alert("아이디 또는 비밀번호를 확인해주세요");
		}
	});
}

//카카오 로그인
function loginWithKakao() {
	Kakao.Auth.login({
		success: function(authObj){
			Kakao.API.request({
				url: '/v1/user/me',
				success: function(res){
					$.ajax({
						type: "post",
						url: "login_kakao.do",
						data: {type_id:res.id},
						success: function(data){
							if(data=="true"){
								alert("환영합니다.");
								location.href="intro.jsp";
							}
							else {
								Kakao.Auth.logout(function(){
									alert("정보가 없습니다.");
									location.reload(true);
								});
							}
						}
					})
				},
				fail: function(error){
					alert(JSON.stringify(error));
				}
			});
		},
		fail: function(err){
			alert(JSON.stringify(err));
		}
    });
};

/*$(document).ready(function(){
Kakao.init('7030fc67b3b2c71443851314f53085e2');
Kakao.Auth.createLoginButton({
	container: '#kakao-login-btn',
	success: function(authObj){
		Kakao.API.request({
			url: '/v1/user/me',
			success: function(res){
				alert(res.properties.nickname+'환영합니다.');
				document.write(JSON.stringify(res));
			},
			fail: function(error){
				alert(JSON.stringify(error));
			}
		});
	},
	fail: function(err){
		alert(JSON.stringify(err));
	}
});
});*/

