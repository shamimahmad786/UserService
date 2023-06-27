package com.me.user.UserService.user.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "permission_role", schema="public")
@Data
@Getter
@Setter
public class PermisionRoleMapping {
	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
 	@Column(name = "id")
	    private Integer id;
		@Column(name = "permission_id")
	 private Integer permissionId;
		@Column(name = "role_id")
	 private Integer roleId;
}
