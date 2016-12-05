//숫자 값으로만 id주는것은 대화목록에 사용했음.
var webSocket = null;
var lastDialogIdx = 0;
var lastReadIdx = 0;
var roomId;
var member = [];

$(document).ready(function(){

	roomId = $("#room-id").val();
	//alert(roomId);
	
	loadRightMember();
	
	
	var area = document.getElementById('dialog-enter');
	
	if(area.attachEvent){
		area.attachEvent("dragover", onCancel, false);
		area.attachEvent("dragenter", onCancel, false);
		area.attachEvent("drop", onDropFile, false);
	}
	else{
		area.addEventListener("dragover", onCancel, false);
		area.addEventListener("dragenter", onCancel, false);
		area.addEventListener("drop", onDropFile, false);
	}
	
	//webSocket = new WebSocket("ws://localhost:80/AdvWeb/websocket/"+ $("#user-email").val() + "/" + roomId);
	//webSocket = new WebSocket("ws://localhost:10001/AdvWeb/websocket/"+ $("#user-email").val() + "/" + roomId);
	webSocket = new WebSocket("ws://localhost:8088/AdvWeb/websocket/"+ $("#user-email").val() + "/" + roomId);
	//webSocket = new WebSocket("ws://121.126.233.20:8080/ProjectWorkshop/websocket/"+ $("#user-email").val() + "/" + roomId);
	
    var messageTextArea = document.getElementById("messageTextArea");
    //웹 소켓이 연결되었을 때 호출되는 이벤트
    webSocket.onopen = function(message){
        //messageTextArea.value += "연결성공...\n";
    };
    //웹 소켓이 닫혔을 때 호출되는 이벤트
    webSocket.onclose = function(message){
        messageTextArea.value += "종료...\n";
    };
    //웹 소켓이 에러가 났을 때 호출되는 이벤트
    webSocket.onerror = function(message){
        messageTextArea.value += "오류...\n";
        alert(message.data)
    };
    //웹 소켓에서 메시지가 날라왔을 때 호출되는 이벤트
    webSocket.onmessage = function(message){
    	var temp_photo_url ="" , temp_name = "";
    	
    	//member[index].split('\t')[0] : email, [1] : 이름, [2] : 사진 경로
    	//message.data.split('\t')[0] : 전달된 메시지 내용, message.data.split('\t')[1] : 메시지 보낸 사람의 이메일
    	//message.data.split('\t')[2] : 파일번호(일반 대화면 0, 파일이면 파일번호, 접속 메시지면 ON)
    	for(var i=0; i<member.length; i++){
    		if(member[i].split('\t')[0] == message.data.split('\t')[1]) //전역 변수 배열에 저장된 사람의 이메일을 탐색하면서 메시지 보낸 사람의 이메일과 같은지 비교함
    		{
    			temp_name = member[i].split('\t')[1];
    			temp_photo_url = member[i].split('\t')[2];
    			
    		}
    	}
    	
    	if(message.data.split('\t')[2] == "ON"){ //접속 안내 메시지
    		$(".dialog-ul").append(
    				'<li class="member-online-li">'+
    				'<div id="last-read-div">' + message.data.split('\t')[0] + message.data.split('\t')[1] + '</div>'+
            	'</li>');
		
			$(".member-online-li").css({"width":"97%", "height":"20px", "overflow-y":"auto",
									 "text-align":"center",	"background-color":"#00e48b", "color":"white", "font-family":"NanumSquare",
									 "padding-top":"5px", "padding-bottom":"5px"});
			
			for(var i=0; i<member.length; i++){
				if(member[i].split("\t")[0] == message.data.split('\t')[1].split(' ')[0]){ // 이메일만 추출
					$("#" + member[i].split("\t")[0].replace("@","").replace(".","") + "-right .is-online").text("온라인");
					$("#" + member[i].split("\t")[0].replace("@","").replace(".","") + "-right .is-online").css({"color" : "#00e48b"});
				}
			}
			
			$(".dialog-ul li").css({"margin-bottom":"20px"});
    	}
    	
    	else if(message.data.split('\t')[2] == "OFF"){ //OFF 안내 메시지
    		$(".dialog-ul").append(
    				'<li class="member-offline-li">'+
    				'<div id="last-read-div">' + message.data.split('\t')[0] + message.data.split('\t')[1] + '</div>'+
            	'</li>');
		
			$(".member-offline-li").css({"width":"97%", "height":"20px", "overflow-y":"auto",
									 "text-align":"center",	"background-color":"#333333", "color":"white", "font-family":"NanumSquare",
									 "padding-top":"5px", "padding-bottom":"5px"});
			
			for(var i=0; i<member.length; i++){
				if(member[i].split("\t")[0] == message.data.split('\t')[1].split(' ')[0]){ // 이메일만 추출
					$("#" + member[i].split("\t")[0].replace("@","").replace(".","") + "-right .is-online").text("오프라인");
					$("#" + member[i].split("\t")[0].replace("@","").replace(".","") + "-right .is-online").css({"color" : "red"});
				}
			}
			$(".dialog-ul li").css({"margin-bottom":"20px"});
    	}
    	
    	else if(message.data.split('\t')[2] == "0"){ //일반대화
    		$(".dialog-ul").append(
    				'<li id="' + (++lastDialogIdx) + '" class="dialog-li">'+
    					'<table class="dialog-table">'+
            				'<tr><td class="dialog-img-td" rowspan="2"><img class="img-dialog" src="' + temp_photo_url +'"></td>'+
            					'<td class="speaker-name-td">' + temp_name + '</td>'+
            					'</tr>'+
            					'<tr>'+
            					'<td class="talk-td">'+message.data.split('\t')[0]+'</td>'+                           
            					'</tr>'+
            					'</table>'+
            		'</li>');
    	}
    	else{ //파일
    		$(".dialog-ul").append(
        			'<li id="' + (++lastDialogIdx) + '" class="dialog-li">'+
        				'<table class="dialog-table">'+
                			'<tr><td class="dialog-img-td" rowspan="2"><img class="img-dialog" src="' + temp_photo_url +'"></td>'+
                				'<td class="speaker-name-td">' + temp_name + '</td>'+
                			'</tr>'+
                			'<tr>'+
                				'<td class="talk-td">'+message.data.split('\t')[0]+'<br>'+
                				//'<a href="fileDownload.jsp?roomId='+roomId+'&fileId='+message.data.split('\t')[2]+'">다운로드</a>'+
                				'<a href="javascript:filedown('+message.data.split('\t')[2]+')">다운로드</a>' +
                				'</td>'+                           
                			'</tr>'+
                		'</table>'+
                	'</li>');
    	}
    	$("#dialog-div").scrollTop($("#dialog-div")[0].scrollHeight);
    	
    };
    
    //UI관련  - 세윤
    $("#right-menu-table .tab-td").click(function() {
		$("#tab-content-td div").hide();
		$("#right-menu-table .tab-td").css({"background-color" : "#59738e"});	
		$("#" + $(this).attr("id").split('-')[0] + "-div").fadeIn();
		$("#" + $(this).attr("id").split('-')[0] + "-div div").css({"display":"block"});
		$(this).css({"background-color" : "#5996cf"});		
	});
	
	$("#workshop-info-menu").click(function(){
		location.href = "workshopinfo.jsp?roomId=" + $("#room-id").val() + "";
	});
	$("#schedule-menu").click(function(){
		location.href = "schedule.jsp?roomId=" + $("#room-id").val() + "";
	});
	$("#file-menu").click(function(){
		location.href = "allfile.jsp?roomId=" + $("#room-id").val() + "";
	});
});

