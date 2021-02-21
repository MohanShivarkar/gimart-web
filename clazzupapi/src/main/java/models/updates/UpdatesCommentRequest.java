package models.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdatesCommentRequest {

	int paction;
	int pcommentid;
	int pupdateid;
	String pcomment;
	int pstatus;
	String premark;
	int plogedinuserid;

}
