package com.example.diary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.diary.domain.member.Member;
import com.example.diary.domain.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	
	private final MemberRepository memberRepository;
	
	@Transactional
	public void join(Member member) {
		memberRepository.save(member);
	}

	@Transactional
	public void delete(int id) {
		memberRepository.deleteById(id);
		
	}

	@Transactional
	public void update(int id, Member member) {
		// TODO Auto-generated method stub
		System.out.println("update service");
		//예외처리필요
		Member memberEntity = memberRepository.findById(id).get();
		if(member.getName()!=null) {
			memberEntity.setName(member.getName());
		}
		if(member.getPw()!=null) {
			memberEntity.setPw(member.getPw());
		}
	}
	
	@Transactional
	public int check(String id) {
		
		if(memberRepository.findByChId(id) != null) {
			return 1;
		}else {
			return 0;
		}
		
	}
}
