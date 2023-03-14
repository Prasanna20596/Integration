package com.jwt.react.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jwt.react.entity.User;

public interface UserDao extends JpaRepository<User, Integer> {
	
	User findByUserName(String userName);
	
}
