package com.me.user.UserService.application.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.me.user.UserService.application.modal.ApplicationMaster;
import com.me.user.UserService.application.repository.ApplicationMasterRepository;


@Service
public class ApplicationServiceImpl {

	 @Autowired
	ApplicationMasterRepository applicationMasterRepository;
	
	
public ApplicationMaster applicationSave(ApplicationMaster data) throws Exception {
	// System.out.println("At Impl");
	// System.out.println("data at impl--->"+data.getApplicationName());
		return applicationMasterRepository.save(data);
	}

public List<ApplicationMaster> getApplication() throws Exception {
		return applicationMasterRepository.findAll();
	}



	
}
