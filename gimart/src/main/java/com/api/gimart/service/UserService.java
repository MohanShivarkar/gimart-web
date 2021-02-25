package com.api.gimart.service;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.api.gimart.model.ApiResponse;
import com.api.gimart.model.users.UserHolder;
import com.api.gimart.model.users.UserListRequest;
import com.api.gimart.model.users.UserListResponse;
import com.api.gimart.model.users.UserRequest;
import com.api.gimart.repository.UserRepository;
import com.api.gimart.util.DBUtil;
import com.api.gimart.util.FileUtils;
import com.api.gimart.util.QueryMaker;
import com.api.gimart.util.Utility;

public class UserService implements UserRepository {

	private static final String TAG = UserService.class.getCanonicalName();

	private static UserService instance;

	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}

	public ApiResponse ManageUsers(UserRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("auth_Users", 22));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPuserid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getProleid()) ? request.getProleid() : 0);

			callableStatement.setString(4, Utility.isNullOrEmpty(request.getPfullname()) ? request.getPfullname() : "");
			callableStatement.setString(5, Utility.isNullOrEmpty(request.getPusername()) ? request.getPusername() : "");
			callableStatement.setString(6, Utility.isNullOrEmpty(request.getPpass()) ? request.getPpass() : "");
			callableStatement.setString(7, Utility.isNullOrEmpty(request.getPmobile()) ? request.getPmobile() : "");
			callableStatement.setString(8, Utility.isNullOrEmpty(request.getPemail()) ? request.getPemail() : "");
			callableStatement.setString(9,
					Utility.isNullOrEmpty(request.getPprofilepic()) ? request.getPprofilepic() : "");

			callableStatement.setString(10, Utility.isNullOrEmpty(request.getPdob()) ? request.getPdob() : "");
			callableStatement.setString(11, Utility.isNullOrEmpty(request.getPgender()) ? request.getPgender() : "");
			callableStatement.setString(12, Utility.isNullOrEmpty(request.getPcity()) ? request.getPcity() : "");

			callableStatement.setString(13, Utility.isNullOrEmpty(request.getPgst()) ? request.getPgst() : "");
			callableStatement.setString(14, Utility.isNullOrEmpty(request.getPaddress()) ? request.getPaddress() : "");
			callableStatement.setString(15, Utility.isNullOrEmpty(request.getPpincode()) ? request.getPpincode() : "");

			callableStatement.setString(16,
					Utility.isNullOrEmpty(request.getPdevicename()) ? request.getPdevicename() : "");
			callableStatement.setString(17,
					Utility.isNullOrEmpty(request.getPdeviceid()) ? request.getPdeviceid() : "");
			callableStatement.setString(18, Utility.isNullOrEmpty(request.getPfbtoken()) ? request.getPfbtoken() : "");
			callableStatement.setString(19,
					Utility.isNullOrEmpty(request.getPcoordinates()) ? request.getPcoordinates() : "");

			callableStatement.setInt(20, Utility.isNullOrEmpty(request.getPstatus()) ? request.getPstatus() : 0);

			callableStatement.setString(21, Utility.isNullOrEmpty(request.getPremark()) ? request.getPremark() : "");

			callableStatement.setInt(22, request.getPlogedinuserid());

			callableStatement.execute();
			connection.commit();
			resultSet = callableStatement.getResultSet();

			if (!resultSet.next()) {
				outResponse.setHaserror(1);
				outResponse.setMessage("Something went wrong");
				outResponse.setResult(0);
				Utility.showMessage(TAG, "" + outResponse);
			} else {
				outResponse.setResult(resultSet.getInt("result"));
				outResponse.setMessage(resultSet.getString("message"));
				outResponse.setHaserror(resultSet.getInt("haserror"));
			}

		} catch (Exception e) {
			Utility.showErrorMessage(TAG, e);
			outResponse.setResult(0);
			outResponse.setMessage(e.getMessage());
			outResponse.setHaserror(1);
			return outResponse;
		} finally {
			DBUtil.closeConnection(connection, callableStatement, resultSet);
		}

		return outResponse;
	}

	public UserListResponse GetUserLists(UserListRequest request) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		UserListResponse outResponse = new UserListResponse();
		ArrayList<UserHolder> list = new ArrayList<>();

		try {
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("auth_UsersFetch", 11));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPuserid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getProleid()) ? request.getProleid() : 0);

			callableStatement.setString(4, Utility.isNullOrEmpty(request.getPfullname()) ? request.getPfullname() : "");
			callableStatement.setString(5, Utility.isNullOrEmpty(request.getPusername()) ? request.getPusername() : "");
			callableStatement.setString(6, Utility.isNullOrEmpty(request.getPpass()) ? request.getPpass() : "");
			callableStatement.setString(7, Utility.isNullOrEmpty(request.getPmobile()) ? request.getPmobile() : "");
			callableStatement.setString(8, Utility.isNullOrEmpty(request.getPemail()) ? request.getPemail() : "");

			callableStatement.setInt(9, Utility.isNullOrEmpty(request.getPoffset()) ? request.getPoffset() : 0);
			callableStatement.setInt(10, Utility.isNullOrEmpty(request.getPlimit()) ? request.getPlimit() : 0);
			callableStatement.setString(11, Utility.isNullOrEmpty(request.getPsearch()) ? request.getPsearch() : "");

			callableStatement.execute();

			resultSet = callableStatement.getResultSet();

			if (!resultSet.next()) {
				outResponse.setArrayList(list);
				outResponse.setHaserror(1);
				outResponse.setMessage("Invalid Credentials");
				outResponse.setResult(0);
				Utility.showMessage(TAG, "" + outResponse);
			} else {

				do {
					UserHolder out = new UserHolder();

					out.setUserid(resultSet.getInt("userid"));
					out.setRoleid(resultSet.getInt("roleid"));
					out.setFullname(resultSet.getString("fullname"));
					out.setUsername(resultSet.getString("username"));
					out.setPass(resultSet.getString("pass"));
					out.setMobile(resultSet.getString("mobile"));
					out.setEmail(resultSet.getString("email"));
					out.setProfilepic(resultSet.getString("profilepic"));

					out.setDob(resultSet.getString("dob"));
					out.setGender(resultSet.getString("gender"));
					out.setCity(resultSet.getString("city"));

					out.setGst(resultSet.getString("gst"));
					out.setAddress(resultSet.getString("address"));
					out.setPincode(resultSet.getString("pincode"));
					out.setTotalcoins(resultSet.getInt("totalcoins"));

					out.setDevicename(resultSet.getString("devicename"));
					out.setDeviceid(resultSet.getString("deviceid"));
					out.setFbtoken(resultSet.getString("fbtoken"));
					out.setCoordinates(resultSet.getString("coordinates"));
					out.setStatus(resultSet.getInt("status"));
					out.setRemark(resultSet.getString("remark"));

					list.add(out);
					Utility.showMessage(TAG, "" + out);
				} while (resultSet.next());

				outResponse.setArrayList(list);
				outResponse.setHaserror(0);
				outResponse.setMessage("Successfull");
				outResponse.setResult(1);
				System.out.println("" + outResponse);
			}

		} catch (Exception e) {
			e.printStackTrace();
			outResponse.setArrayList(list);
			outResponse.setHaserror(1);
			outResponse.setMessage(e.getMessage());
			outResponse.setResult(0);
			return outResponse;

		} finally {
			DBUtil.closeConnection(connection, callableStatement);
		}
		return outResponse;

	}

	public ApiResponse ManageProfilePics(InputStream profilepicStream, FormDataContentDisposition fileMetaData,
			String folderpath, String accesspath, UserRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {
			// String UPLOAD_PATH = "/attachments/profilepics/";
			String UPLOAD_PATH = "C:\\inetpub\\wwwroot\\gimart\\profilepics\\";
			String ACCESS_PATH = "http://208.109.8.170/gimart/profilepics/";
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("auth_UsersProfilePic", 4));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPuserid());

