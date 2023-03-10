<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>admin.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <link rel="stylesheet" href="${ctp}/css/font.css">
  
  <style>
  	body{
  		background-color: #aaa;
  		color:#fff;
  	}
  	
  </style>
</head>
<body>
<p><br/></p>
<div class="container-fluid admin-left text-center">
  <h5>관리자메뉴</h5>
  <hr/>
  <p>
    <a href="${ctp}/" target="_top">홈으로</a>
  </p>
  <hr/>
  <p>
    <a href="#">방명록리스트</a>
  </p>
  <hr/>
  <p>
    <a href="${ctp}/admin/member/list" target="adminContent">회원리스트</a>
  </p>
  <p>
    <a href="${ctp}/admin/member/confirm-leave" target="adminContent">회원탈퇴승인처리</a>
  </p>
  <p>
  	<b>임시파일</b>
  </p>
  <p>
    <a href="${ctp}/admin/file/list" target="adminContent">ckeditor 파일 제거</a>
  </p>
</div>
<p><br/></p>
</body>
</html>