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
		$(function() {
			$("#state").on("change", function(){
				let state = $(this).val();
				
				if(state.trim() == ""){
					alert("지역을 선택하세요!");
					return false;
				}
				$.ajax({
					type:"post",
					url:"${ctp}/study/ajax/menu/test2_1",
					data : {state:state},
					success:function(res){
						let str = '<option value="">도시선택</option>';
						for(let i=0; i<res.length; i++){
							if(res[i]==null) break;
							str += '<option>'+res[i]+'</option>';
						}
						$("#city").html(str);
					},
					error : function() {
						alert('에러');
					}
				});
			});
		});
		let fCheck = () => {
			let state = $("#state").val();
			let city = $("#city").val();
			
			if(state.trim() == "" || city == ""){
				alert('지역을 선택하세요');
				return false;
			}
			let str = "선택하신 지역은?" + state + " / " + city + " &nbsp; "
			str += "<button type='button' onclick='location.reload()'>다시검색</button>";
			$('#demo').html(str);
		}
	</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/include/nav.jsp" />
	<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
	<div class="container">
		<h2>AJax를 활용한 값의 전달2(배열처리)</h2>
		<hr>
		<h3>도시를 선택하세요</h3>
		<select name="state" id="state">
			<option value="">지역 선택</option>
			<option value="서울">서울</option>
			<option value="경기">경기</option>
			<option value="충북">충북</option>
			<option value="충남">충남</option>
		</select>
		<select name="city" id="city">
			<option value="">도시선택</option>
		</select>
		<button type="button" onclick="fCheck()">선택</button>
		<button type="button" onclick="location.href='${ctp}/study/ajax/menu'">돌아가기</button>
		<hr>
		<div id="demo"></div>
	</div>
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>