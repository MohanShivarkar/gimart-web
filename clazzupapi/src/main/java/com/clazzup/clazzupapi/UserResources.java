package com.clazzup.clazzupapi;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import models.ApiResponse;
import models.users.UserListRequest;
import models.users.UserListResponse;
import models.users.UserRequest;
import services.UserService;

@Path("users")
public class UserResources {

	@POST
	@Path("/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse userManagement(UserRequest request) {
		return UserService.getInstance().ManageUsers(request);
	}

	@POST
	@Path("/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public UserListResponse getArticleDetails(UserListRequest request) {
		return UserService.getInstance().GetUserLists(request);
	}

	@POST
	@Path("/attachments/manage")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public ApiResponse manageUsers(@FormDataParam("file") InputStream profilepicStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData,

			@FormDataParam("paction") String paction,
			@FormDataParam("puserid") String puserid,
			@FormDataParam("pprofilepic") String pprofilepic,
			@FormDataParam("plogedinuserid") String plogedinuserid) {

		int paction1 = Integer.parseInt(paction);
		int puserid1 = Integer.parseInt(puserid);
		int plogedinuserid1 = Integer.parseInt(plogedinuserid);

		UserRequest request = new UserRequest(paction1, puserid1, pprofilepic, plogedinuserid1);
		return UserService.getInstance().ManageProfilePics(profilepicStream, fileMetaData, "", "", request);

	}

	@POST
	@Path("/profile/manage")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public ApiResponse authManageUsers(@FormDataParam("file") InputStream profilepicStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData,
			@FormDataParam("paction") String paction, 
			@FormDataParam("puserid") String puserid,
			@FormDataParam("proleid") String proleid, 
			@FormDataParam("pfullname") String pfullname,
			@FormDataParam("pusername") String pusername, 
			@FormDataParam("ppass") String ppass,
			@FormDataParam("pmobile") String pmobile, 
			@FormDataParam("pemail") String pemail,
			@FormDataParam("pprofilepic") String pprofilepic, 
			@FormDataParam("pinstituteid") String pinstituteid,
			@FormDataParam("pclassid") String pclassid, 
			@FormDataParam("pdob") String pdob,
			@FormDataParam("pcity") String pcity, 
			@FormDataParam("pgender") String pgender,
			@FormDataParam("pstatus") String pstatus, 
			@FormDataParam("premark") String premark,
			@FormDataParam("plogedinuserid") String plogedinuserid) {

		int paction1 = Integer.parseInt(paction);
		int puserid1 = Integer.parseInt(puserid);
		int proleid1 = Integer.parseInt(proleid);
		int pinstituteid1 = Integer.parseInt(pinstituteid);
		int pclassid1 = Integer.parseInt(pclassid);
		int pstatus1 = Integer.parseInt(pstatus);
		int plogedinuserid1 = Integer.parseInt(plogedinuserid);

		UserRequest request = new UserRequest(paction1, puserid1, proleid1, pfullname, pusername, ppass, pmobile,
				pemail, pprofilepic, pinstituteid1, pclassid1, pdob, pcity, pgender, pstatus1, premark,
				plogedinuserid1);
		return UserService.getInstance().ManageProfilePics(profilepicStream, fileMetaData, "", "", request);

	}

}
