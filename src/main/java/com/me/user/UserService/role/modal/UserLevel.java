package com.me.user.UserService.role.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "oauth_ms_userlevel", schema="public")
@Data
public class UserLevel {
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Integer id;
	    @Column(name = "application_id")
	    private Integer applicationId;
	    @Column(name = "user_level_details")
	    private String userLevelDetails;
	    @Column(name = "user_level_value")
	    private Integer userLevelValue; 
	    
	    
	    
}