//			callableStatement.setString(3,
//					Utility.isNullOrEmpty(request.getPprofilepic()) ? request.getPprofilepic() : "");

			String CurrentDateTime = Utility.GetCurrentDateTime();
			String RANDOM_NUMBER = Utility.GetRandomNumberString();
			String FILE_PATH = UPLOAD_PATH + RANDOM_NUMBER + "-" + CurrentDateTime
					+ FileUtils.getFileExtension(fileMetaData.getFileName());

			String FILE_NAME = RANDOM_NUMBER + "-" + CurrentDateTime
					+ FileUtils.getFileExtension(fileMetaData.getFileName());

			String FINAL_ACCESS_PATH = ACCESS_PATH + FILE_NAME;

			if (FileUtils.SaveFile(FILE_PATH, profilepicStream, fileMetaData)) {
				callableStatement.setString(3, Utility.isNullOrEmpty(FINAL_ACCESS_PATH) ? FINAL_ACCESS_PATH : "");
			} else {

				outResponse.setHaserror(1);
				outResponse.setMessage("File Uploading Failed");
				outResponse.setResult(0);
			}

			callableStatement.setInt(4, request.getPlogedinuserid());

			callableStatement.execute();
			connection.commit();
			resultSet = callableStatement.getResultSet();

			if (!resultSet.next()) {
				outResponse.setHaserror(1);
				outResponse.setMessage("Something went wrong");
				outResponse.setResult(0);
				Utility.showMessage(TAG, "" + outResponse);
			} else {
				outResponse.setResult(resultSet.getInt("result"));
				outResponse.setMessage(resultSet.getString("message"));
				outResponse.setHaserror(resultSet.getInt("haserror"));
			}

		} catch (Exception e) {
			Utility.showErrorMessage(TAG, e);
			outResponse.setResult(0);
			outResponse.setMessage(e.getMessage());
			outResponse.setHaserror(1);
			return outResponse;
		} finally {
			DBUtil.closeConnection(connection, callableStatement, resultSet);
		}

		return outResponse;
	}

	public ApiResponse ManageUserProfiles(InputStream profilepicStream, FormDataContentDisposition fileMetaData,
			String folderpath, String accesspath, UserRequest request) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {
			// String UPLOAD_PATH = "/attachments/profilepics/";
			String UPLOAD_PATH = "C:\\Users\\Ajaz PC\\Desktop\\attachments\\";
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("auth_Users", 22));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPuserid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getProleid()) ? request.getProleid() : 0);

			callableStatement.setString(4, Utility.isNullOrEmpty(request.getPfullname()) ? request.getPfullname() : "");
			callableStatement.setString(5, Utility.isNullOrEmpty(request.getPusername()) ? request.getPusername() : "");
			callableStatement.setString(6, Utility.isNullOrEmpty(request.getPpass()) ? request.getPpass() : "");
			callableStatement.setString(7, Utility.isNullOrEmpty(request.getPmobile()) ? request.getPmobile() : "");
			callableStatement.setString(8, Utility.isNullOrEmpty(request.getPemail()) ? request.getPemail() : "");

			String RANDOM_NUMBER = Utility.GetRandomNumberString();
			String FILE_PATH = UPLOAD_PATH + RANDOM_NUMBER + "-" + Utility.GetCurrentDateTime()
					+ FileUtils.getFileExtension(fileMetaData.getFileName());

			String ACCESS_PATH = accesspath;
			String FILE_NAME = RANDOM_NUMBER + "-" + Utility.GetCurrentDateTime()
					+ FileUtils.getFileExtension(fileMetaData.getFileName());

			if (FileUtils.SaveFile(FILE_PATH, profilepicStream, fileMetaData)) {

				callableStatement.setString(9, Utility.isNullOrEmpty(FILE_NAME) ? FILE_NAME : "");
			} else {

				outResponse.setHaserror(1);
				outResponse.setMessage("File Uploading Failed");
				outResponse.setResult(0);
			}

			callableStatement.setString(10, Utility.isNullOrEmpty(request.getPdob()) ? request.getPdob() : "");
			callableStatement.setString(11, Utility.isNullOrEmpty(request.getPgender()) ? request.getPgender() : "");
			callableStatement.setString(12, Utility.isNullOrEmpty(request.getPcity()) ? request.getPcity() : "");

			callableStatement.setString(13, Utility.isNullOrEmpty(request.getPgst()) ? request.getPgst() : "");
			callableStatement.setString(14, Utility.isNullOrEmpty(request.getPaddress()) ? request.getPaddress() : "");
			callableStatement.setString(15, Utility.isNullOrEmpty(request.getPpincode()) ? request.getPpincode() : "");

			callableStatement.setString(16,
					Utility.isNullOrEmpty(request.getPdevicename()) ? request.getPdevicename() : "");
			callableStatement.setString(17,
					Utility.isNullOrEmpty(request.getPdeviceid()) ? request.getPdeviceid() : "");
			callableStatement.setString(18, Utility.isNullOrEmpty(request.getPfbtoken()) ? request.getPfbtoken() : "");
			callableStatement.setString(19,
					Utility.isNullOrEmpty(request.getPcoordinates()) ? request.getPcoordinates() : "");

			callableStatement.setInt(20, Utility.isNullOrEmpty(request.getPstatus()) ? request.getPstatus() : 0);

			callableStatement.setString(21, Utility.isNullOrEmpty(request.getPremark()) ? request.getPremark() : "");

			callableStatement.setInt(22, request.getPlogedinuserid());

			callableStatement.execute();
			connection.commit();
			resultSet = callableStatement.getResultSet();

			if (!resultSet.next()) {
				outResponse.setHaserror(1);
				outResponse.setMessage("Something went wrong");
				outResponse.setResult(0);
				Utility.showMessage(TAG, "" + outResponse);
			} else {
				outResponse.setResult(resultSet.getInt("result"));
				outResponse.setMessage(resultSet.getString("message"));
				outResponse.setHaserror(resultSet.getInt("haserror"));
			}

		} catch (Exception e) {
			Utility.showErrorMessage(TAG, e);
			outResponse.setResult(0);
			outResponse.setMessage(e.getMessage());
			outResponse.setHaserror(1);
			return outResponse;
		} finally {
			DBUtil.closeConnection(connection, callableStatement, resultSet);
		}

		return outResponse;
	}

}
