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