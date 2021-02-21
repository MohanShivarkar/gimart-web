package models.users;

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
	int pinstituteid;
	int pclassid;
	String pdob;
	String pcity;
	String pgender;

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

	public UserRequest(int paction, int puserid, int proleid, String pfullname, String pusername, String ppass,
			String pmobile, String pemail, String pprofilepic, int pinstituteid, int pclassid, String pdob,
			String pcity, String pgender, int pstatus, String premark, int plogedinuserid) {
		super();
		this.paction = paction;
		this.puserid = puserid;
		this.proleid = proleid;
		this.pfullname = pfullname;
		this.pusername = pusername;
		this.ppass = ppass;
		this.pmobile = pmobile;
		this.pemail = pemail;
		this.pprofilepic = pprofilepic;
		this.pinstituteid = pinstituteid;
		this.pclassid = pclassid;
		this.pdob = pdob;
		this.pcity = pcity;
		this.pgender = pgender;
		this.pstatus = pstatus;
		this.premark = premark;
		this.plogedinuserid = plogedinuserid;
	}

	
	
}
