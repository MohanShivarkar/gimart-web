package com.api.gimart;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Got it!";
	}

	// function Overloading

	// Functions

	public static void printMyText() {
		System.out.println("MyText");
	}

	// Function with Args
	public static void printMyText(String myValue) {
		System.out.println(myValue);
	}

	// Concatenation
	public static void printMyText(int myValueInt, String myValueString) {
		System.out.println(myValueInt + "" + myValueString);

	}

	public static void main(String[] args) {
		// printMyText();
		// printMyText("MyGivenValue");
		// printMyText(1,"MyGivenValue");

//		if(1==1) {
//			System.out.println(true);
//			System.out.println("System.out.println(true);");
//		}else {
//			System.out.println(false);
//		}

		// if () then
		// else

		// endif

	}

}
