package com.api.gimart.model.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CoinTransRequest {
	
	int paction;
	int ptcid;
	int pcoinid;
	int pquantity;
	int preceiverid;
	int pstatus;
	String premark;
	int plogedinuserid;
}
