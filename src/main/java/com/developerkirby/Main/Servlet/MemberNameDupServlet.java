package com.developerkirby.Main.Servlet;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.developerkirby.Main.Common;
import com.developerkirby.Main.DAO.MemberDAO;


@WebServlet("/MemberNameDupServlet")
public class MemberNameDupServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Common.corsResSet(response);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 한글 깨짐 방지를 위해서 설정
		request.setCharacterEncoding("utf-8");
		// CORS 접근 허용
		Common.corsResSet(response);

		// 요청 메시지 받기
		StringBuffer sb = Common.reqStringBuff(request);

		// 요청 받은 메시지 JSON 파싱
		JSONObject jsonObj = Common.getJsonObj(sb);

		String getNickname = (String)jsonObj.get("nickname");
		MemberDAO dao = new MemberDAO();
		boolean isAvailable = dao.memberNameCheck(getNickname);

		PrintWriter out = response.getWriter();

		JSONObject resJson = new JSONObject();
		if(isAvailable) resJson.put("result", "OK");
		else resJson.put("result", "NOK");
		out.print(resJson);
	}

}
