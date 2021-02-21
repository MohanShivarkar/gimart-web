package com.api.gimart.repository;

import com.api.gimart.model.ApiListResponse;
import com.api.gimart.model.ApiResponse;
import com.api.gimart.model.roles.RolesRequest;

public interface RolesRepository {

	ApiListResponse GetRoles(int paction);
	ApiResponse ManageRoles(RolesRequest request);
}
