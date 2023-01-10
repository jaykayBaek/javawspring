package com.spring.javawspring;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.javawspring.pagination.PageProcess;
import com.spring.javawspring.pagination.PageVO;
import com.spring.javawspring.service.BoardService;
import com.spring.javawspring.service.MemberService;
import com.spring.javawspring.vo.BoardVO;
import com.spring.javawspring.vo.MemberVO;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService service;
	
	@Autowired
	private PageProcess pageProcess;
	
	@Autowired
	private MemberService memberServce;
	
	@GetMapping(value = "/list")
	public String boardListGet(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize
			) {
		PageVO pageVo = pageProcess.getTotRecordCnt(pag, pageSize, "board", "", "");
		
		List<BoardVO> vos = service.getBoardList(pageVo.getStartIndexNo(), pageSize);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVo", pageVo);
		
		return "board/boardList";
	}
	
	@GetMapping(value = "/input")
	public String boardInputGet(int pag, int pageSize, Model model, HttpSession session) {
		String mid = ""+session.getAttribute("sMid");
		MemberVO vo = memberServce.getMemberIdCheck(mid);
		String email = vo.getEmail() == null?"":vo.getEmail();
		vo.setEmail(email);
		
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("email", vo.getEmail());
		model.addAttribute("homePage", vo.getHomePage());
		return "board/boardInput";
	}
	@PostMapping(value = "/input")
	public String boardInputPost(BoardVO vo) {
		int res = service.setBoardInput(vo);
		if(res==0) {
			return "redirect:/msg/boardInputFail";
		}
		return "redirect:/msg/boardInputSuccess";
	}
	@GetMapping(value = "/content")
	public String boardContentGet(Model model, int idx, int pag, int pageSize) {
		service.setBoardReadNum(idx);
		
		BoardVO vo = service.getBoardContent(idx);
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		return "board/boardContent";
	}
}