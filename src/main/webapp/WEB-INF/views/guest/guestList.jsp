<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>guList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"></jsp:include>
  <style>
    th {
      text-align: center;
      background-color: #ccc;
    }
  </style>
  <script>
    'use strict';
    function delCheck(idx) {
    	let ans = confirm("정말로 삭제하시겠습니까?");
    	if(ans) location.href="${ctp}/guest/delete?idx="+idx;
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" /><p><br/></p>
<div class="container">
  <h2 class="text-center">방 명 록 리 스 트</h2>
  <br/>
  <table class="table table-borderless mb-0">
		<tr>
		  <td>
		    <c:if test="${sLevel == '0'}"><a href="${ctp}/member/logout/admin" class="btn btn-sm btn-secondary">관리자 로그아웃</a></c:if>
		  </td>
		  <td style="text-align:right;"><a href="${ctp}/guest/input" class="btn btn-sm btn-secondary">글쓰기</a></td>
		</tr>
  </table>
  <table class="table table-borderless m-0 p0">
		<tr>
		  <td class="text-right">
		  	<!-- 첫페이지 / 이전페이지 /(현페이지번호/총페이지수)/ 다음페이지 / 마지막페이지 -->
		  	<c:if test="${pageVo.pag > 1}">
			  	<span class="badge badge-pill badge-primary">
				  	<a href="${ctp}/guest/list?page=1">
						<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="white" class="bi bi-chevron-double-left" viewBox="0 0 16 16">
						  <path fill-rule="evenodd" d="M8.354 1.646a.5.5 0 0 1 0 .708L2.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
						  <path fill-rule="evenodd" d="M12.354 1.646a.5.5 0 0 1 0 .708L6.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
						</svg>
				  	</a>
			  	</span>
			  	<span class="badge badge-pill badge-warning">
			  		<a href="${ctp}/guest/list?page=${page-1}">
						<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="white\" class="bi bi-chevron-left" viewBox="0 0 16 16">
						  <path fill-rule="evenodd" d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
						</svg>
					</a>
			  	</span>
		  	</c:if>
			 ${pageVo.pag}/${pageVo.totPage}
		  	<c:if test="${pageVo.pag < pageVo.totPage}">
			  	<span class="badge badge-pill badge-warning">
				  	<a href="${ctp}/guest/list?page=${pageVo.pag+1}">
						<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="white" class="bi bi-chevron-right" viewBox="0 0 16 16">
						  <path fill-rule="evenodd" d="M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708z"/>
						</svg>
				  	</a>
			  	</span>
			  	<span class="badge badge-pill badge-primary">
				  	<a href="${ctp}/guest/list?page=${totPage}">
						<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="white" class="bi bi-chevron-double-right" viewBox="0 0 16 16">
						  <path fill-rule="evenodd" d="M3.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L9.293 8 3.646 2.354a.5.5 0 0 1 0-.708z"/>
						  <path fill-rule="evenodd" d="M7.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L13.293 8 7.646 2.354a.5.5 0 0 1 0-.708z"/>
						</svg>
				  	</a>
			  	</span>
		  	</c:if>
		  </td>
		</tr>
  </table>
  <c:set var="curScrStartNo" value="${curScrStartNo}"/>
  <c:forEach var="vo" items="${vos}" varStatus="st">
	  <table class="table table-borderless mb-0">
			<tr>
			  <td>방문번호 : ${vo.idx}
			    <c:if test="${sLevel == '0'}"><a href="javascript:delCheck(${vo.idx})" class="btn btn-sm btn-danger">삭제</a></c:if>
			  </td>
			  <td style="text-align:right;">방문IP : ${vo.hostIp}</td>
			</tr>
	  </table>
	  <table class="table table-bordered">
			<tr>
			  <th style="width:20%;">성명</th>
			  <td style="width:25%;">${vo.name}</td>
			  <th style="width:20%;">방문일자</th>
			  <td style="width:35%;">${fn:substring(vo.visitDate,0,19)}</td>
			</tr>
			<tr>
			  <th>전자우편</th>
			  <td colspan="3">
			    <c:if test="${empty vo.email || fn:length(vo.email)<=4 || fn:indexOf(vo.email,'@')==-1 || fn:indexOf(vo.email,'.')==-1}">- 없음 -</c:if>
			    <c:if test="${!empty vo.email && fn:length(vo.email)>4 && fn:indexOf(vo.email,'@')!=-1 && fn:indexOf(vo.email,'.')!=-1}">${vo.email}</c:if>
			  </td>
			</tr>
			<tr>
			  <th>홈페이지</th>
			  <td colspan="3">
			    <c:if test="${fn:length(vo.homePage) <= 8}">- 없음 -</c:if>
			    <c:if test="${fn:length(vo.homePage) > 8}"><a href="${vo.homePage}" target="_blank">${vo.homePage}</a></c:if>
			  </td>
			</tr>
			<tr>
			  <th>방문소감</th>
			  <td colspan="3">${fn:replace(vo.content, newLine, '<br/>')}</td>
			</tr>
	  </table>
	  <br/>
	  <c:set var="curScrStartNo" value="${curScrStartNo - 1}"/>
	  </c:forEach>
		  <!-- 첫페이지 / 이전블럭 / 1 2 3 / 다음블럭 / 마지막페이지 -->
  <div class="text-center">
    <ul class="pagination justify-content-center">
	    <c:if test="${pageVo.pag <= 1}">
	      <li class="page-item disabled"><a class="page-link" href="${ctp}/guest/list?page=1">첫페이지</a></li>
	    </c:if>
	    <c:if test="${pageVo.curBlock <= 0}">
	      <li class="page-item disabled"><a class="page-link" href="${ctp}/guest/list?page=${(pageVo.curBlock-1)*pageVo.blockSize + 1}">이전블록</a></li>
	    </c:if>
	    <c:if test="${pageVo.pag > 1}">
	      <li class="page-item"><a class="page-link" href="${ctp}/guest/list?page=1">첫페이지</a></li>
	    </c:if>
	    <c:if test="${pageVo.curBlock > 0}">
	      <li class="page-item"><a class="page-link" href="${ctp}/guest/list?page=${(pageVo.curBlock-1)*pageVo.blockSize + 1}">이전블록</a></li>
	    </c:if>
	    
	    
	    
	    <c:forEach var="i" begin="${(pageVo.curBlock)*pageVo.blockSize + 1}" end="${(pageVo.curBlock)*pageVo.blockSize + pageVo.blockSize}" varStatus="st">
	      <c:if test="${i <= pageVo.totPage && i == pageVo.pag}">
	    		<li class="page-item active"><a class="page-link" href="${ctp}/guest/list?page=${i}">${i}</a></li>
	    	</c:if>
	      <c:if test="${i <= pageVo.totPage && i != pageVo.pag}">
	    		<li class="page-item"><a class="page-link" href="${ctp}/guest/list?page=${i}">${i}</a></li>
	    	</c:if>
	    </c:forEach>
	    
	    
	    <c:if test="${pageVo.curBlock >= pageVo.lastBlock}">
	      <li class="page-item disabled"><a class="page-link" href="${ctp}/guest/list?page=${(pageVo.curBlock+1)*pageVo.blockSize + 1}">다음블록</a></li>
	    </c:if>
	    <c:if test="${pageVo.pag >= pageVo.totPage}">
	      <li class="page-item disabled"><a class="page-link" href="${ctp}/guest/list?page=${pageVo.totPage}">마지막페이지</a></li>
	    </c:if>
	    
	    <c:if test="${pageVo.curBlock < pageVo.lastBlock}">
	      <li class="page-item"><a class="page-link" href="${ctp}/guest/list?page=${(pageVo.curBlock+1)*pageVo.blockSize + 1}">다음블록</a></li>
	    </c:if>
	    <c:if test="${pageVo.pag < pageVo.totPage}">
	      <li class="page-item"><a class="page-link" href="${ctp}/guest/list?page=${pageVo.totPage}">마지막페이지</a></li>
	    </c:if>
    </ul>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>