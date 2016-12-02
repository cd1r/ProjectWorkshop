var continue_idx;

$(document).ready(function(){
	callMember($('#room-id').val());
	setCalendar($("#year-span").text(), $("#month-span").text());
	setAddScheduleFormVisible();
	
	$("#prev-month-btn").click(function(){
		if($("#month-span").text() == "1"){
			$("#year-span").text(Number($("#year-span").text())-1);
			$("#month-span").text("12");
		}
		
		else $("#month-span").text(Number($("#month-span").text())-1);
		
		setCalendar($("#year-span").text(), $("#month-span").text());
	});
	
	$("#next-month-btn").click(function(){
		if($("#month-span").text() == "12"){
			$("#year-span").text(Number($("#year-span").text())+1);
			$("#month-span").text("1");
		}
		
		else $("#month-span").text(Number($("#month-span").text())+1);
		
		setCalendar($("#year-span").text(), $("#month-span").text());
	});
	
	$("#add-schedule-btn").click(function(){
		addScheduleInDB($('#room-id').val(), $("#worker-select option:selected").attr('id'), $('#job').val(), 
						$('#color option:selected').val(), $('#from').val(), $('#to').val());
		//addScheduleInCal();
	});
	
	$("#workshop-info-menu").click(function(){
		location.href = "workshopinfo.jsp?roomId=" + $("#room-id").val() + "";
	});
	
	$("#chat-room-menu").click(function(){
		location.href = "chat.jsp?roomId=" + $("#room-id").val() + "";
	});
	
	$("#file-menu").click(function(){
		location.href = "allfile.jsp?roomId=" + $("#room-id").val() + "";
	});
	
	$('#color').change(function(){
		$('#color').css({"color":$('#color option:selected').val()});
    });
	
	$("ul").delegate("li","mouseenter", function(e){
		if($(this).attr("class") != "empty-li"){
			$("#follow").css({"display":"block"});
			$("#float-name").text("담당 : " + $(this).find(".mem-name").val());
			if(Number($(this).find(".dday").val()) > 0)
				$("#float-dday").text("만료");
			
			else $("#float-dday").text("D-" + $(this).find(".dday").val());
			
			$("#float-from").text($(this).find(".from").val());
			$("#float-to").text($(this).find(".to").val());
			$("#follow").css({'top' : e.clientY - 20, 'left':e.clientX-20});
		}
	});
	
	$("ul").delegate("li","mouseleave", function(e){
		if($(this).attr("class") == "empty-li"){
			$("#follow").css({"display":"none"});
		}
	});

});

function setAddScheduleFormVisible(){
	if($("#user-email").val() == $("#manager-email").val()){
		$("#add-schedule-form").css({"display":"inline-block"});
	}
}

function loadSchedule(roomId){
	$.ajax({
		type: "post",
		url: "load_schedule.do",
		data: { roomId: roomId },
		datatype: "text",
		success: function(data){
			
			$("table ul").html("");
			
			$(data).find("info").each(function(){
				addScheduleInCalByDB($(this).find("id").text(), $(this).find("name").text(), $(this).find("job").text(), 
						$(this).find("color").text(), $(this).find("from").text(), $(this).find("to").text(), $(this).find("dday").text())
			});
		}
	});	
}

function callMember(roomId)
{
	$.ajax({
		type: "post",
		url: "workshop_member.do",
		data: { roomId: roomId},
		datatype: "text",
		success: function(data){
			$(data).find("info").each(function(){
				$("#worker-select").append(
						'<option id="' + $(this).find("email").text() + '">' + 
							$(this).find("name_gender").text().split(' ')[0] + 
						'</option>');
			});
		}
	});	
}

