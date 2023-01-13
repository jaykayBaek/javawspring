package com.spring.javawspring;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.spring.javawspring.pagination.PageProcess;
import com.spring.javawspring.pagination.PageVO;
import com.spring.javawspring.service.BoardService;
import com.spring.javawspring.service.MemberService;
import com.spring.javawspring.vo.BoardReplyVO;
import com.spring.javawspring.vo.BoardVO;
import com.spring.javawspring.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board")
@Slf4j
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
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize,
			@RequestParam(name="search", defaultValue="", required = false) String search,
			@RequestParam(name="searchString", defaultValue="", required = false) String searchString
			) {
		log.info("listget search{}, searchSTring{}", search, searchString);
		
		PageVO pageVo = pageProcess.getTotRecordCnt(pag, pageSize, "board", search, searchString);
		List<BoardVO> vos = service.getBoardList(pageVo.getStartIndexNo(), pageSize);
		
		log.debug("getpageVo? {}", pageVo);
		log.debug("getvos? {}", vos);

		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVo", pageVo);
		
		return "board/boardList";
	}
	
	@PostMapping("/list")
	public String boardListPost(
			Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize,
			@RequestParam(name="search", required = false) String search,
			@RequestParam(name="searchString", required = false) String searchString
			) {
		log.info("postpag? {}, pageSize? {}", pag, pageSize);
		log.info("postsearch? {}, searchString? {}", search, searchString);
		
		PageVO pageVo = pageProcess.getTotRecordCnt(pag, pageSize, "board", search, searchString);
		List<BoardVO> vos = service.getBoardList(pageVo.getStartIndexNo(), pageSize);
		
		log.debug("postpageVo? {}", pageVo);
		log.debug("postvos? {}", vos);

		model.addAttribute("vos", vos);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("search", search);
		model.addAttribute("searchString", searchString);
		
		return "redirect:/board/list";
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
		
		/*--- 컨텐트에 이미지가 저장되어 있다면? 저장된 이미지만 골라서 /resources/data/board에 저장시킨다.
		 * why? 이미지 5개 중에서 3개만 등록할 때 ckeditor(tmp) 폴더에는 5개의 사진이 있고, 그중 업로드된 3개만 올리기 위해 ---*/
		service.imgCheck(vo.getContent());
		
		/*--- 이미지 복사작업이 종료되면, board 폴더에 실제로 저장된 파일명을 DB에 저장시켜준다. 
		 * (/resources/data/ckeditor ==>>> /resources/data/board/ 경로로---*/
		vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/ckeditor/board/"));
		
		int res = service.setBoardInput(vo);
		if(res==0) {
			return "redirect:/msg/boardInputFail";
		}
		return "redirect:/msg/boardInputSuccess";
	}
	
	@GetMapping(value = "/content")
	public String boardContentGet(Model model, int idx, int pag, int pageSize, HttpSession session) {
		service.setBoardReadNum(idx);
		
		BoardVO vo = service.getBoardContent(idx);
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		String mid = ""+session.getAttribute("sMid");
		// db에서 현재 게시글 좋아요 눌렀는지?
		long partIdx = (long) idx;

		/*--- 이전글 / 다음글 가져오기 ---*/
		List<BoardVO> pnVos = service.getPrevNext(idx);
		
		List<BoardReplyVO> replyVos = service.getBoardReply(idx);
		
		model.addAttribute("pnVos", pnVos);
		model.addAttribute("replyVos", replyVos);
		
		return "board/boardContent";
	}
	
	@GetMapping(value = "/delete")
	public String deletePost(int idx, int pag, int pageSize, Model model) {
		
		/*--- 게시글에 사진이 존재한다면 서버에 있는 사진 파일을 먼저 삭제한다. ---*/
		BoardVO vo = service.getBoardContent(idx);
		
		if(vo.getContent().indexOf("src=\"/") != -1) {
			service.imgDelete(vo.getContent());
		}
		
		// DB에서 실제로 존재하는 게시글을 삭제처리한다.
		service.setBoardDelete(idx);
		
		model.addAttribute("parameter", "?pag="+pag+"&pageSize="+pageSize);
		
		return "redirect:/msg/boardDeleteSuccess";
	}
	
	//수정하기 홈 호출
	@GetMapping("/update")
	public String updateGet(Model model, int idx, int pag, int pageSize) {
		/*--- 수정시 원본파일에 그림파일이 있다면, 현재폴더(board)의 그림파일을 ckeditor(tmp)폴더로 복사시켜준다. ---*/
		BoardVO vo = service.getBoardContent(idx);
		
		if(vo.getContent().indexOf("src=\"/") != -1) {
			service.imgCheckAndUpdate(vo.getContent());
		}
		
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		return "board/boardUpdate";
	}
	
	/*--- 변경 내용 수정 처리(그림포함) ---*/
	@PostMapping("/update")
	public String updatePost(Model model, BoardVO paramVo, int idx, int pag, int pageSize) {
		/*--- 수정된 자료(parameter로 넘어온 paramVo)와 기존의 자료(service->DAO 거친 자료)가 같다면 수정 필요 없음 ---*/
		BoardVO originalVo = service.getBoardContent(idx);
		
		/*--- 내용(content)의 수정이 없음 -> 이미지 등 수정, 삭제가 필요 없음 !(not)연산자이기 때문에 수정이 있다는 뜻임
		 * 오해하면 안되는 부분: 제목 수정은 가능함. 지금 중요한 것은 수정에 따른 '이미지' 처리 ---*/
		if(!originalVo.getContent().equals(paramVo.getContent())) {
			// 1. 원래 있던(board) 그림 삭제할 것이다. 수정창(board/upload) GET 맵핑 시 그림 파일을 ckeditor(tmp)폴더로 복사시켰음.
			// 오리지널 그림을 삭제해야 한다. -1은 없다는 것, 그것의 부정 연산자란 뜻은? 그림이 있다는 뜻.
			if(originalVo.getContent().indexOf("src=\"") != -1) {
				service.imgDelete(originalVo.getContent());
			}
			
			// 2. paramVo.getContent()에 들어있는 이미지 파일의 경로는? ckeditor/board에 있다. 이 경로를 ckeditor(tmp) 폴더로 변경해야 함 
			paramVo.setContent(paramVo.getContent().replace("/data/ckeditor/board/", "/data/ckeditor/"));
			
			/* 3. 앞의 모든 준비가 끝나면, 파일을 처음 업로드한 것과 같은 작업을 처리한다.
			 * 3번의 작업은 처음 게시글을 올릴 때 파일 복사 작업과 동일한 작업이다.
			 */
			service.imgCheck(paramVo.getContent());
			
			/* --- 4. 파일 업로드가 끝나면 다시 경로를 ckeditor 경로에서 ckeditor/board로 변경시켜야 한다. 
			 * (즉, 변경된 paramVo.getContent()를 vo.setContent() 처리한다.) --- */
			paramVo.setContent(paramVo.getContent().replace("/data/ckeditor/", "/data/ckeditor/board/"));
			
			/* --- --- */

		}
		
		// vo를 DB에 UPDATE 시킨다.
		service.setBoardUpdateOk(paramVo);
		
		model.addAttribute("parameter", "?pag="+pag+"&pageSize="+pageSize);
		return "redirect:/msg/boardUpdateSuccess";
	}
	
	@PostMapping("/reply")
