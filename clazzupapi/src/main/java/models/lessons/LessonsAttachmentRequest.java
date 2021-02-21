package models.lessons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LessonsAttachmentRequest {


	int paction;
	int pattachid;
	int plessonid;
	String pattachtype;
	String pattachtitle;
	String pattachdesc;
	int pstatus;
	String premark;
	int plogedinuserid;
}
