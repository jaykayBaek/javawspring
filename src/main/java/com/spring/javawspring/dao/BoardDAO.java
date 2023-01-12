package com.spring.javawspring.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javawspring.vo.BoardReplyVO;
import com.spring.javawspring.vo.BoardVO;
import com.spring.javawspring.vo.GoodVO;

public interface BoardDAO {

	public List<BoardVO> getBoardList(
			@Param("startIndexNo") int startIndexNo,
			@Param("pageSize") int pageSize);

	public int getTotRecordCnt(@Param("part") String part, @Param("searchString") String searchString);

	public int setBoardInput(@Param("vo")BoardVO vo);

	public BoardVO getBoardContent(@Param("idx")int idx);

	public void setBoardReadNum(@Param("idx") int idx);

	public GoodVO getGoodCheck(@Param("part") String part, @Param("idxPart")long idxPart, @Param("mid")String mid);

	public List<BoardVO> getPrevNext(@Param("idx") int idx);

	public void setBoardDelete(@Param("idx")int idx);

	public void setBoardUpdateOk(@Param("vo") BoardVO vo);

	public void setBoardReply(@Param("replyVo") BoardReplyVO replyVo);

	public List<BoardReplyVO> getBoardReply(@Param("idx") int idx);

	public void setBoardReplyDelete(@Param("idx")int idx);

	public Integer getMaxLevelOrder(@Param("boardIdx") int boardIdx);

	public void setLevelOrderPlusForUpdate(@Param("replyVo") BoardReplyVO replyVo);

	public void setBoardReply2(@Param("replyVo") BoardReplyVO replyVo);

	public int setBoardReplyUpdate(@Param("idx") int idx, @Param("content") String content);
	
}
