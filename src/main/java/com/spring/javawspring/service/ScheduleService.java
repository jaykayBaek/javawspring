package com.spring.javawspring.service;

import java.util.List;

import com.spring.javawspring.vo.ScheduleVO;

public interface ScheduleService {

	public void getCalendar();

	public List<ScheduleVO> getScheduleMenu(String mid, String ymd);

	public void setSchedule(ScheduleVO vo);

	public void setScheduleUpdate(ScheduleVO vo);

	public void setScheduleDelete(int idx);

}
