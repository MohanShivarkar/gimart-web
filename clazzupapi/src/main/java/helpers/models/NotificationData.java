package helpers.models;

public class NotificationData {

	String text;
	String title;

	public NotificationData(String text, String title) {
		super();
		this.text = text;
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "NotificationData [text=" + text + ", title=" + title + "]";
	}

}
