package com.me.user.UserService.organization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.me.user.UserService.organization.modal.OrganizationBusinessUnitType;
import com.me.user.UserService.organization.modal.OrganizationHirarchy;

public interface OrganizationBusinessUnitTypeRepository extends JpaRepository<OrganizationBusinessUnitType, Integer>{
	List<OrganizationBusinessUnitType> findAllByApplicationId(Integer appId);
}
