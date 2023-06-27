package com.me.user.UserService.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.me.user.UserService.user.modal.PermisionRoleMapping;
import com.me.user.UserService.user.modal.UserRoleMapping;

public interface PermisionRoleMappingRepository extends JpaRepository<PermisionRoleMapping, Integer>{

}
