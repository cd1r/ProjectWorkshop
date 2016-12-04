/**
 * 
 */
var phone="";
var grade="";
var photo_url = "";
var univ="";

var passwd_chk = false;
var passwd_confirm_chk = false;

$(document).ready(function(){

	$.ajax({
		type: "post",
		url: "get_info",
		data: {userId:$('#user-email').val()},
		datatype: "text",
		success: function login_result(data){
			$(data).find("info").each(function(){
				$('#modify_name').val($(this).find("name").text());
				$('#modify_email').val($('#user-email').val());
				
				if($(this).find("gender").text()=="M") $('#modify_gender').val("남자");
				else $('#modify_gender').val("여자");
				
				document.getElementById("modify_name").disabled = true;
				document.getElementById("modify_email").disabled = true;
				document.getElementById("modify_gender").disabled = true;
				
				phone = $(this).find("phone").text();
				grade = $(this).find("grade").text();
				univ = $(this).find("univ").text();
				//photo_url = $(this).find("image").text().replace("/", "\\");
				
				$('#modify_phone').val(phone);
				$('#modify_organization').val(univ);
				$('#modify_grade_selector').val(grade);
			
			});
		}
	});
	
	if($("#login-type").val()=="kakao"){
		document.getElementById("modify_passwd").disabled = true;
		document.getElementById("modify_passwd_confirm").disabled = true;
		passwd_chk = true;
		passwd_confirm_chk = true;
	}
	
	$("#modify_passwd").keyup(function(e) {
		var err = 0; 
		var str = $("#modify_passwd").val();
		for (var i=0; i<str.length; i++)  { 
			var chk = str.substring(i,i+1); 
			if(!chk.match(/[0-9]|[a-z]|[A-Z]/)) { 
				err = err + 1; 
			} 
		} 
		
		if (err > 0) {
			$("#modify_passwd").val(str.substring(0,str.length-1));
			alert("숫자 및 영문만 입력가능합니다."); 
			return;
		}
		
		if(str.length>=6 && str.length<=15){
			passwd_chk = true;
			$("#ok-sign").text("OK");
			$("#ok-sign").css({
				"color" : "#4d94ff", 
				"margin-left" : "10px", 
				"font-family" : "NanumSquare",
				"font-size" : "17px"}); 
		}
		
		else
		{
			passwd_chk = false;
			$("#ok-sign").text(""); 
		}
		
		if(e.keyCode == 8)
		{
			if($("#modify_passwd").val().length == 0)
				$("#modify_passwd").css({"font-family" : "NanumSquare"});
		}
		else
			$("#modify_passwd").css({"font-family" : "Gulim"});
	});
	
	$("#modify_passwd_confirm").keyup(function(e) {
		var err = 0; 
		var str = $("#modify_passwd_confirm").val();
		for (var i=0; i<str.length; i++)  { 
			var chk = str.substring(i,i+1); 
			if(!chk.match(/[0-9]|[a-z]|[A-Z]/)) { 
				err = err + 1; 
			} 
		} 
		
		if (err > 0) {
			$("#modify_passwd_confirm").val(str.substring(0,str.length-1));
			alert("숫자 및 영문만 입력가능합니다."); 
			return;
		}
		
		//수정함
		if(($("#modify_passwd").val().length>=6 && $("#modify_passwd").val().length<=15) 
				&& ($("#modify_passwd").val() == $("#modify_passwd_confirm").val())){
			passwd_confirm_chk = true;
			$("#ok-sign-confirm").text("OK");
			$("#ok-sign-confirm").css({
				"color" : "#4d94ff", 
				"margin-left" : "10px", 
				"font-family" : "NanumSquare",
				"font-size" : "17px"}); 
		}
		
		else
		{
			passwd_confirm_chk = false;
			$("#ok-sign-confirm").text(""); 
		}
		
		if(e.keyCode == 8)
		{
			if($("#modify_passwd_confirm").val().length == 0)
				$("#modify_passwd_confirm").css({"font-family" : "NanumSquare"});
		}
		else
			$("#modify_passwd_confirm").css({"font-family" : "Gulim"});
	});
	
	
});

//핸드폰 번호 입력시 숫자만 가능하게 (backspace 포함)
function showKeyCode(event) {
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if( ( keyID >=48 && keyID <= 57 ) || ( keyID >=96 && keyID <= 105 ) || (keyID == 8))
	{
		return;
	}
	else
	{
		return false;
	}
}

function checkInput(){
	
	if($("#login-type").val()=="basic" && $("#modify_passwd").val()==""){ //확인
		alert("비밀번호를 입력해주세요");
		$("#modify_passwd").focus();
		return false;
	}
	else if($("#login-type").val()=="basic" && $("#modify_passwd_confirm").val()==""){
		alert("비밀번호 확인을 해주세요");
		$("#modify_passwd_confirm").focus();
		return false;
	}
	else if(passwd_chk==false || passwd_confirm_chk==false){  //수정
		alert("비밀번호 문제");
		return false;
	}
	else if($("#modify_phone").val()==""){
		alert("핸드폰 번호를 입력해주세요");
		$("#modify_phone").focus();
		return false;
	}
	else if($("#modify_organization").val()==""){
		alert("소속을 입력해주세요");
		$("#modify_organization").focus();
		return false;
	}
	
	return true;
}

//취소버튼
$(document).on("click", "#modify_cancel", function(){
	location.href="intro.jsp";
});

//확인버튼
$(document).on("click", "#modify_confirm", function(){
	if(checkInput()==true){
		alert("정보수정 가능");
		register_call();
	}
});

function register_call(){
	//프로필 사진을 넣은 경우
	if($("#photo-path").val() != ""){
		var formData = new FormData();
		formData.append("uploadfile",$("input[id=photo-path]")[0].files[0]);

		$.ajax({
			type: "post",
			url: "profile_upload.do",
			data: formData,
			processData: false,
			contentType: false,
			datatype: "text",
			success: function(data) {
				if(data == "flase") {
					alert("프로필 업로드 실패");
				}
				else{ 
					photo_url = data;
					alert("프로필 업로드 성공\n"+photo_url);
				}
				register_call_db();
			}
		});	
	}
	else register_call_db();
	
}

function register_call_db(){
	
	$.ajax({
		type: "post",
		url: "modify.do",
		data: {type:$("#login-type").val(), email:$('#user-email').val(), pw:$('#modify_passwd').val(), phone:$('#modify_phone').val(),
			organization:$('#modify_organization').val(), grade:$('#modify_grade_selector').val(),
		    profile_url:photo_url},
		datatype: "text",
		success: function(data){
			if(data == "true") {
				alert("정보수정 성공");
			}
			else alert("정보수정 실패");
			location.href="intro.jsp";
		}
	});
}

//사진 올리기  photo-path
$(document).on("change", "#photo-path", function(){
	var file = document.getElementById('photo-path').files[0];
	var p = file.name;
	var type = p.substring(p.indexOf(".")+1).toLowerCase();
	
	if(type!='jpg' && type!='png' && type!='jpeg'){
		$("#photo-path").val("");
		alert("이미지 파일은 (jpg, jpeg, png) 형식만 등록 가능합니다.");
		return ;
	}

	photo_url = $("#photo-path").val();
});

