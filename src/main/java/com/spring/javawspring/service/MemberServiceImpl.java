package com.spring.javawspring.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javawspring.common.JavawspringProvide;
import com.spring.javawspring.dao.MemberDAO;
import com.spring.javawspring.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	MemberDAO dao;

	@Override
	public MemberVO getMemberIdCheck(String mid) {
		
		return dao.getMemberIdCheck(mid);
	}

	@Override
	public MemberVO getMemberNickNameCheck(String nickName) {
		return dao.getMemberNickNameCheck(nickName);
	}

	@Override
	public int setMemberJoinOk(MultipartFile fName, MemberVO vo) {
		int res = 0;
		
		// 업로드된 사진을 서버 파일시스템에 저장시켜준다.
		try {
			String oFileName = fName.getOriginalFilename();
			
			if(oFileName.equals("")) {
				vo.setPhoto("noimage.jpg");
			}
			else {
				UUID uuid = UUID.randomUUID();
				String saveFileName = uuid + "_" +oFileName;
				
				JavawspringProvide.writeFile(fName, saveFileName, "member");
				vo.setPhoto(saveFileName);
			}
			res =  dao.setMemberJoinOk(vo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public void setVisitMemberProcess(MemberVO vo) {
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strNow = sdf.format(now);
		
		// 오늘 처음 방문시는 오늘 방문카운트(todayCnt)를 0으로 셋팅한다. 
		if(!(vo.getLastDate().substring(0,10).equals(strNow))) {
//			dao.setTodayCntUpdate(vo.getMid());
			vo.setTodayCnt(0);
		}
		int todayCnt = vo.getTodayCnt() + 1;
		
		int nowTodayPoint = 0;
		if(vo.getTodayCnt() >= 5) {
			nowTodayPoint = vo.getPoint();
		}
		else {
			nowTodayPoint = vo.getPoint() + 10;
		}
		// 오늘 재방문이라면 '총방문수','오늘방문수','포인트' 누적처리
		dao.setMemTotalUpdate(vo.getMid(), nowTodayPoint, todayCnt);
		
	}

	@Override
	public int getTotRecordCnt(String mid) {
		return dao.getTotRecordCnt(mid);
	}

	@Override
	public List<MemberVO> getMemberList(int startIndexNo, int pageSize, String mid) {
		
		return dao.getMemberList(startIndexNo, pageSize, mid);
	}

	@Override
	public void setMemberPwdUpdate(String mid, String pwd) {
		dao.setMemberPwdUpdate(mid, pwd);
	}

	@Override
	public String getMidToNameAndEmail(String name, String toMail) {
		return dao.getMidToNameAndEmail(name, toMail);
	}

	@Override
	public void setMemberDelNoToYes(String mid) {
		dao.setMemberDelNoToYes(mid);
	}


	
}