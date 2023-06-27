package com.me.user.UserService.organization.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.me.user.UserService.role.modal.Application;

import lombok.Data;

@Entity
@Table(name = "organization_master" , schema = "iamuser")
@Data
public class OrganizationMaster {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer organization_id;
	  @Column(name = "organization_name")
	private String organizationName; 
	  
}
