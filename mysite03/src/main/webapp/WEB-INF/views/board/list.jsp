<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board/listFind" method="post">
					<input type="text" id="kwd" name="kwd" value=""> 
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="size" value="${fn:length(list) }" />
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							<td>${size-status.index }</td>
							<c:choose>
								<c:when test="${vo.depth > 0 }">
									<td class="left" style="text-align:left; padding-left:${20*vo.depth }px">
										<img src="${pageContext.request.contextPath }/assets/images/reply.png">
										<a href="${pageContext.request.contextPath }/board/view/${vo.no }">${vo.title }</a>
									</td>
								</c:when>
								<c:otherwise>
									<td class="left" style="text-align:left">
										<a href="${pageContext.request.contextPath }/board/view/${vo.no }">${vo.title }</a>
									</td>
								</c:otherwise>
							</c:choose>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<c:choose>
								<c:when test="${empty authUser }">
								</c:when>
								<c:when test="${authUser.no eq vo.userNo }">
									<td><a href="${pageContext.request.contextPath }/board/delete/${vo.no }/${vo.userNo }" class="del" style='background-image:url("${pageContext.servletContext.contextPath }/assets/images/recycle.png")'>삭제</a></td>
								</c:when>
							</c:choose>
						</tr>
					</c:forEach>
				</table>

				<!-- pager 추가 -->
				<c:set var="count" value="${count }" />
				<div class="pager">
					<ul>
						<li><a href="${pageContext.request.contextPath }/board/list/1">◀</a></li>
						<c:forEach begin="1" end="${count }" step="1" varStatus="status">
							<li><a href="${pageContext.request.contextPath }/board/list/${status.index }">${status.index }</a></li>
						</c:forEach>
						<li><a href="${pageContext.request.contextPath }/board/list/${ count}">▶</a></li>
					</ul>
				</div>

				<c:choose>
					<c:when test="${empty authUser }">
					</c:when>
					<c:otherwise>
						<div class="bottom">
							<a href="${pageContext.request.contextPath }/board/write" id="new-book">글쓰기</a>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>