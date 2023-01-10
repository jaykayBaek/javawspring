package com.spring.javawspring;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javawspring.common.ARIAUtil;
import com.spring.javawspring.common.SecurityUtil;
import com.spring.javawspring.service.MemberService;
import com.spring.javawspring.service.StudyService;
import com.spring.javawspring.vo.GuestVO;
import com.spring.javawspring.vo.MailVO;
import com.spring.javawspring.vo.MemberVO;

@Controller
@RequestMapping("/study")
public class StudyController {
	
	@Autowired
	StudyService service;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	JavaMailSender mailSender;
	
	@GetMapping(value = "/ajax/menu")
	public String ajaxMenuGet() {
		return "study/ajax/ajaxMenu";
	}
	
//
//	@ResponseBody
//	@PostMapping(value = "/ajax/menu/test1_1")
//	public HashMap<String, String> ajaxMenuTest1_1Post(int idx) {
//		idx = (int)(Math.random()*idx) + 1;
//		String res = idx + " : Happy~";
//		HashMap<String, String> map = new HashMap<>();
//		map.put("h", "1");
//		return map;
//	}
	//일반 String 값의 전달
	@ResponseBody
	@PostMapping(value = "/ajax/menu/test1_1", produces = "application/text; charset=utf8")
	public String ajaxMenuTest1_1Post(int idx) {
		idx = (int)(Math.random()*idx) + 1;
		String res = idx + " : Happy~";
		return res;
	}
	//일반 String 값의 전달2
	@ResponseBody
	@PostMapping(value = "/ajax/menu/test1_2", produces = "application/text; charset=utf8")
	public String ajaxMenuTest1_2Post(int idx) {
		idx = (int)(Math.random()*idx) + 1;
		String res = idx + " : 새해복 많이받으세요! Happy new Year!";
		return res;
	}
	//배열 값의 전달
	@GetMapping(value = "/ajax/menu/test2_1", produces = "application/text; charset=utf8")
	public String ajaxMenuTest2_1Get() {
		return "study/ajax/ajaxTest2_1";
	}
	
	@ResponseBody
	@PostMapping(value = "/ajax/menu/test2_1")
	public String[] ajaxMenuTest2_1Post(String state) {
//		String[] strArr = new String[100];
//		strArr = service.getCityStringArr(state);
//		return strArr;
		return service.getCityStringArr(state);
	}
	
	// 객체 배열 값의 전달
	@GetMapping(value = "/ajax/menu/test2_2")
	public String ajaxMenuTest2_2Get() {
		return "study/ajax/ajaxTest2_2";
	}
	
	@ResponseBody
	@PostMapping(value = "/ajax/menu/test2_2")
	public List<String> ajaxMenuTest2_2Post(String state) {
		return service.getCityList(state);
	}
	
	// Map 형식 값 전달
	@GetMapping(value = "/ajax/menu/test2_3")
	public String ajaxMenuTest2_3Get() {
		return "study/ajax/ajaxTest2_3";
	}
	
	@ResponseBody
	@PostMapping(value = "/ajax/menu/test2_3")
	public Map<Object, Object> ajaxMenuTest2_3Post(String state) {
		List<String> list = new ArrayList<String>();
		list = service.getCityList(state);
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("city", list);
		
		return map;
	}
	
	// DB에서 값 전달 폼
	@GetMapping(value = "/ajax/menu/test3")
	public String ajaxMenuTest3Get() {
		return "study/ajax/ajaxTest3";
	}
	
	// DB에서 값 전달1
	@ResponseBody
	@PostMapping(value = "/ajax/menu/test3_1")
	public GuestVO ajaxMenuTest3_1Post(String mid) {
		return service.getGuestMid(mid);
	}
	
	// DB에서 값 전달2
	@ResponseBody
	@PostMapping(value = "/ajax/menu/test3_2")
	public List<GuestVO> ajaxMenuTest3_2Post(String mid) {
		return service.getGuestName(mid);
	}
	
	// DB에서 값 전달3
	@ResponseBody
	@PostMapping(value = "/ajax/menu/test3_3")
	public List<GuestVO> ajaxMenuTest3_3Post(String search, String condition) {
		return service.getSearchResult(search, condition);
	}
	
	/* --- 암호화 연습 --- */
	@GetMapping(value = "/password/sha256")
	public String sha256Get() {
		return "study/password/sha256";
	}
	
