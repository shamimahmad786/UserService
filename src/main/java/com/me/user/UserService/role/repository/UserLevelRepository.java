package com.me.user.UserService.role.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.me.user.UserService.role.modal.Role;
import com.me.user.UserService.role.modal.UserLevel;

public interface UserLevelRepository extends JpaRepository<UserLevel, Integer>{
List<UserLevel> findAllByApplicationId(Integer applicationId);
}
