package com.example.diary.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.diary.domain.board.Board;
import com.example.diary.domain.member.Member;
import com.example.diary.domain.together.Together;
import com.example.diary.domain.together.TogetherRepository;
import com.example.diary.dto.TogetDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class TogetherService {

	private final TogetherRepository togetherRepositorty;
	private final HttpSession session;
	
	//생성, 삭제, 수정
	
	@Transactional
	public int togetherSave(Member member,Together together) {
		System.out.println("togetherSave 호출");
		together.setMember(member);
		togetherRepositorty.save(together);
		return 1; 
		
	}
	
	@Transactional
	public Together togetherDetail(int id) {
		Together together = togetherRepositorty.findById(id).get();
		return together;	
		}
	
	

	@Transactional
	public void togetherDelete(int gno) {
		System.out.println("togetherDelete 호출");
		togetherRepositorty.deleteById(gno);
	}
	
	@Transactional
	public int togetherUpdate(Together together,int tno) {
		System.out.println("togetherUpdate 호출");
		Together togetherEntity = togetherRepositorty.findById(tno).get();
		
		if(session.getAttribute("id")!=null) {
			togetherEntity.setTcode(together.getTcode());
			togetherEntity.setTname(together.getTname());
			return 1;
		}else {
			return 0;			
		}
		
	}
	
	
	
	
	
	
}
