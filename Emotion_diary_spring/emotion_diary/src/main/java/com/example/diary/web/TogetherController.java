package com.example.diary.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.example.diary.domain.board.Board;
import com.example.diary.domain.member.Member;
import com.example.diary.domain.member.MemberRepository;
import com.example.diary.domain.together.Together;
import com.example.diary.domain.together.TogetherRepository;
import com.example.diary.dto.TogetDto;
import com.example.diary.service.TogetherService;
import com.example.diary.service.MemberService;
import com.example.diary.service.TmemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/together")
@RequiredArgsConstructor
public class TogetherController {
	private final HttpSession session;
	private final TogetherService togetherService;
	private final MemberService memberService;
	private final TogetherRepository togetherRepository;
	private final MemberRepository memberRepository;
	private final TmemberService tmemberService;

	@GetMapping("/")
	public List<Together> togetherList() {
		return togetherRepository.findAll();
	}

	@PostMapping("/save")
	public ResponseEntity<?> togetherSave(HttpServletRequest request, @RequestBody Together together) {
		System.out.println("togetherSave Controller");

		int id = (int) session.getAttribute("id");
		System.out.println("투게더" + id);
		if (session.getAttribute("id") != null) {
			Member member = memberRepository.findByMno(id);
//			System.out.println("member"+member);
			int result = togetherService.togetherSave(member, together);
			System.out.println("그룹 저장?" + result);
			
			System.out.println("저장tname"+together.getTname());
			
			if (result == 1) {
				Together findTogether = togetherRepository.findByTnameAndTcodeEntity(together.getTname(), together.getTcode());
//				int tno = togetherRepository.findByTnameAndTcode(together.getTname(), together.getTcode());
//				together = togetherRepository.findById(id).get();
				System.out.println("tno: " + findTogether.getTno());
				int findTno =findTogether.getTno();
				tmemberService.tmemberAutoSave(member, findTno);
			}

			return new ResponseEntity<String>("together ok!", HttpStatus.OK);
		}

		return new ResponseEntity<String>("You don't have authorization", HttpStatus.FORBIDDEN);

	}

	@GetMapping("/get/{id}")
	public Together together(@PathVariable int id) {
		System.out.println("together하나찾기");
		System.out.println(id);
		Together together = togetherRepository.findByTno(id);

		return together;
	}

	// 수정 필요
	// tno 받아서 그 tno의 together를 호출해서 로그인한 member가 주인이 맞는지 확인 후 받아온 together를
	// save해줘야함
	@PutMapping("/update/{id}")
	public ResponseEntity<?> togetherUpdate(@RequestBody Together together, @PathVariable int id) {
		System.out.println("togetherupdate Controller");

		Together originTogether = togetherRepository.findByTno(id);
		int tno = originTogether.getTno();
		int tmno = originTogether.getMember().getMno();

		id = (int) session.getAttribute("id");
		System.out.println("멤버 아이디" + id);

		if (id == tmno) {
			int result = togetherService.togetherUpdate(together, tno);
			if (result == 1) {
				return new ResponseEntity<String>("together update", HttpStatus.OK);
			}
			return new ResponseEntity<String>("You don't have authorization", HttpStatus.FORBIDDEN);
		} else {
			return new ResponseEntity<String>("member different", HttpStatus.FORBIDDEN);
		}
	}

	// 그룹 삭제
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable int id) {
		System.out.println("삭제호출");

		if (session.getAttribute("id") != null) {
			int mid = (int) session.getAttribute("id");
			Together together = togetherService.togetherDetail(id);

			if (mid == together.getMember().getMno()) {
				togetherService.togetherDelete(id);
				return new ResponseEntity<String>("ok", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("not same member", HttpStatus.FORBIDDEN);
			}
		}
		return new ResponseEntity<String>("You don't have authorization", HttpStatus.FORBIDDEN);

	}

}
