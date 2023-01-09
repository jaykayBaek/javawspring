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
		'use script';
		let aJaxTest1_1 = (idx) => {
			$.ajax({
				type : "post",
				url : "${ctp}/study/ajax/menu/test1_1",
				data : {idx:idx},
				success : function(res){
					console.log(res);
					$("#demo").html(res);
				},
				error : function(){
					alert('전송오류!');
				}
			});
		}
		let aJaxTest1_2 = (idx) => {
			$.ajax({
				type : "post",
				url : "${ctp}/study/ajax/menu/test1_2",
				data : {idx:idx},
				success : function(res){
					console.log(res);
					$("#demo").html(res);
				},
				error : function(){
					alert('전송오류!');
				}
			});
		}
	</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/include/nav.jsp" />
	<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
	<div class="container">
		<h2>ajax 연습</h2>
		<hr/>
		<p>
			기본(String) : 
			<a href="javascript:aJaxTest1_1(10)">값 전달1</a>
			<a href="javascript:aJaxTest1_2(10)">값 전달2</a>
			: <span id="demo"></span>
		</p>
		<p>
			응용1(Array) : 
			<a href="${ctp}/study/ajax/menu/test2_1">시(도)/구(시,군,동)String배열</a>
			<a href="${ctp}/study/ajax/menu/test2_2">시(도)/구(시,군,동)List</a>
			<a href="${ctp}/study/ajax/menu/test2_3">시(도)/구(시,군,동)Map k, v</a>
			: <span id="demo"></span>
		</p>
		<p>
			응용2(database) : 
			<a href="${ctp}/study/ajax/menu/test3">회원 아이디 검색(DB)</a>
			: <span id="demo"></span>
		</p>
	</div>
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>