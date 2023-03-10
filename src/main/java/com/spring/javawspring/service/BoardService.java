package com.spring.javawspring.service;

import java.util.List;

import com.spring.javawspring.vo.BoardReplyVO;
import com.spring.javawspring.vo.BoardVO;
import com.spring.javawspring.vo.GoodVO;

public interface BoardService {

	public List<BoardVO> getBoardList(int startIndexNo, int pageSize);

	public int setBoardInput(BoardVO vo);

	public BoardVO getBoardContent(int idx);

	public void setBoardReadNum(int idx);

	public GoodVO getGoodCheck(String part, long idxPart, String mid);

	public List<BoardVO> getPrevNext(int idx);

	public void imgCheck(String content);

	public void setBoardDelete(int idx);

	public void imgDelete(String content);

	public void imgCheckAndUpdate(String content);

	public void setBoardUpdateOk(BoardVO paramVo);

	public void setBoardReply(BoardReplyVO replyVo);

	public List<BoardReplyVO> getBoardReply(int idx);

	public void setBoardReplyDelete(int idx);

	public Integer getMaxLevelOrder(int boardIdx);

	public void setLevelOrderPlusForUpdate(BoardReplyVO replyVo);

	public void setBoardReply2(BoardReplyVO replyVo);

	public int setBoardReplyUpdate(int idx, String content);


}