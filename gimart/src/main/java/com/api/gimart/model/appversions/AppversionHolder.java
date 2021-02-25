package com.api.gimart.model.appversions;

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
public class AppversionHolder {

	int appversionid;
	int roleid;
	String versionno;
	String description;
	int isactive;
	String created;
	int status;
	String remark;

}
