package com.me.user.UserService.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.me.user.UserService.bean.UserCradentialBean;
import com.me.util.ApiPaths;

@RestController
@RequestMapping(ApiPaths.KVCtrl.CTRL)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class KVController {

	@RequestMapping(value = "/getUserMasterDetails", method = RequestMethod.POST)
	public Map<String, Object> getUserMasterDetails(@RequestBody UserCradentialBean userData) throws Exception {
		
		return null;
	}
	
}
