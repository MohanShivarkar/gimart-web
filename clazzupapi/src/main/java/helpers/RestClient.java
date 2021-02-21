package helpers;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestClient {

	public static void main(String[] args) {
		try {

			Client client = Client.create();

			String URL = "https://jsonplaceholder.typicode.com/todos/1";

			WebResource webResource = client.resource(URL);

			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			String output = response.getEntity(String.class);
			Gson gson = new Gson();

			Example example = gson.fromJson(output, Example.class);

			System.out.println("Output from Server .... \n");
			System.out.println(example);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
