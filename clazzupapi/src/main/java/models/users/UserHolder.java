package models.users;

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
	int instituteid;
	int classid;
	String classname;
	String dob;
	String city;
	String gender;

	String devicename;
	String deviceid;
	String fbtoken;
	String coordinates;

	int status;
	String remark;

}
