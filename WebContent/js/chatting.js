var webSocket = null;
var roomId;
var member = [];

$(document).ready(function(){

	roomId = $("#room-id").val();
	alert(roomId);
	
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
        //messageTextArea.value += "상대 => "+message.data+"\n";
    	$(".dialog-ul").append(
    			'<li class="dialog-li">'+
    				'<table class="dialog-table">'+
            			'<tr><td class="dialog-img-td" rowspan="2"><img class="img-dialog" src="./images/park.png"></td>'+
            				'<td class="speaker-name-td">박효신</td>'+
            			'</tr>'+
            			'<tr>'+
            				'<td class="talk-td">'+message.data+'</td>'+                           
            			'</tr>'+
            		'</table>'+
            	'</li>');
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
				'<li class="dialog-li-own">'+
					'<table class="dialog-table-own">'+
						'<tr>'+
							'<td class="talk-td-own">' + message.value +'</td>'+                           
						'</tr>'+
					'</table>'+
				'</li>');
	
    //messageTextArea.value += "나 => "+message.value+"\n";
    //웹소켓으로 textMessage객체의 값을 보낸다.
    webSocket.send(message.value);
    //textMessage객체의 값 초기화
    message.value = "";
});

//채팅 창 닫으면 웹소켓 종료
window.onbeforeunload = function(e){
	webSocket.close();
	fileSocket.close();
}

var onDropFile = function(e){
	e.preventDefault();
	var file = e.dataTransfer.files[0];
	readFile(file);
};

var onCancel = function(e){
	if(e.preventDefault) {e.preventDefault();}
	return false;
};

var readFile = function(file){
	
	fileSocket = new WebSocket("ws://localhost:8088/AdvWeb/filesocket/"+ $("#user-email").val() + "/" + roomId);
	//fileSocket = new WebSocket("ws://localhost:10001/AdvWeb/filesocket/"+ $("#user-email").val() + "/" + roomId);
	fileSocket.binaryType="arraybuffer";
	
	//웹 소켓이 연결되었을 때 호출되는 이벤트
	fileSocket.onopen = function(message){ alert("File on Open : " + message); };
    //웹 소켓이 닫혔을 때 호출되는 이벤트
	fileSocket.onclose = function(message){ alert("File on Close : " + message); };
    //웹 소켓이 에러가 났을 때 호출되는 이벤트
	fileSocket.onerror = function(message){
        alert(message.data)
    };
    //웹 소켓에서 메시지가 날라왔을 때 호출되는 이벤트
    fileSocket.onmessage = function(message){ 
    	if(message.data == "완료"){
    		alert("파일 전송완료");
    		
    		var fileinfo = file.name+' 업로드';
    		$(".dialog-ul").append(
    				'<li class="dialog-li-own">'+
    					'<table class="dialog-table-own">'+
    						'<tr>'+
    							'<td class="talk-td-own">' + fileinfo +'</td>'+                           
    						'</tr>'+
    					'</table>'+
    				'</li>');

    		webSocket.send(fileinfo);
    		
    		fileSocket.send('upload-file-end');
    		fileSocket.close();
    	}
    };
    
	var name = file.name;
	var size = file.size;
	var type = file.type;
	alert(name+"  "+size+"  "+type);
	
	fileSocket.send(file.name); //파일이름
	
	var reader = new FileReader();
	var rawData = new ArrayBuffer();
	
	reader.loadend = function(){
		
	}
	reader.onload = function(e){
		rawData = e.target.result;
		fileSocket.send(rawData);	
	}
	
	reader.readAsArrayBuffer(file);
};



//tab2 file upload/download관련
$(document).on("click", "#upload-btn", function(){
	var ua = window.navigator.userAgent;
	alert(ua);
	
	fileSocket = new WebSocket("ws://localhost:10001/AdvWeb/filesocket/"+ $("#user-email").val() + "/" + roomId);
	fileSocket.binaryType="arraybuffer";
	
	//웹 소켓이 연결되었을 때 호출되는 이벤트
	fileSocket.onopen = function(message){ };
    //웹 소켓이 닫혔을 때 호출되는 이벤트
	fileSocket.onclose = function(message){ };
    //웹 소켓이 에러가 났을 때 호출되는 이벤트
	fileSocket.onerror = function(message){
        alert(message.data)
    };
    //웹 소켓에서 메시지가 날라왔을 때 호출되는 이벤트
    fileSocket.onmessage = function(message){ 
    	if(message.data == "완료"){
    		alert("파일 전송완료");
    		
    		var fileinfo = file.name+' 업로드';
    		$(".dialog-ul").append(
    				'<li class="dialog-li-own">'+
    					'<table class="dialog-table-own">'+
    						'<tr>'+
    							'<td class="talk-td-own">' + fileinfo +'</td>'+                           
    						'</tr>'+
    					'</table>'+
    				'</li>');

    		webSocket.send(fileinfo);
    		
    		fileSocket.send('upload-file-end');
    		fileSocket.close();
    	}
    };
    
	var file = document.getElementById('file_upload').files[0];
	alert(file.name+" "+file.size);
	
	fileSocket.send(file.name); //파일이름
	
	var reader = new FileReader();
	var rawData = new ArrayBuffer();
	
	reader.loadend = function(){
		
	}
	reader.onload = function(e){
		rawData = e.target.result;
		fileSocket.send(rawData);
		
	}
	
	reader.readAsArrayBuffer(file);
});

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
							'<table class="right-profile-table">' +
								'<tr>' +
									'<td class="profile-img-td" rowspan="2"><div><img src="' + $(this).find("photo_url").text() + '"></div></td>'+
									'<td class="member-name-td">' + $(this).find("name_gender").text().split(' ')[0] + '</td>'+
								'</tr>' +
								'<tr>' +
									'<td class="member-email-td">' + $(this).find("email").text() + '</td>'+
								'</tr>' +
						'</li>');
				
				member.push($(this).find("email").text() + "\t" + $(this).find("name_gender").text().split(' ')[0]);
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
			var lastId = 0;
			$(data).find("dialog").each(function(){
				if($(this).find("speaker").text() == $("#user-email").val()){
					$(".dialog-ul").append(
							'<li id="' + $(this).find("id").text() + '" class="dialog-li-own">'+
								'<table class="dialog-table-own">'+
									'<tr>'+
										'<td class="talk-td-own">' + $(this).find("context").text() + '</td>'+                           
									'</tr>'+
								'</table>'+
							'</li>');
				}
				
				else{
					var speaker_name = null;
					for(var i=0; i<member.length; i++){
						if(member[i].split('\t')[0] == $(this).find("speaker").text())
							speaker_name = member[i].split('\t')[1];
					}
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
				lastId = $(this).find("id").text();
			});
			//$(".dialog-content div").scrollTop($(".dialog-content").height());
			$(".dialog-content div").animate({scrollTop:$("#"+lastId).offset().top}, 500);
			/*$(".right-member-li").css({"padding-top":"7px", "padding-bottom":"7px"});
			$(".profile-img-td div").css({"width":"60px", "height":"60px", "overflow":"hidden", "border-radius":"50%"});
			$(".profile-img-td div img").css({"width":"100%", "height":"auto"});*/
		}
	});
}