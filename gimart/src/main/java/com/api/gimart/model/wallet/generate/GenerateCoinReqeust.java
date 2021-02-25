package com.api.gimart.model.wallet.generate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GenerateCoinReqeust {

	int paction;
	int pgcid;
	int pcoinid;
	int pquantity;
	int pstatus;
	String premark;
	int plogedinuserid;

}
