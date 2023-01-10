package com.spring.javawspring.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javawspring.vo.MemberVO;

public interface MemberDAO {

	public MemberVO getMemberIdCheck(@Param("mid") String mid);

	public MemberVO getMemberNickNameCheck(@Param("nickName") String nickName);

	public int setMemberJoinOk(@Param("vo") MemberVO vo);

	public void setMemTotalUpdate(
			@Param("mid") String mid,
			@Param("nowTodayPoint") int nowTodayPoint,
			@Param("todayCnt") int todayCnt);

	public int getTotRecordCnt(@Param("mid") String mid);

	public List<MemberVO> getMemberList(
			@Param("startIndexNo") int startIndexNo,
			@Param("pageSize") int pageSize,
		    @Param("mid") String mid);

	public void setMemberPwdUpdate(
			@Param("mid") String mid,
			@Param("pwd") String pwd);

	public String getMidToNameAndEmail(
			@Param("name") String name, 
			@Param("email") String email);

	public void setMemberDelNoToYes(@Param("mid") String mid);

	
}
