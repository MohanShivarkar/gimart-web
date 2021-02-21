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
public class ClassMessageHolder {

	int classmsgid;
	int instituteid;
	int classid;
	String classmsgtype;
	String classmsgtitle;
	String classmsgcontent;
	int totallikes;
	int likestatus;
	int totalcomments;
	int isseen;
	String uploadername;
	String uploaderprofilepic;
	String created;
	int status;
	String remark;
}
