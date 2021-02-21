package models.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdatesRequest {
	int paction;
	int pupdateid;
	int pinstituteid;
	String pupdatetype;
	String pupdatetitle;
	String pupdatecontent;
	int pstatus;
	String premark;
	int plogedinuserid;
}
