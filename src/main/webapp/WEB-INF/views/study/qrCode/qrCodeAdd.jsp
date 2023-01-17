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
		<h2>쿠폰 등록</h2>
		쿠폰번호, ${vo.qr_code} 를 등록하시겠습니까?
		<button>예</button>
		<button>아니오</button>
		<br/>
		${vo}
	</div>
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>