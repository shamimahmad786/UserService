package com.me.user.UserService.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.me.user.UserService.organization.modal.OrganizationMaster;
import com.me.user.UserService.role.modal.Application;

public interface OrganizationMasterRepository extends JpaRepository<OrganizationMaster, Integer>{

}
