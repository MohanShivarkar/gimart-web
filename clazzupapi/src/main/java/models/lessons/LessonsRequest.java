package models.lessons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LessonsRequest {

	int paction;
	int plessonid;
	int pinstituteid;
	int pclassid;
	int psubjectid;
	int pchapterno;
	String plessontitle;
	String plessondesc;
	String plessonthumb;
	int pstatus;
	String premark;
	int plogedinuserid;
}
