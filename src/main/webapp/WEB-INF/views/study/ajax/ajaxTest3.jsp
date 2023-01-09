<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/include/bs4.jsp"></jsp:include>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ajaxTest3</title>
	<script>
		'use strict';
		let idCheck = () => {
			let mid = $("#mid").val();
			
			if(mid == ""){
				alert("아이디를 입력하세요!");
				$("#mid").focus();
				return false;
			}
			$.ajax({
				type : "post",
				url : "${ctp}/study/ajax/menu/test3_1",
				data : {mid:mid},
				
				success:function(vo){
					console.log(vo);
					let str = '<b>전송결과</b><hr/>';
					
					if(vo == ''){
						str += '<span>찾는 자료가 없습니다.</span>';
					}
					else{
						str += '성명 : <span>'+vo.name+'</span> <br/>';
						str += '이메일 : <span>'+vo.email+'</span> <br/>';
						str += '홈페이지 : <span>'+vo.homePage+'</span> <br/>';
						str += '방문IP : <span>'+vo.hostIp+'</span> <br/>';
					}
					
					$("#demo").html(str);
				},
				error:function(){
					alert('error');
				}
				
			});
		}
		
		let idCheck2 = () => {
			let mid = $("#mid").val();
			
			if(mid == ""){
				alert("아이디를 입력하세요!");
				$("#mid").focus();
				return false;
			}
			
			$.ajax({
				type : "post",
				url : "${ctp}/study/ajax/menu/test3_2",
				data : {mid:mid},
				
				success:function(vos){
 					let str = '<b>전송결과</b><hr/>';
					
					if(vos == ''){
						str += '<span>찾는 자료가 없습니다.</span>';
					}
					else{
						str += '<table>';
						str += '<tr>';
						str += '<th>성명</th><th>이메일</th><th>홈페이지</th><th>방문IP</th>';
						str += '</tr>';
						for(let i=0; i<vos.length; i++){
							str += '<tr>';
							str += '<td>'+vos[i].name+'</td>';
							str += '<td>'+vos[i].homePage+'</td>';
							str += '<td>'+vos[i].email+'</td>';
							str += '<td>'+vos[i].hostIp+'</td></tr>';
						}
					}
					
					$("#demo").html(str);
				},
				error:function(){
					alert('error');
				}
				
			});
		}
		let idCheck3 = () => {
			let search = $("#search").val();
			let condition = $("#condition").val();

			if(search == ""){
				alert("검색어를 입력하세요!");
				$("#search").focus();
				return false;
			}
			$.ajax({
				type:"post",
				url:"${ctp}/study/ajax/menu/test3_3",
				data: {
					search:search,
					condition:condition
				},
				success: function(data){
					let str = '<b>전송결과</b><hr/>';
					
					if(data == ''){
						str += '<span>찾는 자료가 없습니다.</span>';
					}
					else{
						str += '<table>';
						str += '<tr>';
						str += '<th>성명</th><th>홈페이지</th><th>이메일</th><th>방문IP</th>';
						str += '</tr>';
						for(let i=0; i<data.length; i++){
							str += '<tr>';
							str += '<td>'+data[i].name+'</td>';
							str += '<td>'+data[i].homePage+'</td>';
							str += '<td>'+data[i].email+'</td>';
							str += '<td>'+data[i].hostIp+'</td></tr>';
						}
					}
					$("#demo").html(str);
				},
				error: function(){
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
		<h2>ajax를 활용한 성명 검색</h2>
		<form name="myform">
			<p>
				아이디 : 
				<input type="text" name="mid" id="mid" autofocus/>
				<button type="button" onclick="idCheck()">성명일치검색</button>
				<button type="button" onclick="idCheck2()">성명부분일치검색</button>
				<button type="button" onclick="location.href='${ctp}/study/ajax/menu'">돌아가기</button>
			</p>
			<p>
				검색창 : 
				<input type="text" name="search" id="search" autofocus/>
				<select id="condition">
					<option value="name">이름으로 검색</option>
					<option value="email">이메일로 검색</option>
					<option value="homePage">홈페이지로 검색</option>
					<option value="hostIp">아이피로 검색</option>
				</select>
				<button type="button" onclick="idCheck3()">검색</button>
				<button type="button" onclick="location.href='${ctp}/study/ajax/menu'">돌아가기</button>
			</p>
			<p>
				<span id="demo"></span>
			</p>
		</form>
	</div>
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>