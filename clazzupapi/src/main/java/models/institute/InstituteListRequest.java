package models.institute;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstituteListRequest {

	int paction;
	int poffset;
	int plimit;
	String psearch;
}
