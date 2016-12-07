$(document).ready(function() {
      
	$("#owl-demo").owlCarousel({
	  autoPlay : 5000,
      slideSpeed : 800,
      paginationSpeed : 800,
      singleItem : true

      // "singleItem:true" is a shortcut for:
      // items : 1, 
      // itemsDesktop : false,
      // itemsDesktopSmall : false,
      // itemsTablet: false,
      // itemsMobile : false
      });
	
	$("#first-menu").mouseover(function(){
		$("#first-menu").css({"border":"2px solid #4d94ff"});
		$("#first-menu img").attr("src","./images/img1.gif");
	}).mouseout(function(){
		$("#first-menu").css({"border":"2px solid #d8d8d8"});
		$("#first-menu img").attr("src","./images/img1.png");
	});
	
	$("#second-menu").mouseover(function(){
		$("#second-menu").css({"border":"2px solid #4d94ff"});
		$("#second-menu img").attr("src","./images/img2.gif");
	}).mouseout(function(){
		$("#second-menu").css({"border":"2px solid #d8d8d8"});
		$("#second-menu img").attr("src","./images/img2.png");
	});
	
	$("#third-menu").mouseover(function(){
		$("#third-menu").css({"border":"2px solid #4d94ff"});
		$("#third-menu img").attr("src","./images/img3.gif");
	}).mouseout(function(){
		$("#third-menu").css({"border":"2px solid #d8d8d8"});
		$("#third-menu img").attr("src","./images/img3.png");
	});
});

//프로젝트 성공법
$(document).on("click", "#first-menu", function() {
	location.href="introduce.jsp";
});


//공작소 만들기
$(document).on("click", "#second-menu", function() {
	if($("#user-email").val() == "null"){
		alert("로그인 해주세요");
		location.href="login.jsp";
	}
	else
		location.href="createroom.jsp";
});

//공작소 들어가기
$(document).on("click", "#third-menu", function() {
	if($("#user-email").val() == "null"){
		alert("로그인 해주세요");
		location.href="login.jsp";
	}
	else
		location.href="enterroom.jsp";
});