package com.me.user.UserService.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
//import com.example.demo.bean.MapingCondition;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.user.UserService.bean.MailBean;
import com.me.user.UserService.bean.UserCradentialBean;
import com.me.user.UserService.db.NativeRepository;
import com.me.user.UserService.db.QueryResult;
import com.me.user.UserService.db.StaticReportBean;
import com.me.user.UserService.user.modal.User;
import com.me.user.UserService.user.modal.UserRoleMapping;
import com.me.user.UserService.user.repository.UserRepository;
import com.me.user.UserService.user.service.RestMailService;
import com.me.user.UserService.user.service.UserService;
import com.me.util.ApiPaths;
import com.me.util.FixHashing;

@RestController
@RequestMapping(ApiPaths.UserCredential.CTRL)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EndUserCredential {
	
//	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@Value("${authServeUrl}")
	private String authServeUrl;
	
	@Autowired
	NativeRepository nativeRepository;

	@Autowired
	RestMailService restMailService;
	
	@RequestMapping(value = "/get-usercradential", method = RequestMethod.POST,consumes = MediaType.TEXT_PLAIN_VALUE)
	public Map<String, Object> getUserCradential(@RequestBody String data) throws Exception {
		Map<String, Object> maps=new HashMap<String,Object>();
		ObjectMapper mapperObj = new ObjectMapper();
		UserCradentialBean userData=new UserCradentialBean();
		try {
			userData = mapperObj.readValue(data, new TypeReference<UserCradentialBean>() {
			});
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		if(userData==null) {
			maps.put("status", 0);
			maps.put("message", "You are not authrized to get user credentials");
			return maps;	
		}
		
		if(userData.getAuthcode()==null || userData.getAuthcode()=="") {
			maps.put("status", 0);
			maps.put("message", "You are not authrized to get user credentials");
			return maps;
		}
		
		// System.out.println("In get user credential");
		// System.out.println("Authorization Code------" + userData.getAuthcode());
        // System.out.println("Cradential----->"+userData.getAuthcredential());
        // System.out.println("userData.getRedirectUrl()--->"+userData.getRedirectUrl());
    	// System.out.println("auth server url--->"+authServeUrl);
		
		ResponseEntity<String> response = null;
		RestTemplate restTemplate = new RestTemplate();
		// According OAuth documentation we need to send the client id and secret key in the header for authentication
		byte[] credentials = java.util.Base64.getDecoder().decode(userData.getAuthcredential());
		// System.out.println("credentials--->"+credentials);
		String encodedCredentials = new String(Base64.encodeBase64(credentials));
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Basic " + encodedCredentials);
		HttpEntity<String> request = new HttpEntity<String>(headers);
//		String access_token_url = "http://pgi.seshagun.gov.in/SpringAuthSecurity/oauth/token";
		String access_token_url = authServeUrl+"/token";
		access_token_url += "?code=" + userData.getAuthcode();
		access_token_url += "&grant_type=authorization_code";
//		access_token_url += "&redirect_uri=http://pgi.seshagun.gov.in/shaguntest";
		access_token_url += "&redirect_uri="+userData.getRedirectUrl();

		// System.out.println("access_token_url---->"+access_token_url);
		
		response = restTemplate.exchange(access_token_url, HttpMethod.POST, request, String.class);
		
		 ObjectMapper mapper = new ObjectMapper();
		 Map<String, String> map=null;
		 try {
			   map = mapper.readValue(response.getBody(), Map.class);
			   // System.out.println("map---->"+map);
			  // System.out.println(map.get("access_token"));
		 }catch(Exception ex) {
			 ex.printStackTrace();
		 }
		 try {
			 maps= checkToken(map.get("access_token"),map.get("refresh_token"),encodedCredentials);
		 }catch(Exception ex) {
			 ex.printStackTrace();
		 }
		 
		// System.out.println("Access Token Response ---------" + response.getBody());

		return maps;
	}
	
	@RequestMapping(value = "/get-customusercradential", method = RequestMethod.POST)
	public Map<String, Object> getCustomUserCradential(@RequestBody UserCradentialBean userData) throws Exception {
		
		return null;
	}
	
	
	
	
	public Map<String, Object> checkToken(String  token,String refreshToken, String encodedCredentials) throws Exception {
//		// System.out.println("In check for token");
		// System.out.println("In check Token");
	    ObjectMapper mapper = new ObjectMapper();
	    Map<String, Object> map=null;
		ResponseEntity<String> response = null;
		RestTemplate restTemplate = new RestTemplate();

		// According OAuth documentation we need to send the client id and secret key in the header for authentication
//		String credentials = "pgi:pin";
//		String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Basic " + encodedCredentials);

		HttpEntity<String> request = new HttpEntity<String>(headers);

//		String access_token_url = "http://pgi.seshagun.gov.in/SpringAuthSecurity/oauth/check_token?token="+token;
		String access_token_url =  authServeUrl+"/check_token?token="+token;
//		access_token_url += "?code=" + authCode;
//		access_token_url += "&grant_type=authorization_code";
//		access_token_url += "&redirect_uri=http://10.25.26.251:4200/sandbox/home/dashboard";

		response = restTemplate.exchange(access_token_url, HttpMethod.GET, request, String.class);
		
		 try {
			   map = mapper.readValue(response.getBody(), Map.class);
			  // System.out.println(map.get("access_token"));
		 }catch(Exception ex) {
			 ex.printStackTrace();
		 }
		 map.put("token", token);
		 map.put("refresh_token", refreshToken);
		// System.out.println("Response data--->"+response.getBody());
		
		map.put("applicationDetails",getApplicationDetails(map.get("user_name").toString(),map));
		
		
		// System.out.println("return map----->"+map);
		
		return map;
		
	}
	
	public   List<Map<String, Object>> getApplicationDetails(String  userName,  Map<String, Object> map ) throws Exception{
		StaticReportBean sobj=new StaticReportBean();
		FixHashing hashObj=new FixHashing();
		QueryResult qrObj;
		
		if(userName.equalsIgnoreCase("sysadmin")) {
			qrObj = nativeRepository.executeQueries("select * from iamuser.application_master");
		}else {		
		 qrObj = nativeRepository.executeQueries("select * from public.role_user ru,user_details ud where ud.id= ru.user_id and ud.username='"+userName+"'");
		}
//							QueryResult qrObj = nativeRepository.executeQueries("with cte_a as (\r\n"
//									+ "				  select id,username,email,firstname,lastname,mobile,parentuser from user_details ud where username = '"+userName+"'\r\n"
//									+ "				  ) ,\r\n"
//									+ "				 \r\n"
//									+ "				   cte_b as (\r\n"
//									+ "				  select *\r\n"
//									+ "				  from iamuser.organization_hierarchy_master ohm , role_user ru\r\n"
//									+ "				  where ohm.organization_hierarchy_master_id = ru.organization_hierarchy_master_id\r\n"
//									+ "				  )\r\n"
//									+ "				  select * from  cte_a left join cte_b\r\n"
//									+ "				  on cte_a.id = cte_b.user_id");		  
				  
		// System.out.println(qrObj.getRowValue());
		
		if(!userName.equalsIgnoreCase("sysadmin")) {
		map.put("firstname", qrObj.getRowValue().get(0).get("firstname"));
		map.put("lastname", qrObj.getRowValue().get(0).get("lastname"));
		map.put("mobile", qrObj.getRowValue().get(0).get("mobile"));
		map.put("email", qrObj.getRowValue().get(0).get("email"));
		map.put("hashId",hashObj.encrypt(String.valueOf(qrObj.getRowValue().get(0).get("user_id"))));
		}else if(userName.equalsIgnoreCase("sysadmin")){
			map.put("firstname", "sysadmin");
			qrObj.getRowValue().get(0).put("role_id", "99");
		}
		return qrObj.getRowValue();
	}
	
	@RequestMapping(value = "/validateToken", method = RequestMethod.POST)
	public Map<String, Object> validateToken(@RequestHeader(value="token") String token,@RequestHeader(value="authcredential") String credentials) {
		// System.out.println("In check for token");
		Map<String, Object> mapObj=new  HashMap<String, Object>(); 
	    ObjectMapper mapper = new ObjectMapper();
	    Map<String, Object> map=null;
		ResponseEntity<String> response = null;
		RestTemplate restTemplate = new RestTemplate();

		// According OAuth documentation we need to send the client id and secret key in the header for authentication
//		String credentials = "pgi:pin";
		String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Basic " + encodedCredentials);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		// System.out.println("auth server url--->"+authServeUrl);
		String access_token_url =  authServeUrl+"/check_token?token="+token;
//		String access_token_url = "http://pgi.seshagun.gov.in/SpringAuthSecurity/oauth/check_token?token="+token;
//		access_token_url += "?code=" + authCode;
//		access_token_url += "&grant_type=authorization_code";
//		access_token_url += "&redirect_uri=http://10.25.26.251:4200/sandbox/home/dashboard";
		try {
		response = restTemplate.exchange(access_token_url, HttpMethod.GET, request, String.class);
		map = mapper.readValue(response.getBody(), Map.class);
		
		if(map.get("error") !=null) {
			map.put("status", "0");
		}else if(map.get("client_id") !=null) {
			map.put("status", "1");			
		}
		
		}catch(Exception ex) {
			map.put("status", 0);
		}
//		 try {
//			   map = mapper.readValue(response.getBody(), Map.class);
//			  // System.out.println(map.get("access_token"));
//		 }catch(Exception ex) {
//			 ex.printStackTrace();
//		 }
		 map.put("token", token);
		// System.out.println("Response data--->"+response.getBody());
//		map.put("ApplicationDetails",getApplicationDetails(map.get("user_name").toString()));
		return map;
	}
	
	

	@RequestMapping(value = "/mailService", method = RequestMethod.POST)
	public ResponseEntity<Map> mailService(String data) {
		
//		RestMailService mailService=new RestMailService();
		
		// System.out.println("Mail Service");
		
		ObjectMapper mapperObj = new ObjectMapper();
		MailBean userData=new MailBean();
		try {
			userData = mapperObj.readValue(data, new TypeReference<MailBean>() {
			});
		}catch(Exception ex) {
			
		}
		
		try {
		return	restMailService.getPostsPlainJSON(userData)	;
		}catch(Exception ex) {
			
			ex.printStackTrace();
			
		}
		return null;
		
		
	}
	
	
	
}
