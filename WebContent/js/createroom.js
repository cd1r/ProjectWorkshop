/**
 * 
 */
var mem = 0;
$(document).ready(function(){
	$("#from-year-selector").append('<option value="0">년도</option>');
	$("#from-month-selector").append('<option value="0">월</option>');
	$("#from-day-selector").append('<option value="0">일</option>');
	$("#to-year-selector").append('<option value="0">년도</option>');
	$("#to-month-selector").append('<option value="0">월</option>');
	$("#to-day-selector").append('<option value="0">일</option>');
	
	for(var i=2016; i<=2026; i++){
		$("#from-year-selector").append('<option value="' + i + '">' + i + '</option>');
		$("#to-year-selector").append('<option value="' + i + '">' + i + '</option>');
	}
	
	for(var i=1; i<=12; i++){
		$("#from-month-selector").append('<option value="' + i + '">' + i + '</option>');
		$("#to-month-selector").append('<option value="' + i + '">' + i + '</option>');
	}
	
	for(var i=1; i<=31; i++){
		$("#from-day-selector").append('<option value="' + i + '">' + i + '</option>');
		$("#to-day-selector").append('<option value="' + i + '">' + i + '</option>');
	}
	
});

//모두 입력했는지 판단
function checkInput(){

	if($("#room-name").val()==""){
		alert("공작소 이름을 입력해주세요");
		$("#room-name").focus();
		return false;
	}
	else if(mem==0){
		alert("같이할 팀원을 추가주세요");
		return false;
	}
	else if($("#from-year-selector").val()==0 || $("#from-month-selector").val()==0 || $("#from-day-selector").val()==0){
		alert("프로젝트 시작 날짜를 선택해주세요");
		return false;
	}
	else if($("#to-year-selector").val()==0 || $("#to-month-selector").val()==0 || $("#to-day-selector").val()==0){
		alert("프로젝트 종료 날짜를 선택해주세요");
		return false;
	}
/*	else if(mem==0){
		alert("같이할 팀원을 추가주세요");
		return false;
	}*/

	
	var date = new Date();
	var from_date = date.setFullYear($("#from-year-selector").val(), $("#from-month-selector").val(), $("#from-day-selector").val());
	var to_date = date.setFullYear($("#to-year-selector").val(), $("#to-month-selector").val(), $("#to-day-selector").val());
	
	if(from_date > to_date) {
		alert("프로젝트 기간이 잘못 선택되었습니다.");
		return false;
	}

	return true;
}

//취소버튼
$(document).on("click", "#cr-cancel-btn", function(){
	location.href="intro.jsp";
});

//확인버튼
$(document).on("click", "#cr-confirm-btn", function(){
	if(checkInput()) {
		alert("만들기 가능");
		create_call();
	}
});

function create_call(){
	var from_d = $('#from-year-selector').val()+"-"+$('#from-month-selector').val()+"-"+$('#from-day-selector').val();
	var to_d = $('#to-year-selector').val()+"-"+$('#to-month-selector').val()+"-"+$('#to-day-selector').val();	
	var emailxml = member_email_xmlstring();
	
	$.ajax({
		type: "post",
		url: "create_room.do",
		data: { name:$('#room-name').val(), from_date:from_d, to_date:to_d, member:emailxml }, //table 한번에 넘기기 알아보기
		datatype: "text",
		success: function(data){
			if(data == "true") alert("공작소 만들기 성공");
			else alert("공작소 만들기 실패");
			location.href="intro.jsp";
		}
	});	
}

//공작소 만들때 팀원들의 이메일을 xml형태의 string을 만들어준다.
function member_email_xmlstring(){
	var member_table = document.getElementById('added-person');
	var count = member_table.rows.length;
	
	var emailxml = '';
	emailxml += '<?xml version="1.0" encoding="utf-8" standalone="no"?><root>';
	emailxml += '<member>'+$('#user-email').val()+'</member>'; //팀장 이메일도 넣어준다.
	
	//member인 사람들의 email을 xml형태의 string으로 만듬
	for(var i=1; i<count; i++)
		emailxml += '<member>'+member_table.rows[i].cells[1].innerHTML+'</member>';

	emailxml += '</root>';
	
	return emailxml;
}

//팀원-검색
$(document).on("click", "#search-btn", function(){
	if($('#search-keyword').val() == "") alert("검색어를 입력해주세요");
	else select_call();
});

