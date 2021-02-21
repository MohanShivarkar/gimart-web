package services;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import helpers.DBUtil;
import helpers.FileUtils;
import helpers.FirebaseUtils;
import helpers.QueryMaker;
import helpers.Utility;
import helpers.models.NotificationBodyModel;
import models.ApiResponse;
import models.updates.UpdateCommentsHolder;
import models.updates.UpdateCommentsListResponse;
import models.updates.UpdateSeenRequest;
import models.updates.UpdatesCommentRequest;
import models.updates.UpdatesHolder;
import models.updates.UpdatesLikesRequest;
import models.updates.UpdatesListResponse;
import models.updates.UpdatesRequest;

public class UpdatesServices {

	private static final String TAG = UpdatesServices.class.getCanonicalName();

	private static UpdatesServices instance;

	public static UpdatesServices getInstance() {
		if (instance == null) {
			instance = new UpdatesServices();
		}
		return instance;
	}

	public ApiResponse ManageUpdateMessage(UpdatesRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("update_MessageDetails", 9));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPupdateid());

			callableStatement.setInt(3,
					Utility.isNullOrEmpty(request.getPinstituteid()) ? request.getPinstituteid() : 0);
			callableStatement.setString(4,
					Utility.isNullOrEmpty(request.getPupdatetype()) ? request.getPupdatetype() : "");
			callableStatement.setString(5,
					Utility.isNullOrEmpty(request.getPupdatetitle()) ? request.getPupdatetitle() : "");
			callableStatement.setString(6,
					Utility.isNullOrEmpty(request.getPupdatecontent()) ? request.getPupdatecontent() : "");

			callableStatement.setInt(7, Utility.isNullOrEmpty(request.getPstatus()) ? request.getPstatus() : 0);

			callableStatement.setString(8, Utility.isNullOrEmpty(request.getPremark()) ? request.getPremark() : "");

			callableStatement.setInt(9, request.getPlogedinuserid());

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

		// SendUpdateNotification
		if (outResponse.getHaserror() == 0 && request.getPupdateid() == 0) {

			int instituteid = request.getPinstituteid();
			String notificationtitle = "New Update Message";
			String notificationbody = request.getPupdatetitle();
			NotificationBodyModel bodyModel = new NotificationBodyModel(request.getPlogedinuserid(), "update",
					request.getPupdatetitle(), request.getPupdatetitle());

			FirebaseUtils.getInstance().SendUpdateNotification(instituteid, notificationtitle, notificationbody,
					bodyModel);
		}

		return outResponse;
	}

	public ApiResponse ManageUpdateLikes(UpdatesLikesRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("update_MessageLikes", 6));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPlikeid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPupdateid()) ? request.getPupdateid() : 0);

			callableStatement.setInt(4, Utility.isNullOrEmpty(request.getPstatus()) ? request.getPstatus() : 0);

			callableStatement.setString(5, Utility.isNullOrEmpty(request.getPremark()) ? request.getPremark() : "");

			callableStatement.setInt(6, request.getPlogedinuserid());

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

	public ApiResponse ManageUpdateComments(UpdatesCommentRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("update_MessageComments", 7));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPcommentid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPupdateid()) ? request.getPupdateid() : 0);

			callableStatement.setString(4, Utility.isNullOrEmpty(request.getPcomment()) ? request.getPcomment() : "");

			callableStatement.setInt(5, Utility.isNullOrEmpty(request.getPstatus()) ? request.getPstatus() : 0);

			callableStatement.setString(6, Utility.isNullOrEmpty(request.getPremark()) ? request.getPremark() : "");

			callableStatement.setInt(7, request.getPlogedinuserid());

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

	public ApiResponse ManageUpdateSeen(UpdateSeenRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("update_MessageSeen", 6));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPseenid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPupdateid()) ? request.getPupdateid() : 0);

			callableStatement.setInt(4, Utility.isNullOrEmpty(request.getPstatus()) ? request.getPstatus() : 0);

			callableStatement.setString(5, Utility.isNullOrEmpty(request.getPremark()) ? request.getPremark() : "");

			callableStatement.setInt(6, request.getPlogedinuserid());

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

	public UpdatesListResponse GetUpdatesMessagesLists(int paction, int pinstituteid, int puserid, int poffset,
			int plimit, String psearch) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		UpdatesListResponse outResponse = new UpdatesListResponse();
		ArrayList<UpdatesHolder> list = new ArrayList<>();

		try {
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("update_MessageFetch", 6));

			callableStatement.setInt(1, paction);
			callableStatement.setInt(2, pinstituteid);
			callableStatement.setInt(3, puserid);
			callableStatement.setInt(4, poffset);
			callableStatement.setInt(5, plimit);
			callableStatement.setString(6, Utility.isNullOrEmpty(psearch) ? psearch : "");

			// callableStatement.setString(6, psearch);

			callableStatement.execute();

			resultSet = callableStatement.getResultSet();

			if (!resultSet.next()) {
				outResponse.setArrayList(list);
				outResponse.setHaserror(1);
				outResponse.setMessage("No Data Exits");
				outResponse.setResult(0);
				Utility.showMessage(TAG, "" + outResponse);
			} else {

				do {
					UpdatesHolder out = new UpdatesHolder();

					out.setUpdateid(resultSet.getInt("updateid"));
					out.setUpdatetype(resultSet.getString("updatetype"));
					out.setUpdatetitle(resultSet.getString("updatetitle"));
					out.setUpdatecontent(resultSet.getString("updatecontent"));
					out.setTotallikes(resultSet.getInt("totallikes"));
					// out.setLikestatus(resultSet.getInt("likestatus"));

					if (resultSet.getString("likestatus") == null || resultSet.getString("likestatus").equals(null)) {
						out.setLikestatus(0);
					} else {
						out.setLikestatus(resultSet.getInt("likestatus"));
					}

					out.setTotalcomments(resultSet.getInt("totalcomments"));
					// out.setIsseen(resultSet.getInt("isseen"));

					try {
						if (resultSet.getString("seenstatus") == null || resultSet.getString("seenstatus").equals(null)
								|| resultSet.getString("seenstatus") == ""
								|| resultSet.getString("seenstatus").equals("")) {
							out.setIsseen(0);
						} else {
							out.setIsseen(resultSet.getInt("seenstatus"));
						}
					} catch (Exception e) {
						// System.out.println(""+e);
						out.setIsseen(0);
					}

					out.setUploadername(resultSet.getString("fullname"));
					out.setUploaderprofilepic(resultSet.getString("profilepic"));
					out.setCreated(resultSet.getString("created"));
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

	public UpdateCommentsListResponse GetUpdatesCommentsLists(int paction, int pupdateid) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		UpdateCommentsListResponse outResponse = new UpdateCommentsListResponse();
		ArrayList<UpdateCommentsHolder> list = new ArrayList<>();

		try {
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("update_CommentsFetch", 2));

			callableStatement.setInt(1, paction);
			callableStatement.setInt(2, pupdateid);

			callableStatement.execute();

			resultSet = callableStatement.getResultSet();

			if (!resultSet.next()) {
				outResponse.setArrayList(list);
				outResponse.setHaserror(1);
				outResponse.setMessage("No Data Exits");
				outResponse.setResult(0);
				Utility.showMessage(TAG, "" + outResponse);
			} else {

				do {
					UpdateCommentsHolder out = new UpdateCommentsHolder();

					out.setCommentid(resultSet.getInt("commentid"));
					out.setUpdateid(resultSet.getInt("updateid"));
					out.setComment(resultSet.getString("comment"));
					out.setUploadername(resultSet.getString("uploadername"));
					out.setUploaderprofilepic(resultSet.getString("uploaderprofilepic"));
					out.setCreated(resultSet.getString("created"));
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

	public ApiResponse ManageUpdatesImages(InputStream profilepicStream, FormDataContentDisposition fileMetaData,
			UpdatesRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			String UPLOAD_PATH = "C:\\inetpub\\wwwroot\\clazzup\\update\\image\\";
			String ACCESS_PATH = "http://148.72.213.157/clazzup/update/image/";
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("update_MessageDetails", 9));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPupdateid());

			callableStatement.setInt(3,
					Utility.isNullOrEmpty(request.getPinstituteid()) ? request.getPinstituteid() : 0);

			callableStatement.setString(4,
					Utility.isNullOrEmpty(request.getPupdatetype()) ? request.getPupdatetype() : "");
			callableStatement.setString(5,
					Utility.isNullOrEmpty(request.getPupdatetitle()) ? request.getPupdatetitle() : "");

//			callableStatement.setString(6,
//					Utility.isNullOrEmpty(request.getPupdatecontent()) ? request.getPupdatecontent() : "");

			String CurrentDateTime = Utility.GetCurrentDateTime();
			String RANDOM_NUMBER = Utility.GetRandomNumberString();
			String FILE_PATH = UPLOAD_PATH + RANDOM_NUMBER + "-" + CurrentDateTime
					+ FileUtils.getFileExtension(fileMetaData.getFileName());

			String FILE_NAME = RANDOM_NUMBER + "-" + CurrentDateTime
					+ FileUtils.getFileExtension(fileMetaData.getFileName());

			String FINAL_ACCESS_PATH = ACCESS_PATH + FILE_NAME;

			if (FileUtils.SaveFile(FILE_PATH, profilepicStream, fileMetaData)) {
				callableStatement.setString(6, Utility.isNullOrEmpty(FINAL_ACCESS_PATH) ? FINAL_ACCESS_PATH : "");
			} else {

				outResponse.setHaserror(1);
				outResponse.setMessage("File Uploading Failed");
				outResponse.setResult(0);
			}

			callableStatement.setInt(7, Utility.isNullOrEmpty(request.getPstatus()) ? request.getPstatus() : 0);

			callableStatement.setString(8, Utility.isNullOrEmpty(request.getPremark()) ? request.getPremark() : "");

			callableStatement.setInt(9, request.getPlogedinuserid());

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

	public ApiResponse ManageUpdatesPDFs(InputStream profilepicStream, FormDataContentDisposition fileMetaData,
			UpdatesRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			String UPLOAD_PATH = "C:\\inetpub\\wwwroot\\clazzup\\update\\pdf\\";
			String ACCESS_PATH = "http://148.72.213.157/clazzup/update/pdf/";
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("update_MessageDetails", 9));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPupdateid());

			callableStatement.setInt(3,
					Utility.isNullOrEmpty(request.getPinstituteid()) ? request.getPinstituteid() : 0);

			callableStatement.setString(4,
					Utility.isNullOrEmpty(request.getPupdatetype()) ? request.getPupdatetype() : "");
			callableStatement.setString(5,
					Utility.isNullOrEmpty(request.getPupdatetitle()) ? request.getPupdatetitle() : "");

//			callableStatement.setString(6,
//					Utility.isNullOrEmpty(request.getPupdatecontent()) ? request.getPupdatecontent() : "");

			String CurrentDateTime = Utility.GetCurrentDateTime();
			String RANDOM_NUMBER = Utility.GetRandomNumberString();
			String FILE_PATH = UPLOAD_PATH + RANDOM_NUMBER + "-" + CurrentDateTime
					+ FileUtils.getFileExtension(fileMetaData.getFileName());

			String FILE_NAME = RANDOM_NUMBER + "-" + CurrentDateTime
					+ FileUtils.getFileExtension(fileMetaData.getFileName());

			String FINAL_ACCESS_PATH = ACCESS_PATH + FILE_NAME;

			if (FileUtils.SaveFile(FILE_PATH, profilepicStream, fileMetaData)) {
				callableStatement.setString(6, Utility.isNullOrEmpty(FINAL_ACCESS_PATH) ? FINAL_ACCESS_PATH : "");
			} else {

				outResponse.setHaserror(1);
				outResponse.setMessage("File Uploading Failed");
				outResponse.setResult(0);
			}

			callableStatement.setInt(7, Utility.isNullOrEmpty(request.getPstatus()) ? request.getPstatus() : 0);

			callableStatement.setString(8, Utility.isNullOrEmpty(request.getPremark()) ? request.getPremark() : "");

			callableStatement.setInt(9, request.getPlogedinuserid());

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

	public static void main(String[] args) {
		UpdatesServices.getInstance().GetUpdatesMessagesLists(2, 1, 5, 0, 10, "");
	}

}
