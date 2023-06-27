package com.me.user.UserService.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import com.example.demo.model.ReportAudit;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.user.UserService.bean.BusinessUnit;
import com.me.user.UserService.bean.DesignedChildBean;
import com.me.user.UserService.bean.DropdownBean;
import com.me.user.UserService.bean.DropdownMapping;
import com.me.user.UserService.bean.UserTypeBean;
import com.me.user.UserService.db.NativeRepository;
import com.me.user.UserService.db.QueryResult;
import com.me.user.UserService.db.StaticReportBean;
import com.me.user.UserService.group.modal.Group;
import com.me.user.UserService.group.service.GroupManagementImpl;
import com.me.user.UserService.organization.modal.OrganizationBusinessUnitType;
import com.me.user.UserService.role.modal.Application;
import com.me.user.UserService.role.modal.Role;
import com.me.user.UserService.role.modal.UserLevel;
import com.me.user.UserService.role.repository.RoleRepository;
import com.me.user.UserService.role.service.RoleManagementImpl;
import com.me.user.UserService.user.modal.UserRoleMapping;
import com.me.util.ApiPaths;

@RestController
@RequestMapping(ApiPaths.RoleCtrl.CTRL)
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = "http://10.25.26.251:4200", maxAge = 3600)
public class RoleManagement {
	@Autowired
	RoleManagementImpl roleManagementImpl;
	
	@Autowired
	NativeRepository nativeRepository;
	

	
	
	@RequestMapping(value = "/create-role", method = RequestMethod.POST)
	public Role createGroup(@RequestBody Role data) throws Exception {
		return roleManagementImpl.createRole(data);
	}
	@RequestMapping(value = "/get-role", method = RequestMethod.POST)
	public List<Role> getGroup() throws Exception {
//		// System.out.println("In get role--->"+data.getAppId());
		return roleManagementImpl.getRole();
	}
	
