package com.me.user.UserService.role.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "oauth_ms_context", schema="public")
@Data
public class Context {
	   @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Integer id;
	    @Column(name = "context_name")
	    private String contextName;
}
