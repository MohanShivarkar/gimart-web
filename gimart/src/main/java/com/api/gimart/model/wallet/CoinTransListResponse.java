package com.api.gimart.model.wallet;

import java.util.ArrayList;

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
public class CoinTransListResponse {

	ArrayList<CoinTransHolder> arrayList;
	int result;
	String message;
	int haserror;
}
