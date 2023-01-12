<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>boList.jsp</title>
  
  <jsp:include page="/WEB-INF/views//include/bs4.jsp"/>
  <script>
    'use strict';
    function pageCheck() {
    	let pageSize = document.getElementById("pageSize").value;
    	location.href = "${ctp}/board/list?pag=${pageVo.pag}&pageSize="+pageSize;
    }
    
    function searchCheck() {
    	let searchString = $("#searchString").val();
    	
    	if(searchString.trim() == "") {
    		alert("찾고자 하는 검색어를 입력하세요!");
    		searchForm.searchString.focus();
    	}
    	else {
    		searchForm.submit();
    	}
    }
  </script>

</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center">게 시 판 리 스 트</h2>
  <br/>
  <table class="table table-borderless">
    <tr>
      <td class="text-left p-0">
        <c:if test="${sLevel != 4}"><a href="${ctp}/board/input?pag=${pageVo.pag}&pageSize=${pageVo.pageSize}" class="btn btn-secondary btn-sm">글쓰기</a></c:if>
      </td>
      <td class="text-right p-0">
        <select name="pageSize" id="pageSize" onchange="pageCheck()">
          <option value="5"  ${pageVo.pageSize==5  ? 'selected' : ''}>5건</option>
          <option value="10" ${pageVo.pageSize==10 ? 'selected' : ''}>10건</option>
          <option value="15" ${pageVo.pageSize==15 ? 'selected' : ''}>15건</option>
          <option value="20" ${pageVo.pageSize==20 ? 'selected' : ''}>20건</option>
        </select>
      </td>
    </tr>
  </table>
  <table class="table table-hover text-center">
    <tr class="table-dark text-dark">
      <th>글번호</th>
      <th>글제목</th>
      <th>글쓴이</th>
      <th>글쓴날짜</th>
      <th>조회수</th>
      <th>좋아요</th>
    </tr>
  	<c:set var="curScrStartNo" value="${pageVo.curScrStartNo}"/>
    <c:forEach var="vo" items="${vos}">
    	<tr>
    	  <td>${curScrStartNo}</td>
    	  <td class="text-left">
    	  <a href="${ctp}/board/content?idx=${vo.idx}&pageSize=${pageVo.pageSize}&pag=${pageVo.pag}">${vo.title}</a>
    	  <c:if test="${vo.replyCount!=0}">
	    	  <span class="font-weight-bold">[${vo.replyCount}]</span>
    	  </c:if>
    	  <c:if test="${vo.hour_diff <= 24}"><img src="${ctp}/images/new.gif" style="width:50px; height:20px" /></c:if>
    	  </td>
    	  
    	  <td>${vo.nickName}</td>
    	  <td>
    	    <c:if test="${vo.hour_diff > 24}">${fn:substring(vo.WDate,0,10)}</c:if>
    	    <c:if test="${vo.hour_diff < 24}">
    	      ${vo.day_diff > 0 ? fn:substring(vo.WDate,0,16) : fn:substring(vo.WDate,11,19)}
    	    </c:if>
    	  </td>
    	  <td>${vo.readNum}</td>
    	  <td>${vo.good}</td>
    	</tr>
    	<c:set var="curScrStartNo" value="${curScrStartNo-1}"/>
    </c:forEach>
    <tr><td colspan="6" class="m-0 p-0"></td></tr>
  </table>
</div>
		  <!-- 첫페이지 / 이전블럭 / 1 2 3 / 다음블럭 / 마지막페이지 -->
  <div class="text-center">
    <ul class="pagination justify-content-center">
	    <c:if test="${pageVo.pag <= 1}">
	      <li class="page-item disabled"><a class="page-link" href="${ctp}/board/list?pag=1">첫페이지</a></li>
	    </c:if>
	    <c:if test="${pageVo.curBlock <= 0}">
	      <li class="page-item disabled"><a class="page-link" href="${ctp}/board/list?pag=${(pageVo.curBlock-1)*pageVo.blockSize + 1}">이전블록</a></li>
	    </c:if>
	    <c:if test="${pageVo.pag > 1}">
	      <li class="page-item"><a class="page-link" href="${ctp}/board/list?pag=1">첫페이지</a></li>
	    </c:if>
	    <c:if test="${pageVo.curBlock > 0}">
	      <li class="page-item"><a class="page-link" href="${ctp}/board/list?pag=${(pageVo.curBlock-1)*pageVo.blockSize + 1}">이전블록</a></li>
	    </c:if>
	    
	    <c:forEach var="i" begin="${(pageVo.curBlock)*pageVo.blockSize + 1}" end="${(pageVo.curBlock)*pageVo.blockSize + pageVo.blockSize}" varStatus="st">
	      <c:if test="${i <= pageVo.totPage && i == pageVo.pag}">
	    		<li class="page-item active"><a class="page-link" href="${ctp}/board/list?pag=${i}">${i}</a></li>
	    	</c:if>
	      <c:if test="${i <= pageVo.totPage && i != pageVo.pag}">
	    		<li class="page-item"><a class="page-link" href="${ctp}/board/list?pag=${i}">${i}</a></li>
	    	</c:if>
	    </c:forEach>
	    
	    
	    <c:if test="${pageVo.curBlock >= pageVo.lastBlock}">
	      <li class="page-item disabled"><a class="page-link" href="${ctp}/board/list?pag=${(pageVo.curBlock+1)*pageVo.blockSize + 1}">다음블록</a></li>
	    </c:if>
	    <c:if test="${pageVo.pag >= pageVo.totPage}">
	      <li class="page-item disabled"><a class="page-link" href="${ctp}/board/list?pag=${pageVo.totPage}">마지막페이지</a></li>
	    </c:if>
	    
	    <c:if test="${pageVo.curBlock < pageVo.lastBlock}">
	      <li class="page-item"><a class="page-link" href="${ctp}/board/list?pag=${(pageVo.curBlock+1)*pageVo.blockSize + 1}">다음블록</a></li>
	    </c:if>
	    <c:if test="${pageVo.pag < pageVo.totPage}">
	      <li class="page-item"><a class="page-link" href="${ctp}/board/list?pag=${pageVo.totPage}">마지막페이지</a></li>
	    </c:if>
    </ul>
  </div>
<!-- 검색기 처리 시작  -->
<div class="container text-center">
  <form name="searchForm" method="post">
    <b>검색 : </b>
    <select name="search">
      <option value="title">글제목</option>
      <option value="nickName">글쓴이</option>
      <option value="content">글내용</option>
    </select>
    <input type="text" name="searchString" id="searchString"/>
    <input type="button" value="검색" onclick="searchCheck()" class="btn btn-secondary btn-sm"/>
    <input type="hidden" name="pag" value="${pag}"/>
    <input type="hidden" name="pageSize" value="${pageSize}"/>
  </form>
</div>
<!-- 검색기 처리 끝  -->
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>