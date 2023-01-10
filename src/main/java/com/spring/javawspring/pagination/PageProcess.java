package com.spring.javawspring.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javawspring.dao.BoardDAO;
import com.spring.javawspring.dao.GuestDAO;
import com.spring.javawspring.dao.MemberDAO;

@Service
public class PageProcess {
	@Autowired GuestDAO guestDAO;
	@Autowired MemberDAO memberDAO;
	@Autowired BoardDAO boardDAO;
		
	public PageVO getTotRecordCnt(int pag, int pageSize, String section, String part, String searchString) {
		PageVO pageVo = new PageVO();
		
		int totRecordCnt = 0;
		
		totRecordCnt = getTotRecordCnt(section, totRecordCnt, searchString);
		
		int totPage = (totRecordCnt % pageSize)==0 ? totRecordCnt / pageSize : (totRecordCnt / pageSize) + 1;
		int startIndexNo = (pag - 1) * pageSize;
		int curScrStartNo = totRecordCnt - startIndexNo;
		
		int blockSize = 5;
		int curBlock = (pag - 1) / blockSize;
		int lastBlock = (totPage - 1) / blockSize;
		
		pageVo.setPag(pag);
		pageVo.setPageSize(pageSize);
		pageVo.setTotRecCnt(totRecordCnt);
		pageVo.setTotPage(totPage);
		pageVo.setStartIndexNo(startIndexNo);
		pageVo.setCurScrStartNo(curScrStartNo);
		pageVo.setBlockSize(blockSize);
		pageVo.setCurBlock(curBlock);
		pageVo.setLastBlock(lastBlock);
		
		return pageVo;
	}

	private int getTotRecordCnt(String section, int totRecordCnt, String mid) {
		
		if(section.equals("member")) {
			totRecordCnt = memberDAO.getTotRecordCnt(mid);
		} else if(section.equals("guest")) {
			totRecordCnt = guestDAO.getTotRecordCnt();
		} else if(section.equals("board")) {
			totRecordCnt = boardDAO.getTotRecordCnt();
		}
		return totRecordCnt;
	}
	
	
}