$(document).ready(function() {
	callAllFile();
	
	$("#chat-room-menu").click(function(){
		location.href = "chat.jsp?roomId=" + $("#room-id").val() + "";
	});
	$("#schedule-menu").click(function(){
		location.href = "schedule.jsp?roomId=" + $("#room-id").val() + "";
	});
	$("#file-menu").click(function(){
		location.href = "allfile.jsp?roomId=" + $("#room-id").val() + "";
	});
});