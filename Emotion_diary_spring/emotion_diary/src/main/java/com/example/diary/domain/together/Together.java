package com.example.diary.domain.together;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.diary.domain.member.Member;
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
public class Together {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tno;
	
	@Column(length = 50)
	private String tname;
	
	@Column(length = 500)
	private String tcode;
	
	@JsonIgnoreProperties({"together","board"})
	@JoinColumn(name="mno")
	@ManyToOne()
	private Member member;

	



}
