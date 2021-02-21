package models.channel.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChannelRequest {

	int paction;
	int pchid;
	int pinstituteid;
	String pchtype;
	String pchtitle;
	String pchdesc;
	String pchprofilepic;
	int pstatus;
	String premark;
	int plogedinuserid;
}
