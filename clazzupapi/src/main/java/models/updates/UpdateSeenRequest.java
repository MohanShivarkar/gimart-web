package models.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateSeenRequest {

	int paction;
	int pseenid;
	int pupdateid;
	int pstatus;
	String premark;
	int plogedinuserid;
	
}
