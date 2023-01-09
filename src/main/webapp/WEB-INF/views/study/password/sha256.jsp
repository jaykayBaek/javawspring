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
		'use strict';
		let sha256Check = () => {
			let pwd = $("#pwd").val();
			$.ajax({
				type: "post",
				url : "${ctp}/study/password/sha256",
				data: {pwd:pwd},
				success:function(res){
					$("#demo").append(res);
				},
				error:function(){
					alert('error');
				}
			});
		}
	</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/include/nav.jsp" />
	<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
	<div class="container">
		<h2>SHA-256</h2>
		
		<p>
			SHA-256은 SHA(Secure Hash Algorithm) 알고리즘의 한 종류로서 256비트로 구성되며 64자리 문자열로 구성된다.
			SHA-256은 단방향 암호화 방식이기에 복호화가 불가능하고, 그대신 속도가 빠르다는 장점이 있다.
		</p>
		<p>
			비밀번호<input type="password" name="pwd" id="pwd" autofocus/>
			<button type="button" onclick="sha256Check()">sha256암호화</button>
			<button type="button" onclick="location.reload()">다시하기</button>
		</p>
		<hr/>
		<h2>출력결과?</h2>
		<p>
			<span id="demo"></span>
		</p>		
	</div>
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>