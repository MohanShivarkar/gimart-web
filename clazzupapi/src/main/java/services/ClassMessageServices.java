package services;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import helpers.DBUtil;
import helpers.FileUtils;
import helpers.QueryMaker;
import helpers.Utility;
import models.ApiResponse;
import models.classmsg.ClassMeassageCommentRequest;
import models.classmsg.ClassMessageCommentHolder;
import models.classmsg.ClassMessageCommentsListResponse;
import models.classmsg.ClassMessageHolder;
import models.classmsg.ClassMessageLikeRequest;
import models.classmsg.ClassMessageListResponse;
import models.classmsg.ClassMessageRequest;
import models.classmsg.ClassMessageSeenRequest;

public class ClassMessageServices {

	private static final String TAG = ClassMessageServices.class.getCanonicalName();

	private static ClassMessageServices instance;

	public static ClassMessageServices getInstance() {
		if (instance == null) {
			instance = new ClassMessageServices();
		}
		return instance;
	}

	public ApiResponse ManageClassMessage(ClassMessageRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("classmsg_MessageDetails", 10));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPclassmsgid());

			callableStatement.setInt(3,
					Utility.isNullOrEmpty(request.getPinstituteid()) ? request.getPinstituteid() : 0);

			callableStatement.setInt(4, Utility.isNullOrEmpty(request.getPclassid()) ? request.getPclassid() : 0);

			callableStatement.setString(5,
					Utility.isNullOrEmpty(request.getPclassmsgtype()) ? request.getPclassmsgtype() : "");
			callableStatement.setString(6,
					Utility.isNullOrEmpty(request.getPclassmsgtitle()) ? request.getPclassmsgtitle() : "");
			callableStatement.setString(7,
					Utility.isNullOrEmpty(request.getPclassmsgcontent()) ? request.getPclassmsgcontent() : "");

			callableStatement.setInt(8, Utility.isNullOrEmpty(request.getPstatus()) ? request.getPstatus() : 0);

			callableStatement.setString(9, Utility.isNullOrEmpty(request.getPremark()) ? request.getPremark() : "");

			callableStatement.setInt(10, request.getPlogedinuserid());

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
		Utility.showMessage(TAG, outResponse.toString());

		return outResponse;
	}

	public ApiResponse ManageClassMessageLikes(ClassMessageLikeRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("classmsg_MessageLikes", 6));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPlikeid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPclassmsgid()) ? request.getPclassmsgid() : 0);

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

	public ApiResponse ManageClassMessageComments(ClassMeassageCommentRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("classmsg_MessageComments", 7));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPcommentid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPclassmsgid()) ? request.getPclassmsgid() : 0);

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

	public ApiResponse ManageClassMessageSeen(ClassMessageSeenRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("classmsg_MessageSeen", 6));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPseenid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPclassmsgid()) ? request.getPclassmsgid() : 0);

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

	public ClassMessageListResponse GetClassMessagesLists(int paction, int pinstituteid, int pclassid, int puserid,
			int poffset, int plimit, String psearch) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		ClassMessageListResponse outResponse = new ClassMessageListResponse();
		ArrayList<ClassMessageHolder> list = new ArrayList<>();

		try {
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("classmsg_MessageFetch", 7));

			callableStatement.setInt(1, paction);
			callableStatement.setInt(2, pinstituteid);
			callableStatement.setInt(3, pclassid);
			callableStatement.setInt(4, puserid);
			callableStatement.setInt(5, poffset);
			callableStatement.setInt(6, plimit);
			callableStatement.setString(7, Utility.isNullOrEmpty(psearch) ? psearch : "");

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
					ClassMessageHolder out = new ClassMessageHolder();

					out.setClassmsgid(resultSet.getInt("classmsgid"));
					out.setInstituteid(resultSet.getInt("instituteid"));
					out.setClassid(resultSet.getInt("classid"));

					out.setClassmsgtype(resultSet.getString("classmsgtype"));
					out.setClassmsgtitle(resultSet.getString("classmsgtitle"));
					out.setClassmsgcontent(resultSet.getString("classmsgcontent"));
					out.setTotallikes(resultSet.getInt("totallikes"));

					if (resultSet.getString("likestatus") == null || resultSet.getString("likestatus").equals(null)) {
						out.setLikestatus(0);
					} else {
						out.setLikestatus(resultSet.getInt("likestatus"));
					}

					out.setTotalcomments(resultSet.getInt("totalcomments"));

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

	public ClassMessageCommentsListResponse GetClassMsgCommentsLists(int paction, int pclassmsgid) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		ClassMessageCommentsListResponse outResponse = new ClassMessageCommentsListResponse();
		ArrayList<ClassMessageCommentHolder> list = new ArrayList<>();

		try {
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("classmsg_CommentsFetch", 2));

			callableStatement.setInt(1, paction);
			callableStatement.setInt(2, pclassmsgid);

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
					ClassMessageCommentHolder out = new ClassMessageCommentHolder();

					out.setCommentid(resultSet.getInt("commentid"));
					out.setClassmsgid(resultSet.getInt("classmsgid"));
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

	public ApiResponse ManageClassMsgImages(InputStream profilepicStream, FormDataContentDisposition fileMetaData,
			ClassMessageRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			String UPLOAD_PATH = "C:\\inetpub\\wwwroot\\clazzup\\classmsg\\image\\";
			String ACCESS_PATH = "http://148.72.213.157/clazzup/classmsg/image/";
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("classmsg_MessageDetails", 10));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPclassmsgid());

			callableStatement.setInt(3,
					Utility.isNullOrEmpty(request.getPinstituteid()) ? request.getPinstituteid() : 0);

			callableStatement.setInt(4, Utility.isNullOrEmpty(request.getPclassid()) ? request.getPclassid() : 0);

			callableStatement.setString(5,
					Utility.isNullOrEmpty(request.getPclassmsgtype()) ? request.getPclassmsgtype() : "");
			callableStatement.setString(6,
					Utility.isNullOrEmpty(request.getPclassmsgtitle()) ? request.getPclassmsgtitle() : "");

