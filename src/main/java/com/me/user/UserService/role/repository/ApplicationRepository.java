package com.me.user.UserService.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.me.user.UserService.role.modal.Application;
import com.me.user.UserService.role.modal.Role;

public interface ApplicationRepository extends JpaRepository<Application, Integer>{

}
