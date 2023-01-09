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
	<script>
	</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/include/nav.jsp" />
	<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
	<div class="container">
		<h2>비밀번호 찾기</h2>
		<p>
			아이디와 이메일 주소를 입력 후 메일로 임시비밀번호가 발급된다.
		</p>
		<form name="myform" method="post">
			<table>
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" name="mid" id="mid"/>
					</td>
				</tr>
				<tr>
					<th>메일주소</th>
					<td>
						<input type="text" name="toMail" id="toMail"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="text-center">
						<button>임시비밀번호 발급</button>
						<button type="button" onclick="location.href='${ctp}/member/login'">돌아가기</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>