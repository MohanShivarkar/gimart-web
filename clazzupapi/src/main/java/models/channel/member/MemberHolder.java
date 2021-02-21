package models.channel.member;

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
public class MemberHolder {

	int memberid;
	int chid;
	int userid;
	int isadmin;
	String fullname;
	String profilepic;
	String uploadername;
	String uploaderprofilepic;
	int isactive;
	String created;
	int status;
	String remark;
}
