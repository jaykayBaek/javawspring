package com.spring.javawspring;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javawspring.pagination.PageProcess;
import com.spring.javawspring.pagination.PageVO;
import com.spring.javawspring.service.MemberService;
import com.spring.javawspring.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private PageProcess pageProcess;
	
	@GetMapping(value = "/login")
	public String loginGet(HttpServletRequest request) {
		
		Cookie[] cookies = request.getCookies();
		for(int i=0; i<cookies.length; i++) {
			if(cookies[i].getName().equals("cMid")) {
				request.setAttribute("mid", cookies[i].getValue());
				break;
			}
		}
		
		return "member/memberLogin";
	}
	
	@PostMapping(value = "/login")
	public String loginPost(
			HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(name="mid", defaultValue = "", required = false) String mid,
			@RequestParam(name="pwd", defaultValue = "", required = false) String pwd,
			@RequestParam(name="idCheck", defaultValue = "", required = false) String idCheck) {
		
		/*--- DB에서 아이디가 있는지 확인 ---*/
		MemberVO vo =service.getMemberIdCheck(mid);
		
		if(vo == null) {
			return "redirect:/msg/memberLoginNo";
			
		} else if(!passwordEncoder.matches(pwd, vo.getPwd())) {
			return "redirect:/msg/memberLoginNo";
			
			
		} else if(vo.getUserDel().equals("YES")) {
			return "redirect:/msg/memberUserDelYes";
			
		}
		
		/*--- 위 유효성 모두 통과? 로그인 처리 ->
		 * Session 저장, 쿠키값 처리, 방문자수 1증가, 방문 포인트 증가 등등...
		 *  ---*/
		String strLevel = "준회원";
		switch (vo.getLevel()) {
			case 0:
				strLevel = "관리자";
				break;
			case 1:
				strLevel = "운영자";
				break;
			case 2:
				strLevel = "우수회원";
				break;
			case 3:
				strLevel = "정회원";
				break;
		}
		
		session.setAttribute("sStrLevel", strLevel);
		session.setAttribute("sMid", mid);
		session.setAttribute("sNickName", vo.getNickName());
		session.setAttribute("sLevel", vo.getLevel());
		if(idCheck.equals("on")) {
			Cookie cookie = new Cookie("cMid", mid);
			cookie.setMaxAge(60*60*24*7);
			response.addCookie(cookie);
		}
		else {
			Cookie[] cookies = request.getCookies();
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals("cMid")) {
					cookies[i].setMaxAge(0);
					response.addCookie(cookies[i]);
					break;
				}
			}
		}
		
		// 로그인 사용자의 오늘 방문횟수와 포인트 등등 갱신
		service.setVisitMemberProcess(vo);
		
		return "redirect:/msg/memberLoginOk?mid="+mid;
	}
	
	@GetMapping(value = "/main")
	public String mainGet(Model model, HttpSession session) {
		String mid = ""+session.getAttribute("sMid");
		MemberVO vo = service.getMemberIdCheck(mid);
		
		model.addAttribute("vo", vo);
		
		return "member/memberMain";
	}
	
	@GetMapping(value = "/logout")
	public String logoutGet(HttpSession session) {
		String mid = ""+session.getAttribute("sMid");
		session.invalidate();
		
		return "redirect:/msg/memberLogout?mid="+mid;
	}
	@GetMapping(value = "/logout/admin")
	public String logoutAdminGet(HttpSession session) {
		String mid = ""+session.getAttribute("sMid");
		session.invalidate();
		
		return "redirect:/msg/memberLogout?mid="+mid;
	}
	
	// 회원가입폼
	@GetMapping(value = "/join")
	public String joinGet() {
		return "member/memberJoin";
	}
	// 회원가입처리
	@PostMapping(value = "/join")
	public String joinPost(MemberVO vo) {
		
		// 아이디 중복 체크
		if(service.getMemberIdCheck(vo.getMid()) != null) {
			return "redirect:/msg/memberJoinIdFail";
		}
		// 닉네임 중복 체크
		if(service.getMemberIdCheck(vo.getMid()) != null) {
			return "redirect:/msg/memberJoinNickNameFail";
		}
		
		// 비밀번호 암호와
		vo.setPwd(passwordEncoder.encode(vo.getPwd()));
		
		// 체크가 완료되면 vo에 담긴 자료를 db에 저장시켜준다.
		int res = service.setMemberJoinOk(vo);
		
		if(res == 1) return "redirect:/msg/memberJoinSuccess";
		
		else return "redirect:/msg/memberJoinFail";
	}
	
	@ResponseBody
	@PostMapping(value = "check/id")
	public String checkId(String mid) {
		String res = "0";

		MemberVO vo = service.getMemberIdCheck(mid);
		if(vo != null) {
			res = "1";
		}
		
		return res;
	}
	@ResponseBody
	@PostMapping(value = "check/nickName")
	public String checkNickName(String nickName) {
		String res = "0";
		
		MemberVO vo = service.getMemberNickNameCheck(nickName);
		if(vo != null) {
			res = "1";
		}
		
		return res;
	}
	/*--- 전체리스트와 검색 리스트를 하나의 메소드로 처리... ---*/
	@GetMapping(value = "/list")
	public String listGet(Model model, 
			@RequestParam(value = "mid", defaultValue = "", required = false) String mid,
			@RequestParam(value = "pag", defaultValue = "1", required = false) int pag,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize) {

		PageVO pageVo = pageProcess.getTotRecordCnt(pag, pageSize, "member", "", "");
		List<MemberVO> vos = service.getMemberList(pageVo.getStartIndexNo(), pageVo.getPageSize());
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVo", pageVo);
		
		return "member/memberList";
	}
	
}
