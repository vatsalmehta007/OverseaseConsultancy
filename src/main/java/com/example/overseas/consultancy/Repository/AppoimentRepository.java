package com.example.overseas.consultancy.Repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.overseas.consultancy.entity.AppoimentEntity;

public interface AppoimentRepository extends JpaRepository<AppoimentEntity, Integer> {


	@Modifying
	@Transactional
	@Query("UPDATE AppoimentEntity m SET m.status=:status ,m.modified_on=:dateTime WHERE m.id=:id")
	void updateappoiment(Long id,String status, LocalDateTime dateTime);

	@Query("SELECT m FROM  AppoimentEntity m WHERE m.id =:id")
	AppoimentEntity getdata(Long id);


	@Query("SELECT m FROM AppoimentEntity  m WHERE m.consultant_Id= :consultant_Id AND m.status= :status")
	List<AppoimentEntity> GetByConsultantId(Integer consultant_Id, String status);
	
	@Query("SELECT m FROM  AppoimentEntity m WHERE m.student_Id =:student_Id")
	List<AppoimentEntity> findByStudentId(Integer student_Id);

	@Query("SELECT m FROM AppoimentEntity  m WHERE m.consultant_Id= :consultant_Id")
	List<AppoimentEntity> FindByConsultantId(Integer consultant_Id);

	



}
