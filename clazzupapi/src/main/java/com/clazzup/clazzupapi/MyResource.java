package com.clazzup.clazzupapi;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
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
	@Path("/check")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<String> getDirectroyPaths() {

		File dirName = new File("/home/clazzup/public_html/");

		File[] fileList = dirName.listFiles();
		List<File> files = new ArrayList<>();

		for (File file : fileList) {

			if (file.isFile()) {

				files.add(file);
			} else if (file.isDirectory()) {

				files.add(file);
				// doListing(file);
			}
		}

		ArrayList<String> myFileNames = new ArrayList<>();

		for (File file2 : files) {
			myFileNames.add(file2.getAbsolutePath());
		}

		// return location.getFile();
		return myFileNames;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getIt() {
		URL location = MyResource.class.getProtectionDomain().getCodeSource().getLocation();
		System.out.println(location.getFile());
		return location.getFile();
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		String rootPath = System.getProperty("catalina.home");
		ServletContext ctx = servletContextEvent.getServletContext();
		String relativePath = ctx.getInitParameter("tempfile.dir");
		File file = new File(rootPath + File.separator + relativePath);
		if (!file.exists())
			file.mkdirs();
		System.out.println("File Directory created to be used for storing files");
		ctx.setAttribute("FILES_DIR_FILE", file);
		ctx.setAttribute("FILES_DIR", rootPath + File.separator + relativePath);
	}

}
