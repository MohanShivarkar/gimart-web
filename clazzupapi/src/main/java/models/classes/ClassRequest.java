package models.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClassRequest {

	int paction;
	int pinstituteid;
	int pclassid;
	String pclassname;
	int pstatus;
	String premark;
	int plogedinuserid;
}
