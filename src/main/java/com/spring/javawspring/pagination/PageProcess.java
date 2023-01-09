package com.spring.javawspring.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javawspring.dao.GuestDAO;
import com.spring.javawspring.dao.MemberDAO;

@Service
public class PageProcess {
	@Autowired GuestDAO guestDAO;
	@Autowired MemberDAO memberDAO;
		
	public PageVO getTotRecordCnt(int pag, int pageSize, String section, String part, String SearchString) {
		PageVO vo = new PageVO();
		
		int totRecordCnt = 0;
		
		if(section.equals("member")) {
			totRecordCnt = memberDAO.getTotRecordCnt();
		} else if(section.equals("guest")) {
			totRecordCnt = guestDAO.getTotRecordCnt();
		}
		
		
		
		int totPage = (totRecordCnt % pageSize)==0 ? totRecordCnt / pageSize : (totRecordCnt / pageSize) + 1;
		int startIndexNo = (pag - 1) * pageSize;
		int curScrStartNo = totRecordCnt - startIndexNo;
		
		int blockSize = 5;
		int curBlock = (pag - 1) / blockSize;
		int lastBlock = (totPage - 1) / blockSize;
		
		vo.setPag(pag);
		vo.setPageSize(pageSize);
		vo.setTotRecCnt(totRecordCnt);
		vo.setTotPage(totPage);
		vo.setStartIndexNo(startIndexNo);
		vo.setCurScrStartNo(curScrStartNo);
		vo.setBlockSize(blockSize);
		vo.setCurBlock(curBlock);
		vo.setLastBlock(lastBlock);
		
		return vo;
	}
	
	
}