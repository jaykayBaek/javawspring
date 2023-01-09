package com.spring.javawspring.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

public class JavawspringProvide {
	public static String fileUpload(MultipartFile fName) {
		String res = "0";
		try {
			UUID uuid = UUID.randomUUID();
			String oFileName = fName.getOriginalFilename();
			String saveFileName = uuid + "_" +oFileName;
			
			writeFile(fName, saveFileName, "");
			res = "1";
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return res;
	}

	public static void writeFile(MultipartFile fName, String saveFileName, String flag) throws IOException {
		byte[] data = fName.getBytes();
		
		HttpServletRequest request = 
			((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
			.getRequest();
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/"+flag+"/");
		
		FileOutputStream fos = new FileOutputStream(realPath + saveFileName);
		fos.write(data);
		fos.close();
	}
}
