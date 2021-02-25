package com.api.gimart.repository;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.api.gimart.model.ApiResponse;
import com.api.gimart.model.users.UserListRequest;
import com.api.gimart.model.users.UserListResponse;
import com.api.gimart.model.users.UserRequest;

public interface UserRepository {

	ApiResponse ManageUsers(UserRequest request);

	UserListResponse GetUserLists(UserListRequest request);

	public ApiResponse ManageProfilePics(InputStream profilepicStream, FormDataContentDisposition fileMetaData,
			String folderpath, String accesspath, UserRequest request);

	public ApiResponse ManageUserProfiles(InputStream profilepicStream, FormDataContentDisposition fileMetaData,
			String folderpath, String accesspath, UserRequest request);

}
