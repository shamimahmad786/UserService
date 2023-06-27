package com.me.user.UserService.role.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.me.user.UserService.group.modal.Group;
import com.me.user.UserService.group.repository.GroupManagementRepository;
import com.me.user.UserService.organization.modal.OrganizationBusinessUnitType;
import com.me.user.UserService.organization.repository.OrganizationBusinessUnitTypeRepository;
import com.me.user.UserService.role.modal.Application;
import com.me.user.UserService.role.modal.Role;
import com.me.user.UserService.role.modal.UserLevel;
import com.me.user.UserService.role.repository.ApplicationRepository;
import com.me.user.UserService.role.repository.ContextRepository;
import com.me.user.UserService.role.repository.RoleRepository;
import com.me.user.UserService.role.repository.UserLevelRepository;
import com.me.user.UserService.user.repository.UserRoleMappingRepository;

@Service
public class RoleManagementImpl {
	   @Autowired
		RoleRepository roleRepository;
	   
	   @Autowired
	   ApplicationRepository applicationRepository;
	   
	   @Autowired
	   ContextRepository contextRepository;
	   
	   @Autowired
	   UserLevelRepository userLevelRepository;
	   
	   @Autowired
	   OrganizationBusinessUnitTypeRepository organizationBusinessUnitTypeRepository;
	   
	   @Autowired
	   UserRoleMappingRepository  userRoleMappingRepository;
	   
	
	   
		
	public Role	createRole(Role data){
			return roleRepository.save(data);
		}

	public List<Role> getRole(Integer appId){
		return roleRepository.findAllByAppId(appId);
	}
	
	public List<Role> getRole(){
		return roleRepository.findAll();
	}
	
	public List<Application> getApplication(){
		return applicationRepository.findAll();
	}
	
   public List<UserLevel> getUserlevel(Integer applicationId){
	return userLevelRepository.findAllByApplicationId(applicationId);
	}
   
   public List<OrganizationBusinessUnitType> getBusinessUnitByApplicationId(String appId){
	return organizationBusinessUnitTypeRepository.findAllByApplicationId(Integer.parseInt(appId));
	   
   }
   
	public List<Role> getRoleByBusinessUnitId(String bUnitId){
		return roleRepository.findAllByBusinessUnitTypeId(Integer.parseInt(bUnitId));
	}
	
	
	public List<Role> getRoleByApplicationId(Integer data){
		return roleRepository.findAllByAppId(data);
	}
	
	
	
	
	
	
	
}
