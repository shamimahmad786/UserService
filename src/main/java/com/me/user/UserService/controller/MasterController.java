package com.me.user.UserService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.me.user.UserService.db.NativeRepository;
import com.me.user.UserService.db.QueryResult;
import com.me.user.UserService.db.StaticReportBean;
import com.me.user.UserService.role.modal.Role;
import com.me.util.ApiPaths;

@RestController
@RequestMapping(ApiPaths.MasterCtrl.CTRL)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MasterController {
	
	@Autowired
	NativeRepository nativeRepository;
	@Autowired
	StaticReportBean staticReportBean;

	@RequestMapping(value = "/getState", method = RequestMethod.POST)
	public StaticReportBean getState() throws Exception {
		// System.out.println("In get State");
		QueryResult qrObj = nativeRepository.executeQueries("select * from mst_state");
		staticReportBean.setColumnName(qrObj.getColumnName());
		staticReportBean.setRowValue(qrObj.getRowValue());
		staticReportBean.setColumnDataType(qrObj.getColumnDataType());
		staticReportBean.setStatus("1");
		return staticReportBean;
	}
	
	@RequestMapping(value = "/getDistrict", method = RequestMethod.POST)
	public StaticReportBean getDistrict(@RequestBody Integer state_id) throws Exception {
		// System.out.println("In get District");
		QueryResult qrObj = nativeRepository.executeQueries("select * from mst_district where state_id="+state_id);
		staticReportBean.setColumnName(qrObj.getColumnName());
		staticReportBean.setRowValue(qrObj.getRowValue());
		staticReportBean.setColumnDataType(qrObj.getColumnDataType());
		staticReportBean.setStatus("1");
		return staticReportBean;
	}
	
	
	@RequestMapping(value = "/getBlock", method = RequestMethod.POST)
	public StaticReportBean getBlock(@RequestBody Integer state_id) throws Exception {
		// System.out.println("In get Block");
		QueryResult qrObj = nativeRepository.executeQueries("select * from mst_language");
		staticReportBean.setColumnName(qrObj.getColumnName());
		staticReportBean.setRowValue(qrObj.getRowValue());
		staticReportBean.setColumnDataType(qrObj.getColumnDataType());
		staticReportBean.setStatus("1");
		return staticReportBean;
	}
}