//	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String boardReply(@ModelAttribute BoardReplyVO replyVo) {
		Integer levelOrder = 0;
		levelOrder = service.getMaxLevelOrder(replyVo.getBoardIdx());
		
		if(levelOrder!=null) {
			levelOrder = levelOrder+1;
		}
		else {
			levelOrder = 0;
		}
		
		log.info("levelOrder{}? ", levelOrder);
		
		replyVo.setLevelOrder((int)levelOrder);
		
		service.setBoardReply(replyVo);
		
		return "1";
	}
	
	@PostMapping("/reply2")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String boardReply2Post(@ModelAttribute BoardReplyVO replyVo) {
		service.setLevelOrderPlusForUpdate(replyVo);
		replyVo.setLevel(replyVo.getLevel()+1);
		replyVo.setLevelOrder(replyVo.getLevelOrder()+1);
		
		service.setBoardReply2(replyVo);
		
		return "";
	}
	@ResponseBody
	@DeleteMapping("/reply/{idx}")
	public String boardReplyDelete(@PathVariable("idx") int idx) {
		service.setBoardReplyDelete(idx);
		return "1";
	}
	
	@ResponseBody
	@PostMapping("/reply/{idx}")
	public String boardReplyUpdatePost(
			@PathVariable("idx") int idx,
			String content) {
		log.info("idx? {}, content? {}", idx, content);
		int res = service.setBoardReplyUpdate(idx, content);
		
		return res+"";
	}
}