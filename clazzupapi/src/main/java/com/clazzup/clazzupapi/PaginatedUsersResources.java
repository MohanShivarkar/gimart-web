package com.clazzup.clazzupapi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import models.users.UserListResponse;
import services.UserService;

@Path("paginatedusers")
public class PaginatedUsersResources {

	@GET
	@Path("/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public UserListResponse getPaginatedUsers(@QueryParam("paction") int paction,
			@QueryParam("pinstituteid") int pinstituteid, @QueryParam("pclassid") int pclassid,
			@QueryParam("poffset") int poffset, @QueryParam("plimit") int plimit,
			@QueryParam("psearch") String psearch) {
		return UserService.getInstance().GetUserListsPaginated(paction, pinstituteid, pclassid, poffset, plimit,
				psearch);
	}
}
