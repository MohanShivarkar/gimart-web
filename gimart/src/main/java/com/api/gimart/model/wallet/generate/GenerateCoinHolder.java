package com.api.gimart.model.wallet.generate;

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
public class GenerateCoinHolder {

	int gcid;
	int coinid;
	int quantity;
	int isactive;
	String created;
	int status;
	String remark;
}
