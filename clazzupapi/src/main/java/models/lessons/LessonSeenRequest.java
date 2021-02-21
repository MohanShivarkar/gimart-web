package models.lessons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LessonSeenRequest {
	
	int paction;
	int pseenid;
	int plessonid;
	int pstatus;
	String premark;
	int plogedinuserid;

}
