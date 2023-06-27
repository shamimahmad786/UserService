package com.me.user.UserService.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.me.user.UserService.application.modal.ApplicationMaster;
import com.me.user.UserService.group.modal.Group;

public interface ApplicationMasterRepository extends JpaRepository<ApplicationMaster, Integer>{

}
