package models.lessons;

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
public class LessonsListResponse {

	ArrayList<LessonsHolder> arrayList;
	int result;
	String message;
	int haserror;

}