//Send 버튼을 누르면 실행되는 함수
$(document).on("click", "#send-btn", function(){
	var message = document.getElementById("dialog-enter");
	$(".dialog-ul").append(
				'<li id="' + (++lastDialogIdx) + '" class="dialog-li-own">'+
					'<table class="dialog-table-own">'+
						'<tr>'+
							'<td class="talk-td-own">' + message.value +'</td>'+                           
						'</tr>'+
					'</table>'+
				'</li>');
	
	$("#dialog-div").scrollTop($("#dialog-div")[0].scrollHeight);
    //messageTextArea.value += "나 => "+message.value+"\n";
    //웹소켓으로 textMessage객체의 값을 보낸다.
    webSocket.send(message.value);
    //textMessage객체의 값 초기화
    message.value = "";
    $("#dialog-enter").val("");
});

//채팅 창 닫으면 웹소켓 종료
window.onbeforeunload = function(e){
	webSocket.close();
};

//파일 드래그 앤 드롭 할때
var onDropFile = function(e){
	e.preventDefault();
	var file = e.dataTransfer.files[0];
	alert(file.name);
	
	var formData = new FormData();
	formData.append("uploadfile", file);
	
	$.ajax({
		type: "post",
		url: "fileUpload.jsp?roomId="+roomId+"&email="+$("#user-email").val(),
		data: formData,
		processData: false,
		contentType: false,
		datatype: "text",
		success: function(data) {
			if(data == "flase") {
				alert("파일 업로드 실패");
			}
			else{ 
				alert("파일 업로드 성공 "+data);
				var fileinfo = file.name +" 업로드";
				$(".dialog-ul").append(
	    				'<li id="' + (++lastDialogIdx) + '" class="dialog-li-own">'+
	    					'<table class="dialog-table-own">'+
	    						'<tr>'+
	    							'<td class="talk-td-own">' + 
	    								fileinfo + '<br/>'+
	    								//'<a href="fileDownload.jsp?roomId='+roomId+'&fileId='+data+'">다운로드</a>' +
	    								'<a href="javascript:filedown('+data+')">다운로드</a>' +
	    							'</td>'+                           
	    						'</tr>'+
	    					'</table>'+
	    				'</li>');

				$("#dialog-div").scrollTop($("#dialog-div")[0].scrollHeight);
			
				fileinfo = data + "\t" + fileinfo;
	    		webSocket.send(fileinfo);
			}
		}
	});	
	
	//readFile(file);
};

