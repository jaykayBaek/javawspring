package com.spring.javawspring;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {
	@DeleteMapping("/user/{userId}/age/{age}")
	public String deleteUser(
			@PathVariable("userId") String userId,
			@PathVariable("age") int age) {
		log.info("userId = {}, age = {}", userId, age);
		return "ok";
	}
	@PostMapping("/user/{userId}/age/{age}")
	public String joinUser(
			@PathVariable("userId") String userId,
			@PathVariable("age") int age) {
		log.info("POST: userId = {}, age = {}", userId, age);
		
		return "ok";

	}
}
