package models.fees;

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
public class FeesHolder {

	int feesid;
	int classid;
	String classname;
	String title;
	String startdate;
	String enddate;
	double feesamount;
	String startdatemsg;
	String deadlinemsg;
	double latefees;
	String created;
	int status;
	String remark;
}
