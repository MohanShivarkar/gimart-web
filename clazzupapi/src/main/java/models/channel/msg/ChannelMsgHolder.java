package models.channel.msg;

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
public class ChannelMsgHolder {

	int chmsgid;
	int chid;
	int senderid;
	String chmsgtype;
	String chmsgtitle;
	String chcontent;
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
