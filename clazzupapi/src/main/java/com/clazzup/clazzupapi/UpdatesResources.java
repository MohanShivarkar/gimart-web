package com.clazzup.clazzupapi;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import models.ApiResponse;
import models.updates.UpdateCommentsListResponse;
import models.updates.UpdateSeenRequest;
import models.updates.UpdatesCommentRequest;
import models.updates.UpdatesLikesRequest;
import models.updates.UpdatesListResponse;
import models.updates.UpdatesRequest;
import services.UpdatesServices;

@Path("updates")
public class UpdatesResources {

	@POST
	@Path("/details/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageUpdatesMessages(UpdatesRequest request) {
		return UpdatesServices.getInstance().ManageUpdateMessage(request);
	}

	@POST
	@Path("/likes/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageUpdatesLikes(UpdatesLikesRequest request) {
		return UpdatesServices.getInstance().ManageUpdateLikes(request);
	}

	@POST
	@Path("/comments/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageUpdatesComments(UpdatesCommentRequest request) {
		return UpdatesServices.getInstance().ManageUpdateComments(request);
	}

	@GET
	@Path("/comments/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public UpdateCommentsListResponse getUpdatesComments(@QueryParam("paction") int paction,
			@QueryParam("pupdateid") int pupdateid) {
		return UpdatesServices.getInstance().GetUpdatesCommentsLists(paction, pupdateid);
	}

	@POST
	@Path("/seen/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageUpdatesSeen(UpdateSeenRequest request) {
		return UpdatesServices.getInstance().ManageUpdateSeen(request);
	}
	

	@GET
	@Path("/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public UpdatesListResponse getUpdatesMessages(@QueryParam("paction") int paction,
			@QueryParam("pinstituteid") int pinstituteid, @QueryParam("puserid") int puserid,
			@QueryParam("poffset") int poffset, @QueryParam("plimit") int plimit,
			@QueryParam("psearch") String psearch) {
		return UpdatesServices.getInstance().GetUpdatesMessagesLists(paction, pinstituteid, puserid, poffset, plimit,
				psearch);
	}
	
	
	@POST
	@Path("/upload/image")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public ApiResponse authManageUpdateImages(@FormDataParam("file") InputStream profilepicStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData,
			@FormDataParam("paction") String paction, 
			@FormDataParam("pupdateid") String pupdateid,
			@FormDataParam("pinstituteid") String pinstituteid, 
			@FormDataParam("pupdatetype") String pupdatetype,
			@FormDataParam("pupdatetitle") String pupdatetitle, 
			@FormDataParam("pupdatecontent") String pupdatecontent,
			@FormDataParam("pstatus") String pstatus, 
			@FormDataParam("premark") String premark,
			@FormDataParam("plogedinuserid") String plogedinuserid) {

		int paction1 = Integer.parseInt(paction);
		int pupdateid1 = Integer.parseInt(pupdateid);
		int pinstituteid1 = Integer.parseInt(pinstituteid);
		int pstatus1 = Integer.parseInt(pstatus);
		int plogedinuserid1 = Integer.parseInt(plogedinuserid);
		
		
		UpdatesRequest request=new UpdatesRequest(paction1, pupdateid1,
				pinstituteid1,  pupdatetype, pupdatetitle,
				pupdatecontent, pstatus1, premark, plogedinuserid1);
		
		return UpdatesServices.getInstance().ManageUpdatesImages(profilepicStream, fileMetaData, request);

	}
	
	
	@POST
	@Path("/upload/pdf")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public ApiResponse authManageUpdatePdfs(@FormDataParam("file") InputStream profilepicStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData,
			@FormDataParam("paction") String paction, 
			@FormDataParam("pupdateid") String pupdateid,
			@FormDataParam("pinstituteid") String pinstituteid, 
			@FormDataParam("pupdatetype") String pupdatetype,
			@FormDataParam("pupdatetitle") String pupdatetitle, 
			@FormDataParam("pupdatecontent") String pupdatecontent,
			@FormDataParam("pstatus") String pstatus, 
			@FormDataParam("premark") String premark,
			@FormDataParam("plogedinuserid") String plogedinuserid) {

		int paction1 = Integer.parseInt(paction);
		int pupdateid1 = Integer.parseInt(pupdateid);
		int pinstituteid1 = Integer.parseInt(pinstituteid);
		int pstatus1 = Integer.parseInt(pstatus);
		int plogedinuserid1 = Integer.parseInt(plogedinuserid);
		
		
		UpdatesRequest request=new UpdatesRequest(paction1, pupdateid1,
				pinstituteid1,  pupdatetype, pupdatetitle,
				pupdatecontent, pstatus1, premark, plogedinuserid1);
		
		return UpdatesServices.getInstance().ManageUpdatesPDFs(profilepicStream, fileMetaData, request);

	}
	

}
