package com.me.user.UserService.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


//import com.example.MOERADTEACHER.modal.TeacherFormStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.user.UserService.bean.RenamePassword;
import com.me.user.UserService.bean.UpdateRoleBean;
import com.me.user.UserService.db.NativeRepository;
import com.me.user.UserService.db.QueryResult;
import com.me.user.UserService.db.StaticReportBean;
import com.me.user.UserService.group.modal.Group;
import com.me.user.UserService.user.interceptor.RSAUtil;
import com.me.user.UserService.user.modal.PermisionRoleMapping;
import com.me.user.UserService.user.modal.User;
import com.me.user.UserService.user.modal.UserRoleMapping;
import com.me.user.UserService.user.service.UserService;
//import com.me.user.UserService.role.modal.User;
import com.me.util.ApiPaths;

@RestController
@RequestMapping(ApiPaths.UserCtrl.CTRL)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserManagement {
	
	@Autowired
	UserService userService; 
	
	@Autowired
	NativeRepository nativeRepository;
	
	@RequestMapping(value = "/create-user", method = RequestMethod.POST)
	public User createUser(@RequestBody String data) throws Exception {
		
		ObjectMapper mapperObj = new ObjectMapper();
		User userdata=new User();
		try {
			userdata = mapperObj.readValue(data, new TypeReference<User>() {
			});
		}catch(Exception ex) {
//			LOGGER.warn("--message--",ex);
		}
		
		// System.out.println("get user name--->"+userdata.getUsername());
		// System.out.println("get eNABLE--->"+userdata.getEnabled());
//		// System.out.println("account---->"+data.getAccountnonexpired());
		
		return userService.createUser(userdata);
	}
	
	
	@RequestMapping(value = "/create-kvuser", method = RequestMethod.POST)
	public User createKVUser(@RequestBody String data) throws Exception {
		ObjectMapper mapperObj = new ObjectMapper();
		
		
		System.out.println("data--->"+data);
		
		User userdata=new User();
		try {
			userdata = mapperObj.readValue(data, new TypeReference<User>() {
			});
		}catch(Exception ex) {
			ex.printStackTrace();
//			LOGGER.warn("--message--",ex);
		}
		// System.out.println("get user name--->"+userdata.getUsername());
		// System.out.println("get eNABLE--->"+userdata.getEnabled());
//		// System.out.println("account---->"+data.getAccountnonexpired());
		return userService.createKVUser(userdata);
	}
	
	@RequestMapping(value = "/get-user", method = RequestMethod.POST)
	public List<User> getUser() throws Exception {
		return userService.getUser();
		
//		return null;
//		return groupManagementImpl.getGroup();
	}
	
	@RequestMapping(value = "/getUsersByParentId", method = RequestMethod.POST)
	public List<User> getUsersByParentId(@RequestBody User data) throws Exception {
		return userService.getUsersByParentId(data.getParentuser());
	}
	
	
	
	@RequestMapping(value = "/user-role", method = RequestMethod.POST)
	public UserRoleMapping userRole(@RequestBody UserRoleMapping data) throws Exception {
		// System.out.println("Application Name data---->"+data.getApplicationName());
		// System.out.println("RoleId ---->"+data.getRoleId());
		// System.out.println("Permision--->"+data.getPermision());
		
		//commented after new changes on date 12-10-2021
//		List<String> items = Arrays.asList(data.getPermision().split("\\s*,\\s*"));
//		for(int i=0;i<items.size();i++) {
//			// System.out.println(items.get(i));
//			PermisionRoleMapping obj=new PermisionRoleMapping();
//			obj.setRoleId(data.getRoleId());
//			obj.setPermissionId(Integer.parseInt(items.get(i)));
//			userService.permissionRole(obj);
//		}
		
		
		
		return userService.userRole(data);
//		return groupManagementImpl.getGroup();
	}
	
	@RequestMapping(value = "/getUserRole", method = RequestMethod.POST)
	public StaticReportBean getUserRole(@RequestBody UserRoleMapping data) throws Exception {
		StaticReportBean sobj=new StaticReportBean();		
		QueryResult qrObj = nativeRepository.executeQueries("select * from master.kv_allBusinessUnit_master km , role_user ru "
				+ " where user_id ="+data.getUserId()
				+ " and application_id ="+data.getApplicationId()
				+ " and ru.business_unit_type_id = km.BusinessUnitTypeid  "
				+ " and ru.business_unit_type_code = km.businessunitcode ");
		sobj.setColumnName(qrObj.getColumnName());
		sobj.setRowValue(qrObj.getRowValue());
		sobj.setColumnDataType(qrObj.getColumnDataType());
		sobj.setStatus("1");
		return sobj;
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public Map<String,Object> resetPassword(@RequestBody String userId) throws Exception {
		// System.out.println("userId---->"+userId);
		return userService.resetPassword(userId);
	}
	
	
	  @RequestMapping(value = "/getKey", method = RequestMethod.POST)
		public ResponseEntity<?> getKey(HttpServletRequest req)  {
			Map<String,Object> mp=new HashMap<String,Object>();
			ServletContext context = ((HttpServletRequest) req).getSession().getServletContext();
			// System.out.println("req--->"+context.getAttribute("_public_key"));
			mp.put("key",context.getAttribute("_public_key"));
			return ResponseEntity.ok(mp);
		}
	  
	  
	  @RequestMapping(value = "/renamePassword", method = RequestMethod.POST)
		public Map<String,Object> renamePassword(@RequestBody String data,HttpServletRequest request) throws Exception {
		  
		   System.out.println("data---->"+data);
		  
		  ServletContext context = ((HttpServletRequest) request).getSession().getServletContext();		
			String privateKeys =  (String) context.getAttribute("_private_key");
		 String userId = null;	
		  String password = null;
		  String newPassword = null;
		  // System.out.println(data);
		  ObjectMapper mapperObj = new ObjectMapper();
			RenamePassword tdata=new RenamePassword();
			try {
				tdata = mapperObj.readValue(data, new TypeReference<RenamePassword>() {
				});
			}catch(Exception ex) {
				ex.printStackTrace();
//				LOGGER.warn("--message--",ex);
			}
			
			 System.out.println("userId---->"+tdata.getUserId());
			
			try {
				userId=RSAUtil.decrypt(String.valueOf(tdata.getUserId()), privateKeys);
				password=RSAUtil.decrypt(String.valueOf(tdata.getOldPassword()), privateKeys);
				newPassword=RSAUtil.decrypt(String.valueOf(tdata.getNewPassword()), privateKeys);
				
				System.out.println("userId--->"+userId);
				 System.out.println("password--->"+password);
				 System.out.println("newPassword--->"+newPassword);
//				 System.out.println("matched1--->"+matched1);
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
			
		  return userService.renamePassword(userId,password,newPassword);
//			// System.out.println("userId---->"+userId);
//			return userService.resetPassword(userId);
		}
	  
	  
	  
	  @RequestMapping(value = "/updateRoleOnDropbox", method = RequestMethod.POST)
		public User updateRoleOnDropbox(@RequestBody String data) throws Exception {
		  
		  System.out.println("updateRoleOnDropbox--->"+data);
			ObjectMapper mapperObj = new ObjectMapper();
			UpdateRoleBean userdata=new UpdateRoleBean();
			try {
				userdata = mapperObj.readValue(data, new TypeReference<UpdateRoleBean>() {
				});
			}catch(Exception ex) {
				ex.printStackTrace();
//				LOGGER.warn("--message--",ex);
			}
			
			System.out.println("updateRoleOnDropbox--->"+userdata.getBusinessUnitTypeCode());
			
			return userService.updateRoleOnDropbox(userdata);
		}
	  
	  @RequestMapping(value = "/updateCredential", method = RequestMethod.POST)
			public Map<Object,Object> updateCredential(@RequestBody String data,HttpServletRequest request) throws Exception {
		  ServletContext context = ((HttpServletRequest) request).getSession().getServletContext();		
			String privateKeys =  (String) context.getAttribute("_private_key");
			
			  System.out.println("updateRoleOnDropbox--->"+data);
				ObjectMapper mapperObj = new ObjectMapper();
				User userdata=new User();
				try {
					userdata = mapperObj.readValue(data, new TypeReference<User>() {
					});
				}catch(Exception ex) {
					ex.printStackTrace();
//					LOGGER.warn("--message--",ex);
				}
				userdata.setMobile(RSAUtil.decrypt(String.valueOf(userdata.getMobile()), privateKeys));
				System.out.println("updateRoleOnDropbox--->"+userdata.getBusinessUnitTypeCode());
				return userService.updateCredential(userdata);
			}
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	
}
