package com.spring.javawspring;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javawspring.common.SecurityUtil;
import com.spring.javawspring.pagination.PageProcess;
import com.spring.javawspring.pagination.PageVO;
import com.spring.javawspring.service.PdsService;
import com.spring.javawspring.vo.PdsVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/pds")
public class PdsController {
	
	@Autowired
	private PdsService service;
	
	@Autowired
	private PageProcess pageProcess;
	
	
	
	@GetMapping("/list")
	public String pdsListGet(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part
			) {
		
		PageVO pageVo = pageProcess.getTotRecordCnt(pag, pageSize, "pds", part, "");
		pageVo.setPart(part);
		List<PdsVO> vos = service.getPdsList(pageVo.getStartIndexNo(), pageVo.getPageSize(), pageVo.getPart());
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVo", pageVo);
		
		return "pds/pdsList";
	}
	
	@GetMapping("/input")
	public String pdsInputGet() {
		return "pds/pdsInput";
	}
	
	@PostMapping("/input")
	public String pdsInputPost(PdsVO vo,
			MultipartHttpServletRequest file) {
		String pwd = vo.getPwd();
		pwd = SecurityUtil.encryptSHA256(pwd);
		vo.setPwd(pwd);
		
		// 멀티파일을 서버에 저장시키고, 파일의 정보를 vo에 담아서 db에 저장시킨다
		service.setPdsInput(file, vo);

		
		return "redirect:/msg/pdsInputOk";
	}
	
	@ResponseBody
	@PostMapping("/pdsDownNum")
	public String pdsDownNum(int idx) {
		service.setPdsDownNum(idx);
		return "";
	}
	
	// 삭제 처리(삭제 처리하기 전에 비번 체킹 후 삭제처리)
	@ResponseBody
	@DeleteMapping("/{idx}")
	public String pdsDelete(
			@PathVariable("idx") int idx,
			@RequestParam String pwd) {
		log.info("idx? {}, pwd? {}", idx, pwd);
		pwd = SecurityUtil.encryptSHA256(pwd);
		log.info("password? {}", pwd);
		
		PdsVO vo = service.getPdsContent(idx);
		
		if(!pwd.equals(vo.getPwd())) {
			return "0";
		}
		
		//비밀번호가 맞으면 파일 삭제후 db의 내역을 삭제처리한다
		service.setPdsDelete(vo);
		
		return "1";
	}
	
	@GetMapping("/total-download/{idx}")
	public String pdsTotalDownGet(@PathVariable("idx") int idx, HttpServletRequest request) throws IOException {
		log.info("idx? {}", idx);
		// 파일 압축다운로드전에 다운로드수를 증가시킨다.
		service.setPdsDownNum(idx);
		
		// 여러개의 파일일 경우 모든 파일을 하나의 파일로 압축(?=통합)하여 다운한다. 이때 압축파일명은 '제목'으로 처리한다.
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		
		PdsVO vo = service.getPdsContent(idx);
		
		String[] fNames = vo.getFName().split("/");
		String[] fSNames = vo.getFSName().split("/");
		
		String zipPath = realPath + "temp/";
		String zipName = vo.getTitle() + ".zip";
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath + zipName));
		
		byte[] buffer = new byte[2048];
		
		for(int i=0; i<fSNames.length; i++) {
			fis = new FileInputStream(realPath + fSNames[i]);
			fos = new FileOutputStream(zipPath + fNames[i]);
			File moveAndRename = new File(zipPath + fNames[i]);
			
			// fos에 파을 쓰기작업
			int data;
			while((data = fis.read(buffer, 0, buffer.length)) != -1) {
				fos.write(buffer, 0, data);
			}
			fos.flush();
			fos.close();
			fis.close();
			
			// zip파일에 fos를 넣는 작업
			fis = new FileInputStream(moveAndRename);
			zout.putNextEntry(new ZipEntry(fNames[i]));
			
			while((data = fis.read(buffer, 0, buffer.length)) != -1) {
				zout.write(buffer, 0, data);
			}
			zout.flush();
			zout.closeEntry();
			fis.close();
		}
		zout.close();
		
		return "redirect:/pds/down-action?file="+java.net.URLEncoder.encode(zipName);
	}
	
	@GetMapping("/down-action")
	public void pdsDownACtionGet(HttpServletRequest request, HttpServletResponse response,
			String file) throws IOException {
		String downPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/temp/");
		log.info("downPath?{}, file?{}", downPath, file);
		
		File downFile = new File(downPath + file);
		
		String downFileName = new String(file.getBytes("UTF-8"), "8859_1");
		
		response.setHeader("Content-Disposition",
		"attachment;filename="+downFileName);
		
		FileInputStream fis = new FileInputStream(downFile); ServletOutputStream sos
		= response.getOutputStream();
		
		byte[] buffer = new byte[2048];
		
		int data; while((data = fis.read(buffer, 0, buffer.length)) != -1) {
		sos.write(buffer, 0, data); } sos.flush(); sos.close(); fis.close();
		
		// 다운로드 완료 후 temp 폴더의 파일들을 모두 삭제한다.
		downFile.delete();
		
	}
}