var onCancel = function(e){
	if(e.preventDefault) {e.preventDefault();}
	return false;
};

function loadRightMember(){
	$.ajax({
		type: "post",
		url: "workshop_member.do",
		data: {roomId : $("#room-id").val()},
		datatype: "text",
		success: function(data){
			$(data).find("info").each(function(){
				
				$("#right-member-ul").append(
						'<li class="right-member-li">'+
							'<table class="right-profile-table" id="' + $(this).find("email").text().replace("@","").replace(".","") + '-right">' +
								'<tr>' +
									'<td class="profile-img-td" rowspan="2"><div><img src="' + $(this).find("photo_url").text() + '"></div></td>'+
									'<td class="member-name-td">' + $(this).find("name_gender").text().split(' ')[0] + 
										'<span class="is-online">오프라인</span></td>'+
								'</tr>' +
								'<tr>' +
									'<td class="member-email-td">' + $(this).find("email").text() + '</td>'+
								'</tr>' +
							'</table>'+
						'</li>');
				if($(this).find("email").text() == $("#user-email").val()){
					lastReadIdx = $(this).find("last_read_dialog").text();
				}
		
				member.push($(this).find("email").text() + "\t" + $(this).find("name_gender").text().split(' ')[0] + "\t" + $(this).find("photo_url").text());
		
			});
			$(".right-member-li").css({"padding-top":"7px", "padding-bottom":"7px"});
			$(".profile-img-td div").css({"width":"60px", "height":"60px", "overflow":"hidden", "border-radius":"50%"});
			$(".profile-img-td div img").css({"width":"100%", "height":"auto"});
			
			$(".right-member-li").mouseover(function(){
				$(this).css({"background-color":"#f59329"});
			}).mouseout(function(){
				$(this).css({"background-color":"#272a33"});
			});
			
			loadDialog();

			$("#member-cnt-label").text("총 인원 : " + member.length +"명");
		}
	});
};

