var webSocket = null;
var roomId;

$(document).ready(function(){

	roomId = opener.parent.setRoomId();
	alert(roomId);
	
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
	
	$(".right-member-li").mouseover(function(){
		$(this).css({"background-color":"#f59329"});
	}).mouseout(function(){
		$(this).css({"background-color":"#272a33"});
	});
	
	$("#workshop-info-menu").click(function(){
		location.href = "workshopinfo.jsp?roomId=" + $("#room-id").val() + "";
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

