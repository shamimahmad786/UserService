package com.me.user.UserService.organization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.me.user.UserService.organization.modal.OrganizationHirarchy;
import com.me.user.UserService.organization.modal.OrganizationMaster;

public interface OrganizationHirarchyRepository extends JpaRepository<OrganizationHirarchy, Integer>{

	List<OrganizationHirarchy> findAllByOrganizationId(Integer orgId);
}
