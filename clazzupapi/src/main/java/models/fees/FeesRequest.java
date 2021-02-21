package models.fees;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FeesRequest {

	int paction;
	int pfeesid;
	int pclassid;
	String ptitle;
	String pstartdate;
	String penddate;
	double pfeesamount;
	String pstartdatemsg;
	String pdeadlinemsg;
	double platefees;
	int pstatus;
	String premark;
	int plogedinuserid;

}
