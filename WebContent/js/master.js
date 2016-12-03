$(document).on("click", ".title-div", function(){
	location.href = "intro.jsp";
});

$(document).on("click", "#login-label", function(){
	location.href = "login.jsp";
});

$(document).on("click", "#logout-label", function(){
	
	Kakao.init('7030fc67b3b2c71443851314f53085e2');
	Kakao.Auth.logout(function(){
		location.href = "logout.do";
	});
	
});

$(document).on("click", "#regist-label", function(){
	location.href = "beforeregister.jsp";
});

$(document).on("click", "#modify-label", function(){
	location.href = "modify.jsp";
});