function loadIsOnline()
{
	$.ajax({
		type: "post",
		url: "is_online.do",
		data: {roomId : $("#room-id").val()},
		datatype: "text",
		success: function(data){
			$(data).find("online").each(function(){
				for(var i=0; i<member.length; i++){
					if(member[i].split("\t")[0] == $(this).find("email").text()){
						$("#" + member[i].split("\t")[0].replace("@","").replace(".","") + "-right .is-online").text("온라인");
						$("#" + member[i].split("\t")[0].replace("@","").replace(".","") + "-right .is-online").css({"color" : "#00e48b"});
					}
				}
			});
		}
	});
}

function loadDialog(){
	$.ajax({
		type: "post",
		url: "load_dialog.do",
		data: {roomId : $("#room-id").val()},
		datatype: "text",
		success: function(data){
			lastDialogIdx = 0;
			$(data).find("dialog").each(function(){
				if($(this).find("speaker").text() == $("#user-email").val()){
					
					if(Number($(this).find("file").text()) > 0){
						$(".dialog-ul").append(
								'<li id="' + $(this).find("id").text() + '" class="dialog-li-own">'+
									'<table class="dialog-table-own">'+
			                			'<tr>'+
			                				'<td class="talk-td-own">'+$(this).find("context").text()+'<br>'+
			                				//'<a href="fileDownload.jsp?roomId='+roomId+'&fileId=' + $(this).find("id").text() + '">다운로드</a>'+
			                				'<a href="javascript:filedown('+$(this).find("file").text()+')">다운로드</a>' +
			                				'</td>'+                           
			                			'</tr>'+
			                		'</table>'+
			                	'</li>');
					}
					else{
						$(".dialog-ul").append(
								'<li id="' + $(this).find("id").text() + '" class="dialog-li-own">'+
									'<table class="dialog-table-own">'+
										'<tr>'+
											'<td class="talk-td-own">' + $(this).find("context").text() + 
											'</td>'+                           
										'</tr>'+
									'</table>'+
								'</li>');
					}
				}
				
				else{
					var speaker_name = null;
					for(var i=0; i<member.length; i++){
						if(member[i].split('\t')[0] == $(this).find("speaker").text())
							speaker_name = member[i].split('\t')[1];
					}
					
					if(Number($(this).find("file").text()) > 0){
						$(".dialog-ul").append(
			        			'<li id="' + (++lastDialogIdx) + '" class="dialog-li">'+
			        				'<table class="dialog-table">'+
			                			'<tr><td class="dialog-img-td" rowspan="2"><img class="img-dialog" src="' + $(this).find("photo_url").text() +'"></td>'+
			                				'<td class="speaker-name-td">' + speaker_name + '</td>'+
			                			'</tr>'+
			                			'<tr>'+
			                				'<td class="talk-td">'+$(this).find("context").text()+'<br>'+
			                				//'<a href="fileDownload.jsp?roomId='+roomId+'&fileId=' + $(this).find("file").text() + '">다운로드</a>'+
			                				'<a href="javascript:filedown('+$(this).find("file").text()+')">다운로드</a>' +
			                				'</td>'+                           
			                			'</tr>'+
			                		'</table>'+
			                	'</li>');
					}
					else{	
						$(".dialog-ul").append(
				    			'<li id="' + $(this).find("id").text() + '" class="dialog-li">'+
				    				'<table class="dialog-table">'+
				            			'<tr><td class="dialog-img-td" rowspan="2"><img class="img-dialog" src="' + $(this).find("photo_url").text() + '"></td>'+
				            				'<td class="speaker-name-td">' + speaker_name + '</td>'+
				            			'</tr>'+
				            			'<tr>'+
				            				'<td class="talk-td">' + $(this).find("context").text() + '</td>'+                           
				            			'</tr>'+
				            		'</table>'+
				            	'</li>');
					}
				}
						
				lastDialogIdx = $(this).find("id").text();
			});
			
			$(".dialog-ul li").css({"margin-bottom":"20px"});
			
			loadIsOnline();
			
			if(lastDialogIdx != lastReadIdx && lastReadIdx != 0 ){
				$(".dialog-ul li").eq(lastReadIdx).after(
		    			'<li class="last-read-li">'+
		    				'<div id="last-read-div">여기까지 읽으셨습니다.</div>'+
		            	'</li>');
			
				$(".last-read-li").css({"width":"97%", "height":"20px", "overflow-y":"auto",
										 "text-align":"center",	"background-color":"#272a33", "color":"white", "font-family":"NanumSquare",
										 "padding-top":"5px", "padding-bottom":"5px"});
				
				$(".dialog-ul li").css({"margin-bottom":"20px"});
				$(".dialog-content div").animate({scrollTop:$("#"+lastReadIdx).offset().top}, 500);
			}
			else
				$(".dialog-content div").animate({scrollTop:$("#"+lastDialogIdx).offset().top}, 300);
		}
	});
};

