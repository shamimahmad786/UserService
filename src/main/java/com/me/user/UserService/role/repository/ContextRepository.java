package com.me.user.UserService.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.me.user.UserService.role.modal.Context;
import com.me.user.UserService.role.modal.Role;

public interface ContextRepository extends JpaRepository<Context, Integer>{

}
