package com.example.diary.config.jwt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.diary.domain.member.Member;
import com.example.diary.domain.member.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthenticationFilter implements Filter {

	private MemberRepository memberRepository;

	public JwtAuthenticationFilter(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("JwtAuthenticationFilter 작동");

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		PrintWriter out = resp.getWriter();

		String method = req.getMethod();
		System.out.println(method);
		if (!method.equals("POST")) {
			System.out.println("post 요청이 아니기 때문에 거절");
			out.print("required post method");
			out.flush();
		} else {
			System.out.println("post 요청이 맞습니다.");

			ObjectMapper om = new ObjectMapper();
			try {
				Member member = om.readValue(req.getInputStream(), Member.class);
				System.out.println(member);
				// 1번 username, password를 DB에 던짐
				Member memberEntity = memberRepository.findByUsernameAndPassword(member.getId(), member.getPw());
				// 2번 값이 있으면 있다?. 없다?
				if (memberEntity == null) {
					System.out.println("유저네임 혹은 패스워드가 틀렸습니다.");
					out.print("fail");
					out.flush();
				} else {
					System.out.println("인증되었습니다.");

					String jwtToken = JWT.create().withSubject("토큰제목")
							.withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
							.withClaim("id", memberEntity.getMno()).sign(Algorithm.HMAC512(JwtProps.secret));

					resp.addHeader(JwtProps.header, JwtProps.auth + jwtToken);
					out.print("ok");
					out.flush();
				}
			} catch (Exception e) {
				System.out.println("오류 : " + e.getMessage());
			}

		}
	}
}