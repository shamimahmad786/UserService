package com.me.user.UserService.bean;


public class BusinessUnit {
	
	String organization_hierarchy_master_id;
	Integer business_unit_type_id ;
	String business_unit_type_code ;
	String parent_business_unit_type_id;
	String dept;
	String business_unit_identity_code;
	String business_unit_identity_label;
	String business_unit_identity_name;
	Integer application_id;
	
	public String getOrganization_hierarchy_master_id() {
		return organization_hierarchy_master_id;
	}
	public void setOrganization_hierarchy_master_id(String organization_hierarchy_master_id) {
		this.organization_hierarchy_master_id = organization_hierarchy_master_id;
	}
	public Integer getBusiness_unit_type_id() {
		return business_unit_type_id;
	}
	public void setBusiness_unit_type_id(Integer business_unit_type_id) {
		this.business_unit_type_id = business_unit_type_id;
	}
	public String getBusiness_unit_type_code() {
		return business_unit_type_code;
	}
	public void setBusiness_unit_type_code(String business_unit_type_code) {
		this.business_unit_type_code = business_unit_type_code;
	}
	public String getParent_business_unit_type_id() {
		return parent_business_unit_type_id;
	}
	public void setParent_business_unit_type_id(String parent_business_unit_type_id) {
		this.parent_business_unit_type_id = parent_business_unit_type_id;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getBusiness_unit_identity_code() {
		return business_unit_identity_code;
	}
	public void setBusiness_unit_identity_code(String business_unit_identity_code) {
		this.business_unit_identity_code = business_unit_identity_code;
	}
	public String getBusiness_unit_identity_label() {
		return business_unit_identity_label;
	}
	public void setBusiness_unit_identity_label(String business_unit_identity_label) {
		this.business_unit_identity_label = business_unit_identity_label;
	}
	public String getBusiness_unit_identity_name() {
		return business_unit_identity_name;
	}
	public void setBusiness_unit_identity_name(String business_unit_identity_name) {
		this.business_unit_identity_name = business_unit_identity_name;
	}
	public Integer getApplication_id() {
		return application_id;
	}
	public void setApplication_id(Integer application_id) {
		this.application_id = application_id;
	}

	
	

}
