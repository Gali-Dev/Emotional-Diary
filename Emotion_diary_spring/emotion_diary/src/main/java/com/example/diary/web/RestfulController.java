package com.example.diary.web;

import java.util.List;

import javax.servlet.ServletResponse;

import org.python.antlr.ast.Str;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.diary.domain.board.Board;
import com.example.diary.domain.board.BoardSaveRequestDto;
import com.example.diary.service.BoardService;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;

@RequestMapping("/sendboard")
@RestController
@RequiredArgsConstructor
public class RestfulController {

	private final BoardService boardService;

	private Logger LOGGER = LoggerFactory.getLogger(RestfulController.class);

	@Autowired
	RestTemplate restTemplate;

	@RequestMapping("/restfulTest")
	public String home() {

		String obj = restTemplate.getForObject("http://10.100.102.90:7000/analysis", String.class);

		return obj;
	}
	
	// 전체글 분석
	@RequestMapping("/all")
	public void boardAll(List<Board> board) {

		restTemplate.postForObject("http://10.100.102.90:7000/analysis", board, Board.class);

	}
	

	// 게시글 저장할 때 분석
	@RequestMapping("/save")
	public Board send(BoardSaveRequestDto dto) {

		Board board = restTemplate.postForObject("http://10.100.102.90:7000/analysis/saveEmotion", dto, Board.class);
		
		return board;
	}

	// 게시글 하나 분석
	@RequestMapping("/one")
	public Board sendOne(BoardSaveRequestDto dto) {

		Board board = restTemplate.postForObject("http://10.100.102.90:7000/analysis/one/", dto, Board.class);

		return board;
	}

	// 그룹 게시글 분석
	@RequestMapping("/groupEmotion")
	public void groupBoard(List<Board> board, int tno) {

		restTemplate.postForObject("http://10.100.102.90:7000/analysis/groupEmotion/" + tno, board,
				Board.class);

	}


	// 내글 게시글 분석
	@RequestMapping("/myEmotion")
	public void myBoard(List<Board> board, int mno) {

		restTemplate.postForObject("http://10.100.102.90:7000/analysis/myEmotion/" + mno, board,
				Board.class);

	}
	
}
