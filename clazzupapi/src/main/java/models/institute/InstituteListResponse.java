package models.institute;

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
public class InstituteListResponse {

	ArrayList<InstituteHolder> arrayList;
	int result;
	String message;
	int haserror;
}
