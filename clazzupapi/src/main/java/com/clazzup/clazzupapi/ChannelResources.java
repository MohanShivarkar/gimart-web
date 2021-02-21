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
import models.channel.core.ChannelListRequest;
import models.channel.core.ChannelListResponse;
import models.channel.core.ChannelRequest;
import models.channel.member.MemberListResponse;
import models.channel.member.MemberRequest;
import models.channel.msg.ChannelMsgCommentListReposne;
import models.channel.msg.ChannelMsgCommentRequest;
import models.channel.msg.ChannelMsgLikeRequest;
import models.channel.msg.ChannelMsgListResponse;
import models.channel.msg.ChannelMsgRequest;
import models.channel.msg.ChannelMsgSeenRequest;
import services.ChannelService;

@Path("channels")
public class ChannelResources {

	@POST
	@Path("/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse channelManagement(ChannelRequest request) {
		return ChannelService.getInstance().ManageChannels(request);
	}

	@POST
	@Path("/withthumb/manage")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public ApiResponse lessonThumbManagement(@FormDataParam("file") InputStream profilepicStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData, @FormDataParam("paction") String paction,
			@FormDataParam("pchid") String pchid, @FormDataParam("pinstituteid") String pinstituteid,
			@FormDataParam("pchtype") String pchtype, @FormDataParam("pchtitle") String pchtitle,
			@FormDataParam("pchdesc") String pchdesc, @FormDataParam("pchprofilepic") String pchprofilepic,
			@FormDataParam("pstatus") String pstatus, @FormDataParam("premark") String premark,
			@FormDataParam("plogedinuserid") String plogedinuserid) {

		int paction1 = Integer.parseInt(paction);
		int pchid1 = Integer.parseInt(pchid);
		int pinstituteid1 = Integer.parseInt(pinstituteid);
		int pstatus1 = Integer.parseInt(pstatus);
		int plogedinuserid1 = Integer.parseInt(plogedinuserid);

		ChannelRequest request = new ChannelRequest(paction1, pchid1, pinstituteid1, pchtype, pchtitle, pchdesc,
				pchprofilepic, pstatus1, premark, plogedinuserid1);

		return ChannelService.getInstance().ManageChannelWithThumbnail(profilepicStream, fileMetaData, request);
	}

	@POST
	@Path("/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public ChannelListResponse getChannels(ChannelListRequest request) {
		return ChannelService.getInstance().GetChannelLists(request);
	}

	@POST
	@Path("/members/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse channelMemberManagement(MemberRequest request) {
		return ChannelService.getInstance().ManageChannelMembers(request);
	}

	@GET
	@Path("/members/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public MemberListResponse getChannelMembers(@QueryParam("paction") int paction, @QueryParam("pchid") int pchid,
			@QueryParam("poffset") int poffset, @QueryParam("plimit") int plimit,
			@QueryParam("psearch") String psearch) {
		return ChannelService.getInstance().GetChannelMembers(paction, pchid, poffset, plimit, psearch);
	}

	// Channel Message Region
	@POST
	@Path("/msg/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageChannelMessages(ChannelMsgRequest request) {
		return ChannelService.getInstance().ManageChannelMessage(request);
	}

	@GET
	@Path("/msg/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public ChannelMsgListResponse getChannelMsgLists(@QueryParam("paction") int paction, @QueryParam("pchid") int pchid,
			@QueryParam("puserid") int puserid, @QueryParam("poffset") int poffset, @QueryParam("plimit") int plimit,
			@QueryParam("psearch") String psearch) {
		return ChannelService.getInstance().GetChannelMsgLists(paction, pchid, puserid, poffset, plimit, psearch);
	}

	@POST
	@Path("/msg/likes/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageChannelMsgLikes(ChannelMsgLikeRequest request) {
		return ChannelService.getInstance().ManageChannelMsgLikes(request);
	}

	@POST
	@Path("/msg/comments/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageChannelMsgComments(ChannelMsgCommentRequest request) {
		return ChannelService.getInstance().ManageChannelMsgComments(request);
	}

	@GET
	@Path("/msg/comments/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public ChannelMsgCommentListReposne getUpdatesComments(@QueryParam("paction") int paction,
			@QueryParam("pchmsgid") int pchmsgid) {
		return ChannelService.getInstance().GetChannelMsgCommentsLists(paction, pchmsgid);
	}

	@POST
	@Path("/msg/seen/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageChannelSeen(ChannelMsgSeenRequest request) {
		return ChannelService.getInstance().ManageChannelMsgSeen(request);
	}

	@POST
	@Path("/msg/upload/image")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public ApiResponse authManageUsers(@FormDataParam("file") InputStream profilepicStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData, @FormDataParam("paction") String paction,
			@FormDataParam("pchmsgid") String pchmsgid, @FormDataParam("pchid") String pchid,
			@FormDataParam("psenderid") String psenderid, @FormDataParam("pchmsgtype") String pchmsgtype,
			@FormDataParam("pchmsgtitle") String pchmsgtitle, @FormDataParam("pchcontent") String pchcontent,
			@FormDataParam("pstatus") String pstatus, @FormDataParam("premark") String premark,
			@FormDataParam("plogedinuserid") String plogedinuserid) {

		int paction1 = Integer.parseInt(paction);
		int pchmsgid1 = Integer.parseInt(pchmsgid);
		int pchid1 = Integer.parseInt(pchid);
		int psenderid1 = Integer.parseInt(psenderid);
		int pstatus1 = Integer.parseInt(pstatus);
		int plogedinuserid1 = Integer.parseInt(plogedinuserid);

		ChannelMsgRequest request = new ChannelMsgRequest(paction1, pchmsgid1, pchid1, psenderid1, pchmsgtype,
				pchmsgtitle, pchcontent, pstatus1, premark, plogedinuserid1);

		return ChannelService.getInstance().ManageChannelMsgImages(profilepicStream, fileMetaData, request);

	}

	@POST
	@Path("/msg/upload/pdf")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public ApiResponse authManagePDFs(@FormDataParam("file") InputStream profilepicStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData, @FormDataParam("paction") String paction,
			@FormDataParam("pchmsgid") String pchmsgid, @FormDataParam("pchid") String pchid,
			@FormDataParam("psenderid") String psenderid, @FormDataParam("pchmsgtype") String pchmsgtype,
			@FormDataParam("pchmsgtitle") String pchmsgtitle, @FormDataParam("pchcontent") String pchcontent,
			@FormDataParam("pstatus") String pstatus, @FormDataParam("premark") String premark,
			@FormDataParam("plogedinuserid") String plogedinuserid) {

		int paction1 = Integer.parseInt(paction);
		int pchmsgid1 = Integer.parseInt(pchmsgid);
		int pchid1 = Integer.parseInt(pchid);
		int psenderid1 = Integer.parseInt(psenderid);
		int pstatus1 = Integer.parseInt(pstatus);
		int plogedinuserid1 = Integer.parseInt(plogedinuserid);

		ChannelMsgRequest request = new ChannelMsgRequest(paction1, pchmsgid1, pchid1, psenderid1, pchmsgtype,
				pchmsgtitle, pchcontent, pstatus1, premark, plogedinuserid1);

		return ChannelService.getInstance().ManageChannelMsgPDFs(profilepicStream, fileMetaData, request);

	}

}
