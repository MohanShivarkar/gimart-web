package models.lessons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LessonsListRequest {

	int paction;
	int pinstituteid; 
	int pclassid;
	int psubjectid;
	int puserid;
	int poffset;
	int plimit; 
	String psearch;
}
