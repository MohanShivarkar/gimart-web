package helpers.models;

public class FirebaseNotification {

	NotificationObject notification;
	NotificationData data;
	String to;

	public FirebaseNotification() {
		super();
	}

	public FirebaseNotification(NotificationObject notification, NotificationData data, String to) {
		super();
		this.notification = notification;
		this.data = data;
		this.to = to;
	}

	public NotificationObject getNotification() {
		return notification;
	}

	public void setNotification(NotificationObject notification) {
		this.notification = notification;
	}

	public NotificationData getData() {
		return data;
	}

	public void setData(NotificationData data) {
		this.data = data;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}
