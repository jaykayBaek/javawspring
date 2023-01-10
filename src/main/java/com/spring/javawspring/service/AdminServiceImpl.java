package com.spring.javawspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javawspring.dao.AdminDAO;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminDAO dao;

	@Override
	public int setMemberLevelUp(int idx, int level) {
		return dao.setMemberLevelUp(idx, level);
	}
}
