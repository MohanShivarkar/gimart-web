package models.channel.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChannelMsgCommentRequest {

	int paction;
	int pcommentid;
	int pchmsgid;
	String pcomment;
	int pstatus;
	String premark;
	int plogedinuserid;
}
