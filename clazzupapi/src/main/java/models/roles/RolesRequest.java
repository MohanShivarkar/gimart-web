package models.roles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RolesRequest {

	int paction;
	int proleid;
	String prolename;
	int pstatus;
	String premark;
	int plogedinuserid;

}