	@ResponseBody
	@PostMapping(value = "/password/sha256", produces = "application/text; charset=utf8")
	public String sha256Post(String pwd) {
		String encPwd = SecurityUtil.encryptSHA256(pwd);
		pwd = "원본 비밀번호 : " + pwd + " / <br/> 암호화된 비밀번호 " + encPwd + "<br/>";
		return pwd;
	}
	@GetMapping(value = "/password/aria")
	public String ariaGet() {
		return "study/password/aria";
	}
	
	@ResponseBody
	@PostMapping(value = "/password/aria", produces = "application/text; charset=utf8")
	public String ariaPost(String pwd) throws InvalidKeyException, UnsupportedEncodingException {
		// 암호화
		String encPwd = ARIAUtil.ariaEncrypt(pwd);
		// 복호화
		String decPwd = ARIAUtil.ariaDecrypt(encPwd);
		pwd = "원본 비밀번호 : " + pwd + " / 암호화된 비밀번호 " + encPwd + " / 복호화된 비밀번호 : "+decPwd+"<br/>";
		return pwd;
	}
	
	@GetMapping(value = "/password/bCrypt")
	public String bCryptGet() {
		return "study/password/security";
	}
	
	@ResponseBody
	@PostMapping(value = "/password/bCrypt", produces = "application/text; charset=utf8")
	public String bCryptPost(String pwd) {
		String encPwd = "";
		encPwd = passwordEncoder.encode(encPwd);
				
		pwd = "원본 비밀번호 : " + pwd + " / 암호화된 비밀번호 "+ encPwd +"<br/>";
		
		return pwd;
	}
	/*--- 이런!! ---*/
	@GetMapping(value = "/mail/mailForm")
	public String mailFormGet(Model model, String email) {
		
		List<MemberVO> vos = memberService.getMemberList(0, 1000, "");

		model.addAttribute("vos", vos);
		model.addAttribute("cnt", vos.size());
		model.addAttribute("email", email);
		return "study/mail/mailForm";
	}
	
	// 주소록 호출
	@PostMapping(value="/mail/mailForm/jusorok")
	public List<MemberVO> JusorokPost(){
		return null;
	}
	
	@PostMapping(value = "/mail/mailForm")
	public String mailFormPost(MailVO vo, HttpServletRequest request) {
		try {
			String toMail = vo.getToMail();
			String title = vo.getTitle();
			String content = vo.getContent();
			
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
			content += "<img src=\"cid:main.jpg\" width='500px'/>";
			content += "<p>방문하기 : <a href='http://49.142.157.251:9090/green2209J_08/'>환영합니다</a></p>";
			
			messageHelper.setText(content, true);
			
			// 본문에 기재된 그림파일의 경로를 따로 표시시켜준다. 보관함에 다시 저장
			FileSystemResource file = new FileSystemResource("D:\\JavaWorkspace\\springframework\\works\\javawspring\\src\\main\\webapp\\resources\\images\\main.jpg");
			messageHelper.addInline("main.jpg", file);
			
			// 첨부파일 보내기(서버 파일시스템에 있는 파일)
			file = new FileSystemResource("D:\\JavaWorkspace\\springframework\\works\\javawspring\\src\\main\\webapp\\resources\\images\\newyork.jpg");
			messageHelper.addAttachment("newyork.jpg", file);

//			file = new FileSystemResource(request.getRealPath("/resources/images/paris.jpg"));
			file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/paris.jpg"));
			messageHelper.addAttachment("paris.jpg", file);
			
			
			// 메일 전송하기
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return "redirect:/msg/mailSendOk";
	}
	
	// UUID 입력폼
	@GetMapping(value = "/uuid/uuidForm")
	public String uuidFormGet() {
		return "study/uuid/uuidForm";
	}
	@ResponseBody
	@PostMapping(value = "/uuid/uuidProcess")
	public String uuidProcessPost() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	/*--- 파일 업로드 폼 ---*/
	@GetMapping(value = "/file/uploadForm")
	public String fileUploadFormGet() {
		return "study/fileUpload/fileUploadForm";
	}
	
	/*--- 파일 업로드 처리---*/
	@PostMapping(value = "/file/uploadForm")
	public String fileUploadFormPost(MultipartFile fName) {
		String res = service.fileUpload(fName);
		if(res.equals("1") ) {
			return "redirect:/msg/fileUploadSuccess";
		}
		else {
			return "redirect:/msg/fileUploadFail";
		}
	}
	
	
	
}