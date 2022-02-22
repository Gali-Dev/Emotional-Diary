package com.example.diary.domain.board;

import lombok.Data;

@Data
public class BoardSaveRequestDto {
	private int bno;
	private String contents;
	private String title;
	public static Board toEntity(BoardSaveRequestDto dto) {
		Board board = Board.builder()
				.bno(dto.getBno())
				.contents(dto.getContents())
				.title(dto.getTitle())
				.build();
		return board;
	}
}
