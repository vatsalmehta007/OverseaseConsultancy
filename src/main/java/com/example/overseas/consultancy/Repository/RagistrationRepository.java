package com.example.overseas.consultancy.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.overseas.consultancy.entity.UserEntity;

public interface RagistrationRepository extends JpaRepository<UserEntity, Integer>
{

	Optional<UserEntity> findByEmailIgnoreCase(String email);

    
	Optional<UserEntity> findOneByEmailIgnoreCase(String username);

	@Query("SELECT m FROM UserEntity  m WHERE m.role= :role")
	List<UserEntity> findbyrole(String role);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM UserEntity m WHERE m.Id= :Id")
	void deleteFromUser(Integer Id);

	
	@Transactional
	@Modifying
	@Query("UPDATE UserEntity m SET m.password=:password WHERE m.email=:email")
	void updatepassword(String email, String password);

}
