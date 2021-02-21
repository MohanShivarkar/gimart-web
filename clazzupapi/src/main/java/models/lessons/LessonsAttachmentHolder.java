package models.lessons;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlRootElement
public class LessonsAttachmentHolder {

	int attachid;
	int lessonid;
	String attachtype;
	String attachtitle;
	String attachdesc;
	String uploadername;
	String uploaderprofilepic;
	int isactive;
	String created;
	int createdBy;
	int status;
	String remark;
}
