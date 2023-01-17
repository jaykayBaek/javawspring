<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/include/bs4.jsp"></jsp:include>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>qrCode.jsp</title>
	<script>
		let qrCreate = (no) => {
			let mid = '';
			let param = '';
			if(no == 1){
				mid = myform.mid.value;
				param = myform.email.value;
			}
			else if(no == 2){
				mid = myform.mid.value;
				param = myform.moveUrl.value;
			}
			
			let query = {
					mid : mid,
					param : param
			}
			
			$.ajax({
				type: "post",
				url : "${ctp}/study/qr-code",
				data: query,
				success:function(data){
					alert('QR코드가 생성되었습니다 이름은 ? ' + data);
					$('#qrCodeView').show();
					$('#qrView').html(data);
					
					let qrImage = '<img src = "${ctp}/data/qrCode/'+data+'.png"/>';
					
					$('#qrImage').html(qrImage);
				},
				error:function(){
					console.log('전송오류');
				}
			});
			
		}
		let qrCreateCoupon = () => {
			let category = $('#category').val();
			let mid = '${sMid}';
			let query = {
					mid : mid,
					category : category
			}
			$.ajax({
				type: "post",
				url : "${ctp}/study/qr-code/coupon",
				data: query,
				success:function(data){
					alert('QR코드가 생성되었습니다 이름은 ? ' + data);
 					$('#qrCodeView').show();
					$('#qrView').html(data);
					
					let qrImage = '<img src = "${ctp}/data/qrCode/'+data+'.png"/>';
					
					$('#qrImage').html(qrImage);
				},
				error:function(){
					console.log('전송오류');
				}
			});
		}
		let searchQr = () => {
			let content = $('#search-qrcode').val();
			
			$.ajax({
				type : "post",
				url:"${ctp}/study/qr-code/search",
				data:{content:content},
				success:function(res){
					console.log(res);
				}
			})
			
		}
	</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/include/nav.jsp" />
	<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
	<div class="container">
		<div class="row">
			<form name="myform">
				<h2>QRCode 생성 연습</h2>
				<p>
					<b>개인 정보 입력</b> : <br>
					아이디 : <input type="text" name="mid" value="${vo.mid}"/> <br>
					이메일 : <input type="text" name="email" value="${vo.email}"/> <br>
					<button type="button" onclick="qrCreate(1)" >신상정보 QR코드 생성</button>
				</p>
				<hr />
				<h4>소개하고싶은 사이트 주소를 입력하세요</h4>
				<p>
					이동할 주소 : <input type="text" name="moveUrl" size="40" value="naver.com"/>
					<button type="button" onclick="qrCreate(2)">신상정보 QR코드 생성</button>
				</p>
				<hr />
				<h4>쿠폰 생성기</h4>
				<hr />
				<select name="category" id="category">
					<option value="">카테고리 선택</option>
					<option value="shoes">신발</option>
					<option value="pants">바지</option>
					<option value="clothes">옷</option>
					<option value="watch">시계</option>
				</select>
				<button type="button" onclick="qrCreateCoupon()" >쿠폰생성기</button>
				<div id="qrCodeView" style="display:none">
					<h3>생성된 QR코드 확인하기</h3>
					<div>
						-생성된 qr코드명 : <span id="qrView"></span><br/>
						<span id="qrImage"></span>
					</div>
				</div>
				<div class="search">
					<input type="text" name="search-qrcode" id="search-qrcode"/>
					<button type="button" onclick="searchQr()">qr 검색</button>
				</div>
			</form>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>