package com.example.diary.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.diary.domain.board.Board;
import com.example.diary.domain.board.BoardRepository;
import com.example.diary.domain.member.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;

	@Transactional
	public void 글쓰기(Board board, Member principal) {
		System.out.println("글쓰기서비스");
		board.setMember(principal);
		boardRepository.save(board);
	}

	@Transactional
	public Board 글상세(int id) {
		return boardRepository.findById(id).orElseThrow(()->new IllegalArgumentException(id+"는 존재하지 않습니다."));
	}
	
	@Transactional
	public List<Board> 글목록() {
		return boardRepository.findAll();
	}
	
	@Transactional
	public List<Board> 내글목록(int MemberId) {
		return boardRepository.findAllMemberId(MemberId);	
		}
	
	@Transactional
	public List<Board> 나만의글목록(int memberId, int tno) {
		return boardRepository.findAllMemberIdandTno(memberId,tno);	
		}
	
	@Transactional
	public List<Board> 그룹글목록(int tno) {
		return boardRepository.findAllTno(tno);	
		}
	
	//글수정
	@Transactional
	public int 글수정(Board board, int id, Member principal) {
		System.out.println("글수정 서비스");
		//글 검색
		Board boardEntity = boardRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(id + "는 존재하지 않습니다"));
		//작성자 확인
		if (boardEntity.getMember().getId() == principal.getId()) {
			boardEntity.setTitle(board.getTitle());
			boardEntity.setContents(board.getContents());
			return 1;
		} else { //글번호와 작성자 일치하지않음
			return 0;
		}
	}
	
	@Transactional
	public int 글삭제(int id, Member principal) {
		System.out.println("글삭제");
		//글 검색
		Board boardEntity = boardRepository.findById(id).orElseThrow(()->new IllegalArgumentException(id+"는 존재하지 않습니다"));
		//작성자 확인
		if(boardEntity.getMember().getId() == principal.getId()) {
			boardRepository.deleteById(id);
			return 1;
		}else {
			return 0;
		}
	}

	
}
