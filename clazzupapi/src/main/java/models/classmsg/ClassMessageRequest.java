package models.classmsg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClassMessageRequest {

	int paction;
	int pclassmsgid;
	int pinstituteid;
	int pclassid;
	String pclassmsgtype;
	String pclassmsgtitle;
	String pclassmsgcontent;
	int pstatus;
	String premark;
	int plogedinuserid;

}
