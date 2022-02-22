package com.example.diary.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.diary.domain.member.Member;
import com.example.diary.domain.member.MemberRepository;
import com.example.diary.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/member")
@RequiredArgsConstructor
@RestController
public class MemberController {

	private final HttpSession session;
	private final MemberService memberService;
	private final MemberRepository memberRepository;

	// 회원가입
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody Member member) {
		System.out.println("join 호출");
		
		String checkId = member.getId();
		System.out.println("join id: "+checkId);
		
		int check = memberService.check(checkId);
		
		if(check == 0) {
			memberService.join(member);
			return new ResponseEntity<String>("ok", HttpStatus.CREATED);			
		}else if(check == 1) {
			return new ResponseEntity<String>("id duplicate", HttpStatus.CREATED);
		}else {
			return new ResponseEntity<String>("fail", HttpStatus.CREATED);
		}
		
	}
	// 중복확인
		@PostMapping("/duplicate")
		public ResponseEntity<?> duplicateCheck(@RequestBody Member member) {
			System.out.println("중복확인 호출");
			System.out.println(member.getId());
			int check = memberService.check(member.getId());
			System.out.println(check);
			if(check == 0) {
				return new ResponseEntity<String>("ok", HttpStatus.OK);			
			}else if(check == 1) {
				return new ResponseEntity<String>("id duplicate", HttpStatus.FORBIDDEN);
			}else {
				return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
			}
			
		}

	// 로그아웃
	@GetMapping("/logout")
	public ResponseEntity<?> logout() {
		System.out.println("logout 호출");
		int sid = (int) session.getAttribute("id");
		System.out.println("mno:"+sid);
		session.invalidate();
		System.out.println("mno 삭제?"+sid);
		
		if(session.getAttribute("id")!=null) {
//			session.removeAttribute("id");
			return new ResponseEntity<String>("logout 실패", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("logout", HttpStatus.OK);
		}
	}

	

	@GetMapping("/get")
	public ResponseEntity<?> memberone() {
		int mid = (int) session.getAttribute("id");
		if (session.getAttribute("id") != null) {
			Member member = memberRepository.findByMno(mid);
			return new ResponseEntity<Member>(member, HttpStatus.OK);
		}
		return new ResponseEntity<String>("You don't have authorization", HttpStatus.FORBIDDEN);
	}

	// 수정
	@PutMapping("/update")
	public ResponseEntity<?> update(HttpServletRequest request, @RequestBody Member member) {
		System.out.println("update 호출");
		int sid = (int) session.getAttribute("id");
		System.out.println(sid);
		if (session.getAttribute("id") != null) {
			Member originMember = memberRepository.findByMno(sid);
			int id = originMember.getMno();
			System.out.println("id: " + originMember.getId());
			System.out.println("member: " + member);
			memberService.update(id, member);
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		}
		return new ResponseEntity<String>("You don't have authorization", HttpStatus.FORBIDDEN);
	}

	// 삭제
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(HttpServletRequest request) {
		System.out.println("delete 호출");
		int sid = (int) session.getAttribute("id");
		System.out.println(sid);

		if (session.getAttribute("id") != null) {
			Member originMember = memberRepository.findByMno(sid);
			int id = originMember.getMno();
			System.out.println("id: " + originMember.getId());
			memberService.delete(id);
		} else {
			System.out.println("session=null");
		}
		return new ResponseEntity<String>("ok", HttpStatus.CREATED);
	}

}