function select_call(){
	$.ajax({
		type: "post",
		url: "search_keyword.do",
		data: {select:$('#search-criteria-selector').val(), keyword:$('#search-keyword').val()},
		datatype: "text",
		success: function(data){
			if(data=="false") alert("정보가 없습니다.");
			else {		
				//기존에 있는 내용 지우기
				var info_table = document.getElementById('search-result-person');
				var count = info_table.rows.length;
				for(var i=count-1; i>0; i--) //테이블 아래에서 위로 행 지우기
					info_table.deleteRow(i);
				
				//검색 결과 table에 보여주기
				$(data).find("info").each(function(){
					var row = info_table.insertRow(info_table.rows.length);
					var col_name = row.insertCell(0);
					var col_email = row.insertCell(1);
					var col_univ = row.insertCell(2);
					var col_btn = row.insertCell(3);
					
					col_name.innerHTML = $(this).find("name").text();
					col_email.innerHTML = $(this).find("email").text();
					col_univ.innerHTML = $(this).find("univ").text();
					col_btn.innerHTML = '<input type="button" class="add_btn" value="+" id="'+ $(this).find("email").text() + '">';
				
				});
				
				//동적으로 CSS 입히는 코드
				$("#search-person-div").css({"display" : "block"});
				$("#search-result-label").css({"display" : "block"});
				$("#search-result-person td").css({"font-family" : "NanumSquare", "font-size" : "15px", 
													"padding-top" : "10px", "padding-bottom" : "10px"});
				$("#search-result-person .add_btn").css({"background-color" : "rgba( 255, 255, 255, 0 );", "color" : "#18bc9c",
														"border" : "0", "outline" : "0", "font-size" : "20px", "cursor" : "pointer"});
				$("#search-result-person tr:nth-child(odd)").css({"background-color" : "#cff6ee"});
				$(".last-btn-div").css({"margin-top" : "20px"});
			}
		}
	});	
}

//팀원-추가 : 추가버튼 클릭 시 팀원 테이블에 추가됨
$(document).on("click", ".add_btn", function(){
	
	var id = $(this).attr('id');
	var result = redundancy_add(id); //중복확인
	
	if(result == false) alert("이미 추가되었습니다.");
	else{
		mem++;
		var info_table = document.getElementById('search-result-person');
	
		var member_table = document.getElementById('added-person');
		var row = member_table.insertRow(member_table.rows.length);
		var col_name = row.insertCell(0);
		var col_email = row.insertCell(1);
		var col_univ = row.insertCell(2);
		var col_btn = row.insertCell(3);
	
		//버튼을 클릭한 그 행의 번호를 알고난 후에 열의 값을 얻는다.
		var num = $(this).closest('tr').prevAll().length;
	
		col_name.innerHTML = info_table.rows[num].cells[0].innerHTML; 
		col_email.innerHTML = info_table.rows[num].cells[1].innerHTML; 
		col_univ.innerHTML = info_table.rows[num].cells[2].innerHTML; 
		col_btn.innerHTML = '<input type="button" class="del_btn" value="X" id="'+ $(this).find("email").text() + '">';
		
		//동적으로 CSS 입히는 코드
		$("#added-person .del_btn").css({"background-color" : "rgba( 255, 255, 255, 0 );", "color" : "#253755",
										"border" : "0", "outline" : "0", "font-size" : "15px", "cursor" : "pointer"});	
		$("#search-person-div").css({"display" : "none"});
		$("#search-result-label").css({"display" : "none"});
		$("#added-person tr:nth-child(odd)").css({"background-color" : "#ccdced"});
		
	}

});

//추가하려는 학생이 이미 추가 되었는지 email로 확인
function redundancy_add(data){
	var member_table = document.getElementById('added-person');
	var count = member_table.rows.length;

	for(var i=0; i<count; i++)
		if(member_table.rows[i].cells[1].innerHTML == data) return false;
	
	return true;
}

//삭제버튼 클릭 시 팀원 테이블에서 삭제됨
$(document).on("click", ".del_btn", function(){
	var check = confirm("삭제 하시겠습니까?");
	if(check){
		mem--;
		var tr = $(this).parent().parent(); //삭제버튼누른 행 삭제
		tr.remove();
	}
});
