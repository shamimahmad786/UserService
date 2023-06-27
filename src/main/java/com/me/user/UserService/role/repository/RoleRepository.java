package com.me.user.UserService.role.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.me.user.UserService.role.modal.Role;

//import com.me.user.UserService.group.modal.Group;

public interface RoleRepository extends JpaRepository<Role, Integer>{
List<Role>  findAllByAppId(Integer appId);
List<Role>  findAllByBusinessUnitTypeId(Integer businessUnitTypeId);
//List<Role>  findAllByAppId(Integer appId);
}
