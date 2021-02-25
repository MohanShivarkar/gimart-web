package com.api.gimart.model.wallet;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlRootElement
public class CoinTransHolder {

	int tcid;
	int coinid;
	int quantity;
	int receiverid;
	String receiver;
	String receiverprofilepic;
	String sender;
	int sendcoins;
	int receivedcoins;
	int isactive;
	String created;
	int status;
	String remark;
}
