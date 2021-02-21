package models.channel.core;

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
public class ChannelHolder {
	
	int chid;
	int instituteid;
	String chtype;
	String chtitle;
	String chdesc;
	String chprofilepic;
	int seenstatus;
	String uploadername;
	String uploaderprofilepic;
	int isactive;
	String created;
	int status;
	String remark;
}