	@RequestMapping(value = "/get-roleByApplication", method = RequestMethod.POST)
	public List<Role> getGroup(@RequestBody Role data) throws Exception {
		// System.out.println("In get role--->"+data.getAppId());
		return roleManagementImpl.getRole(data.getAppId());
	}
	@RequestMapping(value = "/get-application", method = RequestMethod.POST)
	public StaticReportBean getApplication() throws Exception {
		
		// System.out.println("in get state review called");
		StaticReportBean sobj=new StaticReportBean();
		QueryResult qrObj = nativeRepository.executeQueries("select * from iamuser.application_master");
//				+ "order by  psq.domainid , psq.questionid ");
		sobj.setColumnName(qrObj.getColumnName());
		sobj.setRowValue(qrObj.getRowValue());
		sobj.setColumnDataType(qrObj.getColumnDataType());
		sobj.setStatus("1");
		return sobj;
		
//		// System.out.println("In get role");
//		return roleManagementImpl.getApplication();
	}
	@RequestMapping(value = "/get-userlevel", method = RequestMethod.POST)
	public StaticReportBean getUserlevel(@RequestBody UserTypeBean data) throws Exception {
		// System.out.println("applicationId---->"+data.getApplicationId());
		// System.out.println("userFlag--->"+data.getUserFlag());
		StaticReportBean sobj=new StaticReportBean();
		QueryResult qrObj = null ;
//		QueryResult qrObj = nativeRepository.executeQueries("select * from iamuser.organization_business_unit_type_master where application_id="+applicationId.getApplicationId()+"order by business_unit_type_id ");
		if(data.getUserFlag().equals("N")) {
			
			// System.out.println("in if condition");
			
		qrObj = nativeRepository.executeQueries("WITH RECURSIVE rec  as\r\n"
				+ "(\r\n"
				+ "  SELECT business_unit_type_id, business_unit_type_code from\r\n"
				+ "  iamuser.organization_business_unit_type_master  where\r\n"
				+ "  business_unit_type_id in\r\n"
				+ "  (\r\n"
				+ "select ohm.business_unit_type_id from role_user ru , iamuser.organization_hierarchy_master ohm\r\n"
				+ "where ru.user_id = "+data.getUserId()+" and ru.role_id = "+data.getRoleId()+" \r\n"
				+ "and ru.organization_hierarchy_master_id = ohm.organization_hierarchy_master_id\r\n"
				+ ")\r\n"
				+ "  UNION ALL\r\n"
				+ "  SELECT obutm .business_unit_type_id, obutm.business_unit_type_code\r\n"
				+ "  from rec, iamuser.organization_business_unit_type_master obutm  where obutm.parent_business_unit_type_id = rec.business_unit_type_id\r\n"
				+ "  )\r\n"
				+ "SELECT *\r\n"
				+ "FROM rec  limit 2;");
		}else if(data.getUserFlag().equals("S") || data.getUserFlag().equals("A")) {
			// System.out.println("in else condition");
			qrObj = nativeRepository.executeQueries("select * from iamuser.organization_business_unit_type_master where application_id="+data.getApplicationId()+"order by business_unit_type_id ");
		}
		sobj.setColumnName(qrObj.getColumnName());
		sobj.setRowValue(qrObj.getRowValue());
		sobj.setColumnDataType(qrObj.getColumnDataType());
		sobj.setStatus("1");
		return sobj;
	}
	
//	@RequestMapping(value = "/getDesignDataByUserLevel", method = RequestMethod.POST)
//	public LinkedList<Map<String,Object>> getDesignDataByUserLevel(@RequestBody String levelId){
//		// System.out.println("levelId---->"+levelId);
//		
//		StaticReportBean sobj=new StaticReportBean();
//		
//		String sql="";
//		QueryResult qrObj = nativeRepository.executeQueries(" select * from ("
//				+ "   WITH RECURSIVE parents AS (\r\n"
//				+ "  SELECT\r\n"
//				+ "business_unit_type_id, business_unit_type_code, parent_business_unit_type_id, 0 AS relative_depth\r\n"
//				+ "  FROM iamuser.organization_business_unit_type_master\r\n"
//				+ "  WHERE business_unit_type_id = "+Integer.parseInt(levelId)
//				+ "\r\n"
//				+ "  UNION ALL\r\n"
//				+ "\r\n"
//				+ "  SELECT\r\n"
//				+ "  cat.business_unit_type_id, cat.business_unit_type_code, cat.parent_business_unit_type_id, p.relative_depth - 1\r\n"
//				+ "  FROM iamuser.organization_business_unit_type_master cat, parents p\r\n"
//				+ "  WHERE cat.business_unit_type_id = p.parent_business_unit_type_id\r\n"
//				+ "  and cat.parent_business_unit_type_id is not null\r\n"
//				+ ")\r\n"
//				+ "SELECT null as business_unit_identity_code , null business_unit_identity_label , null as business_unit_identity_name,\r\n"
//				+ "business_unit_type_code, business_unit_type_id,  relative_depth , null as organization_hierarchy_master_id  FROM parents\r\n "
//				+ "  \r\n"
//				+ "union\r\n"
//				+ "\r\n"
//				+ "-- get all state\r\n"
//				+ "select ohm.business_unit_identity_code , ohm.business_unit_identity_label , ohm.business_unit_identity_name , ohm.business_unit_type_code , ohm.business_unit_type_id , null as relative_depth , ohm.organization_hierarchy_master_id \r\n"
//				+ "from iamuser.organization_hierarchy_master ohm\r\n"
//				+ "where ohm.business_unit_type_id in\r\n"
//				+ "(\r\n"
//				+ "\r\n"
//				+ " WITH RECURSIVE parents AS (\r\n"
//				+ " SELECT\r\n"
//				+ "business_unit_type_id, business_unit_type_code, parent_business_unit_type_id, 0 AS relative_depth\r\n"
//				+ " FROM iamuser.organization_business_unit_type_master\r\n"
//				+ " WHERE business_unit_type_id = "+Integer.parseInt(levelId)
//				+ "\r\n"
//				+ " UNION ALL\r\n"
//				+ "\r\n"
//				+ " SELECT\r\n"
//				+ " cat.business_unit_type_id, cat.business_unit_type_code, cat.parent_business_unit_type_id, p.relative_depth - 1\r\n"
//				+ " FROM iamuser.organization_business_unit_type_master cat, parents p\r\n"
//				+ " WHERE cat.business_unit_type_id = p.parent_business_unit_type_id\r\n"
//				+ " and cat.parent_business_unit_type_id is not null\r\n"
//				+ ")\r\n"
//				+ "select  business_unit_type_id FROM parents order by relative_depth limit 1\r\n"
//				+ ")"
//				+ " ) org order by relative_depth asc , business_unit_identity_name ");
//		sobj.setColumnName(qrObj.getColumnName());
//		sobj.setRowValue(qrObj.getRowValue());
//		sobj.setColumnDataType(qrObj.getColumnDataType());
//		sobj.setStatus("1");
//		
//		List<Map<String,Object>> litMap=new ArrayList<Map<String,Object>>(); 
//		LinkedList<Map<String,Object>> designList= new LinkedList<Map<String,Object>>();
//		
//		for(int i=0;i<qrObj.getRowValue().size();i++) {
//			if(qrObj.getRowValue().get(i).get("relative_depth") ==null) {
//				litMap.add(qrObj.getRowValue().get(i));
//			}else {
//				designList.add(qrObj.getRowValue().get(i));		
//			}
//		}
//		
//		designList.get(0).put("value", litMap);
//				
//		
//		return designList;
//	}
	
