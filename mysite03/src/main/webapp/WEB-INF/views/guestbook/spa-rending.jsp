<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js" type="text/javascript"></script>
<script>
	/* guestbook application based on jQuery */
	/* 
	과제 ex1: 리스트
	- no 기준의 리스트를 부분적(3개씩) 가져와서 리스트 렌더링(append)
	- 버튼 이벤트 구현 -> 스크롤 이벤트 바꾼다.
	- no 기준으로 동적 쿼리를 레포지토리에 구현한다.
	- 렌더링 참고: /ch08/test/gb/ex1
	
	과제 ex2: 메세지 등록(add)
	- validation
	- message 기반 dialog plugin 사용법
	- form submit 막기
	- 데이터 하나를 렌더링(preappend)
	- 참고: /ch08/test/gb/ex2
	
	과제 ex3: 메시지 삭제(delete)
	- a tag 기본동작 막기
	- live event
	- form 기반 dialog plugin 사용법
	- 응답에 대해 해당 li
	- 비밀번호가 틀린 경우(삭제실패, no=0) 사용자한테 알려주는 UI
	- 삭제가 성공한 경우(no > 0), data-no=10인 li element를 삭제
	- 참고: /ch08/test/gb/ex3
	*/
	
	var listEJS = new EJS({
		url: "${pageContext.request.contextPath }/assets/js/ejs/list-template.ejs"
	});
	
	var listItemEJS = new EJS({
		url: "${pageContext.request.contextPath }/assets/js/ejs/listitem-template.ejs"
	});
	
	var fetch = function() {
		$.ajax({
			url: "${pageContext.request.contextPath }/guestbook/api/list",
			dataType: "json",
			type: "get",
			success: function(response) {
				/* response.data.forEach(function(vo) {
					html = "<li data-no='" + vo.no + "'>" + 
					"<strong>" + vo.name + "</strong>" +
					"<p>" + vo.message + "</p>" +
					"<strong></strong>" + 
					"<a href='' data-no='" + vo.no + "'>삭제</a>" + 
					"</li>";
					
					$("#list-guestbook").append(html);
				}); */
				
				var html = listEJS.render(response);
				$("#list-guestbook").append(html);
			}
		});
	}
	
	// alert dialog
	const valid = function(msg) {
		$("#dialog-message").dialog({
			title: msg,
			modal: true,
			buttons: {
				"확인": function() {
					$(this).dialog("close");
				}
			}
		});
	}
	
	$(function() {		
		// 최초 데이터 가져오기
		fetch();
		
		$("#add-form").submit(function(event) {
			event.preventDefault();
			
			vo = {};
			
			vo.name = $("#input-name").val();
			vo.password = $("#input-password").val();
			vo.message = $("#tx-content").val();
			
			// validation
			if(vo.name == "" || vo.password == "" || vo.message == "") {
				if(vo.name == "") {
					$("#dialog-message").text("이름이 비어있습니다.");
					$("#input-name").focus();
				} else if(vo.password == "") {
					$("#dialog-message").text("비밀번호가 비어있습니다.");
					$("#input-password").focus();
				} else if(vo.message == "") {
					$("#dialog-message").text("내용이 비어있습니다.");
					$("#tx-content").focus();
				}
				valid("알림");
				return;
			}
			
			// 데이터 등록
			$.ajax({
				url: "${pageContext.request.contextPath }/guestbook/api/add",
				dataType: "json",
				type: "post",
				contentType: "application/json",
				data: JSON.stringify(vo),
				success: function(response) {
					// var vo = response.data;
					
					/* html = "<li data-no='" + vo.no + "'>" + 
							"<strong>" + vo.name + "</strong>" +
							"<p>" + vo.message + "</p>" +
							"<strong></strong>" + 
							"<a href='' data-no='" + vo.no + "'>삭제</a>" + 
							"</li>"; */
					var html = listItemEJS.render(response.data);
							
					$("#list-guestbook").prepend(html);
					
					$("#add-form")[0].reset();
				}
			});
		});
		
		// live event: 존재하지 않는 element의 이벤트 핸들러를 미리 등록
		// delegation(위임) -> document
		$(document).on("click", "#list-guestbook li a", function(event){
			event.preventDefault();
			let no = $(this).data("no");
			$("#hidden-no").val(no);
			
			deleteDialog.dialog("open");
		});
		
		// 삭제 다이얼로그 만들기
		const deleteDialog = $("#dialog-delete-form").dialog({
			autoOpen: false,
			width: 300,
			height: 220,
			modal: true,
			buttons: {
				"삭제": function(){
					const no = $("#hidden-no").val();
					const password = $("#password-delete").val();
					$.ajax({
						url: "${pageContext.request.contextPath }/guestbook/api/delete/"+no,
						dataType: "json",
						type: "post",
						data: "password=" + password,
						success: function(response){
							if(response.data == -1){
								// 비밀번호가 틀린경우
								$(".validateTips.error").show();
								return;
							}						
							
							$("#list-guestbook li[data-no=" + response.data + "]").remove();
							deleteDialog.dialog('close');
						}
					});					
				},
				"취소": function(){
					$(this).dialog("close");
				}
			},
			close: function(){
				//1. password 비우기
				$("#password-delete").val('');
				//2. no 비우기
				$("#hidden-no").val('');
				//3. error message 숨기기
				$(".validateTips.error").hide();
			}
		});
	});
	
	var lastScrollTop = 0;
	var easeEffect = 'easeInQuint';
	
	// 1. 스크롤 이벤트 발생
	$(window).scroll(function(){ // ① 스크롤 이벤트 최초 발생
		
		var currentScrollTop = $(window).scrollTop();
		
		/*  
			=================	다운 스크롤인 상태	================
		*/
		if( currentScrollTop - lastScrollTop > 0 ){
			// down-scroll : 현재 게시글 다음의 글을 불러온다.
			console.log("down-scroll");
			
			// 2. 현재 스크롤의 top 좌표가  > (게시글을 불러온 화면 height - 윈도우창의 height) 되는 순간
			if ($(window).scrollTop() >= ($(document).height() - $(window).height()) ){ //② 현재스크롤의 위치가 화면의 보이는 위치보다 크다면
	            
				// 3. id가 list-guestbook인 것의 요소 중 마지막인 요소를 선택한 다음 그것의 data-no속성 값을 받아온다.
				//		즉, 현재 뿌려진 게시글의 마지막 no값을 읽어오는 것이다.( 이 다음의 게시글들을 가져오기 위해 필요한 데이터이다.)
				var lastNo = $("#list-guestbook li:last").attr("data-no");
				
				// 4. ajax를 이용하여 현재 뿌려진 게시글의 마지막 no를 서버로 보내어 그 다음 5개의 게시물 데이터를 받아온다. 
				$.ajax({
					type : 'post',	// 요청 method 방식 
					url : '${pageContext.request.contextPath }/guestbook/api/listScroll',// 요청할 서버의 url
					headers : { 
						"Content-Type" : "application/json",
						"X-HTTP-Method-Override" : "POST"
					},
					dataType : 'json', // 서버로부터 되돌려받는 데이터의 타입을 명시하는 것이다.
					data : JSON.stringify({ // 서버로 보낼 데이터 명시 
						no : lastNo
					}),
					success : function(response){// ajax 가 성공했을시에 수행될 function이다. 이 function의 파라미터는 서버로 부터 return받은 데이터이다.
						
						var html = "";
						
						// 5. 받아온 데이터가 null이 아닌 경우에 DOM handling을 해준다.
						if(response.data.length != 0){
							//6. 서버로부터 받아온 response가 list이므로 이 각각의 원소에 접근하려면 forEach문을 사용한다.
							/* response.data.forEach(function(vo) {
								// 7. 새로운 데이터를 갖고 html코드형태의 문자열을 만들어준다.
									html +=	"<li data-no='" + vo.no + "'>" + 
									"<strong>" + vo.name + "</strong>" +
									"<p>" + vo.message + "</p>" +
									"<strong></strong>" + 
									"<a href='' data-no='" + vo.no + "'>삭제</a>" + 
									"</li>";
								 		
							});// forEach */
							html = listEJS.render(response);
							// 8. 위에서 만든 html을 뿌려준다.
							$("#list-guestbook").append(html);
						 		
						}// if : response==null
						else{ // 9. 만약 서버로 부터 받아온 데이터가 없으면
							alert("더 불러올 데이터가 없습니다.");
						}// else
		
					}// success
				});// ajax
				// 여기서 id가 listToChange인 것중 가장 처음인 것을 찾아서 그 위치로 이동하자.
				var position = $("#list-guestbook li:first").offset();// 위치 값
				
				// 이동  위로 부터 position.top px 위치로 스크롤 하는 것이다. 그걸 500ms 동안 애니메이션이 이루어짐.
				$('html,body').stop().animate({scrollTop : position.top }, 500, easeEffect);
	
	        }//if : 현재 스크롤의 top 좌표가  > (게시글을 불러온 화면 height - 윈도우창의 height) 되는 순간
			
			// lastScrollTop을 현재 currentScrollTop으로 갱신해준다.
			lastScrollTop = currentScrollTop;
		}// 다운스크롤인 상태
	});// scroll event
	
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">		
				</ul>
				<div style="margin:20px 0 0 0">
				</div>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>