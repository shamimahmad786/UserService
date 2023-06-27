//package com.me.user.UserService.bean;
//
//
//import java.math.BigDecimal;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.mkyong.jdbc.model.Employee;
//
//public class TestJdbc {
//
//	private static final String SQL_SELECT = "SELECT * FROM EMPLOYEE";
//
//	public static void main(String[] args) {
//
//		// sameusersamelevel();
//		userlevel();
//		try {
//			// // System.out.println("1");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	public static void userlevel() {
//
//		String strIdenticode = "100102";
//
//		int user_business_unit_type_id = 404; // Information received from Login 402- NATIONA 403- STATE-404 DISTRICT
//												// 405- BLOCK
//		String user_business_unit_identity_code = "100102";
//		int selected_level_business_unit_type_id = 405;
//
//		List<BusinessUnit> result = new ArrayList<>();
//		
//		
//		String SQL_ANCESTOR_OF_SELECTED_LEVEL_WITH_USER_VALUES = "  with recursive  rec_a  AS\r\n"
//				+ "			    (\r\n"
//				+ "			      select null as organization_hierarchy_master_id1,  obutm.business_unit_type_id , obutm.business_unit_type_code,obutm.parent_business_unit_type_id , 0 as dept\r\n"
//				+ "			       from iamuser.organization_business_unit_type_master obutm \r\n"
//				+ "			      WHERE business_unit_type_id = '405' and application_id = 399\r\n"
//				+ "			      UNION all\r\n"
//				+ "			      select null as organization_hierarchy_master_id1, obutm.business_unit_type_id , obutm.business_unit_type_code,obutm.parent_business_unit_type_id ,  dept  -1 as dept\r\n"
//				+ "			      from     rec_a, iamuser.organization_business_unit_type_master obutm  \r\n"
//				+ "			      WHERE obutm.business_unit_type_id  = rec_a.parent_business_unit_type_id and application_id = 399\r\n"
//				+ "			    ) ,\r\n" + "			    rec_lv  AS\r\n" + "			    (\r\n"
//				+ "			      select ohm.organization_hierarchy_master_id , ohm.business_unit_identity_code , ohm.business_unit_identity_label ,ohm.business_unit_identity_name, 0 as dept1,\r\n"
//				+ "			     ohm.parent_business_unit_identity_code  , ohm.business_unit_type_id as business_unit_type_id1\r\n"
//				+ "			      from iamuser.organization_hierarchy_master ohm \r\n"
//				+ "			      WHERE business_unit_identity_code = 'PGI_CD_001' and application_id = 399\r\n"
//				+ "			      UNION all\r\n"
//				+ "			      select ohm.organization_hierarchy_master_id , ohm.business_unit_identity_code , ohm.business_unit_identity_label ,ohm.business_unit_identity_name, dept1 - 1 as dept1,\r\n"
//				+ "			      ohm.parent_business_unit_identity_code ,ohm.business_unit_type_id as business_unit_type_id1\r\n"
//				+ "			      from rec_lv, iamuser.organization_hierarchy_master ohm \r\n"
//				+ "			      WHERE ohm.business_unit_identity_code = rec_lv.parent_business_unit_identity_code and application_id = 399\r\n"
//				+ "			    ) \r\n" + "			   select * from rec_a left join  rec_lv\r\n"
//				+ "			   ON rec_a.business_unit_type_id = rec_lv.business_unit_type_id1\r\n"
//				+ "			   order by dept   ";
//		// System.out.print(SQL_ANCESTOR_OF_SELECTED_LEVEL_WITH_USER_VALUES);
//
//		try {
//			Connection conn = DriverManager.getConnection("jdbc:postgresql://10.25.26.30:5432/auth_db", "postgres",
//					"password");
//			PreparedStatement preparedStatement = conn.prepareStatement(SQL_ANCESTOR_OF_SELECTED_LEVEL_WITH_USER_VALUES,
//					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//
//			ResultSet resultSet = preparedStatement.executeQuery();
//			int i = 0;
//			boolean flag = true;
//			while (resultSet.next()) {
//				i++;
//
//				// // System.out.println("i "+i);
//				String organization_hierarchy_master_id = resultSet.getString("organization_hierarchy_master_id");
//				Integer business_unit_type_id = resultSet.getInt("business_unit_type_id");
//				String business_unit_type_code = resultSet.getString("business_unit_type_code");
//
//				String business_unit_identity_code = resultSet.getString("business_unit_identity_code");
//				String business_unit_identity_label = resultSet.getString("business_unit_identity_label");
//				String business_unit_identity_name = resultSet.getString("business_unit_identity_name");
//				// int dept=resultSet.getInt("dept");
//				// String parent_business_unit_identity_code =
//				// resultSet.getString("parent_business_unit_identity_code");
//
////				// System.out.println("business_unit_type_id " + business_unit_type_id);
////				// System.out.println("business_unit_type_code " + business_unit_type_code);
////				// System.out.println("business_unit_identity_code " + business_unit_identity_code);
////				// System.out.println("business_unit_identity_name " + business_unit_identity_name);
//
//				
//
//				if (business_unit_identity_code != null) {
//					BusinessUnit obj = new BusinessUnit();
//					
//					obj.setOrganization_hierarchy_master_id(organization_hierarchy_master_id);
//					obj.setBusiness_unit_type_id(business_unit_type_id);
//					obj.setBusiness_unit_type_code(business_unit_type_code);
//					obj.setBusiness_unit_identity_code(business_unit_identity_code);
//					obj.setBusiness_unit_identity_label(business_unit_identity_label);
//					obj.setBusiness_unit_identity_name(business_unit_identity_name);
//					result.add(obj);
//				    
//				} else if (business_unit_identity_code == null && flag == true) { // business_unit_identity_code==null 
//
//					flag = false;
//					resultSet.previous();
//					String prev_business_unit_identity_code = resultSet.getString("business_unit_identity_code");
//					resultSet.next();
//					String SQL_LIST_OF_VALUES_BUSINESS_UNIT_IDENTITY_CODE = "select organization_hierarchy_master_id, ohm.business_unit_type_id , ohm.business_unit_type_code ,ohm.parent_business_unit_type_id , ohm.business_unit_identity_name, '0'::integer,\r\n"
//							+ "          ohm.business_unit_identity_code ,ohm.business_unit_identity_label ,0::integer, ohm.parent_business_unit_identity_name ,\r\n"
//							+ "          ohm.business_unit_type_id \r\n"
//							+ "          from iamuser.organization_hierarchy_master ohm where application_id = 399 and \r\n"
//							+ "          parent_business_unit_identity_code ='" + prev_business_unit_identity_code
//							+ "' and business_unit_type_id =" + business_unit_type_id;
//
//					PreparedStatement ListpreparedStatement = conn.prepareStatement(
//							SQL_LIST_OF_VALUES_BUSINESS_UNIT_IDENTITY_CODE, ResultSet.TYPE_SCROLL_INSENSITIVE,
//							ResultSet.CONCUR_UPDATABLE);
//					ResultSet ListresultSet = ListpreparedStatement.executeQuery();
//
//					while (ListresultSet.next()) {
//
//						BusinessUnit obj = new BusinessUnit();
//						
//						String organization_hierarchy_master_id2 = resultSet
//								.getString("organization_hierarchy_master_id");
//						Integer business_unit_type_id2 = resultSet.getInt("business_unit_type_id");
//						String business_unit_type_code2 = resultSet.getString("business_unit_type_code");
//
//						String business_unit_identity_code2 = resultSet.getString("business_unit_identity_code");
//						String business_unit_identity_label2 = resultSet.getString("business_unit_identity_label");
//						String business_unit_identity_name2 = resultSet.getString("business_unit_identity_name");
//
//						obj.setOrganization_hierarchy_master_id(organization_hierarchy_master_id2);
//						obj.setBusiness_unit_type_id(business_unit_type_id2);
//						obj.setBusiness_unit_type_code(business_unit_type_code2);
//						obj.setBusiness_unit_identity_code(business_unit_identity_code2);
//						obj.setBusiness_unit_identity_label(business_unit_identity_label2);
//						obj.setBusiness_unit_identity_name(business_unit_identity_name2);
//						result.add(obj);
//					}
//
//				}else {  // business_unit_identity_code == null && flag == false
//					BusinessUnit obj = new BusinessUnit();
//					
//					obj.setBusiness_unit_type_id(business_unit_type_id);
//					obj.setBusiness_unit_type_code(business_unit_type_code);
//					result.add(obj);
//				}
//           
//			
//			}
//			//System.out.print(result.size());
//			result.forEach(x -> // System.out.println(x.getBusiness_unit_type_id() ));
//
//
//		} catch (Exception e) {
//
//		}
//
//	}
//
//}