	int k=0;
	@RequestMapping(value = "/getDesignDataByUserLevel", method = RequestMethod.POST)
	public List<Map<String,Object>> getDesignDataByUserLevel(@RequestBody BusinessUnit data){
		
		// System.out.println("data---->"+data.getBusiness_unit_identity_code());
		
		List<Map<String,Object>> result = new ArrayList<>();
		StaticReportBean sobj=new StaticReportBean();
		StaticReportBean sobj1=new StaticReportBean();
		boolean flag = true;
		Object fieldFlag="";
		
		String SQL_ANCESTOR_OF_SELECTED_LEVEL_WITH_USER_VALUES ="";
		
		if(data.getBusiness_unit_identity_code() !=null) {
		
			// System.out.println("in if condition");
			
		SQL_ANCESTOR_OF_SELECTED_LEVEL_WITH_USER_VALUES = "  with recursive  rec_a  AS\r\n"
				+ "			    (\r\n"
				+ "			      select null as organization_hierarchy_master_id1,  obutm.business_unit_type_id , obutm.business_unit_type_code,obutm.parent_business_unit_type_id , 0 as dept\r\n"
				+ "			       from iamuser.organization_business_unit_type_master obutm \r\n"
				+ "			      WHERE business_unit_type_id = '"+data.getBusiness_unit_type_id()+"' and application_id = "+data.getApplication_id()+"\r\n"
				+ "			      UNION all\r\n"
				+ "			      select null as organization_hierarchy_master_id1, obutm.business_unit_type_id , obutm.business_unit_type_code,obutm.parent_business_unit_type_id ,  dept  -1 as dept\r\n"
				+ "			      from     rec_a, iamuser.organization_business_unit_type_master obutm  \r\n"
				+ "			      WHERE obutm.business_unit_type_id  = rec_a.parent_business_unit_type_id and application_id = "+data.getApplication_id()+"\r\n"
				+ "			    ) ,\r\n" + "			    rec_lv  AS\r\n" + "			    (\r\n"
				+ "			      select ohm.organization_hierarchy_master_id , ohm.business_unit_identity_code , ohm.business_unit_identity_label ,ohm.business_unit_identity_name, 0 as dept1,\r\n"
				+ "			     ohm.parent_business_unit_identity_code  , ohm.business_unit_type_id as business_unit_type_id1\r\n"
				+ "			      from iamuser.organization_hierarchy_master ohm \r\n"
				+ "			      WHERE business_unit_identity_code = '"+data.getBusiness_unit_identity_code()+"' and application_id = "+data.getApplication_id()+ " \r\n"
				+ "			      UNION all\r\n"
				+ "			      select ohm.organization_hierarchy_master_id , ohm.business_unit_identity_code , ohm.business_unit_identity_label ,ohm.business_unit_identity_name, dept1 - 1 as dept1,\r\n"
				+ "			      ohm.parent_business_unit_identity_code ,ohm.business_unit_type_id as business_unit_type_id1\r\n"
				+ "			      from rec_lv, iamuser.organization_hierarchy_master ohm \r\n"
				+ "			      WHERE ohm.business_unit_identity_code = rec_lv.parent_business_unit_identity_code and application_id = "+data.getApplication_id()+"\r\n"
				+ "			    ) \r\n" + "			   select * from rec_a left join  rec_lv\r\n"
				+ "			   ON rec_a.business_unit_type_id = rec_lv.business_unit_type_id1\r\n"
				+ "			   order by dept   ";
		
		}else if(data.getBusiness_unit_identity_code() ==null) {
			
			// System.out.println("in else condition");
			
			SQL_ANCESTOR_OF_SELECTED_LEVEL_WITH_USER_VALUES = "  with recursive  rec_a  AS\r\n"
					+ "			    (\r\n"
					+ "			      select null as organization_hierarchy_master_id1,  obutm.business_unit_type_id , obutm.business_unit_type_code,obutm.parent_business_unit_type_id , 0 as dept\r\n"
					+ "			       from iamuser.organization_business_unit_type_master obutm \r\n"
					+ "			      WHERE business_unit_type_id = '"+data.getBusiness_unit_type_id()+"' and application_id = "+data.getApplication_id()+"\r\n"
					+ "			      UNION all\r\n"
					+ "			      select null as organization_hierarchy_master_id1, obutm.business_unit_type_id , obutm.business_unit_type_code,obutm.parent_business_unit_type_id ,  dept  -1 as dept\r\n"
					+ "			      from     rec_a, iamuser.organization_business_unit_type_master obutm  \r\n"
					+ "			      WHERE obutm.business_unit_type_id  = rec_a.parent_business_unit_type_id and application_id = "+data.getApplication_id()+"\r\n"
					+ "			    ) ,\r\n" + "			    rec_lv  AS\r\n" + "			    (\r\n"
					+ "			      select ohm.organization_hierarchy_master_id , ohm.business_unit_identity_code , ohm.business_unit_identity_label ,ohm.business_unit_identity_name, 0 as dept1,\r\n"
					+ "			     ohm.parent_business_unit_identity_code  , ohm.business_unit_type_id as business_unit_type_id1\r\n"
					+ "			      from iamuser.organization_hierarchy_master ohm \r\n"
					+ "			      WHERE parent_business_unit_type_id is null and application_id = "+data.getApplication_id()+ " \r\n"
					+ "			      UNION all\r\n"
					+ "			      select ohm.organization_hierarchy_master_id , ohm.business_unit_identity_code , ohm.business_unit_identity_label ,ohm.business_unit_identity_name, dept1 - 1 as dept1,\r\n"
					+ "			      ohm.parent_business_unit_identity_code ,ohm.business_unit_type_id as business_unit_type_id1\r\n"
					+ "			      from rec_lv, iamuser.organization_hierarchy_master ohm \r\n"
					+ "			      WHERE ohm.business_unit_identity_code = rec_lv.parent_business_unit_identity_code and application_id = "+data.getApplication_id()+"\r\n"
					+ "			    ) \r\n" + "			   select * from rec_a left join  rec_lv\r\n"
					+ "			   ON rec_a.business_unit_type_id = rec_lv.business_unit_type_id1\r\n"
					+ "			   order by dept   ";
		}
		QueryResult qrObj = nativeRepository.executeQueries(SQL_ANCESTOR_OF_SELECTED_LEVEL_WITH_USER_VALUES);
		sobj.setColumnName(qrObj.getColumnName());
		sobj.setRowValue(qrObj.getRowValue());
		sobj.setColumnDataType(qrObj.getColumnDataType());
		sobj.setStatus("1");
		
		
		// System.out.println("1-------------------"+qrObj.getRowValue().size());
		
		
		for (int k=0;k<qrObj.getRowValue().size();k++) {
		//	i++;
			
			
			// System.out.println("running loop--->"+k);
			
			

			// // System.out.println("i "+i);
			Object organization_hierarchy_master_id =qrObj.getRowValue().get(k).get("organization_hierarchy_master_id");
			Object business_unit_type_id = qrObj.getRowValue().get(k).get("business_unit_type_id");
			Object business_unit_type_code = qrObj.getRowValue().get(k).get("business_unit_type_code");

			Object business_unit_identity_code = qrObj.getRowValue().get(k).get("business_unit_identity_code");
			Object business_unit_identity_label = qrObj.getRowValue().get(k).get("business_unit_identity_label");
			Object business_unit_identity_name = qrObj.getRowValue().get(k).get("business_unit_identity_name");
            
//			if(k<qrObj.getRowValue().size()) {
//			 fieldFlag=qrObj.getRowValue().get(k+1).get("business_unit_identity_code");
//			}

			

			if (business_unit_identity_code != null) {
				BusinessUnit obj = new BusinessUnit();
				
				Map<String,Object> mp=new HashMap<String,Object>();
				List<Map<String,Object>> obj1=new ArrayList<Map<String,Object>>();
				Map<String,Object> busMap=new HashMap<String,Object>();
				mp.put("business_unit_type_code", business_unit_type_code);
				
				
				// System.out.println("fieldFlag---->");
				
//				if(fieldFlag !=null) {
				mp.put("fieldFlag", "D");
//				}else {
//					mp.put("fieldFlag", "ND");
//				}
//				obj.setOrganization_hierarchy_master_id(String.valueOf(organization_hierarchy_master_id));
//				obj.setBusiness_unit_type_id(Integer.parseInt(String.valueOf(business_unit_type_id)));
//				obj.setBusiness_unit_type_code(String.valueOf(business_unit_type_code));
//				obj.setBusiness_unit_identity_code(String.valueOf(business_unit_identity_code));
//				obj.setBusiness_unit_identity_label(String.valueOf(business_unit_identity_label));
//				obj.setBusiness_unit_identity_name(String.valueOf(business_unit_identity_name));
				
//				mp.put("value", obj);
				busMap.put("organization_hierarchy_master_id", String.valueOf(organization_hierarchy_master_id));
				busMap.put("business_unit_type_id", Integer.parseInt(String.valueOf(business_unit_type_id)));
				busMap.put("business_unit_type_code", String.valueOf(business_unit_type_code));
				busMap.put("business_unit_identity_code", String.valueOf(business_unit_identity_code));
				busMap.put("business_unit_identity_label", String.valueOf(business_unit_identity_label));
				busMap.put("business_unit_identity_name", String.valueOf(business_unit_identity_name));
				
//				result.add(obj);
				obj1.add(busMap);
//				obj1.add(mp);
				
				mp.put("value", obj1);
				
				result.add(mp);
				
//				// System.out.println("1--------->"+result);
				
			    
			} else if (business_unit_identity_code == null && flag == true) { // business_unit_identity_code==null 

				flag = false;
				k--;
				//resultSet.previous();
				Object prev_business_unit_identity_code = qrObj.getRowValue().get(k).get("business_unit_identity_code");
				// System.out.println("prev_business_unit_identity_code----->"+prev_business_unit_identity_code);
//				resultSet.next();
				k++;
				String SQL_LIST_OF_VALUES_BUSINESS_UNIT_IDENTITY_CODE = "select organization_hierarchy_master_id, ohm.business_unit_type_id , ohm.business_unit_type_code ,ohm.parent_business_unit_type_id , ohm.business_unit_identity_name, '0'::integer,\r\n"
						+ "          ohm.business_unit_identity_code ,ohm.business_unit_identity_label ,0::integer, ohm.parent_business_unit_identity_name ,\r\n"
						+ "          ohm.business_unit_type_id \r\n"
						+ "          from iamuser.organization_hierarchy_master ohm where application_id = "+data.getApplication_id()+" and \r\n"
						+ "          parent_business_unit_identity_code ='" + prev_business_unit_identity_code
						+ "' and business_unit_type_id =" + business_unit_type_id;

//				PreparedStatement ListpreparedStatement = conn.prepareStatement(
//						SQL_LIST_OF_VALUES_BUSINESS_UNIT_IDENTITY_CODE, ResultSet.TYPE_SCROLL_INSENSITIVE,
//						ResultSet.CONCUR_UPDATABLE);
//				ResultSet ListresultSet = ListpreparedStatement.executeQuery();
				
				
				QueryResult qrObj1 = nativeRepository.executeQueries(SQL_LIST_OF_VALUES_BUSINESS_UNIT_IDENTITY_CODE);
//				sobj1.setColumnName(qrObj.getColumnName());
//				sobj1.setRowValue(qrObj.getRowValue());
//				sobj1.setColumnDataType(qrObj.getColumnDataType());
//				sobj1.setStatus("1");

//				// System.out.println(qrObj1.getRowValue().size());
				
				Map<String,Object> mp=new HashMap<String,Object>();
				
				List<Map<String,Object>> obj1=new ArrayList<Map<String,Object>>();
				
				
				for (int j=0; j<qrObj1.getRowValue().size();j++) {
					mp.put("business_unit_type_code", qrObj1.getRowValue().get(j).get("business_unit_type_code"));
					mp.put("fieldFlag", "ND");
				
					Map<String,Object> busMap=new HashMap<String,Object>();
					BusinessUnit obj = new BusinessUnit();
					Object organization_hierarchy_master_id2 = qrObj1.getRowValue().get(j).get("organization_hierarchy_master_id");
					Object business_unit_type_id2 = qrObj1.getRowValue().get(j).get("business_unit_type_id");
					Object business_unit_type_code2 = qrObj1.getRowValue().get(j).get("business_unit_type_code");

					Object business_unit_identity_code2 = qrObj1.getRowValue().get(j).get("business_unit_identity_code");
					Object business_unit_identity_label2 =qrObj1.getRowValue().get(j).get("business_unit_identity_label");
					Object business_unit_identity_name2 = qrObj1.getRowValue().get(j).get("business_unit_identity_name");

//					obj.setOrganization_hierarchy_master_id(String.valueOf(organization_hierarchy_master_id2));
//					obj.setBusiness_unit_type_id(Integer.parseInt(String.valueOf(business_unit_type_id2)));
//					obj.setBusiness_unit_type_code(String.valueOf(business_unit_type_code2));
//					obj.setBusiness_unit_identity_code(String.valueOf(business_unit_identity_code2));
//					obj.setBusiness_unit_identity_label(String.valueOf(business_unit_identity_label2));
//					obj.setBusiness_unit_identity_name(String.valueOf(business_unit_identity_name2));
					mp.put("business_unit_identity_code", String.valueOf(business_unit_identity_code2));
					busMap.put("organization_hierarchy_master_id", String.valueOf(organization_hierarchy_master_id2));
					busMap.put("business_unit_type_id", Integer.parseInt(String.valueOf(business_unit_type_id2)));
					busMap.put("business_unit_type_code", String.valueOf(business_unit_type_code2));
					busMap.put("business_unit_identity_code", String.valueOf(business_unit_identity_code2));
					busMap.put("business_unit_identity_label", String.valueOf(business_unit_identity_label2));
					busMap.put("business_unit_identity_name", String.valueOf(business_unit_identity_name2));
					
//					result.add(obj);
					obj1.add(busMap);
				}
				mp.put("value", obj1);
				
				result.add(mp);
				
				// System.out.println("final i--"+k);
				

			}else {  // business_unit_identity_code == null && flag == false
				BusinessUnit obj = new BusinessUnit();
				Map<String,Object> mp=new HashMap<String,Object>();
				mp.put("business_unit_type_code", business_unit_type_code);
				mp.put("fieldFlag", "ND");
				obj.setBusiness_unit_type_id(Integer.parseInt(String.valueOf(business_unit_type_id)));
				obj.setBusiness_unit_type_code(String.valueOf(business_unit_type_code));
				result.add(mp);
//				// System.out.println("3------------>"+result);
			}
//       // System.out.println("result------->"+result);
			
		
			// System.out.println("qrObj.getRowValue().size()----->"+qrObj.getRowValue().size());
		
		}
		
//		// System.out.println("row value----->"+qrObj.getRowValue());
//		// System.out.println("result------------>"+result);
		
	return result;
	}
	
	
	@RequestMapping(value = "/getDesignedChildData", method = RequestMethod.POST)
	public StaticReportBean getDesignedChildData(@RequestBody DesignedChildBean data){
//		// System.out.println(data.getApplication_id());
//		// System.out.println(data.getParent_business_unit_identity_code());
//		// System.out.println("data--->"+data.getDataType());
//		// System.out.println("getUserFlag----->"+data.getUserFlag());
//		
//		// System.out.println("Business Unit Identiy code--->"+data.getBusiness_unit_identity_code());
//		
		QueryResult qrObj=null;
		StaticReportBean sobj=new StaticReportBean();
//		if((data.getUserFlag().equals("N") && !data.getDataType().equalsIgnoreCase("A"))||(data.getUserFlag().equals("N") && !data.getDataType().equalsIgnoreCase("A") && data.getBusinessUnitTypeCodeFlag().equalsIgnoreCase("M"))) {
////		if(true) {
//		// System.out.println("In normal data");
//	     qrObj = nativeRepository.executeQueries(" with recursive\r\n"
//	     		+ "rec_d  AS\r\n"
//	     		+ "    (\r\n"
//	     		+ "      select ohm.business_unit_identity_code , ohm.business_unit_identity_label ,ohm.business_unit_identity_name, 1 as dept,'C' as rel_type,\r\n"
//	     		+ "     ohm.parent_business_unit_identity_code\r\n"
//	     		+ "      from iamuser.organization_hierarchy_master ohm\r\n"
//	     		+ "      WHERE business_unit_identity_code = '"+data.getParent_business_unit_identity_code()+"' and application_id = "+data.getApplication_id()+" \r\n"
//	     		+ "      UNION all\r\n"
//	     		+ "      select ohm.business_unit_identity_code , ohm.business_unit_identity_label ,ohm.business_unit_identity_name, dept + 1 as dept,'C' as rel_type,\r\n"
//	     		+ "      ohm.parent_business_unit_identity_code\r\n"
//	     		+ "      from rec_d, iamuser.organization_hierarchy_master ohm\r\n"
//	     		+ "      WHERE ohm.parent_business_unit_identity_code = rec_d.business_unit_identity_code and application_id = "+data.getApplication_id()+" \r\n"
//	     		+ "    ) ,\r\n"
//	     		+ "   \r\n"
//	     		+ "   \r\n"
//	     		+ " \r\n"
//	     		+ " -- all parents\r\n"
//	     		+ "\r\n"
//	     		+ "   rec_a  AS\r\n"
//	     		+ "    (\r\n"
//	     		+ "      select ohm.business_unit_identity_code , ohm.business_unit_identity_label ,ohm.business_unit_identity_name, 1 as dept,'P' as rel_type,\r\n"
//	     		+ "     ohm.parent_business_unit_identity_code\r\n"
//	     		+ "      from iamuser.organization_hierarchy_master ohm\r\n"
//	     		+ "      WHERE business_unit_identity_code = '"+data.getBusiness_unit_identity_code()+"' and application_id = "+data.getApplication_id()+" \r\n"
//	     		+ "      UNION all\r\n"
//	     		+ "      select ohm.business_unit_identity_code , ohm.business_unit_identity_label ,ohm.business_unit_identity_name, dept + 1 as dept,'P' as rel_type,\r\n"
//	     		+ "      ohm.parent_business_unit_identity_code\r\n"
//	     		+ "      from rec_a, iamuser.organization_hierarchy_master ohm\r\n"
//	     		+ "      WHERE ohm.business_unit_identity_code = rec_a.parent_business_unit_identity_code and application_id = "+data.getApplication_id()+"\r\n"
//	     		+ "    )\r\n"
//	     		+ "   select * from rec_a, rec_d\r\n"
//	     		+ "     \r\n"
//	     		+ "   where rec_d.dept =  2\r\n"
//	     		+ "  and rec_d.business_unit_identity_code = rec_a.business_unit_identity_code;");	
//			
////		 qrObj = nativeRepository.executeQueries("select * from iamuser.organization_hierarchy_master ohm where parent_business_unit_identity_code = '"+data.getParent_business_unit_identity_code()+"' and application_id = "+data.getApplication_id() +" and business_unit_type_id="+data.getBusiness_unit_type_id() +" and business_unit_identity_code='"+data.getBusiness_unit_identity_code()+"'");
//		}else if(data.getUserFlag().equals("N") && data.getDataType().equalsIgnoreCase("A")) {
//			 qrObj = nativeRepository.executeQueries("select * from iamuser.organization_hierarchy_master ohm where parent_business_unit_identity_code = '"+data.getParent_business_unit_identity_code()+"' and application_id = "+data.getApplication_id() +" and business_unit_type_id="+data.getBusiness_unit_type_id());
//		} else if(data.getUserFlag().equals("S") || data.getUserFlag().equals("A")) {
//			
//			// System.out.println("In system data");
			
		 qrObj = nativeRepository.executeQueries("select * from iamuser.organization_hierarchy_master ohm where parent_business_unit_identity_code = '"+data.getParent_business_unit_identity_code()+"' and application_id = "+data.getApplication_id() );	
//		}
		sobj.setColumnName(qrObj.getColumnName());
		sobj.setRowValue(qrObj.getRowValue());
		sobj.setColumnDataType(qrObj.getColumnDataType());
		sobj.setStatus("1");
		return sobj;
	}
	
