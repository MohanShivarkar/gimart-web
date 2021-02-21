package models.classmsg;

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
public class ClassMessageCommentHolder {

	
	int commentid;
	int classmsgid;
	String comment;
	String uploadername;
	String uploaderprofilepic;
	String created;
	int status;
	String remark;
}
