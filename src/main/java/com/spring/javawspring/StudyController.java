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
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.spring.javawspring.vo.QrCodeVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
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
	//?????? String ?????? ??????
	@ResponseBody
	@PostMapping(value = "/ajax/menu/test1_1", produces = "application/text; charset=utf8")
	public String ajaxMenuTest1_1Post(int idx) {
		idx = (int)(Math.random()*idx) + 1;
		String res = idx + " : Happy~";
		return res;
	}
	//?????? String ?????? ??????2
	@ResponseBody
	@PostMapping(value = "/ajax/menu/test1_2", produces = "application/text; charset=utf8")
	public String ajaxMenuTest1_2Post(int idx) {
		idx = (int)(Math.random()*idx) + 1;
		String res = idx + " : ????????? ??????????????????! Happy new Year!";
		return res;
	}
	//?????? ?????? ??????
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
	
	// ?????? ?????? ?????? ??????
	@GetMapping(value = "/ajax/menu/test2_2")
	public String ajaxMenuTest2_2Get() {
		return "study/ajax/ajaxTest2_2";
	}
	
	@ResponseBody
	@PostMapping(value = "/ajax/menu/test2_2")
	public List<String> ajaxMenuTest2_2Post(String state) {
		return service.getCityList(state);
	}
	
	// Map ?????? ??? ??????
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
	
	// DB?????? ??? ?????? ???
	@GetMapping(value = "/ajax/menu/test3")
	public String ajaxMenuTest3Get() {
		return "study/ajax/ajaxTest3";
	}
	
	// DB?????? ??? ??????1
	@ResponseBody
	@PostMapping(value = "/ajax/menu/test3_1")
	public GuestVO ajaxMenuTest3_1Post(String mid) {
		return service.getGuestMid(mid);
	}
	
	// DB?????? ??? ??????2
	@ResponseBody
	@PostMapping(value = "/ajax/menu/test3_2")
	public List<GuestVO> ajaxMenuTest3_2Post(String mid) {
		return service.getGuestName(mid);
	}
	
	// DB?????? ??? ??????3
	@ResponseBody
	@PostMapping(value = "/ajax/menu/test3_3")
	public List<GuestVO> ajaxMenuTest3_3Post(String search, String condition) {
		return service.getSearchResult(search, condition);
	}
	
	/* --- ????????? ?????? --- */
	@GetMapping(value = "/password/sha256")
	public String sha256Get() {
		return "study/password/sha256";
	}
	
	@ResponseBody
	@PostMapping(value = "/password/sha256", produces = "application/text; charset=utf8")
	public String sha256Post(String pwd) {
		String encPwd = SecurityUtil.encryptSHA256(pwd);
		pwd = "?????? ???????????? : " + pwd + " / <br/> ???????????? ???????????? " + encPwd + "<br/>";
		return pwd;
	}
	@GetMapping(value = "/password/aria")
	public String ariaGet() {
		return "study/password/aria";
	}
	
	@ResponseBody
	@PostMapping(value = "/password/aria", produces = "application/text; charset=utf8")
	public String ariaPost(String pwd) throws InvalidKeyException, UnsupportedEncodingException {
		// ?????????
		String encPwd = ARIAUtil.ariaEncrypt(pwd);
		// ?????????
		String decPwd = ARIAUtil.ariaDecrypt(encPwd);
		pwd = "?????? ???????????? : " + pwd + " / ???????????? ???????????? " + encPwd + " / ???????????? ???????????? : "+decPwd+"<br/>";
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
				
		pwd = "?????? ???????????? : " + pwd + " / ???????????? ???????????? "+ encPwd +"<br/>";
		
		return pwd;
	}
	/*--- ??????!! ---*/
	@GetMapping(value = "/mail/mailForm")
	public String mailFormGet(Model model, String email) {
		
		List<MemberVO> vos = memberService.getMemberList(0, 1000, "");

		model.addAttribute("vos", vos);
		model.addAttribute("cnt", vos.size());
		model.addAttribute("email", email);
		return "study/mail/mailForm";
	}
	
	// ????????? ??????
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
			
			// ????????? ?????????????????? ?????? : MimeMessage() , MimeMessageHelper()
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			// ?????????????????? ????????? ????????? ??????????????? ?????? ???????????????.
			messageHelper.setTo(toMail);
			messageHelper.setSubject(title);
			messageHelper.setText(content);

			// ????????? ???????????? ??????(content)??? ????????? ????????? ????????? ????????? ??????????????? ????????? ??????.
			content = content.replace("\n", "<br/>");
			content += "<br><hr><h3>CJ Green?????? ????????????.</h3><hr><br>";
			content += "<img src=\"cid:main.jpg\" width='500px'/>";
			content += "<p>???????????? : <a href='http://49.142.157.251:9090/green2209J_08/'>???????????????</a></p>";
			
			messageHelper.setText(content, true);
			
			// ????????? ????????? ??????????????? ????????? ?????? ??????????????????. ???????????? ?????? ??????
			FileSystemResource file = new FileSystemResource("D:\\JavaWorkspace\\springframework\\works\\javawspring\\src\\main\\webapp\\resources\\images\\main.jpg");
			messageHelper.addInline("main.jpg", file);
			
			// ???????????? ?????????(?????? ?????????????????? ?????? ??????)
			file = new FileSystemResource("D:\\JavaWorkspace\\springframework\\works\\javawspring\\src\\main\\webapp\\resources\\images\\newyork.jpg");
			messageHelper.addAttachment("newyork.jpg", file);

