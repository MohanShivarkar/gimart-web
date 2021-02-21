package models.institute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InstituteRequest {

	int paction;
	int pinstituteid;
	String pinstitutename;
	String pphone;
	String pemail;
	String pwebsite;
	String pprofilepic;
	String pdescription;
	String paddress;
	String ptwitter;
	String pfacebook;
	String pinstagram;
	String pwhatsapp;
	String plinkedin;
	int pstatus;
	String premark;
	int plogedinuserid;

	public InstituteRequest(int paction, int pinstituteid, String pprofilepic, int plogedinuserid) {
		super();
		this.paction = paction;
		this.pinstituteid = pinstituteid;
		this.pprofilepic = pprofilepic;
		this.plogedinuserid = plogedinuserid;
	}

}
