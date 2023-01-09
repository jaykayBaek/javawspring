package com.spring.javawspring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/msg")
public class MessageController {
	
	@GetMapping(value="/{msgFlag}")
	public String messageGet(
			@PathVariable String msgFlag, Model model, 
			@RequestParam(value = "mid", defaultValue = "", required = false) String mid) {
		
		if(msgFlag.equals("memberLoginOk")) {
			model.addAttribute("msg", mid+"님 로그인되셨습니다.");
			model.addAttribute("url", "member/main");
		}
		else if(msgFlag.equals("memberLoginNo")) {
			model.addAttribute("msg", "아이디나 비밀번호를 다시 확인해주세요");
			model.addAttribute("url", "member/login");
		}
		else if(msgFlag.equals("memberUserDelYes")) {
			model.addAttribute("msg", "회원탈퇴한 계정입니다.");
			model.addAttribute("url", "member/login");
		}
		else if(msgFlag.equals("memberLogout")) {
			model.addAttribute("msg", mid+"님 로그아웃되셨습니다");
			model.addAttribute("url", "member/login");
		}
		else if(msgFlag.equals("guestInputOk")) {
			model.addAttribute("msg", "방명록에 글이 등록되었습니다");
			model.addAttribute("url", "guest/list");
		}
		else if(msgFlag.equals("guestDeleteOk")) {
			model.addAttribute("msg", "게시글이 삭제되었습니다");
			model.addAttribute("url", "guest/list");
		}
		else if(msgFlag.equals("memberJoinSuccess")) {
			model.addAttribute("msg", "회원가입 완료되었습니다");
			model.addAttribute("url", "member/login");
		}
		else if(msgFlag.equals("memberJoinIdFail")) {
			model.addAttribute("msg", "중복된 아이디가 있습니다.");
			model.addAttribute("url", "member/join");
		}
		else if(msgFlag.equals("memberJoinNickNameFail")) {
			model.addAttribute("msg", "중복된 닉네임이 있습니다.");
			model.addAttribute("url", "member/join");
		}
		/*--- 등급 관련 ---*/
		else if(msgFlag.equals("levelAdminNo")) {
			model.addAttribute("msg", "관리자가 아니시거나 세션이 만료되었습니다. 로그인해주세요.");
			model.addAttribute("url", "member/login");
		}
		else if(msgFlag.equals("levelMemberNo")) {
			model.addAttribute("msg", "로그인 이후 사용할 수 있는 서비스입니다.");
			model.addAttribute("url", "member/login");
		}
		else if(msgFlag.equals("levelCheck")) {
			model.addAttribute("msg", "등업이후 사용할 수 있는 서비스입니다.");
			model.addAttribute("url", "member/login");
		}
		
		else if(msgFlag.equals("mailSendOk")) {
			model.addAttribute("msg", "메일이 성공적으로 전송되었습니다.");
			model.addAttribute("url", "study/mail/mailForm");
		}
		
		return "include/message";
	}
}
