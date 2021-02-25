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
public class CoinsHolder {

	int coinid;
	int price;
	int generatedcoins;
	int rowcount;
	int isactive;
	String created;
	String modified;
}
