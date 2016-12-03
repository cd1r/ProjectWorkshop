$(document).on("click", "#own-type", function(){
	location.href='register.jsp?type=basic';
});

/*$(document).on("click", "#regist-label", function(){
	//location.href =
});*/

function loginWithKakao() {
	Kakao.init('7030fc67b3b2c71443851314f53085e2');
	
	Kakao.Auth.login({
		success: function(authObj){
			Kakao.API.request({
				url: '/v1/user/me',
				success: function(res){
					$.ajax({
						type: "post",
						url: "register_kakao.do",
						data: {id:res.id, name:res.properties.nickname},
						success: function(data){
							Kakao.Auth.logout(function(){
								location.href='register.jsp?type=kakao';
							});
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