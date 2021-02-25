package com.api.gimart.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.api.gimart.model.appversions.AppversionsListResponse;
import com.api.gimart.service.AppversionService;

@Path("appversions")
public class AppversionResources {

//	@GET
//	@Path("/fetch")
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response getArticleDetails(@QueryParam("paction") int paction, @QueryParam("proleid") int proleid) {
//		ApiListResponse apiListResponse = AppversionService.getInstance().GetAppVersionsLists(paction, proleid);
//		return Response.ok(apiListResponse).build();
//	}

	@GET
	@Path("/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public AppversionsListResponse getArticleDetails(@QueryParam("paction") int paction, @QueryParam("puserid") int puserid) {
		return AppversionService.getInstance().GetAppVersionsLists(paction, puserid);
	}

}
