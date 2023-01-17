package com.spring.javawspring.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.javawspring.vo.GuestVO;
import com.spring.javawspring.vo.QrCodeVO;

public interface StudyService {

	public String[] getCityStringArr(String state);

	public List<String> getCityList(String state);

	public GuestVO getGuestMid(String mid);

	public List<GuestVO> getGuestName(String mid);

	public List<GuestVO> getSearchResult(String search, String condition);

	public String fileUpload(MultipartFile fName);

	public void getCalendar();

	public String createQRCode(String mid, String param, String realPath);

	public String createQRCodeCoupon(String mid, String category, String realPath);

	public QrCodeVO getQrCodeInfo(String qrCode);
	
	
}