function addScheduleInCalByDB(id, name, job, color, from, to, dday)
{
	var tmp_cnt = 0, first_chk=0;
	var fromDate = new Date(
		from.split('-')[0],
		from.split('-')[1],
		from.split('-')[2]);
		//alert(fromDate);

	var toDate = new Date(
		to.split('-')[0],
		to.split('-')[1],
		to.split('-')[2]);
		//alert(toDate);

	for (var d = fromDate; d <= toDate; d.setDate(d.getDate() + 1)) {
		
		if(tmp_cnt == 0 && $("#"+ d.getFullYear()+ "-" + d.getMonth() + "-" + d.getDate() + " .empty-li").length == 0){
			continue_idx = $("#"+ d.getFullYear()+ "-" + d.getMonth() + "-" + d.getDate() + " li").length;
			tmp_cnt++;
		}
		
		else if(tmp_cnt == 0 && $("#"+ d.getFullYear()+ "-" + d.getMonth() + "-" + d.getDate() + " .empty-li").length > 0){
			continue_idx = $("#"+ d.getFullYear()+ "-" + d.getMonth() + "-" + d.getDate() + " .empty-li").index();
			//alert(continue_idx);
			tmp_cnt++;
		}
		
		var tmp_li_cnt = $("#"+ d.getFullYear()+ "-" + d.getMonth() + "-" + d.getDate() + " li").length;
		if(continue_idx - tmp_li_cnt > 0){

			for(var i=0; i<(Number(continue_idx) - Number(tmp_li_cnt)); i++)
				$("#"+ d.getFullYear()+ "-" + d.getMonth() + "-" + d.getDate() + " ul").append('<li class="empty-li"><div></div></li>');
			$("#"+ d.getFullYear()+ "-" + d.getMonth() + "-" + d.getDate() + " ul").append(
					'<li class="' + id + '"><div>&nbsp;</div>' 
						//+ job
						+ '<input type="hidden" class="mem-name" value="' + name + '">'
						+ '<input type="hidden" class="from" value="' + from + '">'
						+ '<input type="hidden" class="to" value="' + to + '">'
						+ '<input type="hidden" class="dday" value="' + dday + '">'
					+'</li>');
			
		}
		
		else if(continue_idx - tmp_li_cnt < 0){
			$("#"+ d.getFullYear()+ "-" + d.getMonth() + "-" + d.getDate() + " li").eq(continue_idx).html(
				'<div>&nbsp;</div>' 
					//+ job
					+ '<input type="hidden" class="mem-name" value="' + name + '">'
					+ '<input type="hidden" class="from" value="' + from + '">'
					+ '<input type="hidden" class="to" value="' + to + '">'
					+ '<input type="hidden" class="dday" value="' + dday + '">');
			
			$("#"+ d.getFullYear()+ "-" + d.getMonth() + "-" + d.getDate() + " li").eq(continue_idx).attr("class", id);
		}
		
		else{
			$("#"+ d.getFullYear()+ "-" + d.getMonth() + "-" + d.getDate() + " ul").append(
					'<li class="' + id + '"><div>&nbsp;</div>' 
						//+ job
						+ '<input type="hidden" class="mem-name" value="' + name + '">'
						+ '<input type="hidden" class="from" value="' + from + '">'
						+ '<input type="hidden" class="to" value="' + to + '">'
						+ '<input type="hidden" class="dday" value="' + dday + '">'
					+'</li>');
		}

		if(first_chk == 0){
			$("#"+ d.getFullYear()+ "-" + d.getMonth() + "-" + d.getDate() + " ." + id + " div").text(job);
			first_chk++;
		}
	}
	
	$("ul").css({"list-style":"none", "margin":"0", "padding":"0", "font-size":"14px"});
	$("li").css({"margin-top":"2px", "margin-bottom":"2px"})
	$("." + id + " div").css({"white-space": "nowrap", "width":"126px", "overflow":"hidden", "text-overflow":"clip",
					"color":"white", "background-color":color, "text-align":"center", "height":"20px", "padding-top":"3px"});
	$(".empty-li div").css({"height":"20px", "padding-top":"3px", "width":"126px"});
}

