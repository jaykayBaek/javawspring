package com.spring.javawspring.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javawspring.dao.StudyDAO;
import com.spring.javawspring.vo.GuestVO;

@Service
public class StudyServiceImpl implements StudyService {
	@Autowired
	StudyDAO dao;

	@Override
	public String[] getCityStringArr(String state) {
		String[] strArr = new String[100];
		
		if(state.equals("서울")) {
			strArr[0] = "강남구";
			strArr[1] = "서초구";
			strArr[2] = "동대문구";
			strArr[3] = "성북구";
			strArr[4] = "마포구";
			strArr[5] = "강동구";
			strArr[6] = "관악구";
			strArr[7] = "중구";
			strArr[8] = "서구";
			strArr[9] = "송파구";
		}
		else if (state.equals("경기")) {
			strArr[0] = "수원시";
			strArr[1] = "이천시";
			strArr[2] = "일산시";
			strArr[3] = "용인시";
			strArr[4] = "시흥시";
			strArr[5] = "광명시";
			strArr[6] = "광주시";
			strArr[7] = "의정부시";
			strArr[8] = "평택시";
			strArr[9] = "안성시";
		}
		else if (state.equals("충북")) {
			strArr[0] = "청주시";
			strArr[1] = "충주시";
			strArr[2] = "괴산군";
			strArr[3] = "진천군";
			strArr[4] = "제천시";
			strArr[5] = "음성군";
			strArr[6] = "옥천군";
			strArr[7] = "영동군";
			strArr[8] = "증평군";
			strArr[9] = "단양군";
		}
		else if (state.equals("충남")) {
			strArr[0] = "천안시";
			strArr[1] = "병천시";
			strArr[2] = "옥산군";
			strArr[3] = "아산시";
			strArr[4] = "공주시";
			strArr[5] = "당진군";
			strArr[6] = "보령시";
			strArr[7] = "계룡시";
			strArr[8] = "논산시";
			strArr[9] = "예산군";
		}
		return strArr;
	}

	@Override
	public List<String> getCityList(String state) {
		List<String> list = new ArrayList<String>();
		
		if(state.equals("서울")) {
			list.add("강남구");
			list.add("서초구");
			list.add("동대문구");
			list.add("성북구");
			list.add("마포구");
			list.add("강동구");
			list.add("관악구");
			list.add("중구");
			list.add("서구");
			list.add("송파구");
		}
		else if (state.equals("경기")) {
			list.add("수원시");
			list.add("이천시");
			list.add("일산시");
			list.add("용인시");
			list.add("시흥시");
			list.add("광명시");
			list.add("광주시");
			list.add("의정부시");
			list.add("평택시");
			list.add("안성시");
		}
		else if (state.equals("충북")) {
			list.add("청주시");
			list.add("충주시");
			list.add("괴산군");
			list.add("진천군");
			list.add("제천시");
			list.add("음성군");
			list.add("옥천군");
			list.add("영동군");
			list.add("증평군");
			list.add("단양군");
		}
		else if (state.equals("충남")) {
			list.add("천안시");
			list.add("병천시");
			list.add("옥산군");
			list.add("아산시");
			list.add("공주시");
			list.add("당진군");
			list.add("보령시");
			list.add("계룡시");
			list.add("논산시");
			list.add("예산군");
		}
		
		return list;
	}

	
	@Override
	public GuestVO getGuestMid(String mid) {
		
		return dao.getGuestMid(mid);
	}

	@Override
	public List<GuestVO> getGuestName(String mid) {
		
		return dao.getGuestName(mid);
	}

	@Override
	public List<GuestVO> getSearchResult(String search, String condition) {
		return dao.getSearchResult(search, condition);
	}

	@Override
	public String fileUpload(MultipartFile fName) {
		String res = "0";
		try {
			UUID uuid = UUID.randomUUID();
			String oFileName = fName.getOriginalFilename();
			String saveFileName = uuid + "_" +oFileName;
			
			writeFile(fName, saveFileName);
			res = "1";
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
		
		return res;
	}

	public void writeFile(MultipartFile fName, String saveFileName) throws IOException {
		byte[] data = fName.getBytes();
		
		HttpServletRequest request = 
			((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
			.getRequest();
	//	request.getRealPath("/resources/pds/temp/");
		String realPath = request.getSession().getServletContext().getRealPath("/resources/pds/temp/");
		FileOutputStream fos = new FileOutputStream(realPath + saveFileName);
		fos.write(data);
		fos.close();
	}
	
}
