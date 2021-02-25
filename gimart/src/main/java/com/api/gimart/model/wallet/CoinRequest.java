package com.api.gimart.model.wallet;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CoinRequest {

	int paction;
	int pcoinid;
	int pprice;
	int pstatus;
	String premark;
	int plogedinuserid;
}