/*function addScheduleInCal(lastId)
{
	var fromDate = new Date(
		$("#from").val().split('-')[0],
		$("#from").val().split('-')[1],
		$("#from").val().split('-')[2]);
		//alert(fromDate);

	var toDate = new Date(
		$("#to").val().split('-')[0],
		$("#to").val().split('-')[1],
		$("#to").val().split('-')[2]);
		//alert(toDate);

	for (var d = fromDate; d <= toDate; d.setDate(d.getDate() + 1)) {
		//alert("#"+d.getFullYear()+ "-" + d.getMonth() + "-" + d.getDate());
		$("#"+ d.getFullYear()+ "-" + d.getMonth() + "-" + d.getDate() + " ul").append(
				'<li class="'+ lastId + '"><div>' 
					+ $("#job").val() 
					+ '<input type="hidden" class="mem_email" value="' + $("#worker-select option:selected").val() + '">'
					+ '<input type="hidden" class="from" value="' + $('#from').val() + '">'
					+ '<input type="hidden" class="to" value="' + $('#to').val() + '">'
					//+ '<input type="hidden" class="dday" value="' + dday + '">'
				+ '</div></li>');
		$("ul").css({"list-style":"none", "margin":"0", "padding":"0", "font-size":"12px"});
		$("li div").css({"white-space": "nowrap", "width":"90px", "overflow":"hidden", "text-overflow":"clip",
						"background-color":$('#color option:selected').val(), "color":"white"});
	}
}*/

function addScheduleInDB(roomId, email, job, color, from, to)
{
	$.ajax({
		type: "post",
		url: "insert_schedule.do",
		data: {roomId : roomId, email : email, job : job, 
				color : color, from : from, to : to},
		datatype: "text",
		success: function(data){
			//alert(data);
			if(data.split(' ')[0] == "true"){
				//addScheduleInCal(data.split(' ')[1]);
				loadSchedule(roomId);
				alert("일정이 추가되었습니다.");
			}
			else{alert("일정 추가에 실패하였습니다.");}
		},
		error : function(){
			alert("error");
		}
	});	
}

function setCalendar(year, month)
{
	var todayCal = new Date();
	var Calendar = new Date(year, month-1, todayCal.getDate());
	/*var day_of_week = ['일', '월', '화', '수', '목', '금', '토'];
	var month_of_year = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월','11월', '12월'];*/
	
	var year = Calendar.getFullYear();
	var month = Calendar.getMonth();
	var today = Calendar.getDate();
	var weekday = Calendar.getDay();

	Calendar.setDate(1);
	//alert("Set : " + Calendar.getFullYear() + " " + (Calendar.getMonth()+1) + " " + today);
	
	var DAYS_OF_WEEK = 7;
	var DAYS_OF_MONTH = 31;
	
	$(".days-tr").each(function(){
		$(this).remove();
	});
	
	var str='<tr class="days-tr">';
	for(var i=0; i<Calendar.getDay(); ++i){
		str += '<td></td>';
	}
	
	for(var i=0; i<DAYS_OF_MONTH; ++i){
		if(Calendar.getDate() > i)
		{
			var day = Calendar.getDate();
			var week_day = Calendar.getDay();
			
			if(week_day == 0 && day != 1){
				str += '<tr class="days-tr">';
			}
						
			if(todayCal.getFullYear()==year && todayCal.getMonth()==month && day==today){
					str += '<td class="today"  id="' + (year+"-"+(month+1)+"-"+day) + '">' +
								 day + 
								 '<ul></ul>' +
							'</td>';
			}
			else{
				switch(week_day){
					case 0:
						str += '<td class="sunday" id="' + (year+"-"+(month+1)+"-"+day) + '">' + 
									day + 
									'<ul></ul>' +
								'</td>';
						break;
						
					case 6:
						str += '<td class="saturday" id="' + (year+"-"+(month+1)+"-"+day) + '">' + 
									day + 
									'<ul></ul>' +
								'</td>';
						str += '</tr>';
						break;
						
					default:
						str += '<td class="default" id="' + (year+"-"+(month+1)+"-"+day) + '">' + 
									day + 
									'<ul></ul>' +
								'</td>';
						break;
						
				}
			}
			
			Calendar.setDate(Calendar.getDate() + 1);
		}
	}
	str += '</tr>';

	$("#calendar").append(str);
	
	//var cnt=0;
	$(".days-tr").each(function(){
			//cnt++;
			//if(cnt%2 == 0)
				//$(this).css({"background-color" : "#cfd5ea", "border-bottom" : "1px #4472c4 solid"});
			//else
				$(this).css({"background-color" : "white", "border-bottom" : "1px #4472c4 solid"});
	});
	
	loadSchedule($('#room-id').val());
}