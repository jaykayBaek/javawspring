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
		let ariaCheck = () => {
			let pwd = $("#pwd").val();
			$.ajax({
				type: "post",
				url : "${ctp}/study/password/aria",
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
		<h2>ARIA 암호화</h2>
		
		<p>
			ARIA 암호화 방식은 경량환경 및 하드웨어 구현을 위해 최적화된, Involution SPN 구조를 갖는 범용블록 암호화 알고리즘입니다.
			ARIA가 사용하는 연산은 대부분 XOR와 같은 단순한 바이트 단위 연산으로, 블록 크기는 128bit이다.<br/>
			ARIA는 Academy(학계), Research Institute(연구소), Agency(정부기관)의 첫글자를 본따 만들었다.
			ARIA 개발에 참여한 사람들은 aria를 뚫을 수 있다,
			aria복호화가 가능하다
		</p>
		<p>
			비밀번호<input type="password" name="pwd" id="pwd" autofocus/>
			<button type="button" onclick="ariaCheck()">aria암호화</button>
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