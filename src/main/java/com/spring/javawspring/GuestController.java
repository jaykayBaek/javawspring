package com.spring.javawspring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.javawspring.pagination.PageProcess;
import com.spring.javawspring.pagination.PageVO;
import com.spring.javawspring.service.GuestService;
import com.spring.javawspring.vo.GuestVO;

@Controller
@RequestMapping("/guest")
public class GuestController {
	
	@Autowired
	private GuestService service;
	
	@Autowired
	PageProcess pageProcess;
	/*
	@GetMapping(value = "/list")
	public String listGet(Model model,
			// 1.페이지(page)를 결정한다.
			@RequestParam(name="page", defaultValue = "1", required = false) int page) {
		
		// 2. 한 페이지의 분량을 결정한다.
		final int pageSize = 10;
		
		// 3. 총 레코드 건수를 구한다.
		int totRecordCnt = service.getTotRecordCnt();
		
		// 4. 총 페이지 건수를 구한다.
		int totPage = (totRecordCnt % pageSize)==0 ? totRecordCnt / pageSize : (totRecordCnt / pageSize) + 1;
		
		// 5. 현재페이지의 시작 인덱스번호를 구한다.
		int startIndexNo = (page - 1) * pageSize;
		
		// 6. 현재 화면에 보여주는 시작번호를 구한다.
		int curScrStartNo = totRecordCnt - startIndexNo;
		
		// 블록페이징처리.....(3단계) -> 블록의 시작번호를 0번부터 처리했다.
		// 1. 블록의 크기를 결정한다.(여기선 3으로 지정)
		int blockSize = 5;

		// 2. 현재페이지가 위치하고 있는 블록 번호를 구한다.(예:1페이지는 0블록, 3페이지는 0블록, 5페이지는 1블록)
		// int curBlock = (pag % blockSize)==0 ? (pag / blockSize) - 1 : (pag / blockSize);
		int curBlock = (page - 1) / blockSize;
		
		// 3. 마지막블록을 구한다.
//		int lastBlock = (totPage % blockSize)==0 ? (totPage / blockSize) - 1 : (totPage / blockSize);
		int lastBlock = (totPage - 1) / blockSize;
		
		// ArrayList<GuestVO> vos = dao.getGuestList();
		List<GuestVO> vos = service.getGuestList(startIndexNo, pageSize);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pag", page);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStartNo", curScrStartNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);		

		return "guest/guestList";
	}
	*/
	@GetMapping(value = "/list")
	public String listGet(Model model,
			// 1.페이지(page)를 결정한다.
			@RequestParam(name="page", defaultValue = "1", required = false) int pag,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize) {
		
		PageVO pageVo = pageProcess.getTotRecordCnt(pag,pageSize,"guest","","");
		
		// ArrayList<GuestVO> vos = dao.getGuestList();
		List<GuestVO> vos = service.getGuestList(pageVo.getStartIndexNo(), pageSize);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVo", pageVo);

		return "guest/guestList";
	}
	
	@GetMapping(value = "/input")
	public String inputGet() {
		
		
		return "guest/guestInput";
	}
	
	@PostMapping(value = "/input")
	public String inputPost(GuestVO vo) {
		service.setGuestInput(vo);
		return "redirect:/msg/guestInputOk";
	}
	@GetMapping(value = "/delete")
	public String deleteGet(int idx) {
		service.setGuestDelete(idx);
		
		return "redirect:/msg/guestDeleteOk";
	}
	
}
