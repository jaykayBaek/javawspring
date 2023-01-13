package com.spring.javawspring.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javawspring.dao.StudyDAO;
import com.spring.javawspring.vo.GuestVO;

@Service
public class StudyServiceImpl implements StudyService {
	@Autowired
	StudyDAO dao;

	@Override
	public String[] getCityStringArr(String state) {
		String[] strArr = new String[100];

		if (state.equals("서울")) {
			strArr[0] = "강남구";
			strArr[1] = "서초구";
			strArr[2] = "동대문구";
			strArr[3] = "성북구";
			strArr[4] = "마포구";
			strArr[5] = "강동구";
			strArr[6] = "관악구";
			strArr[7] = "중구";
			strArr[8] = "서구";
			strArr[9] = "송파구";
		} else if (state.equals("경기")) {
			strArr[0] = "수원시";
			strArr[1] = "이천시";
			strArr[2] = "일산시";
			strArr[3] = "용인시";
			strArr[4] = "시흥시";
			strArr[5] = "광명시";
			strArr[6] = "광주시";
			strArr[7] = "의정부시";
			strArr[8] = "평택시";
			strArr[9] = "안성시";
		} else if (state.equals("충북")) {
			strArr[0] = "청주시";
			strArr[1] = "충주시";
			strArr[2] = "괴산군";
			strArr[3] = "진천군";
			strArr[4] = "제천시";
			strArr[5] = "음성군";
			strArr[6] = "옥천군";
			strArr[7] = "영동군";
			strArr[8] = "증평군";
			strArr[9] = "단양군";
		} else if (state.equals("충남")) {
			strArr[0] = "천안시";
			strArr[1] = "병천시";
			strArr[2] = "옥산군";
			strArr[3] = "아산시";
			strArr[4] = "공주시";
			strArr[5] = "당진군";
			strArr[6] = "보령시";
			strArr[7] = "계룡시";
			strArr[8] = "논산시";
			strArr[9] = "예산군";
		}
		return strArr;
	}

	@Override
	public List<String> getCityList(String state) {
		List<String> list = new ArrayList<String>();

		if (state.equals("서울")) {
			list.add("강남구");
			list.add("서초구");
			list.add("동대문구");
			list.add("성북구");
			list.add("마포구");
			list.add("강동구");
			list.add("관악구");
			list.add("중구");
			list.add("서구");
			list.add("송파구");
		} else if (state.equals("경기")) {
			list.add("수원시");
			list.add("이천시");
			list.add("일산시");
			list.add("용인시");
			list.add("시흥시");
			list.add("광명시");
			list.add("광주시");
			list.add("의정부시");
			list.add("평택시");
			list.add("안성시");
		} else if (state.equals("충북")) {
			list.add("청주시");
			list.add("충주시");
			list.add("괴산군");
			list.add("진천군");
			list.add("제천시");
			list.add("음성군");
			list.add("옥천군");
			list.add("영동군");
			list.add("증평군");
			list.add("단양군");
		} else if (state.equals("충남")) {
			list.add("천안시");
			list.add("병천시");
			list.add("옥산군");
			list.add("아산시");
			list.add("공주시");
			list.add("당진군");
			list.add("보령시");
			list.add("계룡시");
			list.add("논산시");
			list.add("예산군");
		}

		return list;
	}

	@Override
	public GuestVO getGuestMid(String mid) {

		return dao.getGuestMid(mid);
	}

	@Override
	public List<GuestVO> getGuestName(String mid) {

		return dao.getGuestName(mid);
	}

	@Override
	public List<GuestVO> getSearchResult(String search, String condition) {
		return dao.getSearchResult(search, condition);
	}

