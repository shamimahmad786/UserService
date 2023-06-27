package com.me.user.UserService.organization.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name = "organization_hirarchy" , schema = "iamuser")
@Data
public class OrganizationHirarchy {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "business_unit_type_id")
	private Integer businessUnitTypeId;
	
	@Column(name = "business_unit_type_code")
    private String businessUnitTypeCode;
	@Column(name = "organization_id")
	private Integer organizationId;
	@Column(name = "organization_name")
    private String organizationName;
	@Column(name = "parent_business_unit_type_id")
    private Integer parentUnitId;
	@Column(name = "parent_business_unit_type_code")
    private String parentUnit;
	
}
