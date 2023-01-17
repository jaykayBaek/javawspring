package com.spring.javawspring.vo;

import lombok.Data;

@Data
public class QrCodeVO {
	int idx;
	// 직원이 검색할 qr코드명
	String qr_code;
	// 할인율 5~50% 난수
	int discount_rate;
	// 만기일 (db에서 오늘로부터 +7일까지 )
	String expire_date;
	// 쿠폰이 적용될 카테고리
	String category;
}
