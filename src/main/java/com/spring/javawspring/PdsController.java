package com.spring.javawspring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	
}