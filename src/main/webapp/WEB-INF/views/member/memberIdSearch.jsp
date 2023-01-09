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
		let idSearch = () => {
			let name = $('#name').val();
			let toMail = $('#toMail').val();
			$.ajax({
				type:"post",
				data:{
					name:name,
					toMail:toMail},
				url : "${ctp}/member/find/id",
				success:function(data){
					if(data==""){
						$('#id-demo').html('찾으시는 계정의 정보가 없습니다');
						$('#id-modal').modal('show');
						return false;
					}
					$('#id-demo').html(
						'<div>찾은 아이디는? </div> '+data+'<div>보안상 아이디의 일부를 알려드렸습니다. 세부 아이디는 이메일을 통해 발급해드렸습니다.</div>');
					$('#id-modal').modal('show');
				},
				error:function(){
					
				}
			});
		}
	</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/include/nav.jsp" />
	<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
	<div class="container">
		<h2>아이디 찾기</h2>
		<p>
			성명과 이메일 주소를 입력해주세요.
		</p>
		<form name="myform" method="post">
			<table>
				<tr>
					<th>이름</th>
					<td>
						<input type="text" name="name" id="name"/>
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
						<button type="button" onclick="idSearch()">아이디 찾기</button>
						<button type="button" onclick="location.href='${ctp}/member/login'">돌아가기</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div class="modal fade" id="id-modal" tabindex="-1" aria-labelledby="id-modal-label" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="id-modal-label">아이디 찾기</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      	<div id="id-demo"></div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary">Save changes</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>