/**
 * 
 */
// 패스워드 관련
var passwd_chk = false;
var passwd_confirm_chk = false;
var email_chk = false;
var photo_url = "./images/null_profile.png";

$(document).ready(function(){
	$("#reg_passwd").keyup(function(e) {
		var err = 0; 
		var str = $("#reg_passwd").val();
		for (var i=0; i<str.length; i++)  { 
			var chk = str.substring(i,i+1); 
			if(!chk.match(/[0-9]|[a-z]|[A-Z]/)) { 
				err = err + 1; 
			} 
		} 
		
		if (err > 0) {
			$("#reg_passwd").val(str.substring(0,str.length-1));
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
			if($("#reg_passwd").val().length == 0)
				$("#reg_passwd").css({"font-family" : "NanumSquare"});
		}
		else
			$("#reg_passwd").css({"font-family" : "Gulim"});
	});
	
	$("#reg_passwd_confirm").keyup(function(e) {
		var err = 0; 
		var str = $("#reg_passwd_confirm").val();
		for (var i=0; i<str.length; i++)  { 
			var chk = str.substring(i,i+1); 
			if(!chk.match(/[0-9]|[a-z]|[A-Z]/)) { 
				err = err + 1; 
			} 
		} 
		
		if (err > 0) {
			$("#reg_passwd_confirm").val(str.substring(0,str.length-1));
			alert("숫자 및 영문만 입력가능합니다."); 
			return;
		}
		
		//수정함
		if(($("#reg_passwd").val().length>=6 && $("#reg_passwd").val().length<=15) 
				&& ($("#reg_passwd").val() == $("#reg_passwd_confirm").val())){
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
			if($("#reg_passwd_confirm").val().length == 0)
				$("#reg_passwd_confirm").css({"font-family" : "NanumSquare"});
		}
		else
			$("#reg_passwd_confirm").css({"font-family" : "Gulim"});
	});
	
	$("#reg_email_selector").change(function(){
		 $("#reg_email_company").val($("#reg_email_selector option:selected").val());
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

//모두 입력했는지 판단
function checkInput(){
	
	if($("#reg_name").val()==""){
		alert("이름을 입력해주세요");
		$("#reg_name").focus();
		return false;
	}
	else if($("#reg_eamil_id").val()=="" || $("#reg_email_company").val()==""){
		alert("이메일을 입력해주세요");
		return false;
	}
	else if(email_chk==false){
		alert("이메일중복 확인을 해주세요");
		return false;
	}
	else if($("#reg_passwd").val()==""){ //확인
		alert("비밀번호를 입력해주세요");
		$("#reg_passwd").focus();
		return false;
	}
	else if($("#reg_passwd_confirm").val()==""){
		alert("비밀번호 확인을 해주세요");
		$("#reg_passwd_confirm").focus();
		return false;
	}
	else if(passwd_chk==false || passwd_confirm_chk==false){  //수정
		alert("비밀번호 문제");
		return false;
	}
	else if($("#reg_phone").val()==""){
		alert("핸드폰 번호를 입력해주세요");
		$("#reg_phone").focus();
		return false;
	}
	else if($("#reg_organization").val()==""){
		alert("소속을 입력해주세요");
		$("#reg_organization").focus();
		return false;
	}
	else if(document.all.gender[0].checked==false && document.all.gender[1].checked==false){
		alert("성별을 선택해주세요");
		return false;
	}
	
	return true;
}

//취소버튼
$(document).on("click", "#reg_cancel", function(){
	location.href="intro.jsp";
});

//확인버튼
$(document).on("click", "#reg_confirm", function(){

	if(checkInput()==true){
		alert("회원가입 가능");
		register_call();
	}
});

function register_call(){

	//프로필 사진을 넣은 경우
	if(photo_url != "./images/null_profile.png"){
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
					photo_url = "./images/null_profile.png";
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
	var emailstr = $('#reg_email_id').val() + '@' + $('#reg_email_company').val();
	var type = photo_url.substring(photo_url.lastIndexOf(".")+1).toLowerCase();
	$.ajax({
		type: "post",
		url: "register.do",
		data: {name:$('#reg_name').val(), pw:$('#reg_passwd').val(), phone:$('#reg_phone').val(),
			email:emailstr, organization:$('#reg_organization').val(), grade:$('#reg_grade_selector').val(),
			gender:$(':input:radio[name=gender]:checked').val(), profile_url:photo_url, extention:type},
		datatype: "text",
		success: function(data){
			if(data == "true") {
				alert("회원가입 성공");
			}
			else alert("회원가입 실패");
			location.href="login.jsp";
		}
	});
}

//이메일 중복
$(document).on("click", "#check_email", function(){
	if($("#reg_email_id").val() != "" && $("#reg_email_company").val() != "")
		checkemail_call();
	else {
		//alert("이메일을 정확하게 입력해주세요");
		//이메일 입력이 비정상일 경우 알림 방식 변경 - 세윤 
		$("#email-check-span").text("이메일을 정확하게 입력해주세요");
		$("#email-check-span").css({"color":"red"});
	}
});

function checkemail_call(){

	var emailstr = $('#reg_email_id').val() + '@' + $('#reg_email_company').val();
	$.ajax({
		type: "post",
		url: "check_email.do",
		data: { email:emailstr },
		datatype: "text",
		success: function(data) {
			//이메일 중복 검사 후 알림 방식 변경 - 세윤 
			if(data == "true") {
				//alert("사용가능합니다.");
				$("#email-check-span").text("사용가능한 이메일입니다.");
				$("#email-check-span").css({"color":"green"});
				email_chk = true;
			}
			else{ 
				//alert("이미 등록된 이메일입니다.");
				$("#email-check-span").text("이미 등록된 이메일입니다.");
				$("#email-check-span").css({"color":"red"});
			}
		}
	});	

}

//사진 올리기  load-photo-btn photo-path
$(document).on("change", "#photo-path", function(){
	var file = document.getElementById('photo-path').files[0];
	var p = file.name;
	var type = p.substring(p.indexOf(".")+1).toLowerCase();
	
	if(type!='jpg' && type!='png' && type!='jpeg'){
		$("#photo-path").val("");
		alert("이미지 파일은 (jpg, jpeg, png) 형식만 등록 가능합니다.");
		photo_url = "./images/null_profile.png";
		return ;
	}
	//photo_url  ./images/profile/email로 저장
	photo_url = $("#photo-path").val();
});

/*$(document).on("click", "#load-photo-btn", function(){
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
			if(data == "true") {
				alert("프로필 업로드 성공");
			}
			else{ 
				alert("프로필 업로드 실패");
			}
		}
	});	
});*/
