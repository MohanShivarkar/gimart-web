package com.api.gimart.model.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CoinTransListRequest {

	int paction;
	int pcoinid;
	int puserid;
	int preceiverid;
	int pstatus;
	int poffset;
	int plimit;
	String psearch;

}
