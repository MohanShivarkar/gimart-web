package helpers.models;

public class NotificationBodyModel {

	int userid;
	String type;
	String title;
	String message;
	
	

	public NotificationBodyModel(int userid, String type, String title, String message) {
		super();
		this.userid = userid;
		this.type = type;
		this.title = title;
		this.message = message;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
