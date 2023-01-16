package com.spring.javawspring.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javawspring.dao.PdsDAO;
import com.spring.javawspring.vo.PdsVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PdsServiceImpl implements PdsService {
	@Autowired
	PdsDAO dao;

	@Override
	public List<PdsVO> getPdsList(int startIndexNo, int pageSize, String part) {
		return dao.getPdsList(startIndexNo, pageSize, part);
	}
	
	@Override
	public void setPdsInput(MultipartHttpServletRequest mFile, PdsVO vo) {
		try {
			List<MultipartFile> fileList = mFile.getFiles("file");
			String oFileNames = "";
			String sFileNames = "";
			int fileSizes = 0;
			
			for(MultipartFile file : fileList) {
				String oFileName = file.getOriginalFilename();
				String sFileName = saveFileName(oFileName);
				
				// 파일 업로드 메소드 호출
				writeFile(file, sFileName);
				
				oFileNames += oFileName + "/";
				sFileNames += sFileName + "/";
				fileSizes += file.getSize();
			}
			vo.setFName(oFileNames);
			vo.setFSName(sFileNames);
			vo.setFSize(fileSizes);
			
			dao.setPdsInput(vo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setPdsDownNum(int idx) {
		dao.setPdsDownNum(idx);
	}
	
	
	

	/* --- 실제 서버에 저장되는 파일명 중복 방지를 위한 파일명 설정 ---*/
	private String saveFileName(String oFileName) {
		String fileName = "";
		
		Calendar cal = Calendar.getInstance();
		fileName += cal.get(Calendar.YEAR);
		fileName += cal.get(Calendar.MONTH);
		fileName += cal.get(Calendar.DATE);
		fileName += cal.get(Calendar.HOUR);
		fileName += cal.get(Calendar.MINUTE);
		fileName += cal.get(Calendar.SECOND);
		fileName += cal.get(Calendar.MILLISECOND);
		fileName += "_" + oFileName;
		
		return fileName;
	}
	
	/* --- 실제로 파일을 서버에 저장한다. ---*/
	private void writeFile(MultipartFile file, String systemFileName) throws IOException {
		byte[] bytes = file.getBytes();
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		
		FileOutputStream fos = new FileOutputStream(realPath + systemFileName);
		fos.write(bytes);
		fos.close();
	}


	
}
