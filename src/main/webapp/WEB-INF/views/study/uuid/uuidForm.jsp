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
    	let cnt = 0;
    	let str = "";
    	let uuidCheck = () => {
    		$.ajax({
    			type:"post",
				url:"${ctp}/study/uuid/uuidProcess",
    			success:function(res){
    				cnt++;
    				str += cnt + " : " + res + "<br/>";
    				$("#demo").html(str);
    			},
    			error:function(){
    				console.log('error');
    			}
    		});
    	}
    </script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/include/nav.jsp" />
	<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
	<div class="container">
		<h2>UUID에 대하여</h2>
		<p>
			UUID(Univerally Unique Identifier)란, 네트워크상 고유성이 보장되는 id를 만들기 위한 규약으로
			32자리의 16진수(128bit)로 표현된다.<br/>
			표시 방식? 8-4-4-4-12 자리로 끊어서 표현한다. <br/>
			예 : 77c84a1-..... 식으로
		</p>
		<p>
			<button type="button" onclick="uuidCheck()">UUID 생성</button>
			<button type="button" onclick='location.reload()'>돌아가기</button>
		</p>
		<p>
			<span id="demo"></span>
		</p>
	</div>
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>