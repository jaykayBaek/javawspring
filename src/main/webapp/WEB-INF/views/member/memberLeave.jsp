<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/include/bs4.jsp"></jsp:include>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>title</title>
    <style>
    </style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/include/nav.jsp" />
	<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
	<div class="container">
		<h2>회원 탈퇴창</h2>
		탈퇴 시 포인트 전부 삭제
		정말 회원탈퇴 하시겠습니까?
		<button type="button" onclick="location.href='${ctp}/member/leaveOk'">예</button>
		<button type="button" onclick="location.href='${ctp}/member/list'">돌아가기</button>
	</div>
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>