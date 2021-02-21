package models.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdatesLikesRequest {

	int paction;
	int plikeid;
	int pupdateid;
	int pstatus;
	String premark;
	int plogedinuserid;
}
