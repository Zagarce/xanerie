package org.xianerie.xianierie.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.xianerie.xianierie.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	//JPQL... Java Persistence Query Language
	@Query("SELECT u FROM User u WHERE u.email=?1")//Consultas integras de JPA
	User findByEmail(String email);
}
