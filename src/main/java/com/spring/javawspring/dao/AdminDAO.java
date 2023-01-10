package com.spring.javawspring.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javawspring.vo.MemberVO;

public interface AdminDAO {

	public int setMemberLevelUp(
			@Param("idx") int idx,
			@Param("level") int level);

	public List<MemberVO> getBeforeLeaveMemberList();

	public int setUserLeave(@Param("idx") int idx);

}
