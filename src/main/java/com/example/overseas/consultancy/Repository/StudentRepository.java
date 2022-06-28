package com.example.overseas.consultancy.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.overseas.consultancy.entity.AppoimentEntity;
import com.example.overseas.consultancy.entity.UserEntity;

public interface StudentRepository extends JpaRepository<UserEntity, Integer>
{

	@Query("SELECT m FROM UserEntity  m WHERE m.role= :role AND m.specification= :specification")
	List<UserEntity> FindByspecification(String specification,String role);

	@Query("SELECT m FROM UserEntity  m WHERE  m.role=:role")
	List<UserEntity> FindByRole(String role);

	@Query("SELECT m FROM UserEntity  m WHERE m.role= :role AND m.id= :id")
	UserEntity findById(Integer id, String role);

	AppoimentEntity save(AppoimentEntity populatedata);

	



	

	



}
