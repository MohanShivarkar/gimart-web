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
import models.classmsg.ClassMeassageCommentRequest;
import models.classmsg.ClassMessageCommentsListResponse;
import models.classmsg.ClassMessageLikeRequest;
import models.classmsg.ClassMessageListResponse;
import models.classmsg.ClassMessageRequest;
import models.classmsg.ClassMessageSeenRequest;
import services.ClassMessageServices;

@Path("classmsg")
public class ClassMessageResources {

	@POST
	@Path("/details/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageClassMessages(ClassMessageRequest request) {
		return ClassMessageServices.getInstance().ManageClassMessage(request);
	}

	@POST
	@Path("/likes/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageClassMessagesLikes(ClassMessageLikeRequest request) {
		return ClassMessageServices.getInstance().ManageClassMessageLikes(request);
	}

	@POST
	@Path("/comments/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageClassMessagesComments(ClassMeassageCommentRequest request) {
		return ClassMessageServices.getInstance().ManageClassMessageComments(request);
	}

	@GET
	@Path("/comments/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public ClassMessageCommentsListResponse getUpdatesComments(@QueryParam("paction") int paction,
			@QueryParam("pclassmsgid") int pclassmsgid) {
		return ClassMessageServices.getInstance().GetClassMsgCommentsLists(paction, pclassmsgid);
	}

	@POST
	@Path("/seen/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageClassesSeen(ClassMessageSeenRequest request) {
		return ClassMessageServices.getInstance().ManageClassMessageSeen(request);
	}

	@GET
	@Path("/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public ClassMessageListResponse getClassMessagesMessages(@QueryParam("paction") int paction,
			@QueryParam("pinstituteid") int pinstituteid, 
			@QueryParam("pclassid") int pclassid,@QueryParam("puserid") int puserid,
			@QueryParam("poffset") int poffset, @QueryParam("plimit") int plimit,
			@QueryParam("psearch") String psearch) {
		return ClassMessageServices.getInstance().GetClassMessagesLists(paction, pinstituteid,pclassid, puserid,
				poffset, plimit, psearch);
	}
	
	//Uploading Media
	
	
	@POST
	@Path("/upload/image")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public ApiResponse authManageUsers(@FormDataParam("file") InputStream profilepicStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData,
			@FormDataParam("paction") String paction, 
			@FormDataParam("pclassmsgid") String pclassmsgid,
			@FormDataParam("pinstituteid") String pinstituteid, 
			@FormDataParam("pclassid") String pclassid, 
			@FormDataParam("pclassmsgtype") String pclassmsgtype,
			@FormDataParam("pclassmsgtitle") String pclassmsgtitle, 
			@FormDataParam("pclassmsgcontent") String pclassmsgcontent,
			@FormDataParam("pstatus") String pstatus, 
			@FormDataParam("premark") String premark,
			@FormDataParam("plogedinuserid") String plogedinuserid) {

		int paction1 = Integer.parseInt(paction);
		int pclassmsgid1 = Integer.parseInt(pclassmsgid);
		int pinstituteid1 = Integer.parseInt(pinstituteid);
		int pclassid1 = Integer.parseInt(pclassid);
		int pstatus1 = Integer.parseInt(pstatus);
		int plogedinuserid1 = Integer.parseInt(plogedinuserid);

		
		ClassMessageRequest request=new ClassMessageRequest(paction1, pclassmsgid1,
				pinstituteid1, pclassid1, pclassmsgtype, pclassmsgtitle,
				pclassmsgcontent, pstatus1, premark, plogedinuserid1);
		
		return ClassMessageServices.getInstance().ManageClassMsgImages(profilepicStream, fileMetaData, request);

	}
	
	@POST
	@Path("/upload/pdf")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public ApiResponse authManagePDFs(@FormDataParam("file") InputStream profilepicStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData,
			@FormDataParam("paction") String paction, 
			@FormDataParam("pclassmsgid") String pclassmsgid,
			@FormDataParam("pinstituteid") String pinstituteid, 
			@FormDataParam("pclassid") String pclassid, 
			@FormDataParam("pclassmsgtype") String pclassmsgtype,
			@FormDataParam("pclassmsgtitle") String pclassmsgtitle, 
			@FormDataParam("pclassmsgcontent") String pclassmsgcontent,
			@FormDataParam("pstatus") String pstatus, 
			@FormDataParam("premark") String premark,
			@FormDataParam("plogedinuserid") String plogedinuserid) {

		int paction1 = Integer.parseInt(paction);
		int pclassmsgid1 = Integer.parseInt(pclassmsgid);
		int pinstituteid1 = Integer.parseInt(pinstituteid);
		int pclassid1 = Integer.parseInt(pclassid);
		int pstatus1 = Integer.parseInt(pstatus);
		int plogedinuserid1 = Integer.parseInt(plogedinuserid);

		
		ClassMessageRequest request=new ClassMessageRequest(paction1, pclassmsgid1,
				pinstituteid1, pclassid1, pclassmsgtype, pclassmsgtitle,
				pclassmsgcontent, pstatus1, premark, plogedinuserid1);
		
		return ClassMessageServices.getInstance().ManageClassMsgPDFs(profilepicStream, fileMetaData, request);

	}
	
	
}
