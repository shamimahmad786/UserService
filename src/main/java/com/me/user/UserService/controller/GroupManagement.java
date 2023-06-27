package com.me.user.UserService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.me.user.UserService.group.modal.Group;
import com.me.user.UserService.group.service.GroupManagementImpl;
//import com.me.dto.RegistirationRequest;
import com.me.util.ApiPaths;

@RestController
@RequestMapping(ApiPaths.GroupCtrl.CTRL)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GroupManagement {
	@Autowired
	GroupManagementImpl groupManagementImpl;
	
	@RequestMapping(value = "/create-group", method = RequestMethod.POST)
	public Group createGroup(@RequestBody Group data) throws Exception {
		return groupManagementImpl.createGroup(data);
	}
	@RequestMapping(value = "/get-group", method = RequestMethod.POST)
	public List<Group> getGroup() throws Exception {
		return groupManagementImpl.getGroup();
	}
}
