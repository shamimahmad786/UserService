package com.me.user.UserService.application.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "application_master", schema = "iamuser")
public class ApplicationMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "application_id")
	private Integer applicationId;
	@Column(name = "application_name")
	private String applicationName;
	@Column(name = "application_code")
	private String applicationCode;
	@Column(name = "application_description")
	private String applicationDescription;
	
	

}
