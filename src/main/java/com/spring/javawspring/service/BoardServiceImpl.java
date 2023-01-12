package com.spring.javawspring.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.javawspring.dao.BoardDAO;
import com.spring.javawspring.vo.BoardReplyVO;
import com.spring.javawspring.vo.BoardVO;
import com.spring.javawspring.vo.GoodVO;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	BoardDAO dao;

	@Override
	public List<BoardVO> getBoardList(int startIndexNo, int pageSize) {
		return dao.getBoardList(startIndexNo, pageSize);
	}

	@Override
	public int setBoardInput(BoardVO vo) {
		return dao.setBoardInput(vo);
	}

	@Override
	public BoardVO getBoardContent(int idx) {
		return dao.getBoardContent(idx);
	}

	@Override
	public void setBoardReadNum(int idx) {
		dao.setBoardReadNum(idx);

	}

	@Override
	public GoodVO getGoodCheck(String part, long idxPart, String mid) {
		return dao.getGoodCheck(part, idxPart, mid);
	}

	@Override
	public List<BoardVO> getPrevNext(int idx) {
		return dao.getPrevNext(idx);
	}

	@Override
	public void imgCheck(String content) {
		//     0         1         2         3         4         5         6         7         8         9                    
		//     0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789
		//<img src="/javawspring/data/ckeditor/230111121457_9781781106303.jpg" style="height:750px; width:500px" />
		/*--- content안에 그림파일이 존재할 때만 작업을 수행한다. ---*/
		if(content.indexOf("src=\"/") == -1) {
			return;
		}
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor/");
		
		final int position = 32;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		
		boolean run = true;
		while(run) {
			/*--- 그림파일명 꺼내오기 ---*/
			String imgFile = nextImg.substring(0,nextImg.indexOf("\""));
			
			String originalFilePath = uploadPath + imgFile;
			String copyFilePath = uploadPath+"board/" + imgFile;
			
			/*--- board 폴더에 파일을 복사하고자 한다. ---*/
			fileSaveToBoard(originalFilePath, copyFilePath);
			
			/*--- 그림이 없을 경우---*/
			if(nextImg.indexOf("src=\"/") == -1) {
				run = false;
			}else {
				nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
			}
		}
		
	}

	private void fileSaveToBoard(String originalFilePath, String copyFilePath) {
		File originalFile = new File(originalFilePath);
		File copyFile = new File(copyFilePath);
		
		try {
			FileInputStream fis = new FileInputStream(originalFile);
			FileOutputStream fos = new FileOutputStream(copyFile);
			
			/*--- 2k로 전송하고자 한다. ---*/
			byte[] buffer = new byte[2048];
			int cnt = 0;
			
			/*--- 인풋 스트림이 buffer(2k)만큼 읽어드리되, 읽을 수 있을 만큼(-1) 읽기 ---*/
			while((cnt = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, cnt);
			}
			/*--- 전송 시 찌꺼기 재거 ---*/
			fos.flush();
			fos.close();
			fis.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setBoardDelete(int idx) {
		dao.setBoardDelete(idx);
	}

	@Override
	public void imgDelete(String content) {
		//     0         1         2         3         4         5         6         7         8         9                    
		//     0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789
		//<img src="/javawspring/data/ckeditor/board/230111121457_9781781106303.jpg" style="height:750px; width:500px" />
		/*--- content안에 그림파일이 존재할 때만 작업을 수행한다. ---*/
		if(content.indexOf("src=\"/") == -1) {
			return;
		}
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor/board/");
		
		final int position = 38;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		
		boolean run = true;
		while(run) {
			String imgFile = nextImg.substring(0,nextImg.indexOf("\""));
			
			String originalFilePath = uploadPath + imgFile;
			String copyFilePath = uploadPath+"board/" + imgFile;
			
			/*--- board 폴더에 파일을 삭제하고자 한다. ---*/
			fileDelete(originalFilePath);
			
			/*--- 그림이 없을 경우---*/
			if(nextImg.indexOf("src=\"/") == -1) {
				run = false;
			}else {
				nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
			}
		}
		
	}

	private void fileDelete(String originalFilePath) {
		File deleteFile = new File(originalFilePath);
		if(deleteFile.exists()) {
			deleteFile.delete();
		}
	}

	@Override
	public void imgCheckAndUpdate(String content) {
		//     0         1         2         3         4         5         6         7         8         9                    
		//     0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789
		//<img src="/javawspring/data/ckeditor/board/230111121457_9781781106303.jpg" style="height:750px; width:500px" />
		/*--- content안에 그림파일이 존재할 때만 작업을 수행한다. ---*/
		if(content.indexOf("src=\"/") == -1) {
			return;
		}
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor/board/");
		
		final int position = 38;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		
		boolean run = true;
		while(run) {
			/*--- 그림파일명 꺼내오기 ---*/
			String imgFile = nextImg.substring(0,nextImg.indexOf("\""));
			
			String originalFilePath = uploadPath + imgFile;
			String copyFilePath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor/"+imgFile);
			
			/*--- board 폴더에 파일을 복사하고자 한다. ---*/
			fileSaveToBoard(originalFilePath, copyFilePath);
			
			/*--- 그림이 없을 경우---*/
			if(nextImg.indexOf("src=\"/") == -1) {
				run = false;
			}else {
				nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
			}
		}
	}

	@Override
	public void setBoardUpdateOk(BoardVO vo) {
		dao.setBoardUpdateOk(vo);
	}

	@Override
	public void setBoardReply(BoardReplyVO replyVo) {
		dao.setBoardReply(replyVo);
	}

	@Override
	public List<BoardReplyVO> getBoardReply(int idx) {
		return dao.getBoardReply(idx);
	}

	@Override
	public void setBoardReplyDelete(int idx) {
		dao.setBoardReplyDelete(idx);
	}

	@Override
	public Integer getMaxLevelOrder(int boardIdx) {
		// TODO Auto-generated method stub
		return dao.getMaxLevelOrder(boardIdx);
	}

	@Override
	public void setLevelOrderPlusForUpdate(BoardReplyVO replyVo) {
		dao.setLevelOrderPlusForUpdate(replyVo);
	}

	@Override
	public void setBoardReply2(BoardReplyVO replyVo) {
		dao.setBoardReply2(replyVo);
	}

	@Override
	public int setBoardReplyUpdate(int idx, String content) {
		return dao.setBoardReplyUpdate(idx, content);
	}
	
	
}
