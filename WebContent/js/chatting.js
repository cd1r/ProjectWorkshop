var webSocket = null;
var roomId;

$(document).ready(function(){

	roomId = $("#room-id").val();
	alert(roomId);
	
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
	
	webSocket = new WebSocket("ws://localhost:10001/AdvWeb/websocket/"+ $("#user-email").val() + "/" + roomId);
	//webSocket = new WebSocket("ws://localhost:8088/AdvWeb/websocket/"+ $("#user-email").val() + "/" + roomId);
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
	
	$(".right-member-li").mouseover(function(){
		$(this).css({"background-color":"#f59329"});
	}).mouseout(function(){
		$(this).css({"background-color":"#272a33"});
	});
	
	$("#workshop-info-menu").click(function(){
		location.href = "workshopinfo.jsp?roomId=" + $("#room-id").val() + "";
	});
	$("#schedule-menu").click(function(){
		location.href = "schedule.jsp?roomId=" + $("#room-id").val() + "";
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
}

//파일 드래그 앤 드롭 할때
var onDropFile = function(e){
	e.preventDefault();
	var file = e.dataTransfer.files[0];
	readFile(file);
};

var onCancel = function(e){
	if(e.preventDefault) {e.preventDefault();}
	return false;
};

//tab2 file upload/download관련
$(document).on("click", "#upload-btn", function(){
	
	var file = document.getElementById('file_upload').files[0];
	readFile(file);
});

//filesocket으로 파일 업로드
var readFile = function(file){

	fileSocket = new WebSocket("ws://localhost:10001/AdvWeb/filesocket/"+ $("#user-email").val() + "/" + roomId + "/" + file.name);
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
    	if(message.data == "파일완료"){ //파일업로드 완료
    		fileSocket.send('upload-file-db/'+file.size); //파일정보 디비에 저장하라고 요청
    	}
    	else if(message.data == "완료"){ //디비에 저장까지 완료
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
    	else{ //파일전송에러
    		alert(message.data);
    	}
    };

	answer = confirm("파일을 업로드 하시겠습니까?");  // 이거 절대 빼면 안됨
	if (answer == true) {
		fileSocket.send(file.name); // 파일이름

		var reader = new FileReader();
		var rawData = new ArrayBuffer();

		reader.loadend = function() {

		}
		reader.onload = function(e) {
			rawData = e.target.result;
			fileSocket.send(rawData);
		}

		reader.readAsArrayBuffer(file);
	}
	else{
		fileSocket.close();
	}
};


