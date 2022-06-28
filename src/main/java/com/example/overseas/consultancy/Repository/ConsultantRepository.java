package com.example.overseas.consultancy.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.overseas.consultancy.entity.UserEntity;

public interface ConsultantRepository extends JpaRepository<UserEntity, Integer>
{
	
	@Query("SELECT m FROM UserEntity  m WHERE  m.role=:role")
	List<UserEntity> FindByRole(String role);
	
	@Query("SELECT m FROM UserEntity  m WHERE m.role= :role AND m.id= :id")
	UserEntity findByUserId(Integer id, String role);
	
}
