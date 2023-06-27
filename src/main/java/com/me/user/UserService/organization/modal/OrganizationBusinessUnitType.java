package com.me.user.UserService.organization.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "organization_business_unit_type_master" , schema = "iamuser")
@Data
public class OrganizationBusinessUnitType {


@Column(name = "business_unit_type_id")	
private Integer	businessUnitTypeId;
@Column(name = "business_unit_type_code")
private String	businessUnitTypeCode;
@Column(name = "application_id")
private Integer	applicationId;
@Column(name = "application_name")
private String	applicationName;
@Id	
@GeneratedValue(strategy = GenerationType.AUTO)
@Column(name = "organization_business_unit_type_master_id")
private Integer	organizationBusinessUnitTypeMasterId;
@Column(name = "parent_business_unit_type_id")
private Integer	parentUnitId;
@Column(name = "hierarchy_id")
private String	hierarchyId;
@Column(name = "hierarchy_level")
private Integer	hierarchyLevel;
@Column(name = "root_yn")
private Integer	rootYn;
@Column(name = "leaf_yn")
private Integer	leafYn;
@Column(name = "organization_id")
private Integer organizationId;
	
}