//			callableStatement.setString(7,
//					Utility.isNullOrEmpty(request.getPclassmsgcontent()) ? request.getPclassmsgcontent() : "");

			String CurrentDateTime = Utility.GetCurrentDateTime();
			String RANDOM_NUMBER = Utility.GetRandomNumberString();
			String FILE_PATH = UPLOAD_PATH + RANDOM_NUMBER + "-" + CurrentDateTime
					+ FileUtils.getFileExtension(fileMetaData.getFileName());

			String FILE_NAME = RANDOM_NUMBER + "-" + CurrentDateTime
					+ FileUtils.getFileExtension(fileMetaData.getFileName());

			String FINAL_ACCESS_PATH = ACCESS_PATH + FILE_NAME;

			if (FileUtils.SaveFile(FILE_PATH, profilepicStream, fileMetaData)) {
				callableStatement.setString(7, Utility.isNullOrEmpty(FINAL_ACCESS_PATH) ? FINAL_ACCESS_PATH : "");
			} else {

				outResponse.setHaserror(1);
				outResponse.setMessage("File Uploading Failed");
				outResponse.setResult(0);
			}

			callableStatement.setInt(8, Utility.isNullOrEmpty(request.getPstatus()) ? request.getPstatus() : 0);

			callableStatement.setString(9, Utility.isNullOrEmpty(request.getPremark()) ? request.getPremark() : "");

			callableStatement.setInt(10, request.getPlogedinuserid());

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

	
	public ApiResponse ManageClassMsgPDFs(InputStream profilepicStream, FormDataContentDisposition fileMetaData,
			ClassMessageRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			String UPLOAD_PATH = "C:\\inetpub\\wwwroot\\clazzup\\classmsg\\pdf\\";
			String ACCESS_PATH = "http://148.72.213.157/clazzup/classmsg/pdf/";
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("classmsg_MessageDetails", 10));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPclassmsgid());

			callableStatement.setInt(3,
					Utility.isNullOrEmpty(request.getPinstituteid()) ? request.getPinstituteid() : 0);

			callableStatement.setInt(4, Utility.isNullOrEmpty(request.getPclassid()) ? request.getPclassid() : 0);

			callableStatement.setString(5,
					Utility.isNullOrEmpty(request.getPclassmsgtype()) ? request.getPclassmsgtype() : "");
			callableStatement.setString(6,
					Utility.isNullOrEmpty(request.getPclassmsgtitle()) ? request.getPclassmsgtitle() : "");

//			callableStatement.setString(7,
//					Utility.isNullOrEmpty(request.getPclassmsgcontent()) ? request.getPclassmsgcontent() : "");

			String CurrentDateTime = Utility.GetCurrentDateTime();
			String RANDOM_NUMBER = Utility.GetRandomNumberString();
			String FILE_PATH = UPLOAD_PATH + RANDOM_NUMBER + "-" + CurrentDateTime
					+ FileUtils.getFileExtension(fileMetaData.getFileName());

			String FILE_NAME = RANDOM_NUMBER + "-" + CurrentDateTime
					+ FileUtils.getFileExtension(fileMetaData.getFileName());

			String FINAL_ACCESS_PATH = ACCESS_PATH + FILE_NAME;

			if (FileUtils.SaveFile(FILE_PATH, profilepicStream, fileMetaData)) {
				callableStatement.setString(7, Utility.isNullOrEmpty(FINAL_ACCESS_PATH) ? FINAL_ACCESS_PATH : "");
			} else {

				outResponse.setHaserror(1);
				outResponse.setMessage("File Uploading Failed");
				outResponse.setResult(0);
			}

			callableStatement.setInt(8, Utility.isNullOrEmpty(request.getPstatus()) ? request.getPstatus() : 0);

			callableStatement.setString(9, Utility.isNullOrEmpty(request.getPremark()) ? request.getPremark() : "");

			callableStatement.setInt(10, request.getPlogedinuserid());

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
		// ClassMessageServices.getInstance().GetClassMessagesLists(3, 1, 2, 9, 0, 10,
		// "");

//		ClassMessageRequest request = new ClassMessageRequest(1, 0, 1, 2, "youtube", "title",
//				"https://www.youtube.com/watch?v=4St_bIaAFkk", 1, "active", 5);
//		ClassMessageServices.getInstance().ManageClassMessage(request);
	}

}
