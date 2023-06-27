package com.me.user.UserService.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.me.user.UserService.application.modal.ApplicationMaster;
import com.me.user.UserService.application.service.ApplicationServiceImpl;
import com.me.user.UserService.bean.UserCradentialBean;
import com.me.util.ApiPaths;

@RestController
@RequestMapping(ApiPaths.AppCtrl.CTRL)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ApplicationController {

	@Autowired
	ApplicationServiceImpl   applicationServiceImpl;
	
	@RequestMapping(value = "/applicationSave", method = RequestMethod.POST)
	public ApplicationMaster applicationSave(@RequestBody ApplicationMaster data) throws Exception {
		// System.out.println("data---->"+data.getApplicationName());
		return applicationServiceImpl.applicationSave(data);
	}
	
	
	@RequestMapping(value = "/getApplication", method = RequestMethod.POST)
	public List<ApplicationMaster> getApplication() throws Exception {
//		// System.out.println("data---->"+data.getApplicationName());
		return applicationServiceImpl.getApplication();
	}
		
}
