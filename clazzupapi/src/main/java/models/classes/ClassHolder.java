package models.classes;

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
public class ClassHolder {

	int classid;
	int instituteid;
	String classname;
	int isactive;
	String created;
	int status;
	String remark;
}
