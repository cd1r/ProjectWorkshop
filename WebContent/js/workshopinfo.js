$(document).ready(function() {
	callWorkshopInfo();
});

function callWorkshopInfo()
{
	$.ajax({
		type: "post",
		url: "workshop_info.do",
		data: {roomId : $("#room-id").val()},
		datatype: "text",
		success: function(data){
			$(data).find("info").each(function(){
				var content_table = document.getElementById('content-table');
				$("#workshop-name-td").text($(this).find("workshop_name").text());
				$("#workshop-dday-td").html('마감<br>D-' + $(this).find("dday").text());
				$("#workshop-etc-td").html(
						'<div>공작소 개설일&emsp;<span>' + $(this).find("create_date").text() +'</span></div>'+
						'<div>총 인원&emsp;<span>' + $(this).find("mem_cnt").text() + '명</span></div>');
			});
			callWorkshopMember();
		}
	});	
}

function callWorkshopMember()
{
	var row_cnt = 1, td_cnt = 0;
	$.ajax({
		type: "post",
		url: "workshop_member.do",
		data: {roomId : $("#room-id").val()},
		datatype: "text",
		success: function(data){
			$("#content-table").append('<tr id="mem_row' + row_cnt + '">');
			$(data).find("info").each(function(){
				$("#mem_row" + row_cnt).append(
						'<td class="member-info">'+
							'<div class="mem-position">' + $(this).find("position").text() + '</div>'+
							'<div class="mem-profile"><img src="./images/null_profile.png"></div>'+
							'<div class="mem-name-gender">' + $(this).find("name_gender").text() + '</div>'+
							'<div class="mem-org-grade">' + $(this).find("univ_grade").text() + '</div>'+
							'<div class="mem-email">' + $(this).find("email").text() + '</div>'+
							'<div class="mem-phone">' + $(this).find("phone").text() + '</div>'+
						'</td>');
				td_cnt++;
				
				if(td_cnt == 3)
				{
					row_cnt++;
					$("#content-table").append('<tr id="mem_row' + row_cnt + '">');
					td_cnt = 0;
				}
				/*$(".mem-profile").css({"width":"100%", "height":"160px", "overflow":"hidden"});
				$(".mem-profile img").css({"width":"160px", "height":"auto", "border-radius":"7px"});
				$(".member-info").css({"width":"33%", "text-align":"center",});
				$(".member-info div").css({"margin-top":"5px", "margin-bottom":"5px"});*/
			});
		}
	});
}