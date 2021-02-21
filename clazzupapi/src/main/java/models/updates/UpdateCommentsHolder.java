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
public class UpdateCommentsHolder {

	int commentid;
	int updateid;
	String comment;
	String uploadername;
	String uploaderprofilepic;
	String created;
	int status;
	String remark;
}
