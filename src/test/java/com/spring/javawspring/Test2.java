package com.spring.javawspring;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Test2 {

	@Test
	public int lamda(int a, int b) {
		return a > b ? a : b;
	}
	
	@Test
	public void test() {
		int res =  lamda(1, 2);
		assertThat(res).isEqualTo(2);
	}
	
	@Test
	public void createNum() {
		for(int i = 0; i<50; i++) {
			int res = (int) (Math.random()*45)+5;
			log.info("res? {}", res);
			
		}

	}
	
	@Test
	public void createCouponName() {
		String qrCodeName = "";
		
		UUID uid = UUID.randomUUID();
		qrCodeName = "baekice" + "_" + uid.toString().substring(0, 5);
		
		String bigo = "category" + "/" + "saleRate" + "/" + "expiredDate" + "qrCodeName";
		
		
		log.info(qrCodeName);
	}
	
	@Test
	void date() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 7);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		
	}
}
