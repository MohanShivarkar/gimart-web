package com.api.gimart.model.users;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlRootElement
public class UserHolder {

	int userid;
	int roleid;
	String fullname;
	String username;
	String pass;
	String mobile;
	String email;
	String profilepic;

	String dob;
	String gender;
	String city;
	String gst;
	String address;
	String pincode;
	int totalcoins;

	String devicename;
	String deviceid;
	String fbtoken;
	String coordinates;

	int status;
	String remark;

}
