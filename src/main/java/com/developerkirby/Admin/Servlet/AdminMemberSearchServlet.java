package com.developerkirby.Admin.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.developerkirby.Admin.Common;
import com.developerkirby.Admin.DAO.AdminMemberDAO;
import com.developerkirby.Admin.VO.AdminMemberVO;

@WebServlet("/AdminMemberSearchServlet")
public class AdminMemberSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Common.corsResSet(response);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// CORS 접근 허용
		Common.corsResSet(response);
		// 요청 메시지 받기
		StringBuffer sb = Common.reqStringBuff(request);
		// 요청 받은 메시지 JSON 파싱
		JSONObject jsonObj = Common.getJsonObj(sb); // 여기까지가 공통 루틴

		String reqCmd = (String)jsonObj.get("target"); // 요청된 target를 받음
		PrintWriter out = response.getWriter(); // 출력을 위해 만듦, 출력 스트림에 텍스트를 보내겠다는 뜻

		// 정상적인 경우
		AdminMemberDAO dao = new AdminMemberDAO();
		List<AdminMemberVO> list = dao.memberSearchSelect(reqCmd);
		JSONArray memberArray = new JSONArray();

		for(AdminMemberVO e : list) {
			JSONObject memberInfo = new JSONObject();
			memberInfo.put("memberNum", e.getMemberNum());
			memberInfo.put("nickname", e.getNickname());
			memberInfo.put("grade", e.getGrade());
			memberInfo.put("countWrite", e.getCountWrite());
			memberInfo.put("countComment", e.getCountComment());
			memberInfo.put("phone", e.getPhone());
			memberInfo.put("email", e.getEmail());
			memberInfo.put("pfImg", e.getPfImg());
			memberInfo.put("isAdOk", e.getIsAdOk());
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");
			String dateToStr = sdf.format(e.getRegDate());
			memberInfo.put("regDate", dateToStr);
			memberArray.add(memberInfo);
		}
		out.print(memberArray);
	}

}
