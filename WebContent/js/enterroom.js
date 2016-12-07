var roomId = "";

$(document).ready(function() {

	$.ajax({
		type: "post",
		url: "load_room_list.do",
		data: { email:$('#user-email').val() },
		datatype: "text",
		success: function(data){
			var info_cnt = 0;
			var row_cnt = 1;
			var room_table = document.getElementById('room-list-table');
			$("#room-list-table").append('<tr id="row' + ((row_cnt*3)-2) + '" class="room-info-tr"></tr>');
			$("#room-list-table").append('<tr id="row' + ((row_cnt*3)-1) + '" class="enter-btn-tr"></tr>');
			$("#room-list-table").append('<tr id="row' + (row_cnt*3) + '" class="spacing-tr"></tr>');

			$(data).find("info").each(function(){
				$("#row"+((row_cnt*3)-2)).append(
						'<td>'+
						'<div class="del-btn"><input type="button" value="X" id="del'+ $(this).find("id").text() +'"></div>'+
						'<div class="workshop-name">' + $(this).find("room_name").text() + '</div>'+
						'<div class="leader-name">팀장 : ' + $(this).find("manager_name").text() + '</div>'+
						'<div class="mem-cnt">' + $(this).find("mem_cnt").text() + '명 소속</div>'+
						'</td>'
						);
				
				$("#row"+((row_cnt*3)-1)).append(
						'<td>'+
						'<div class="enter-btn-div"><button id="' + $(this).find("id").text() + '" class="enter-btn">입장하기</button></div>'+
						'</td>');
		
				$("#row"+(row_cnt*3)).append('<td> </td>');
				
				info_cnt++;
				
				if(info_cnt == 3)
				{
					info_cnt=0;
					row_cnt++;
					$("#room-list-table").append('<tr id="row' + ((row_cnt*3)-2) + '" class="room-info-tr"></tr>');
					$("#room-list-table").append('<tr id="row' + ((row_cnt*3)-1) + '" class="enter-btn-tr"></tr>');
					$("#room-list-table").append('<tr id="row' + (row_cnt*3) + '" class="spacing-tr"></tr>');
				}
			});
			
			$(".enter-btn").css({"width" : "150px", "height" : "35px", "background-color:" : "#4d94ff", "color" : "white",
				"font-family" : "NanumSquare", "font-size" : "15px", "border" : "0", "box-shadow" : "none"});
			$(".del-btn").css({"text-align":"right", "margin-top":"10px", "padding-bottom":"20px"});
		}
	});	
});

$(document).on("click", ".enter-btn", function() {
	roomId = $(this).attr('id');
	window.open('chat.jsp?roomId=' + $(this).attr('id') + '', '_blank', "toolbar=no,status=no,scrollbars=yes,resizable=no,width=1050,height=820"); 
	//window.open("chatting.jsp", "chatting", "toolbar=no,status=no,scrollbars=yes,resizable=yes,width=500,height=300");
});

//방 삭제
$(document).on("click", ".del-btn input", function() {
	
	if(confirm("방을 탈퇴 하시겠습니까?") == true){
		$.ajax({
			type: "post",
			url: "delete_room.do",
			data: {roomId : $(this).attr('id').split('del')[1] },
			datatype: "text",
			success: function(data){
				if(data=="true") {
					location.href = "enterroom.jsp";
				}
				else {
					alert("삭제 실패");
				}
			}
		});
	}	
});
