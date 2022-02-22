package com.example.diary.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.diary.domain.member.Member;
import com.example.diary.domain.member.MemberRepository;
import com.example.diary.domain.tmember.Tmember;
import com.example.diary.domain.tmember.TmemberRepository;
import com.example.diary.domain.together.Together;
import com.example.diary.domain.together.TogetherRepository;
import com.example.diary.dto.TmDto;
import com.example.diary.service.TmemberService;
import com.example.diary.service.TogetherService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/tmember")
@RequiredArgsConstructor
@RestController
public class TmemberController {

	private final HttpSession session;
	private final TmemberRepository tmemberRepository;
	private final MemberRepository memberRepository;
	private final TogetherRepository togetherRepository;
	private final TogetherService togetherService;
	private final TmemberService tmemberService;

	@PostMapping("/save")
	public ResponseEntity<?> tmemberSave(HttpServletRequest request, @RequestBody Together together) {
		System.out.println("tmember Controller");

		int id = (int) session.getAttribute("id");
		System.out.println("tmember" + id);

		if (session.getAttribute("id") != null) {
			Member member = memberRepository.findByMno(id);

			Together togetherEntity = togetherRepository.findByTnameAndTcodeEntity(together.getTname(),
					together.getTcode());

			if (togetherEntity != null) {
				tmemberService.tmemberSave(member, togetherEntity);
				return new ResponseEntity<String>("tmember ok!", HttpStatus.OK);

			} else {
				return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
			}

		}

		return new ResponseEntity<String>("You don't have authorization", HttpStatus.FORBIDDEN);

	}

	@GetMapping("/get")
	public List<Tmember> getGroup() {
		System.out.println("여기옴");
		List<Tmember> groupList = new ArrayList<Tmember>();
		int id = (int) session.getAttribute("id");

		groupList = tmemberRepository.findByMno(id);

		return groupList;
	}

	// 내가 속한 그룹 탈퇴
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable int id) {
		System.out.println("삭제호출");

		if (session.getAttribute("id") != null) {
			int mid = (int) session.getAttribute("id");
			System.out.println("member id:" + mid);
			Together together = togetherService.togetherDetail(id);
			System.out.println("to id: " + together.getMember().getMno());
			if (mid != together.getMember().getMno()) {
				tmemberService.tmemberDelete(mid, id);
				return new ResponseEntity<String>("ok", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("you are master", HttpStatus.FORBIDDEN);
			}
		}
		return new ResponseEntity<String>("You don't have authorization", HttpStatus.FORBIDDEN);

	}
}
