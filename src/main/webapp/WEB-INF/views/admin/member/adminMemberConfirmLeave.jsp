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
		let leaveConfirm = (idx) => {
			let res = confirm('색인 '+idx+" 유저를 회원탈퇴 시키시겠습니까?");
			if(res == false){
				return false;
			}
			$.ajax({
				type:"post",
				url:"${ctp}/admin/member/confirm-leave",
				data:{idx:idx},
				success:function(res){
					if(res == '1'){
						alert('삭제 완료되었습니다');
					}
					else{
						alert('삭제 실패');
					}
				},
				error:function(){
					console.log('errror');
				}
			});
		}
	</script>
	<style>
		.leave .list:hover {
			background-color: red;
			cursor: pointer;
		}
	</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/include/nav.jsp" />
	<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
	<div class="container">
		<h2>탈퇴 승인해주세요</h2>
		<table class="leave">
			<tr>
				<th>색인</th>
				<th>아이디</th>
				<th>이름</th>
				<th>닉네임</th>
			</tr>
			<c:forEach var="vo" items="${vos}">
				<tr class="list" onclick="leaveConfirm(${vo.idx})">
					<td>${vo.idx}</td>
					<td>${vo.mid}</td>
					<td>${vo.name}</td>
					<td>${vo.nickName}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>