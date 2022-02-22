package com.example.diary.domain.together;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TogetherRepository extends JpaRepository<Together, Integer>{
	
	@Query(value = "select * From together WHERE tno = :tno ",nativeQuery = true)
	Together findByTno(int tno);
	
	@Query(value = "select tno From together WHERE tname = :tname and  tcode = :tcode",nativeQuery = true)
	int findByTnameAndTcode(String tname, String tcode);
	
	@Query(value = "select * From together WHERE tname = :tname and  tcode = :tcode",nativeQuery = true)
	Together findByTnameAndTcodeEntity(String tname, String tcode);
	
}
