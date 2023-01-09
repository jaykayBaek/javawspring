package com.spring.javawspring.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.javawspring.vo.MemberVO;

public interface MemberService {

	public MemberVO getMemberIdCheck(String mid);

	public MemberVO getMemberNickNameCheck(String nickName);

	public int setMemberJoinOk(MultipartFile fName, MemberVO vo);

	public void setVisitMemberProcess(MemberVO vo);

	public int getTotRecordCnt();

	public List<MemberVO> getMemberList(int startIndexNo, int pageSize);

	public void setMemberPwdUpdate(String mid, String pwd);

	public String getMidToNameAndEmail(String name, String toMail);

	public void setMemberDelNoToYes(String mid);

}
