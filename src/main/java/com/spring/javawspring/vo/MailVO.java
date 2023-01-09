package com.spring.javawspring.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailVO {
	private String toMail;
	private String title;
	private String content;
}
