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
    <style>
    </style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/include/nav.jsp" />
	<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
	
	<div class="container" style="padding:30px">
	  <form name="myform" method="post" action="${ctp}/memUpdateOk.mem" class="was-validated">
	    <h2>회 원 정 보 수 정</h2>
	    <br/>
	    <div class="form-group">
	      아이디 : ${sMid}
	    </div>
	    <div class="form-group">
	      <label for="nickName">닉네임 : 
	      <input type="button" value="닉네임 중복체크" id="nickNameBtn" class="btn btn-secondary btn-sm" onclick="nickCheck()"/></label>
	      <input type="text" value="${vo.nickName}" class="form-control" id="nickName" placeholder="별명을 입력하세요." name="nickName" required />
	    </div>
	    <div class="form-group">
	      <label for="name">성명 :</label>
	      <input type="text" value="${vo.name}" class="form-control" id="name" placeholder="성명을 입력하세요." name="name" required />
	    </div>
	    <div class="form-group">
	      <label for="email1">Email address:</label>
					<div class="input-group mb-3">
					  <input type="text" value="${email1}" class="form-control" placeholder="Email을 입력하세요." id="email1" name="email1" required />
					  <div class="input-group-append">
					    <select name="email2" class="custom-select">
						    <option value="naver.com"   <c:if test="${email2=='naver.com'}">selected</c:if>>naver.com</option>
						    <option value="hanmail.net" <c:if test="${email2=='hanmail.net'}">selected</c:if>>hanmail.net</option>
						    <option value="hotmail.com" <c:if test="${email2=='hotmail.com'}">selected</c:if>>hotmail.com</option>
						    <option value="gmail.com"   <c:if test="${email2=='gmail.com'}">selected</c:if>>gmail.com</option>
						    <option value="nate.com"    <c:if test="${email2=='nate.com'}">selected</c:if>>nate.com</option>
						    <option value="yahoo.com"   <c:if test="${email2=='yahoo.com'}">selected</c:if>>yahoo.com</option>
						  </select>
					  </div>
					</div>
		  </div>
	    <div class="form-group">
	      <div class="form-check-inline">
	        <span class="input-group-text">성별 :</span> &nbsp; &nbsp;
				  <label class="form-check-label">
				    <input type="radio" class="form-check-input" name="gender" value="남자" <c:if test="${vo.gender=='남자'}">checked</c:if>>남자
				  </label>
				</div>
				<div class="form-check-inline">
				  <label class="form-check-label">
				    <input type="radio" class="form-check-input" name="gender" value="여자" <c:if test="${vo.gender=='여자'}">checked</c:if>>여자
				  </label>
				</div>
	    </div>
	    <div class="form-group">
	      <label for="birthday">생일</label>
	      <input type="date" name="birthday" value="${birthday}" class="form-control"/>
	    </div>
	    <div class="form-group">
	      <div class="input-group mb-3">
		      <div class="input-group-prepend">
		        <span class="input-group-text">전화번호 :</span> &nbsp;&nbsp;
				      <select name="tel1" class="custom-select">
						    <option value="010" ${tel1=="010" ? "selected" : ""}>010</option>
						    <option value="02"  ${tel1=="02"  ? "selected" : ""}>서울</option>
						    <option value="031" ${tel1=="031" ? "selected" : ""}>경기</option>
						    <option value="032" ${tel1=="032" ? "selected" : ""}>인천</option>
						    <option value="041" ${tel1=="041" ? "selected" : ""}>충남</option>
						    <option value="042" ${tel1=="042" ? "selected" : ""}>대전</option>
						    <option value="043" ${tel1=="043" ? "selected" : ""}>충북</option>
				        <option value="051" ${tel1=="051" ? "selected" : ""}>부산</option>
				        <option value="052" ${tel1=="052" ? "selected" : ""}>울산</option>
				        <option value="061" ${tel1=="061" ? "selected" : ""}>전북</option>
				        <option value="062" ${tel1=="062" ? "selected" : ""}>광주</option>
						  </select>-
		      </div>
		      <input type="text" name="tel2" value="${tel2}" size=4 maxlength=4 class="form-control"/>-
		      <input type="text" name="tel3" value="${tel3}" size=4 maxlength=4 class="form-control"/>
		    </div> 
	    </div>
	    <div class="form-group">
	      <label for="address">주소</label>
				<input type="hidden" name="address" id="address">
				<div class="input-group mb-1">
					<input type="text" name="postcode" value="${postcode}" id="sample6_postcode" placeholder="우편번호" class="form-control">
					<div class="input-group-append">
						<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기" class="btn btn-secondary">
					</div>
				</div>
				<input type="text" name="roadAddress" value="${roadAddress}" id="sample6_address" size="50" placeholder="주소" class="form-control mb-1">
				<div class="input-group mb-1">
					<input type="text" name="detailAddress" value="${detailAddress}" id="sample6_detailAddress" placeholder="상세주소" class="form-control"> &nbsp;&nbsp;
					<div class="input-group-append">
						<input type="text" name="extraAddress" value="${extraAddress}" id="sample6_extraAddress" placeholder="참고항목" class="form-control">
					</div>
				</div>
	    </div>
	    <div class="form-group">
		    <label for="homepage">Homepage address:</label>
		    <input type="text" class="form-control" name="homePage" value="${vo.homePage}" placeholder="홈페이지를 입력하세요." id="homePage"/>
		  </div>
	    <div class="form-group">
	      <label for="name">직업</label>
	      <select class="form-control" id="job" name="job">
	        <option ${vo.job=="학생"  ? "selected" : ""}>학생</option>
	        <option ${vo.job=="회사원" ? "selected" : ""}>회사원</option>
	        <option ${vo.job=="공무원" ? "selected" : ""}>공무원</option>
	        <option ${vo.job=="군인"  ? "selected" : ""}>군인</option>
	        <option ${vo.job=="의사"  ? "selected" : ""}>의사</option>
	        <option ${vo.job=="법조인" ? "selected" : ""}>법조인</option>
	        <option ${vo.job=="세무인" ? "selected" : ""}>세무인</option>
	        <option ${vo.job=="자영업" ? "selected" : ""}>자영업</option>
	        <option ${vo.job=="기타"  ? "selected" : ""}>기타</option>
	      </select>
	    </div>
	    <div class="form-group">
		    취미 : 
		    <c:set var="varHobbys" value="${fn:split('등산/낚시/수영/독서/영화감상/바둑/축구/기타','/')}"/>
		    <c:forEach var="tempHobby" items="${varHobbys}" varStatus="st"> &nbsp; &nbsp;
		      <input type="checkbox" class="form-check-input" value="${tempHobby}" name="hobby" <c:if test="${fn:contains(hobby,varHobbys[st.index])}">checked</c:if>/>${tempHobby} &nbsp;
		    </c:forEach>
	    </div>
	    <div class="form-group">
	      <label for="content">자기소개</label>
	      <textarea rows="5" class="form-control" id="content" name="content" placeholder="자기소개를 입력하세요.">${vo.content}</textarea>
	    </div>
	    <div class="form-group">
	      <div class="form-check-inline">
	        <span class="input-group-text">정보공개</span>  &nbsp; &nbsp; 
				  <label class="form-check-label">
				    <input type="radio" class="form-check-input" name="userInfor" value="공개" ${vo.userInfor=="공개" ? "checked" : ""}/>공개
				  </label>
				</div>
				<div class="form-check-inline">
				  <label class="form-check-label">
				    <input type="radio" class="form-check-input" name="userInfor" value="비공개" ${vo.userInfor=="비공개" ? "checked" : ""}/>비공개
				  </label>
				</div>
	    </div>
	    <div  class="form-group">
	      회원 사진(파일용량:2MByte이내) :
	      <img src="${ctp}/data/member/${vo.photo}" width="100px"/>
	      <input type="file" name="fName" id="file" class="form-control-file border"/>
	    </div>
	    <button type="button" class="btn btn-secondary" onclick="fCheck()">회원정보수정</button> &nbsp;
	    <button type="reset" class="btn btn-secondary">다시작성</button> &nbsp;
	    <button type="button" class="btn btn-secondary" onclick="location.href='${ctp}/memMain.mem';">돌아가기</button>
	    
	    <input type="hidden" name="photo"/>
	    <input type="hidden" name="tel"/>
	    <input type="hidden" name="email"/>
	  </form>
	</div>

	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>