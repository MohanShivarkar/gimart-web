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
import models.classes.ClassListResponse;
import models.classes.ClassRequest;
import models.institute.InstituteListRequest;
import models.institute.InstituteListResponse;
import models.institute.InstituteRequest;
import models.subjects.SubjectListResponse;
import models.subjects.SubjectRequest;
import services.MasterService;

@Path("master")
public class MasterResources {

	@POST
	@Path("/institute/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageInstitutes(InstituteRequest request) {
		return MasterService.getInstance().ManageInstitues(request);
	}
	
	@POST
	@Path("/institute/profilepic")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public ApiResponse manageUsers(@FormDataParam("file") InputStream profilepicStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData,

			@FormDataParam("paction") String paction,
			@FormDataParam("pinstituteid") String pinstituteid,
			@FormDataParam("pprofilepic") String pprofilepic,
			@FormDataParam("plogedinuserid") String plogedinuserid) {

		int paction1 = Integer.parseInt(paction);
		int pinstituteid1 = Integer.parseInt(pinstituteid);
		int plogedinuserid1 = Integer.parseInt(plogedinuserid);

		InstituteRequest request = new InstituteRequest(paction1, pinstituteid1, pprofilepic, plogedinuserid1);
		return MasterService.getInstance().ManageInstitutesProfilePics(profilepicStream, fileMetaData, request);

	}


	@POST
	@Path("/institute/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public InstituteListResponse getInstitutes(InstituteListRequest request) {
		return MasterService.getInstance().GetInstituteLists(request);
	}

	@POST
	@Path("/class/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageClasses(ClassRequest request) {
		return MasterService.getInstance().ManageClasses(request);
	}

	@GET
	@Path("/class/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public ClassListResponse getClasses(@QueryParam("paction") int paction,
			@QueryParam("pinstituteid") int pinstituteid) {
		return MasterService.getInstance().GetClassesLists(paction, pinstituteid);
	}

	@POST
	@Path("/subject/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageSubjects(SubjectRequest request) {
		return MasterService.getInstance().ManageSubjects(request);
	}

	@GET
	@Path("/subject/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public SubjectListResponse getSubjects(@QueryParam("paction") int paction, @QueryParam("pclassid") int pclassid) {
		return MasterService.getInstance().GetSubjectsLists(paction, pclassid);
	}
}
