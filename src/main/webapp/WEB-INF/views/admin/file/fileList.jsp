<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="/WEB-INF/views/include/bs4.jsp"></jsp:include>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>title</title>
	<script>
		'use strict';
	    $(function(){
	    	$("#selectAll").on("click", function(){
	    		if($("#selectAll").prop("checked")) {
		    		$(".select").prop("checked", true);
	    		}
	    		else {
		    		$(".select").prop("checked", false);
	    		}
	    	});
	    });
    	let data = [];
	    let deleteSelectImg = () => {
	    	$("input[name=select]:checked").each(function(){
	    		data.push($(this).val());
	    	});
	    	console.log(data);
	    	$.ajax({
	    		type:"post",
	    		data:{data:data},
	    		url:"${ctp}/admin/file/list",
	    		success:function(res){
	    			if(res == "1"){
	    				alert('파일 삭제 성공');
	    			}
	    			else{
	    				alert('이거 뜨면 야근이욤');
	    			}
	    			
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
		<h2>Server file list</h2>
		<input type="checkbox" name="selectAll" id="selectAll"/> 전체선택	
		<button type="button" onclick="deleteSelectImg()">선택 삭제</button>	
		
		<p>서버의 파일 경로 : ${ctp}/data/ckeditor/*</p>
		<c:forEach var="file" items="${files}" varStatus="st">
			<c:if test="${fn:contains(file, '.jpg') || fn:contains(file, '.gif') || fn:contains(file, '.png')}">
				<p>
					<input type="checkbox" name="select" class="select" id="select${st.count}" value="${file}"/>
					<img src="${ctp}/data/ckeditor/${file}" alt="이미지" width="150px"/>
				</p>
			</c:if>
		</c:forEach>
	</div>
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>