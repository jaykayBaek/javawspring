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
		let jusorokView = () => {
			let jusorok = '';
			jusorok += '<table class="table">';
			jusorok += '<tr class="table-dark text-white">';
			jusorok += '<th>번호</th>';
			jusorok += '<th>아이디</th>';
			jusorok += '<th>성명</th>';
			jusorok += '<th>메일주소</th>';
			jusorok += '</tr>';
			jusorok += '<c:forEach var="vo" items="${vos}" varStatus="">';
			jusorok += '<tr onclick = "location.href=\'${ctp}/study/mail/mailForm?email=${}\'">';
			jusorok += '<td>${st.count}</td>';
			jusorok += '<td>${vo.mid}</td>';
			jusorok += '<td>${vo.name}</td>';
			jusorok += '<td>${vo.email}</td>';
			jusorok += '<tr>';
			jusorok += '</c:forEach>';
			jusorok += '</table>';
			$(".modal-header #search-cnt").html(${cnt});
			
			$('#searchModal').modal('show');

			$(".modal-body #jusorok-demo").html(jusorok);
		}
	</script>
	<style>
		.modal-content{
			width:750px;
		}
		.modal-body{
			overflow: auto;
		}
	</style>
</head>
<body>
	
	<jsp:include page="/WEB-INF/views/include/nav.jsp" />
	<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
	<div class="container">
		<h2>메일 보내기</h2>
		<p>받는 사람의 메일주소를 정확히 입력하셔야 합니다</p>
		<form name="myform" method="post">
			<table>
				<tr>
					<th>받는사람</th>
					<td>
						<input type="text" name="toMail" id="toMail" placeholder="받는 사람의 메일 주소를 입력하세요" required/>
						<button type="button" onclick="jusorokView(${email})">주소록</button>
					</td>
				</tr>
				<tr>
					<th>메일 제목</th>
					<td>
						<input type="text" name="title" placeholder="메일 제목을 입력하세요" required/>
					</td>
				</tr>
				<tr>
					<th>메일 내용</th>
					<td>
						<textarea name="content" id="" cols="30" rows="10" required>
						</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<button>전송하기</button>
						<button type="button" onclick="location.href='${ctp}/'">뒤로가기</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div class="modal fade" id="searchModal" tabindex="-1" aria-labelledby="searchModal" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="searchModal">검색 결과(건수 : <span id="search-cnt"></span>)</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <div id="jusorok-demo"></div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>