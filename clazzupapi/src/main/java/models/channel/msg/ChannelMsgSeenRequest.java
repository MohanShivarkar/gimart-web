package models.channel.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChannelMsgSeenRequest {

	int paction;
	int pseenid;
	int pchmsgid;
	int pstatus;
	String premark;
	int plogedinuserid;
	
}
