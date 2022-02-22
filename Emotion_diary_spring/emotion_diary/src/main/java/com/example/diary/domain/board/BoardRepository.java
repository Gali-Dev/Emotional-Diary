package com.example.diary.domain.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Integer> {
		
	@Query(value = "select * from board where memberId = :memberId", nativeQuery = true)
	List<Board> findAllMemberId(int memberId);

	@Query(value = "select * From board WHERE memberId = :memberId and tno = :tno",nativeQuery = true)
	List<Board> findAllMemberIdandTno(int memberId, int tno);

	@Query(value = "select * From board WHERE tno = :tno",nativeQuery = true)
	List<Board> findAllTno(int tno);
}
