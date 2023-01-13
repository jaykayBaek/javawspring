package com.spring.javawspring.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.javawspring.vo.GuestVO;

public interface StudyService {

	public String[] getCityStringArr(String state);

	public List<String> getCityList(String state);

	public GuestVO getGuestMid(String mid);

	public List<GuestVO> getGuestName(String mid);

	public List<GuestVO> getSearchResult(String search, String condition);

	public String fileUpload(MultipartFile fName);

	public void getCalendar();
	
}
