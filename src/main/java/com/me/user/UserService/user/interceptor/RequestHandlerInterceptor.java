package com.me.user.UserService.user.interceptor;


import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

//import com.mhrd.SpringAuthSecurity.filter.AddableHttpRequest;
//import com.mhrd.SpringAuthSecurity.filter.CustomDataEncriptor;
//import com.mhrd.SpringAuthSecurity.filter.RSAUtil;

@Component
public class RequestHandlerInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		ServletContext context = ((HttpServletRequest) request).getSession().getServletContext();		
//		String privateKeys =  (String) context.getAttribute("_private_key");
//		// System.out.println("private key-->"+privateKeys);
//		
//		// System.out.println("preHandle() is invoked");
//		
//		// System.out.println(request.getRequestURI().indexOf("changePassword"));
//		
//		
//		if(request.getRequestURI().indexOf("renamePassword")>0) {
//			
//			 byte[] body = StreamUtils.copyToByteArray(request.getInputStream());
//			 // System.out.println(body);
//		        Map<String, Object> jsonRequest = new ObjectMapper().readValue(body, Map.class);
//		 
//		        // System.out.println("print json--->"+jsonRequest.get("userId"));
////		        String.valueOf(jsonRequest.get("userId"))
////		        // System.out.println(RSAUtil.decrypt(String.valueOf(jsonRequest.get("userId")), privateKeys));
//			 Map<String, String[]> extraParams = new TreeMap<String, String[]>();
////				try {
////					// System.out.println(RSAUtil.decrypt(request.getParameter("password"), privateKeys));
////					extraParams.put("password",new String[]{ RSAUtil.decrypt(request.getParameter("password"), privateKeys)});
////				} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException
////						| NoSuchAlgorithmException | NoSuchPaddingException e) {
////					e.printStackTrace();
////				}
//				
//		}
		
		
		
		   if(context.getAttribute("_private_key") ==null) {
			   KeyPairGenerator keyGen = null;
			try {
				keyGen = KeyPairGenerator.getInstance("RSA");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		        keyGen.initialize(2048);
		        KeyPair pair = keyGen.generateKeyPair();
		       context.setAttribute("_private_key",Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded()));
		       context.setAttribute("_public_key", Base64.getEncoder().encodeToString(pair.getPublic().getEncoded()));
		   }
//		   
//		   
//		
//		
//		// System.out.println("request forward");
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// System.out.println("postHandle() is invoked");

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// System.out.println("afterCompletion() is invoked");
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// System.out.println("afterConcurrentHandlingStarted() is invoked");
	}
}
