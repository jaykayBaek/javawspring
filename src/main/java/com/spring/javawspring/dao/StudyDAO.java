package com.spring.javawspring.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javawspring.vo.GuestVO;

public interface StudyDAO {

	public GuestVO getGuestMid(@Param(value = "name") String mid);

	public List<GuestVO> getGuestName(@Param(value = "name") String mid);

	public List<GuestVO> getSearchResult(
			@Param(value = "search") String search, 
			@Param(value = "condition") String condition);
	
	
}
