package helpers;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import helpers.models.FirebaseNotification;
import helpers.models.NotificationBodyModel;
import helpers.models.NotificationData;
import helpers.models.NotificationObject;

public class FirebaseUtils {

	private static final String TAG = FirebaseUtils.class.getCanonicalName();

	private static FirebaseUtils instance;

	public static FirebaseUtils getInstance() {
		if (instance == null) {
			instance = new FirebaseUtils();
		}
		return instance;
	}

	public void SendNotification() {

		// Create noti_body to send other details

		NotificationBodyModel bodyModel = new NotificationBodyModel(100, "classmsg", "New ClassMSg",
				"it is the the classmesasage");
		String jsonBodyModel = new Gson().toJson(bodyModel);
		
		//String to = "dB90t1hqQ8q7kFrCo1ZRNJ:APA91bFM0S9KYN84k-nBGBMVEBEWlm2wZiFLoct7LkUp7039WJWz7aDqwn_ezWnLjbz32dfOmm70cTFdjbhEszriOd3R4CDBSIvgaQjlmjA5AnFWQhky4mqKcH6wInf0w9andEn_BNBK";
		
		String to = "/topics/updates";
		
		String collapse_key = "type_a";
		
		String noti_body = "My Notification Body";
		String noti_title = "My Notification title";
		
		String body1 = "Body";
		String title = "Title";
		String key_1 = "Value for key_1";
		String key_2 = "Value for key_2";

		NotificationObject notification = new NotificationObject(noti_body, noti_title, "high");
		NotificationData data = new NotificationData(jsonBodyModel, title);
		FirebaseNotification firebaseNotification = new FirebaseNotification(notification, data, to);
		String jsonObject = new Gson().toJson(firebaseNotification);
		System.out.println(jsonObject);

		try {

			Client client = Client.create();

			String URL = "https://fcm.googleapis.com/fcm/send";
			String SERVER_KEY = "key=AAAA9O-jno8:APA91bF1BI9V4fsLAtZGy5H5M2sq3JyjXbMqkzPUZEguAvi4O2Tpzz3p-3JXN3WUUSKHMppuna8ihykfpk-7drQkua0d2nJRWW_dKznV4jtqUe-sE6UKygJFxpUEpUydZm0QqZ0iwCYe";

			WebResource webResource = client.resource(URL);

			// ;charset=UTF-8
			ClientResponse response = webResource.accept("application/json").header("Content-Type", "application/json")
					.header("Authorization", SERVER_KEY).type("application/json")
					.post(ClientResponse.class, jsonObject);

			if (response.getStatus() != 200) {
				System.out.println(response);
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			String output = response.getEntity(String.class);
			// Gson gson = new Gson();

			// Example example = gson.fromJson(output, Example.class);

			System.out.println("Output from Server .... \n");
			System.out.println(output);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	
	public void SendNotificationForNewClass() {

		// Create noti_body to send other details

		NotificationBodyModel bodyModel = new NotificationBodyModel(100, "classmsg", "New ClassMSg",
				"it is the the classmesasage");
		String jsonBodyModel = new Gson().toJson(bodyModel);
	
		String to = "/topics/classmsg";
		
		String collapse_key = "type_a";
		
		String noti_body = "This is notification for users for subscribed for ClassMessages";
		String noti_title = "Clazzup Class Subscribed Notifications";
		
		String body1 = "Body";
		String title = "Title";
		String key_1 = "Value for key_1";
		String key_2 = "Value for key_2";

		NotificationObject notification = new NotificationObject(noti_body, noti_title, "high");
		NotificationData data = new NotificationData(jsonBodyModel, title);
		FirebaseNotification firebaseNotification = new FirebaseNotification(notification, data, to);
		String jsonObject = new Gson().toJson(firebaseNotification);
		System.out.println(jsonObject);

		try {

			Client client = Client.create();

			String URL = "https://fcm.googleapis.com/fcm/send";
			String SERVER_KEY = "key=AAAA9O-jno8:APA91bF1BI9V4fsLAtZGy5H5M2sq3JyjXbMqkzPUZEguAvi4O2Tpzz3p-3JXN3WUUSKHMppuna8ihykfpk-7drQkua0d2nJRWW_dKznV4jtqUe-sE6UKygJFxpUEpUydZm0QqZ0iwCYe";

			WebResource webResource = client.resource(URL);

			// ;charset=UTF-8
			ClientResponse response = webResource.accept("application/json").header("Content-Type", "application/json")
					.header("Authorization", SERVER_KEY).type("application/json")
					.post(ClientResponse.class, jsonObject);

			if (response.getStatus() != 200) {
				System.out.println(response);
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			String output = response.getEntity(String.class);
			// Gson gson = new Gson();

			// Example example = gson.fromJson(output, Example.class);

			System.out.println("Output from Server .... \n");
			System.out.println(output);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	
	
	
	public void SendUpdateNotification(int instituteid,String notificationtitle,
			String notificationbody,NotificationBodyModel bodyModel) {

		String topicName="/topics/updates"+instituteid;
		String jsonBodyModel = new Gson().toJson(bodyModel);
		//String to = topicName;
		String title = "Title";

		NotificationObject notification = new NotificationObject(notificationbody, notificationtitle, "high");
		NotificationData data = new NotificationData(jsonBodyModel, title);
		FirebaseNotification firebaseNotification = new FirebaseNotification(notification, data, topicName);
		String jsonObject = new Gson().toJson(firebaseNotification);
		System.out.println(jsonObject);

		try {

			Client client = Client.create();

			String URL = "https://fcm.googleapis.com/fcm/send";
			String SERVER_KEY = "key=AAAA9O-jno8:APA91bF1BI9V4fsLAtZGy5H5M2sq3JyjXbMqkzPUZEguAvi4O2Tpzz3p-3JXN3WUUSKHMppuna8ihykfpk-7drQkua0d2nJRWW_dKznV4jtqUe-sE6UKygJFxpUEpUydZm0QqZ0iwCYe";

			WebResource webResource = client.resource(URL);
			// ;charset=UTF-8
			ClientResponse response = webResource.accept("application/json").header("Content-Type", "application/json")
					.header("Authorization", SERVER_KEY).type("application/json")
					.post(ClientResponse.class, jsonObject);

			if (response.getStatus() != 200) {
				System.out.println(response);
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			String output = response.getEntity(String.class);
			// Gson gson = new Gson();
			// Example example = gson.fromJson(output, Example.class);
			System.out.println("Output from Server .... \n");
			System.out.println(output);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	
	
	public static void main(String[] args) {
		FirebaseUtils.getInstance().SendNotification();
		//FirebaseUtils.getInstance().SendNotificationForNewClass();
	}

}
