package com.example.diary.domain.analysis;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Analysis {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ano;
	
	@Lob
	private byte[] wordcloud;

}
