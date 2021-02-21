package models.subjects;

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
public class SubjectHolder {

	int subjectid;
	int classid;
	String subjectname;
	String classname;
	int isactive;
	String created;
	int status;
	String remark;

}
