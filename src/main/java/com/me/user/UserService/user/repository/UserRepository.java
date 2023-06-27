package com.me.user.UserService.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.me.user.UserService.user.modal.User;



public interface UserRepository extends JpaRepository<User, Integer>{
User findByUsername(String userName);
@Query(value = "select * from user_details limit 10", nativeQuery = true)
List<User> findAllTop10();

List<User> findAllByParentuserOrderByUsernameAsc(String parentuser);
User findAllByUsername(String username);

}
