package com.me.user.UserService.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.me.user.UserService.bean.MailBean;
import com.me.user.UserService.bean.UpdateRoleBean;
import com.me.user.UserService.db.NativeRepository;
import com.me.user.UserService.db.QueryResult;
import com.me.user.UserService.group.modal.Group;
import com.me.user.UserService.user.modal.PermisionRoleMapping;
import com.me.user.UserService.user.modal.User;
import com.me.user.UserService.user.modal.UserRoleMapping;
import com.me.user.UserService.user.repository.PermisionRoleMappingRepository;
import com.me.user.UserService.user.repository.UserRepository;
import com.me.user.UserService.user.repository.UserRoleMappingRepository;
import com.me.util.FixHashing;

//import org.springframework.security.crypto.bcrypt.BCrypt;
@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserRoleMappingRepository userRoleMappingRepository;
	@Autowired
	PermisionRoleMappingRepository permisionRoleMappingRepository;

	@Autowired
	RestMailService restMailService;

	@Autowired
	NativeRepository nativeRepository;

	public User createUser(User data) throws Exception {
		data.setPassword("{bcrypt}" + data.getPassword());
		User userObj = userRepository.save(data);

		FixHashing hashObj = new FixHashing();
//
//        String target="imparator";
		String encrypted = hashObj.encrypt(String.valueOf(userObj.getId()));
//        String decrypted=td.decrypt(encrypted);
		userObj.setUserHash(encrypted);
		userObj.setId(null);
		userObj.setPassword(null);
		return userObj;
//		return groupManagementImpl.createGroup(data);
	}

	public User createKVUser(User data) throws Exception {
		
//		String id = null;
//		try {
//			QueryResult qrObj = null;
//
//			qrObj = nativeRepository.executeQueries(" select max(id) as id from user_details ");
//			System.out.println(qrObj.getRowValue().size());
//			if (qrObj.getRowValue().size() > 0) {
//				id = String.valueOf(qrObj.getRowValue().get(0).get("id"));
//			}
//			
//			System.out.println("max id--->"+id);
//			
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
		
		data.setPassword("{bcrypt}" + data.getPassword());
		User userObj = new User();
//		data.setBusinessUnitTypeCode("null");
		if (!(data.getBusinessUnitTypeCode() == "null") && data.getBusinessUnitTypeCode() != null) {

			User findUserObj = userRepository.findAllByUsername(data.getUsername());
			// System.out.println("findUserObj---->"+findUserObj);
			FixHashing hashObj = new FixHashing();
			if (findUserObj != null) {
				data.setId(findUserObj.getId());
				userObj = userRepository.save(data);

				List<UserRoleMapping> map = userRoleMappingRepository.findAllByUserName(findUserObj.getUsername());

				if (map.size() == 0) {
					UserRoleMapping mapObj = new UserRoleMapping();
					mapObj.setApplicationId(2);
					mapObj.setApplicationName("Teacher");
					mapObj.setUserId(findUserObj.getId());
					mapObj.setUserName(data.getUsername());
					mapObj.setRoleType("1");
					mapObj.setUserLevelId(6);
					mapObj.setRoleId(16);
					mapObj.setUserLevelName(data.getBusinessUnitTypeCode());
					userRole(mapObj);
				} else {
					UserRoleMapping mapObj = new UserRoleMapping();

					// System.out.println("role id--->"+map.get(0).getId());

//				 mapObj.setId(map.get(0).getId());
					mapObj.setRoleId(map.get(0).getRoleId());
					mapObj.setApplicationId(2);
					mapObj.setApplicationName("Teacher");
					mapObj.setUserId(findUserObj.getId());
					mapObj.setUserName(data.getUsername());
					mapObj.setRoleType("1");
					mapObj.setUserLevelId(6);
					mapObj.setRoleId(16);
					mapObj.setUserLevelName(data.getBusinessUnitTypeCode());
					userRole(mapObj);
				}

				if (findUserObj.getUsername() != null) {
					String encrypted = hashObj.encrypt(String.valueOf(findUserObj.getId()));
					findUserObj.setUserHash(String.valueOf(findUserObj.getId()));
					findUserObj.setId(null);
					findUserObj.setPassword(null);
					findUserObj.setBusinessUnitTypeCode(data.getBusinessUnitTypeCode());
					findUserObj.setStatus("1");
					return findUserObj;
				}

				findUserObj.setStatus("1");

				return findUserObj;
			} else {
				// System.out.println("in else condition");
				String id = null;
				try {
					QueryResult qrObj = null;
					qrObj = nativeRepository.executeQueries(" select max(id) as id from user_details ");
					if (qrObj.getRowValue().size() > 0) {
						id = String.valueOf(qrObj.getRowValue().get(0).get("id"));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				try {
					if(id !=null) {
					data.setId(Integer.parseInt(id+1));
					}
					userObj = userRepository.save(data);
					UserRoleMapping mapObj = new UserRoleMapping();
					mapObj.setApplicationId(2);
					mapObj.setApplicationName("Teacher");
					mapObj.setUserId(userObj.getId());
					mapObj.setUserName(data.getUsername());
					mapObj.setRoleType("1");
					mapObj.setUserLevelId(6);
					mapObj.setRoleId(16);
					mapObj.setUserLevelName(data.getBusinessUnitTypeCode());
					userRole(mapObj);
					String encrypted = hashObj.encrypt(String.valueOf(userObj.getId()));
					userObj.setUserHash(String.valueOf(userObj.getId()));
					userObj.setId(null);
					userObj.setPassword(null);

				} catch (Exception ex) {
					ex.printStackTrace();
				}

				try {
// System.out.println("Before message send");
					MailBean obj = new MailBean();
					obj.setApplicationName("Kvs Teacher");
					obj.setApplicationId("1");
					obj.setEmailTemplateId("MSG-5836");
					obj.setEmailTo(userObj.getEmail());
					obj.setSubject("Teacher Module Credential");
					obj.setSignature("Dear " + userObj.getFirstname());
					obj.setContent(
							"Your login account has been created successfully for KV Annual Transfer Process. You are requested to update your profile on the portal https://kvsonlinetransfer.kvs.gov.in by using the UID: "
									+ userObj.getUsername()
									+ " and Password: system123# - Team NIC -Ministry of Education, Government of India");
					obj.setClosing("KVS Headquarter");
					obj.setMobile(userObj.getMobile());
					obj.setUserid(userObj.getUsername());
					obj.setName(userObj.getFirstname());
					obj.setAttachmentYn(null);
					obj.setAttachmentPath(null);

					restMailService.getPostsPlainJSON(obj);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				userObj.setStatus("1");
				return userObj;
			}

		} else {
			System.out.println("Business unit code null and user id--->" + userObj.getUsername());
			userObj.setStatus("0");
			return userObj;
		}

	}

	public List<User> getUser() throws Exception {
		// System.out.println("In get user");
		return userRepository.findAllTop10();
//		return groupManagementImpl.createGroup(data);
	}

	public List<User> getUsersByParentId(String parentuser) throws Exception {
		// System.out.println("In get user");
		return userRepository.findAllByParentuserOrderByUsernameAsc(parentuser);
//		return groupManagementImpl.createGroup(data);
	}

	public UserRoleMapping userRole(UserRoleMapping data) throws Exception {

		return userRoleMappingRepository.save(data);
	}

	public List<UserRoleMapping> getUserRole(Integer userId) throws Exception {

		return userRoleMappingRepository.findByUserId(userId);
	}

	public void permissionRole(PermisionRoleMapping data) throws Exception {

		permisionRoleMappingRepository.save(data);
	}

	public List<UserRoleMapping> getApplicationDetails(String userName) {
		User userObj = userRepository.findByUsername(userName);
		return userRoleMappingRepository.findAllByUserId(userObj.getId());
	}

	public List<User> getUserDetails(String userName) {
		User userObj = userRepository.findByUsername(userName);
		// System.out.println("Role data--->"+userObj.getRoles());
		return null;
	}

	public Map<String, Object> resetPassword(String userName) {
		User findUserObj = userRepository.findAllByUsername(userName);
		Map<String, Object> mp = new HashMap<String, Object>();

		// System.out.println("findUserObj--->"+findUserObj);

		try {
			if (findUserObj != null) {

				try {
					findUserObj.setPassword("{bcrypt}$2a$10$xRoEcGw9rTUrhvC7EDsVS.Hu1df3mfW.mMkeJ03AlCFvX5goIj9R6");
					userRepository.save(findUserObj);
					MailBean obj = new MailBean();
					obj.setApplicationName("Kvs Teacher");
					obj.setApplicationId("1");
					obj.setEmailTemplateId("MSG-5836");
					obj.setEmailTo(findUserObj.getEmail());
					obj.setSubject("Teacher Module Credential");
					obj.setSignature("Dear " + findUserObj.getFirstname());
					obj.setContent(
							"Your login account has been created successfully for KV Annual Transfer Process. You are requested to update your profile on the portal https://pgi.udiseplus.gov.in/school by using the UID: "
									+ findUserObj.getUsername()
									+ " and Password: system123# - Team NIC -Ministry of Education, Government of India");
					obj.setClosing("KVS Headquarter");
					obj.setMobile(findUserObj.getMobile());
					obj.setUserid(findUserObj.getUsername());
					obj.setName(findUserObj.getFirstname());
					restMailService.getPostsPlainJSON(obj);
					mp.put("status", 1);
					mp.put("message", "Passowrd reset successfully");
				} catch (Exception ex) {
					mp.put("status", 0);
					mp.put("message", "Error in password reset or message delivery");
					ex.printStackTrace();
				}
			} else {
				mp.put("status", 0);
				mp.put("message", "User Don't Exist");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mp;
	}

	public Map<String, Object> renamePassword(String userId, String password, String newPassword) {

//	// System.out.println("userId--->"+userId);
		Map<String, Object> mp = new HashMap<String, Object>();

		User userObj = userRepository.findByUsername(userId);
		String exsitingPassword;
		String generatedSecuredPasswordHash;
		if (userObj != null) {
			exsitingPassword = userObj.getPassword().replace("{bcrypt}", "");
			boolean matched1 = BCrypt.checkpw(password, exsitingPassword);
//		
			System.out.println("userId--->" + userId);
			System.out.println("password--->" + password);
			System.out.println("newPassword--->" + newPassword);
			System.out.println("matched1--->" + matched1);

			if (matched1) {
				generatedSecuredPasswordHash = BCrypt.hashpw(newPassword, BCrypt.gensalt(10));
				userObj.setPassword("{bcrypt}" + generatedSecuredPasswordHash);
				userObj.setTextPassword(newPassword);
				userObj.setParentuser("admin");

				// System.out.println("userObj--->"+userObj.getTextPassword());
				userRepository.save(userObj);
				mp.put("status", 1);
				mp.put("message", "Password Changed successfully");
//			 // System.out.println("final password-->"+userObj.getPassword());
			} else {
				mp.put("status", 0);
				mp.put("message", "Old password didn't match");
				// System.out.println("not updated");
			}
		} else {
			mp.put("status", 0);
			mp.put("message", "Old password didn't match");
		}

//	// System.out.println("Role data--->"+userObj.getRoles());
		return mp;
	}

	public User updateRoleOnDropbox(UpdateRoleBean userDetails) {
		Integer userId = null;
		QueryResult qrObj = null;
		try {

			qrObj = nativeRepository
					.executeQueries("select id from user_details where username='" + userDetails.getTeacherId() + "'");
			if (qrObj.getRowValue().size() > 0) {
				userId = Integer.parseInt(String.valueOf(qrObj.getRowValue().get(0).get("id")));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			if (qrObj.getRowValue().size() > 0) {
				nativeRepository.updateQueries("update public.role_user set business_unit_type_code='"
						+ userDetails.getBusinessUnitTypeCode() + "' where user_id=" + userId);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;

	}

	public Map<Object, Object> updateCredential(User userdetails) {

		Map<Object, Object> mp = new HashMap<Object, Object>();
		try {
			FixHashing hashObj = new FixHashing();
			System.out.println(userdetails.getMobile());
			nativeRepository.updateQueries("update public.user_details set mobile='" + userdetails.getMobile()
					+ "' where username='" + userdetails.getUsername() + "'");
			mp.put("status", "1");
		} catch (Exception ex) {
			mp.put("status", "0");
			ex.printStackTrace();
		}
		return mp;
	}

}
