package com.example.diary.domain.tmember;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.diary.domain.together.Together;


public interface TmemberRepository extends JpaRepository<Tmember, Integer>{

	@Query(value = "select * From tmember WHERE mno = :mno ",nativeQuery = true)
	List<Tmember> findByMno(int mno);

	@Query(value = "select * From tmember WHERE tno = :tno ",nativeQuery = true)
	List<Tmember> findByTno(int tno);

	@Modifying
	@Transactional
	@Query(value = "delete From tmember WHERE mno = :mno and tno = :tno ",nativeQuery = true)
	int deleteByMidandTno(int mno, int tno);
}
