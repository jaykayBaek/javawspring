package com.spring.javawspring;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javawspring.service.ScheduleService;
import com.spring.javawspring.vo.ScheduleVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/schedule")
public class ScheduleController {
	
	@Autowired
	private ScheduleService service;
	
	@GetMapping("/schedule")
	public String scheduleMain() {
		service.getCalendar();
		return "schedule/schedule";
	}

	@GetMapping("/menu")
	public String menuGet(HttpSession session, String ymd, Model model) {
		String mid = ""+session.getAttribute("sMid");
		log.debug("mid? {}, ymd? {}", mid, ymd);
		
		List<ScheduleVO> vos = service.getScheduleMenu(mid, ymd);
		
		log.debug("vos? {}", vos);
		
		model.addAttribute("vos", vos);
		model.addAttribute("ymd", ymd);
		model.addAttribute("scheduleCnt", vos.size());

		return "schedule/scheduleMenu";
	}
	
	@ResponseBody
	@PostMapping("/input")
	public String scheduleInput(ScheduleVO vo) {
		log.info("vo? {}" , vo);
		
		service.setSchedule(vo);

		return "";
	}
	
	@ResponseBody
	@PostMapping("/update")
	public String scheduleUpdate(ScheduleVO vo) {
		log.debug("vo? {}" , vo);
		
		service.setScheduleUpdate(vo);

		return "1";
	}
	
	@ResponseBody
	@PostMapping("/delete")
	public String scheduleDelete(int idx) {
		log.debug("idx{}" , idx);
//		
		service.setScheduleDelete(idx);

		return "1";
	}
	
}