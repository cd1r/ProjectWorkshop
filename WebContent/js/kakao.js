/**
 * 
 */

//$(document).on("click", "#kakao-login-btn", function() {
$(document).ready(function(){
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
});

$(document).on("click", "#kakao-logout-btn", function() {
	Kakao.Auth.logout(function(){
		setTimeout(function(){
			location.href="intro.jsp";
		}, 1000);
	});
});
