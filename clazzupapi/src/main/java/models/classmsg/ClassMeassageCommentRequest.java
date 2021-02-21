package models.classmsg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClassMeassageCommentRequest {

	int paction;
	int pcommentid;
	int pclassmsgid;
	String pcomment;
	int pstatus;
	String premark;
	int plogedinuserid;
}
