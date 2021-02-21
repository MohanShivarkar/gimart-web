package models.classmsg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClassMessageLikeRequest {

	int paction;
	int plikeid;
	int pclassmsgid;
	int pstatus;
	String premark;
	int plogedinuserid;
}
