package models.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserListRequest {

	int paction;
	int puserid;
	int proleid;
	String pfullname;
	String pusername;
	String ppass;
	String pmobile;
	String pemail;
	int pinstituteid;
	int pclassid;

}
