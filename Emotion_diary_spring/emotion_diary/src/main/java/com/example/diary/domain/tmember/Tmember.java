package com.example.diary.domain.tmember;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.diary.domain.board.Board;
import com.example.diary.domain.member.Member;
import com.example.diary.domain.together.Together;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tmember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tmno;
	
	@JsonIgnoreProperties({"tmember","board"})
	@JoinColumn(name="mno")
	@ManyToOne()
	private Member member;
	
	@JsonIgnoreProperties({"mno"})
	@JoinColumn(name="tno")
	@ManyToOne()
	private Together together;
	
}
