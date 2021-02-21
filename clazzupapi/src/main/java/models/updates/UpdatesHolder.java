package models.updates;

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
public class UpdatesHolder {

	int updateid;
	String updatetype;
	String updatetitle;
	String updatecontent;
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
