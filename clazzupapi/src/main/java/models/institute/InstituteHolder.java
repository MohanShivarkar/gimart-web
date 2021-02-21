package models.institute;

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
public class InstituteHolder {

	int instituteid;
	String institutename;
	String phone;
	String email;
	String website;
	String profilepic;
	String description;
	String address;
	String twitter;
	String facebook;
	String instagram;
	String whatsapp;
	String linkedin;
	int isactive;
	String created;
	int status;
	String remark;

}
