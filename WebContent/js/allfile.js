$(document).ready(function() {
	callAllFile($("#room-id").val());
	
	$("#workshop-info-menu").click(function(){
		location.href = "workshopinfo.jsp?roomId=" + $("#room-id").val() + "";
	});
	$("#chat-room-menu").click(function(){
		location.href = "chat.jsp?roomId=" + $("#room-id").val() + "";
	});
	$("#schedule-menu").click(function(){
		location.href = "schedule.jsp?roomId=" + $("#room-id").val() + "";
	});
});

function callAllFile(roomId)
{
	$.ajax({
		type: "post",
		url: "load_file.do",
		data: { roomId: roomId},
		datatype: "text",
		success: setFileView
	});	
}

function setFileView(data){
	
	$("#content-table").empty();
	
	var oldExt = "", rowCnt=1, tdCnt=0, size;
	
	$(data).find("files").each(function(){
		size = calSize(parseFloat($(this).find("size").text()));
		
		if(oldExt != $(this).find("extention").text()){ //최초 확장인 경우
			tdCnt=0;
			rowCnt = 1;
			$("#content-table").append(
				'<tr class="ext-row">'+
					'<td colspan=6>' + $(this).find("extention").text().toUpperCase() + '</td>'+
				'</tr>'+
				'<tr id="' + $(this).find("extention").text() + '-row' + rowCnt + '" class="file-row">'+
				'</tr>');
			
			$("#" + $(this).find("extention").text() + "-row" + rowCnt).append(
					'<td><div><img id="' + $(this).find("id").text() + '" src="./images/extension/' + $(this).find("extention").text() + '.png"></div>'+
						'<div class="filename-div">' + $(this).find("file_name").text().split('.')[0] + '<img src="./images/down-arrow.png"></div>'+
						'<div class="etc-info-div" style="display:none;" rel="false">'+
							'<div>' + size + '</div>'+
							'<div>' + $(this).find("name").text() + '</div>'+
							'<div>' + $(this).find("upload_date").text() + '</div>'+
						'</div>'+
					'</td>');
				tdCnt++;
			
			oldExt = $(this).find("extention").text();
		}
		
		else{
			
			if(tdCnt > 6){
				tdCnt = 0;
				rowCnt++;
				$("#content-table").append(
					'<tr id="' + $(this).find("extention").text() + '-row' + rowCnt + '" class="file-row">'+
					'</tr>');
			}
			
			$("#" + $(this).find("extention").text() + "-row" + rowCnt).append(
				'<td><div><img id="' + $(this).find("id").text() + '" src="./images/extension/' + $(this).find("extention").text() + '.png"></div>'+
					'<div class="filename-div">' + $(this).find("file_name").text().split('.')[0] + '<img src="./images/down-arrow.png"></div>'+
					'<div class="etc-info-div" style="display:none;" rel="false">'+
						'<div>' + size + '</div>'+
						'<div>' + $(this).find("name").text() + '</div>'+
						'<div>' + $(this).find("upload_date").text() + '</div>'+
					'</div>'+
				'</td>');
			tdCnt++;
		}
	});
	
	$( ".filename-div" ).click(function() { // 파일 상세정보 슬라이드 애니메이션
		if($(this).next().attr("rel") == "false"){
			$(this).next().slideDown( "slow", function() {
				$(this).attr("rel", "true");
			});
		}
		else{
			$(this).next().slideUp( "slow", function() { 
				$(this).attr("rel", "false");
			});
		}
	});
}

function calSize(originSize){
	var tmpSize;
	
	tmpSize = originSize / parseFloat(1024); // Byte를 KB 단위로
	
	if(tmpSize < 1024) return tmpSize.toFixed(1) + "KB"; // 1024 KB 미만이면 그냥 KB 단위로 리턴
	
	if(tmpSize >= 1024){ // 1024 KB 이상이면 MB로 다시 계산하여 리턴
		tmpSize = tmpSize/1024;
		return tmpSize.toFixed(1) + "MB";
	}
	
	
}

$(document).on("click", "#search-btn", function(){
	$.ajax({
		type: "post",
		url: "load_file.do",
		data: { roomId: $("#room-id").val(), 
				searchCriteria : $('#search-criteria option:selected').val(),
				searchKeyword : $('#search-keyword').val(),
		},
		datatype: "text",
		success: setFileView
	});
});

$(document).on("click", "#refresh-btn", function(){
	callAllFile($("#room-id").val());
});
