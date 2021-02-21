package models.channel.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberRequest {

	int paction;
	int pmemberid;
	int pchid;
	int puserid;
	int pisadmin;
	int pstatus;
	String premark;
	int plogedinuserid;
}
