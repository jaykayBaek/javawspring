package com.spring.javawspring.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javawspring.dao.GuestDAO;
import com.spring.javawspring.vo.GuestVO;

@Service
public class GuestServiceImpl implements GuestService {
	@Autowired
	final GuestDAO dao = null;

	@Override
	public List<GuestVO> getGuestList(int startIndexNo, int pageSize) {
		return dao.getGuestList(startIndexNo, pageSize);
	}
	
	@Override
	public void setGuestInput(GuestVO vo) {
		dao.setGuestInput(vo);
	}

	@Override
	public int getTotRecordCnt() {
		return dao.getTotRecordCnt();
	}

	@Override
	public void setGuestDelete(int idx) {
		dao.setGuestDelete(idx);
		
	}
}