	@Override
	public String fileUpload(MultipartFile fName) {
		String res = "0";
		try {
			UUID uuid = UUID.randomUUID();
			String oFileName = fName.getOriginalFilename();
			String saveFileName = uuid + "_" + oFileName;

			writeFile(fName, saveFileName);
			res = "1";
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

		return res;
	}

	public void writeFile(MultipartFile fName, String saveFileName) throws IOException {
		byte[] data = fName.getBytes();

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		// request.getRealPath("/resources/pds/temp/");
		String realPath = request.getSession().getServletContext().getRealPath("/resources/pds/temp/");
		FileOutputStream fos = new FileOutputStream(realPath + saveFileName);
		fos.write(data);
		fos.close();
	}

	@Override
	public void getCalendar() {
		// model객체를 사용하게되면 불필요한 메소드가 많이 따라오기에 여기서는 request객체를 사용했다.
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		// 오늘 날짜 저장시켜둔다.(calToday변수, 년(toYear), 월(toMonth), 일(toDay))
		Calendar calToday = Calendar.getInstance();
		int toYear = calToday.get(Calendar.YEAR);
		int toMonth = calToday.get(Calendar.MONTH);
		int toDay = calToday.get(Calendar.DATE);

		// 화면에 보여줄 해당 '년(yy)/월(mm)'을 셋팅하는 부분(처음에는 오늘 년도와 월을 가져오지만, '이전/다음'버튼 클릭하면 해당 년과
		// 월을 가져오도록 한다.
		Calendar calView = Calendar.getInstance();
		int yy = request.getParameter("yy") == null ? calView.get(Calendar.YEAR)
				: Integer.parseInt(request.getParameter("yy"));
		int mm = request.getParameter("mm") == null ? calView.get(Calendar.MONTH)
				: Integer.parseInt(request.getParameter("mm"));

		if (mm < 0) { // 1월에서 전월 버튼을 클릭시에 실행
			yy--;
			mm = 11;
		}
		if (mm > 11) { // 12월에서 다음월 버튼을 클릭시에 실행
			yy++;
			mm = 0;
		}
		calView.set(yy, mm, 1); // 현재 '년/월'의 1일을 달력의 날짜로 셋팅한다.

		int startWeek = calView.get(Calendar.DAY_OF_WEEK); // 해당 '년/월'의 1일에 해당하는 요일값을 숫자로 가져온다.
		int lastDay = calView.getActualMaximum(Calendar.DAY_OF_MONTH); // 해당월의 마지막일자(getActualMaxximum메소드사용)를 구한다.

		// 화면에 보여줄 년월기준 전년도/다음년도를 구하기 위한 부분
		int prevYear = yy; // 전년도
		int prevMonth = (mm) - 1; // 이전월
		int nextYear = yy; // 다음년도
		int nextMonth = (mm) + 1; // 다음월

		if (prevMonth == -1) { // 1월에서 전월 버튼을 클릭시에 실행..
			prevYear--;
			prevMonth = 11;
		}

		if (nextMonth == 12) { // 12월에서 다음월 버튼을 클릭시에 실행..
			nextYear++;
			nextMonth = 0;
		}

		// 현재달력에서 앞쪽의 빈공간은 '이전달력'을 보여주고, 뒷쪽의 남은공간은 '다음달력'을 보여주기위한 처리부분(아래 6줄)
		Calendar calPre = Calendar.getInstance(); // 이전달력
		calPre.set(prevYear, prevMonth, 1); // 이전 달력 셋팅
		int preLastDay = calPre.getActualMaximum(Calendar.DAY_OF_MONTH); // 해당월의 마지막일자를 구한다.

		Calendar calNext = Calendar.getInstance();// 다음달력
		calNext.set(nextYear, nextMonth, 1); // 다음 달력 셋팅
		int nextStartWeek = calNext.get(Calendar.DAY_OF_WEEK); // 다음달의 1일에 해당하는 요일값을 가져온다.

		/* --------- 아래는 앞에서 처리된 값들을 모두 request객체에 담는다. ----------------- */

		// 오늘기준 달력...
		request.setAttribute("toYear", toYear);
		request.setAttribute("toMonth", toMonth);
		request.setAttribute("toDay", toDay);

		// 화면에 보여줄 해당 달력...
		request.setAttribute("yy", yy);
		request.setAttribute("mm", mm);
		request.setAttribute("startWeek", startWeek);
		request.setAttribute("lastDay", lastDay);

		// 화면에 보여줄 해당 달력 기준의 전년도, 전월, 다음년도, 다음월 ...
		request.setAttribute("prevYear", prevYear);
		request.setAttribute("prevMonth", prevMonth);
		request.setAttribute("nextYear", nextYear);
		request.setAttribute("nextMonth", nextMonth);

		// 현재 달력의 '앞/뒤' 빈공간을 채울, 이전달의 뒷부분과 다음달의 앞부분을 보여주기위해 넘겨주는 변수
		request.setAttribute("preLastDay", preLastDay); // 이전달의 마지막일자를 기억하고 있는 변수
		request.setAttribute("nextStartWeek", nextStartWeek); // 다음달의 1일에 해당하는 요일을 기억하고있는 변수
	}

}
