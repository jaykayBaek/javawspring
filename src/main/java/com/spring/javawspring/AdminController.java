package com.spring.javawspring;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javawspring.pagination.PageProcess;
import com.spring.javawspring.pagination.PageVO;
import com.spring.javawspring.service.AdminService;
import com.spring.javawspring.service.MemberService;
import com.spring.javawspring.vo.MemberVO;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private AdminService service;
	
	@Autowired
	private PageProcess pageProcess;
	
	@Autowired
	JavaMailSender mailSender;
	
	
	@GetMapping(value = "/main")
	public String adminMainGet() {
		
		return "admin/adminMain";
	}
	@GetMapping(value = "/left")
	public String adminLeftGet() {
		return "admin/adminLeft";
	}
	@GetMapping(value = "/content")
	public String adminContentGet() {
		return "admin/adminContent";
	}
	
		
	/*--- 전체리스트와 검색 리스트를 하나의 메소드로 처리... ---*/
	@GetMapping(value = "/member/list")
	public String listGet(Model model, 
			@RequestParam(value = "mid", defaultValue = "", required = false) String mid,
			@RequestParam(value = "pag", defaultValue = "1", required = false) int pag,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize) {

		PageVO pageVo = pageProcess.getTotRecordCnt(pag, pageSize, "member", "", mid);
		List<MemberVO> vos = memberService.getMemberList(pageVo.getStartIndexNo(), pageVo.getPageSize(), mid);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVo", pageVo);
		
		return "admin/member/adminMemberList";
	}
	@PostMapping(value = "/member/list")
	public String listPost(Model model, 
			@RequestParam(value = "mid", defaultValue = "", required = false) String mid,
			@RequestParam(value = "pag", defaultValue = "1", required = false) int pag,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize) {
		
		PageVO pageVo = pageProcess.getTotRecordCnt(pag, pageSize, "member", "", mid);
		List<MemberVO> vos = memberService.getMemberList(pageVo.getStartIndexNo(), pageVo.getPageSize(), mid);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVo", pageVo);
		
		return "admin/member/adminMemberList";
	}
	
	/*--- 회원 등업하기 ---*/
	@ResponseBody
	@PostMapping(value = "/member/level")
	public String adminMemberLevelPost(int idx, int level) {
		int res = service.setMemberLevelUp(idx, level);
		return res+"";
	}
	/*--- 회원탈퇴승인창가기 ---*/
	@GetMapping(value = "/member/confirm-leave")
	public String adminMemberConfirmLeaveGet(Model model) {
		List<MemberVO> vos = service.getBeforeLeaveMemberList();
		model.addAttribute("vos", vos);
		return "admin/member/adminMemberConfirmLeave";
	}
	@PostMapping(value = "/member/confirm-leave")
	@ResponseBody
	public String adminMemberConfirmLeavePost(int idx) {
		int res = service.setUserLeave(idx);
		return res+"";
	}
	
	@GetMapping("file/list")
	public String fileListGet(HttpServletRequest request, Model model) {
		String realPath = request.getRealPath("/resources/data/ckeditor/");

		String[] files = new File(realPath).list();
		
		model.addAttribute("files", files);
		
		return "admin/file/fileList";
	}
	@PostMapping("file/list")
	@ResponseBody
	public String fileListPost(
			@RequestParam(value="data[]") List<String> data, HttpServletRequest request) {
		final String ckeditorPath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor/");
		String filePath = "";
		String res = "0";
		if(data.iterator().hasNext()) {
			for(String fileName : data) {
				filePath = ckeditorPath + fileName;
				File deleteFile = new File(filePath);
				deleteFile.delete();
			}
			res = "1";
		}
		
		return res;
	}
}