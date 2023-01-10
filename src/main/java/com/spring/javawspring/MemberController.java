package com.spring.javawspring;

import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
	
	@Autowired
	JavaMailSender mailSender;
	
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
	public String joinPost(MultipartFile fName, MemberVO vo) {
		
		// 아이디 중복 체크
		if(service.getMemberIdCheck(vo.getMid()) != null) {
			return "redirect:/msg/memberJoinIdFail";
		}
		// 닉네임 중복 체크
		if(service.getMemberIdCheck(vo.getMid()) != null) {
			return "redirect:/msg/memberJoinNickNameFail";
		}
		
		// 비밀번호 암호화
		vo.setPwd(passwordEncoder.encode(vo.getPwd()));
		
		// 체크가 완료되면 사진 파일 업로드 후 vo에 담긴 자료를 DB에 저장시켜준다. - 서비스 객체에서 수행 처리
		
		int res = service.setMemberJoinOk(fName, vo);
		
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
		List<MemberVO> vos = service.getMemberList(pageVo.getStartIndexNo(), pageVo.getPageSize(), mid);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVo", pageVo);
		
		return "member/memberList";
	}
	
	//아이디 찾기
	@GetMapping(value="/find/id")
	public String memberFindIdGet() {
		return "member/memberIdSearch";
	}
	
	@ResponseBody
	@PostMapping(value="/find/id", produces = "application/text; charset=utf8")
	public String MemberFindIdPost(String name, String toMail) {
		System.out.println("name"+name);
		System.out.println("tomail"+toMail);
		String mid = service.getMidToNameAndEmail(name, toMail);
		if(mid==null) {
			return "";
		}
		
		MemberVO vo = service.getMemberIdCheck(mid);
		String tmpMid = vo.getMid();
		String star = "";
		for(int i = 0; i<tmpMid.substring(3).length(); i++) {
			star+="*";
		}
		tmpMid = tmpMid.replace(tmpMid.substring(3), star);
		
		System.out.println("---------------");
		System.out.println("????"+tmpMid);
		
		sendTmpEmail(toMail, vo.getMid(), "아이디 찾기");
		return tmpMid;
	}
	
	//비밀번호를 찾기 위한 임시비밀번호 발급 폼으로 이동
	@GetMapping(value="/find/pwd")
	public String memberPwdSearchGet() {
		return "member/memberPwdSearch";
	}

	@PostMapping(value="/find/pwd")
	public String memberPwdSearchPost(String mid, String toMail) {
		MemberVO vo = service.getMemberIdCheck(mid);
		String getEmail = vo.getEmail();
		if(!(getEmail.equals(toMail))) {
			return "redirect:/msg/notMatchEmail";
		}
		// 회원 정보가 맞다면 임시 비밀번호를 발급 받는다
		UUID uuid = UUID.randomUUID();
		String tmpPwd = uuid.toString().substring(0, 8);
		
		// 임시 비밀번호를 메일로 전송한다.
		String res = sendTmpEmail(toMail, tmpPwd, "임시비밀번호");
		
		// 발급 받은 임시 비밀번호를 암호화 처리해서 DB에 저장한다.
		tmpPwd = passwordEncoder.encode(tmpPwd);
		service.setMemberPwdUpdate(mid, tmpPwd);
		
		if(res.equals("1")) return "redirect:/msg/memberTmpPwdSendSuccess";
		else return "redirect:/msg/memberTmpPwdSendFail";
		
	}
	
	//비밀번호 변경 폼
	@GetMapping(value="/update/pwd")
	public String memberUpdatePwdGet() {
		return "member/memberUpdatePwd";
	}
	@PostMapping(value="/update/pwd")
	public String memberUpdatePwdPost(String newPwd, String rePwd, HttpServletRequest request) {
		HttpSession session = request.getSession();
		int level = session.getAttribute("sLevel") == null ? 100 : (int) session.getAttribute("sLevel");
		
		if(level == 100) {
			return "redirect:/msg/levelMemberNo";
		}
		else if(!newPwd.equals(rePwd)) {
			return "redirect:/msg/pwdNotMatch";
		}
		
		String mid = ""+session.getAttribute("sMid");
		rePwd = passwordEncoder.encode(rePwd);
		service.setMemberPwdUpdate(mid, rePwd);
		session.invalidate();
		return "redirect:/msg/pwdChangeSuccess";
	}
	
	@GetMapping(value="/leave")
	public String memberLeaveGet() {
		return "member/memberLeave";
	}
	
	@GetMapping(value="/leaveOk")
	public String memberLeaveOkGet(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String mid = session.getAttribute("sMid") == null ? "" : ""+session.getAttribute("sMid");
		if(mid.equals("")) {
			return "redirect:/msg/levelMemberNo";
		}
		service.setMemberDelNoToYes(mid);
		return "redirect:/msg/leaveOk?mid="+mid;
	}
	
	@GetMapping(value = "/update")
	public String memberUpdateGet(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String mid = session.getAttribute("sMid") == null?"":""+session.getAttribute("sMid");
		if(mid.equals("")) {
			return "redirect:/msg/levelMemberNo";
		}
		MemberVO vo = service.getMemberIdCheck(mid);
		model.addAttribute("vo", vo);
		String[] email =vo.getEmail().split("@");
		model.addAttribute("email1", email[0]);
		model.addAttribute("email2", email[1]);
		String[] tel = vo.getTel().split("-");
		model.addAttribute("tel1", tel[0]);
		model.addAttribute("tel2", tel[1]);
		model.addAttribute("tel3", tel[2]);
		String[] address = vo.getAddress().split("/");
		model.addAttribute("postcode", address[0]);
		model.addAttribute("roadAddress", address[1]);
		model.addAttribute("detailAddress", address[2]);
		model.addAttribute("extraAddress", address[3]);
		
		return "member/memberUpdate";
	}
	
	private String sendTmpEmail(String toMail, String tmp, String flag) {
		try {
			String title = "그린컴퓨터학원"+flag+"발급!";
			String content = "";
			
			// 메일을 전송하기위한 객체 : MimeMessage() , MimeMessageHelper()
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			// 메일보관함에 회원이 보내온 메세지들을 모두 저장시킨다.
			messageHelper.setTo(toMail);
			messageHelper.setSubject(title);
			messageHelper.setText(content);

			// 메세지 보관함의 내용(content)에 필요한 정보를 추가로 담아서 전송시킬수 있도록 한다.
			content = content.replace("\n", "<br/>");
			content += "<br><hr><h3>CJ Green에서 보냅니다.</h3><hr><br>";
			content += "<span>"+flag+"? "+tmp+"</span>";
			content += "<img src=\"cid:main.jpg\" width='500px'/>";
			content += "";
			content += "<p>방문하기 : <a href='http://49.142.157.251:9090/green2209J_08/'>환영합니다</a></p>";
			
			messageHelper.setText(content, true);
			// 메일 전송하기
			mailSender.send(message);
			return "1";
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "0";
	}
	
}
