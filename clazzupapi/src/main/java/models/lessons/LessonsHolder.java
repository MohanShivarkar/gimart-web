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
public class LessonsHolder {

	int lessonid;
	int instituteid;
	int classid;
	int subjectid;
	String subjectname;
	int chapterno;
	String lessontitle;
	String lessondesc;
	String lessonthumb;
	int seenstatus;
	String uploadername;
	String uploaderprofilepic;
	int isactive;
	String created;
	int status;
	String remark;
}
