package com.me.user.UserService.organization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.me.user.UserService.organization.modal.OrganizationBusinessUnitType;
import com.me.user.UserService.organization.modal.OrganizationHirarchy;
import com.me.user.UserService.organization.modal.OrganizationMaster;
import com.me.user.UserService.organization.repository.OrganizationBusinessUnitTypeRepository;
import com.me.user.UserService.organization.repository.OrganizationHirarchyRepository;
import com.me.user.UserService.organization.repository.OrganizationMasterRepository;

@Service
public class OrganizationServiceImpl {
	
	
     @Autowired
	OrganizationMasterRepository   organizationMasterRepository;
     
     @Autowired
     OrganizationHirarchyRepository organizationHirarchyRepository;
	
     @Autowired
     OrganizationBusinessUnitTypeRepository  organizationBusinessUnitTypeRepository;
     
	@RequestMapping(value = "/getOrganization", method = RequestMethod.POST)
	public List<OrganizationMaster> getOrganization() throws Exception {
		return organizationMasterRepository.findAll();
	}
	
	
	
	public OrganizationHirarchy saveOrganizationHirarchy(OrganizationHirarchy data) throws Exception {
		return organizationHirarchyRepository.save(data);
	}
	
	
	public List<OrganizationHirarchy> getBusinessUnitByOrganizationId(Integer data) throws Exception {
		return organizationHirarchyRepository.findAllByOrganizationId(data);
	}
	
	
	public List<OrganizationBusinessUnitType> saveBusinessUnitMapping(List<OrganizationBusinessUnitType> data){
		return organizationBusinessUnitTypeRepository.saveAll(data);
	}
}
