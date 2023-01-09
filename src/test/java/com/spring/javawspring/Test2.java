package com.spring.javawspring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
}
