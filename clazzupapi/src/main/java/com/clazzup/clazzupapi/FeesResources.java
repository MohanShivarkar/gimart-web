package com.clazzup.clazzupapi;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import models.ApiResponse;
import models.fees.FeesListResponse;
import models.fees.FeesRequest;
import services.FeesServices;

@Path("fees")
public class FeesResources {

	@POST
	@Path("/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse channelManagement(FeesRequest request) {
		return FeesServices.getInstance().ManageFees(request);
	}

	@GET
	@Path("/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public FeesListResponse getChannelMembers(@QueryParam("paction") int paction,
			@QueryParam("pclassid") int pclassid) {
		return FeesServices.getInstance().GetFees(paction, pclassid);
	}
}
