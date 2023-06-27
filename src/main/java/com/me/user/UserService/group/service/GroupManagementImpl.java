package com.me.user.UserService.group.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.me.user.UserService.group.modal.Group;
import com.me.user.UserService.group.repository.GroupManagementRepository;

@Service
public class GroupManagementImpl {
    @Autowired
	GroupManagementRepository groupManagementRepository;
	
public Group	createGroup(Group data){
		return groupManagementRepository.save(data);
	}

public List<Group>	getGroup(){
	return groupManagementRepository.findAll();
}
}
