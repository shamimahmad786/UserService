package com.me.user.UserService.user.interceptor;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.me.user.UserService.user.interceptor.RequestHandlerInterceptor;

@Component
public class WebAppConfigAdapter implements WebMvcConfigurer {

	@Autowired
	RequestHandlerInterceptor interceptor;
	@Autowired
	ServletContext context;
	@Override
	public void addInterceptors(InterceptorRegistry interceptorRegistry) {
		// System.out.println("this method will get invoked by container while deployment");
		// System.out.println("value of interceptor is " + interceptor);
//		ServletContext context = ((HttpServletRequest) request).getSession().getServletContext();	
		   KeyPairGenerator keyGen = null;
					try {
						keyGen = KeyPairGenerator.getInstance("RSA");
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					}
				        keyGen.initialize(1024);
				        KeyPair pair = keyGen.generateKeyPair();
				       context.setAttribute("_private_key",Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded()));
				       context.setAttribute("_public_key", Base64.getEncoder().encodeToString(pair.getPublic().getEncoded()));
		
		interceptorRegistry.addInterceptor(interceptor);
	}
}