package models.channel.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChannelMsgRequest {

	int paction;
	int pchmsgid;
	int pchid;
	int psenderid;
	String pchmsgtype;
	String pchmsgtitle;
	String pchcontent;
	int pstatus;
	String premark;
	int plogedinuserid;

}
