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
import models.lessons.LessonAttachmentListResponse;
import models.lessons.LessonSeenRequest;
import models.lessons.LessonsAttachmentRequest;
import models.lessons.LessonsListRequest;
import models.lessons.LessonsListResponse;
import models.lessons.LessonsRequest;
import services.LessonsServices;

@Path("lessons")
public class LessonsResources {

	@POST
	@Path("/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse lessonManagement(LessonsRequest request) {
		return LessonsServices.getInstance().ManageLessons(request);
	}

	@POST
	@Path("/withthumb/manage")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public ApiResponse lessonThumbManagement(@FormDataParam("file") InputStream profilepicStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData, @FormDataParam("paction") String paction,
			@FormDataParam("plessonid") String plessonid,
			@FormDataParam("pinstituteid") String pinstituteid,
			@FormDataParam("pclassid") String pclassid,
			@FormDataParam("psubjectid") String psubjectid,
			@FormDataParam("pchapterno") String pchapterno,
			@FormDataParam("plessontitle") String plessontitle,
			@FormDataParam("plessondesc") String plessondesc,
			@FormDataParam("plessonthumb") String plessonthumb,
			@FormDataParam("pstatus") String pstatus,
			@FormDataParam("premark") String premark,
			@FormDataParam("plogedinuserid") String plogedinuserid) {

		int paction1 = Integer.parseInt(paction);
		int plessonid1 = Integer.parseInt(plessonid);
		int pinstituteid1 = Integer.parseInt(pinstituteid);
		int pclassid1 = Integer.parseInt(pclassid);
		int psubjectid1 = Integer.parseInt(psubjectid);
		int pchapterno1 = Integer.parseInt(pchapterno);
		int pstatus1 = Integer.parseInt(pstatus);
		int plogedinuserid1 = Integer.parseInt(plogedinuserid);
		
		
		LessonsRequest request = new LessonsRequest(paction1, plessonid1, pinstituteid1, pclassid1, psubjectid1,
				pchapterno1, plessontitle, plessondesc, plessonthumb, pstatus1, premark, plogedinuserid1);

		return LessonsServices.getInstance().ManageLessonsWithThumbnail(profilepicStream, fileMetaData, request);
	}

	@POST
	@Path("/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public LessonsListResponse getLessons(LessonsListRequest request) {
		return LessonsServices.getInstance().GetLessonsLists(request);
	}

	@POST
	@Path("/seen/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageLessonsSeen(LessonSeenRequest request) {
		return LessonsServices.getInstance().ManageLessonSeen(request);
	}

	@POST
	@Path("/attachment/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageLessonAttachments(LessonsAttachmentRequest request) {
		return LessonsServices.getInstance().ManageLessonAttachments(request);
	}

	@GET
	@Path("/attachment/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public LessonAttachmentListResponse getLessonAttachmentss(@QueryParam("paction") int paction,
			@QueryParam("plessonid") int plessonid,
			@QueryParam("pattachtype") String pattachtype,
			@QueryParam("puserid") int puserid,
			@QueryParam("poffset") int poffset, 
			@QueryParam("plimit") int plimit,
			@QueryParam("psearch") String psearch) {
		return LessonsServices.getInstance().GetLessonAttachmentLists(paction, plessonid, pattachtype, puserid, poffset,
				plimit, psearch);
	}
	
	
	
	@POST
	@Path("/attachment/manage/images")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public ApiResponse lessonAttachmentManagementImage(@FormDataParam("file") InputStream profilepicStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData,
			@FormDataParam("paction") String paction,
			@FormDataParam("pattachid") String pattachid,
			@FormDataParam("plessonid") String plessonid,
			@FormDataParam("pattachtype") String pattachtype,
			@FormDataParam("pattachtitle") String pattachtitle,
			@FormDataParam("pattachdesc") String pattachdesc,
			@FormDataParam("pstatus") String pstatus,
			@FormDataParam("premark") String premark,
			@FormDataParam("plogedinuserid") String plogedinuserid) {

		int paction1 = Integer.parseInt(paction);
		int pattachid1 = Integer.parseInt(pattachid);
		int plessonid1 = Integer.parseInt(plessonid);
		int pstatus1 = Integer.parseInt(pstatus);
		int plogedinuserid1 = Integer.parseInt(plogedinuserid);
		
		LessonsAttachmentRequest request=new LessonsAttachmentRequest(paction1,
				pattachid1, plessonid1, 
				pattachtype, pattachtitle, pattachdesc, 
				pstatus1, premark, plogedinuserid1);
		
		return LessonsServices.getInstance().ManageLessonImages(profilepicStream,
				fileMetaData, request);
	}
	
	@POST
	@Path("/attachment/manage/pdf")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public ApiResponse lessonAttachmentManagementPDF(@FormDataParam("file") InputStream profilepicStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData,
			@FormDataParam("paction") String paction,
			@FormDataParam("pattachid") String pattachid,
			@FormDataParam("plessonid") String plessonid,
			@FormDataParam("pattachtype") String pattachtype,
			@FormDataParam("pattachtitle") String pattachtitle,
			@FormDataParam("pattachdesc") String pattachdesc,
			@FormDataParam("pstatus") String pstatus,
			@FormDataParam("premark") String premark,
			@FormDataParam("plogedinuserid") String plogedinuserid) {

		int paction1 = Integer.parseInt(paction);
		int pattachid1 = Integer.parseInt(pattachid);
		int plessonid1 = Integer.parseInt(plessonid);
		int pstatus1 = Integer.parseInt(pstatus);
		int plogedinuserid1 = Integer.parseInt(plogedinuserid);
		
		LessonsAttachmentRequest request=new LessonsAttachmentRequest(paction1,
				pattachid1, plessonid1, 
				pattachtype, pattachtitle, pattachdesc, 
				pstatus1, premark, plogedinuserid1);
		
		return LessonsServices.getInstance().ManageLessonPDFs(profilepicStream,
				fileMetaData, request);
	}
	
}
