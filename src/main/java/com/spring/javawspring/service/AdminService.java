package com.spring.javawspring.service;

import java.util.List;

import com.spring.javawspring.vo.MemberVO;

public interface AdminService {

	public int setMemberLevelUp(int idx, int level);

	public List<MemberVO> getBeforeLeaveMemberList();

	public int setUserLeave(int idx);
	
}