	@RequestMapping(value = "/getBusinessUnitByApplicationId", method = RequestMethod.POST)
	public List<OrganizationBusinessUnitType> getBusinessUnitByApplicationId(@RequestBody String appId){
		// System.out.println("appId--->"+appId);
		return roleManagementImpl.getBusinessUnitByApplicationId(appId);
	}
	
	@RequestMapping(value = "/getRoleByBusinessUnitId", method = RequestMethod.POST)
	public List<Role> getRoleByBusinessUnitId(@RequestBody String bUnitId){
		return roleManagementImpl.getRoleByBusinessUnitId(bUnitId);
	}
	
	@RequestMapping(value = "/getRoleByApplicationId", method = RequestMethod.POST,consumes = MediaType.TEXT_PLAIN_VALUE)
	public List<Role> getRoleByApplicationId(@RequestBody String data){
		
		ObjectMapper mapperObj = new ObjectMapper();
		Role mappingId=new Role();
		try {
			mappingId = mapperObj.readValue(data, new TypeReference<Role>() {
			});
		}catch(Exception ex) {
			
		}
		
		// System.out.println("AppId--->"+mappingId.getAppId());
		
		return roleManagementImpl.getRoleByApplicationId(mappingId.getAppId());
	}
	
	@RequestMapping(value = "/getDropdownMapping", method = RequestMethod.POST,consumes = MediaType.TEXT_PLAIN_VALUE)
	public StaticReportBean getDropdownMapping(@RequestBody String data){
		
		ObjectMapper mapperObj = new ObjectMapper();
		DropdownMapping mappingId=new DropdownMapping();
		try {
			mappingId = mapperObj.readValue(data, new TypeReference<DropdownMapping>() {
			});
		}catch(Exception ex) {
			
		}
		
		QueryResult qrObj=null;
		StaticReportBean sobj=new StaticReportBean();
	   	 qrObj = nativeRepository.executeQueries("select * from master.mst_dropdown_mapping where buiness_unit_type_id="+mappingId.getBuiness_unit_type_id() +" and organization_id="+mappingId.getOrganization_id());	
			sobj.setColumnName(qrObj.getColumnName());
			sobj.setRowValue(qrObj.getRowValue());
			sobj.setColumnDataType(qrObj.getColumnDataType());
			sobj.setStatus("1");
			return sobj;
		
	}
	
	
	@RequestMapping(value = "/getKVDynamicDrodownMaster", method = RequestMethod.POST,consumes = MediaType.TEXT_PLAIN_VALUE)
	public StaticReportBean getDynamicDrodownMaster(@RequestBody String data){
        String query="";
		ObjectMapper mapperObj = new ObjectMapper();
		DropdownBean dataObj=new DropdownBean();
		try {
			dataObj = mapperObj.readValue(data, new TypeReference<DropdownBean>() {
			});
		}catch(Exception ex) {
			
		}
		
		if(dataObj.getUserName().equalsIgnoreCase("sysadmin")) {
			query="select 'RG' as  BusinessUnitType,   kmr.region_code as BusinessUnitCode  , kmr.region_name as BusinessUnitName  from master.kv_mst_region kmr  order by kmr.region_name";
		}
		
		QueryResult qrObj=null;
		StaticReportBean sobj=new StaticReportBean();
	   	 qrObj = nativeRepository.executeQueries(query);	
			sobj.setColumnName(qrObj.getColumnName());
			sobj.setRowValue(qrObj.getRowValue());
			sobj.setColumnDataType(qrObj.getColumnDataType());
			sobj.setStatus("1");
			return sobj;
		
	}
	
	
	@RequestMapping(value = "/getKVDependentData", method = RequestMethod.POST,consumes = MediaType.TEXT_PLAIN_VALUE)
	public StaticReportBean getKVDependentData(@RequestBody String data){
        String query="";
		ObjectMapper mapperObj = new ObjectMapper();
		DropdownBean dataObj=new DropdownBean();
		try {
			dataObj = mapperObj.readValue(data, new TypeReference<DropdownBean>() {
			});
		}catch(Exception ex) {
			
		}
		
		if(dataObj.getDependentFlag().equalsIgnoreCase("RG")) {
			query="select 'ST' as  BusinessUnitType,   statin_code as BusinessUnitCode  , station_name as BusinessUnitName  from master.kv_station_master kms where kms.region_code='"+dataObj.getDependentValue()+"'  order by kms.station_name ";
		}else if(dataObj.getDependentFlag().equalsIgnoreCase("ST")) {
			query="select 'SC' as  BusinessUnitType,   kv_code as BusinessUnitCode  , kv_name as BusinessUnitName  from master.kv_school_master kms where kms.station_code='"+dataObj.getDependentValue()+"'  order by kms.kv_name ";
		}
		
		QueryResult qrObj=null;
		StaticReportBean sobj=new StaticReportBean();
	   	 qrObj = nativeRepository.executeQueries(query);	
			sobj.setColumnName(qrObj.getColumnName());
			sobj.setRowValue(qrObj.getRowValue());
			sobj.setColumnDataType(qrObj.getColumnDataType());
			sobj.setStatus("1");
			return sobj;
		
	}
	
	
	
	
	
	
	

	
	
	
}
