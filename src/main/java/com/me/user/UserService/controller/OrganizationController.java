package com.me.user.UserService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.me.user.UserService.application.modal.ApplicationMaster;
import com.me.user.UserService.bean.UserCradentialBean;
import com.me.user.UserService.organization.modal.OrganizationBusinessUnitType;
import com.me.user.UserService.organization.modal.OrganizationHirarchy;
import com.me.user.UserService.organization.modal.OrganizationMaster;
import com.me.user.UserService.organization.service.OrganizationServiceImpl;
import com.me.util.ApiPaths;

@RestController
@RequestMapping(ApiPaths.OrgCtrl.CTRL)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrganizationController {
	
	@Autowired
	OrganizationServiceImpl  organizationServiceImpl; 
	
	
	@RequestMapping(value = "/getOrganization", method = RequestMethod.POST)
	public List<OrganizationMaster> getOrganization() throws Exception {
//		// System.out.println("data---->"+data.getApplicationName());
		return organizationServiceImpl.getOrganization();
	}
	
	
	@RequestMapping(value = "/saveOrganizationHirarchy", method = RequestMethod.POST)
	public OrganizationHirarchy saveOrganizationHirarchy(@RequestBody OrganizationHirarchy data) throws Exception {
		// System.out.println("data--->"+data.getOrganizationName());
		return organizationServiceImpl.saveOrganizationHirarchy(data);
	}

	@RequestMapping(value = "/getBusinessUnitByOrganizationId", method = RequestMethod.POST)
	public List<OrganizationHirarchy> getBusinessUnitByOrganizationId(@RequestBody Integer data) throws Exception {
		// System.out.println("organizationId----->"+data);
		return organizationServiceImpl.getBusinessUnitByOrganizationId(data);
	}
	
	@RequestMapping(value = "/saveBusinessUnitMapping", method = RequestMethod.POST)
	public List<OrganizationBusinessUnitType> saveBusinessUnitMapping(@RequestBody List<OrganizationBusinessUnitType> data) throws Exception {
//		// System.out.println("organizationId----->"+data);
		// System.out.println("In save business unit mapping--->"+data.get(0).getApplicationName());
		return organizationServiceImpl.saveBusinessUnitMapping(data);
	}
	
	
}
