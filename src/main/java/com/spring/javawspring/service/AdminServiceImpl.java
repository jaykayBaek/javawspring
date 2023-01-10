package com.spring.javawspring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javawspring.dao.AdminDAO;
import com.spring.javawspring.vo.MemberVO;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminDAO dao;

	@Override
	public int setMemberLevelUp(int idx, int level) {
		return dao.setMemberLevelUp(idx, level);
	}

	@Override
	public List<MemberVO> getBeforeLeaveMemberList() {
		return dao.getBeforeLeaveMemberList();
	}

	@Override
	public int setUserLeave(int idx) {
		return dao.setUserLeave(idx);
	}
}