//			file = new FileSystemResource(request.getRealPath("/resources/images/paris.jpg"));
			file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/paris.jpg"));
			messageHelper.addAttachment("paris.jpg", file);
			
			
			// ?????? ????????????
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return "redirect:/msg/mailSendOk";
	}
	
	// UUID ?????????
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
	
	/*--- ?????? ????????? ??? ---*/
	@GetMapping(value = "/file/uploadForm")
	public String fileUploadFormGet() {
		return "study/fileUpload/fileUploadForm";
	}
	
	/*--- ?????? ????????? ??????---*/
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
	
	@GetMapping("/calendar")
	public String calendarGet() {
		service.getCalendar();
		return "study/calendar/calendar";
	}
	
	// qr?????? ?????????
	@GetMapping("/qr-code")
	public String qrCodeGet(HttpSession session, Model model) {
		String mid = ""+session.getAttribute("sMid");
		
		MemberVO vo = memberService.getMemberIdCheck(mid);
		
		model.addAttribute("vo", vo);
		
		return "study/qrCode/qrCode";
	}
	
	@PostMapping("/qr-code")
	@ResponseBody
	public String qrCodePost(HttpServletRequest request, String mid, String param) {
		log.info("param? {}", param);
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		String qrCodeName = service.createQRCode(mid, param, realPath);
		
		return qrCodeName;
	}
	@PostMapping("/qr-code/coupon")
	@ResponseBody
	public String couponPost(HttpServletRequest request, String mid, String category) {
		log.info("category? {}, mid? {}", category, mid);

		 String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		 String qrCodeName = service.createQRCodeCoupon(mid, category, realPath);

		return qrCodeName;
	}
	
	@PostMapping("/qr-code/search")
	@ResponseBody
	public QrCodeVO couponSearchPost(String content) {
		log.info("content? {}", content);
		
		QrCodeVO vo = service.getQrCodeInfo(content);
		
		return vo;
	}
	
	@GetMapping("/qr-code/add/{qr_code}")
	public String couponAdd(@PathVariable("qr_code") String qr_code, Model model) {
		log.info("qr_code", qr_code);
		QrCodeVO vo = service.getQrCodeInfo(qr_code);
		
		model.addAttribute("vo", vo);
		
		return "study/qrCode/qrCodeAdd";
	}
}