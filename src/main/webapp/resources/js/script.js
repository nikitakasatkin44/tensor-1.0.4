$(document).ready(function(){
	$(".navbar-collapse li").click(function(){
		$(".navbar-collapse li").removeClass('active');
		$(this).addClass('active');
	});
});

