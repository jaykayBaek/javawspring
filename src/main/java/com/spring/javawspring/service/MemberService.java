package com.spring.javawspring.service;

import java.util.List;

import com.spring.javawspring.vo.MemberVO;

public interface MemberService {

	public MemberVO getMemberIdCheck(String mid);

	public MemberVO getMemberNickNameCheck(String nickName);

	public int setMemberJoinOk(MemberVO vo);

	public void setVisitMemberProcess(MemberVO vo);

	public int getTotRecordCnt();

	public List<MemberVO> getMemberList(int startIndexNo, int pageSize);

}
