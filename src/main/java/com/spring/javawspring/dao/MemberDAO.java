package com.spring.javawspring.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javawspring.vo.MemberVO;

public interface MemberDAO {

	MemberVO getMemberIdCheck(@Param("mid") String mid);

	MemberVO getMemberNickNameCheck(@Param("nickName") String nickName);

	int setMemberJoinOk(@Param("vo") MemberVO vo);

	void setMemTotalUpdate(
			@Param("mid") String mid,
			@Param("nowTodayPoint") int nowTodayPoint,
			@Param("todayCnt") int todayCnt);

	int getTotRecordCnt();

	List<MemberVO> getMemberList(
			@Param("startIndexNo") int startIndexNo,
			@Param("pageSize") int pageSize);

	void setMemberPwdUpdate(
			@Param("mid") String mid,
			@Param("pwd") String pwd);

	String getMidToNameAndEmail(
			@Param("name") String name, 
			@Param("email") String email);

	void setMemberDelNoToYes(@Param("mid") String mid);

	
}
