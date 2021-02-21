package com.api.gimart.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.api.gimart.model.ApiListResponse;
import com.api.gimart.model.ApiResponse;
import com.api.gimart.model.roles.RolesRequest;
import com.api.gimart.service.RoleService;

@Path("roles")
public class RolesResources {

	@POST
	@Path("/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageRoles(RolesRequest request) {
		return RoleService.getInstance().ManageRoles(request);
	}

	@GET
	@Path("/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public ApiListResponse getArticleDetails(@QueryParam("paction") int paction) {
		return RoleService.getInstance().GetRoles(paction);
	}
}
