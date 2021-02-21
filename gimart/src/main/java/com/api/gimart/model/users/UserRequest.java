package com.api.gimart.model.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequest {

	int paction;
	int puserid;
	int proleid;
	String pfullname;
	String pusername;
	String ppass;
	String pmobile;
	String pemail;
	String pprofilepic;
	
	String pdob;
	String pgender;
	String pcity;
	
	String pgst;
	String paddress;
	String ppincode;

	String pdevicename;
	String pdeviceid;
	String pfbtoken;
	String pcoordinates;

	int pstatus;
	String premark;
	int plogedinuserid;

	public UserRequest(int paction, int puserid, String pprofilepic, int plogedinuserid) {
		super();
		this.paction = paction;
		this.puserid = puserid;
		this.pprofilepic = pprofilepic;
		this.plogedinuserid = plogedinuserid;
	}


	
	
}
