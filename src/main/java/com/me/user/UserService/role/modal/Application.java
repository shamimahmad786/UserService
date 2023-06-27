package com.me.user.UserService.role.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "oauth_ms_application", schema="public")
@Data
public class Application {
    	  @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Integer id;
    	  @Column(name = "application_name")
    	private String applicationName;  
}
