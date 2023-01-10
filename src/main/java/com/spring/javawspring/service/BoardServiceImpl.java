package com.spring.javawspring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javawspring.dao.BoardDAO;
import com.spring.javawspring.vo.BoardVO;

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
}