$(document).on("click", "#tab2-td", function(){
	$.ajax({
		type: "post",
		url: "load_file.do",
		data: {roomId : $("#room-id").val(), isList : "True"},
		datatype: "text",
		success: function(data){
			$("#filelist-ul").empty();	
			var file_cnt = 0;
			$(data).find("files").each(function(){
				$("#filelist-ul").append(
					'<li><div class="file-li-wrap">' +
						'<div id="' + $(this).find("id").text() + '" class="li-filename">' + $(this).find("file_name").text() + '</div>'+
						'<span class="li-size">' + calSize($(this).find("size").text()) + '</span>'+
						'<div class="li-uploaddate">' + $(this).find("upload_date").text() + '</div>'+
					'</div></li>');
				file_cnt++;
			});
			$("#file-cnt-label").text("총 파일 : " + file_cnt + "개");
			
			$(".li-filename").mouseover(function(){
				$(this).css({"color":"#ff5d8b"});
			}).mouseout(function(){
				$(this).css({"color":"white"});
			});
			
			$(".li-filename").click(function(){
				//alert($(this).attr('id'));
				filedown($(this).attr('id'));
			});
		}
	});
});


$(document).on("click", "#tab3-td", function(){
	$.ajax({
		type: "post",
		url: "load_schedule.do",
		data: {isTerm : "False", roomId : $("#room-id").val()},
		datatype: "text",
		success: function(data){
			$("#worklist-ul").empty();	
			var job_cnt = 0;
			$(data).find("info").each(function(){
				if($(this).find("mem_email").text() == $("#user-email").val() &&
						Number($(this).find("dday").text()) >= 0){
					$("#worklist-ul").append(
						'<li>' +
							'<table>' +
								'<tr id="sch' + $(this).find("id").text() + '">' +
									'<td class="job">'+ $(this).find("job").text() +'</td>'+
									'<td class="dday">D-' + $(this).find("dday").text() +'</td>'+
								'</tr>'+
							'</table>'+
						'</li>');
					job_cnt++;
				}
			});
			
			$("#worklist-ul li").css({"margin-bottom":"20px"});
			$("#worklist-ul .job").css({"color":"white", "width":"160px"});
			$("#worklist-ul .dday").css({"padding-left" : "10px", "color":"#d7b113", "width":"40px"});
			
			$("#work-cnt-label").text("내 일정 " + job_cnt + "개");
		}
	});
});

function filedown(fileId){
	window.open("fileDownload.jsp?roomId="+roomId+"&fileId="+fileId, '_blank', "toolbar=no,status=no,scrollbars=yes,resizable=no,width=400,height=100"); 
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
