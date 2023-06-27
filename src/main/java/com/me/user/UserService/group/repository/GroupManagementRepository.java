package com.me.user.UserService.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

//import com.me.dataentry.modal.AcademicResults;
import com.me.user.UserService.group.modal.Group;

public interface GroupManagementRepository extends JpaRepository<Group, Integer> {

}
