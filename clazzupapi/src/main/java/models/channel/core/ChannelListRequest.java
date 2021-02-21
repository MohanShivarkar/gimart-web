package models.channel.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChannelListRequest {

	int paction;
	int pinstituteid;
	String pchtype;
	int puserid;
	int poffset;
	int plimit;
	String psearch;
}
