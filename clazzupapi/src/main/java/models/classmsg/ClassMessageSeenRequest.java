package models.classmsg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClassMessageSeenRequest {

	int paction;
	int pseenid;
	int pclassmsgid;
	int pstatus;
	String premark;
	int plogedinuserid;
}
