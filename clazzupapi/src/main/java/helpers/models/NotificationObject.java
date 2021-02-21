package helpers.models;

public class NotificationObject {

	String body;
	String title;
	String priority;

	public NotificationObject(String body, String title, String priority) {
		super();
		this.body = body;
		this.title = title;
		this.priority = priority;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "NotificationObject [body=" + body + ", title=" + title + ", priority=" + priority + "]";
	}
	
	

}
