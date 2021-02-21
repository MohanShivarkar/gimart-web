package models.subjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubjectRequest {

	int paction;
	int psubjectid;
	int pclassid;	
	String psubjectname;
	int pstatus;
	String premark;
	int plogedinuserid;
	
}
