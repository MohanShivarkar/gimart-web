package models.channel.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChannelMsgLikeRequest {

	int paction;
	int plikeid;
	int pchmsgid;
	int pstatus;
	String premark;
	int plogedinuserid;
}
