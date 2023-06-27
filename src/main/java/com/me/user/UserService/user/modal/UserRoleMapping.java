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
@Table(name = "role_user", schema="public")
@Data
@Getter
@Setter
public class UserRoleMapping {
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
    	@Column(name = "id")
	    private Integer id;
	   @Column(name = "user_id")
	   private Integer userId;
	    @Column(name = "role_id")
	   private Integer roleId;
	    @Column(name = "application_id") 
	   private Integer applicationId;
	    @Column(name = "application_name") 
	   private String applicationName;
	    @Column(name = "application_role_id")
	    private Integer applicationRoleId;
	    @Column(name = "business_unit_type_id") 
	   private Integer userLevelId;
	    @Column(name = "business_unit_type_code") 
	   private String userLevelName;
//	    @Column(name = "state_id") 
//	   private Integer stateId;
//	    @Column(name = "state_name") 
//	   private String  stateName;
//	    @Column(name = "district_id") 
//	   private Integer  districtId;
//	    @Column(name = "district_name") 
//	   private String  districtName;
//	    @Column(name = "block_id") 
//	   private Integer  blockId;
//	    @Column(name = "block_name") 
//	   private String   blockName;
	    @Column(name = "role_name") 
	   private String  roleName;
	    @Column(name = "permision") 
	   private String permision;
	    @Column(name = "context") 
	    private String context;
	    @Column(name = "organization_hierarchy_master_id")
	    private Integer organizationHierarchyMasterId;
	    @Column(name = "user_name")
	    private String userName;
	    
	    @Column(name = "role_type")
	    private String roleType;
	   
}


