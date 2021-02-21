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
import models.lessons.LessonAttachmentListResponse;
import models.lessons.LessonSeenRequest;
import models.lessons.LessonsAttachmentHolder;
import models.lessons.LessonsAttachmentRequest;
import models.lessons.LessonsHolder;
import models.lessons.LessonsListRequest;
import models.lessons.LessonsListResponse;
import models.lessons.LessonsRequest;
import models.updates.UpdatesRequest;

public class LessonsServices {

	private static final String TAG = LessonsServices.class.getCanonicalName();

	private static LessonsServices instance;

	public static LessonsServices getInstance() {
		if (instance == null) {
			instance = new LessonsServices();
		}
		return instance;
	}

	public ApiResponse ManageLessons(LessonsRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("edu_Lessons", 12));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPlessonid());

			callableStatement.setInt(3,
					Utility.isNullOrEmpty(request.getPinstituteid()) ? request.getPinstituteid() : 0);

			callableStatement.setInt(4, Utility.isNullOrEmpty(request.getPclassid()) ? request.getPclassid() : 0);

			callableStatement.setInt(5, Utility.isNullOrEmpty(request.getPsubjectid()) ? request.getPsubjectid() : 0);

			callableStatement.setInt(6, Utility.isNullOrEmpty(request.getPchapterno()) ? request.getPchapterno() : 0);

			callableStatement.setString(7,
					Utility.isNullOrEmpty(request.getPlessontitle()) ? request.getPlessontitle() : "");
			callableStatement.setString(8,
					Utility.isNullOrEmpty(request.getPlessondesc()) ? request.getPlessondesc() : "");

			callableStatement.setString(9,
					Utility.isNullOrEmpty(request.getPlessonthumb()) ? request.getPlessonthumb() : "");

			callableStatement.setInt(10, Utility.isNullOrEmpty(request.getPstatus()) ? request.getPstatus() : 0);

			callableStatement.setString(11, Utility.isNullOrEmpty(request.getPremark()) ? request.getPremark() : "");

			callableStatement.setInt(12, request.getPlogedinuserid());

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

	public ApiResponse ManageLessonsWithThumbnail(InputStream profilepicStream, FormDataContentDisposition fileMetaData,
			LessonsRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			String UPLOAD_PATH = "C:\\inetpub\\wwwroot\\clazzup\\lessons\\images\\";
			String ACCESS_PATH = "http://148.72.213.157/clazzup/lessons/images/";
			connection = DBUtil.openConnection();

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("edu_Lessons", 12));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPlessonid());

			callableStatement.setInt(3,
					Utility.isNullOrEmpty(request.getPinstituteid()) ? request.getPinstituteid() : 0);

			callableStatement.setInt(4, Utility.isNullOrEmpty(request.getPclassid()) ? request.getPclassid() : 0);

			callableStatement.setInt(5, Utility.isNullOrEmpty(request.getPsubjectid()) ? request.getPsubjectid() : 0);

			callableStatement.setInt(6, Utility.isNullOrEmpty(request.getPchapterno()) ? request.getPchapterno() : 0);

			callableStatement.setString(7,
					Utility.isNullOrEmpty(request.getPlessontitle()) ? request.getPlessontitle() : "");
			callableStatement.setString(8,
					Utility.isNullOrEmpty(request.getPlessondesc()) ? request.getPlessondesc() : "");

			// callableStatement.setString(9,
			// Utility.isNullOrEmpty(request.getPlessonthumb()) ? request.getPlessonthumb()
			// : "");

			String CurrentDateTime = Utility.GetCurrentDateTime();
			String RANDOM_NUMBER = Utility.GetRandomNumberString();
			String FILE_PATH = UPLOAD_PATH + RANDOM_NUMBER + "-" + CurrentDateTime
					+ FileUtils.getFileExtension(fileMetaData.getFileName());

			String FILE_NAME = RANDOM_NUMBER + "-" + CurrentDateTime
					+ FileUtils.getFileExtension(fileMetaData.getFileName());

			String FINAL_ACCESS_PATH = ACCESS_PATH + FILE_NAME;

			if (FileUtils.SaveFile(FILE_PATH, profilepicStream, fileMetaData)) {
				callableStatement.setString(9, Utility.isNullOrEmpty(FINAL_ACCESS_PATH) ? FINAL_ACCESS_PATH : "");
			} else {

				outResponse.setHaserror(1);
				outResponse.setMessage("File Uploading Failed");
				outResponse.setResult(0);
			}

			callableStatement.setInt(10, Utility.isNullOrEmpty(request.getPstatus()) ? request.getPstatus() : 0);

			callableStatement.setString(11, Utility.isNullOrEmpty(request.getPremark()) ? request.getPremark() : "");

			callableStatement.setInt(12, request.getPlogedinuserid());

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

	public LessonsListResponse GetLessonsLists(LessonsListRequest request) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		LessonsListResponse outResponse = new LessonsListResponse();
		ArrayList<LessonsHolder> list = new ArrayList<>();

		try {
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("edu_LessonsFetch", 8));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPinstituteid());
			callableStatement.setInt(3, request.getPclassid());
			callableStatement.setInt(4, request.getPsubjectid());
			callableStatement.setInt(5, request.getPuserid());
			callableStatement.setInt(6, request.getPoffset());
			callableStatement.setInt(7, request.getPlimit());
			callableStatement.setString(8, Utility.isNullOrEmpty(request.getPsearch()) ? request.getPsearch() : "");

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
					LessonsHolder out = new LessonsHolder();

					out.setLessonid(resultSet.getInt("lessonid"));
					out.setInstituteid(resultSet.getInt("instituteid"));
					out.setClassid(resultSet.getInt("classid"));
					out.setSubjectid(resultSet.getInt("subjectid"));
					out.setSubjectname(resultSet.getString("subjectname"));
					out.setChapterno(resultSet.getInt("chapterno"));

					out.setLessontitle(resultSet.getString("lessontitle"));
					out.setLessondesc(resultSet.getString("lessondesc"));
					out.setLessonthumb(resultSet.getString("lessonthumb"));

					try {
						if (resultSet.getString("seenstatus") == null || resultSet.getString("seenstatus").equals(null)
								|| resultSet.getString("seenstatus") == ""
								|| resultSet.getString("seenstatus").equals("")) {
							out.setSeenstatus(0);
						} else {
							out.setSeenstatus(resultSet.getInt("seenstatus"));
						}
					} catch (Exception e) {
						// System.out.println(""+e);
						out.setSeenstatus(0);
					}

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

	public ApiResponse ManageLessonSeen(LessonSeenRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("edu_LessonSeen", 6));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPseenid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPlessonid()) ? request.getPlessonid() : 0);

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

	public LessonsListResponse GetLessonsAttachments(LessonsListRequest request) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		LessonsListResponse outResponse = new LessonsListResponse();
		ArrayList<LessonsHolder> list = new ArrayList<>();

		try {
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("edu_LessonsFetch", 8));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPinstituteid());
			callableStatement.setInt(3, request.getPclassid());
			callableStatement.setInt(4, request.getPsubjectid());
			callableStatement.setInt(5, request.getPuserid());
			callableStatement.setInt(6, request.getPoffset());
			callableStatement.setInt(7, request.getPlimit());
			callableStatement.setString(8, Utility.isNullOrEmpty(request.getPsearch()) ? request.getPsearch() : "");

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
					LessonsHolder out = new LessonsHolder();

					out.setLessonid(resultSet.getInt("lessonid"));
					out.setInstituteid(resultSet.getInt("instituteid"));
					out.setClassid(resultSet.getInt("classid"));
					out.setSubjectid(resultSet.getInt("subjectid"));
					out.setSubjectname(resultSet.getString("subjectname"));
					out.setChapterno(resultSet.getInt("chapterno"));

					out.setLessontitle(resultSet.getString("lessontitle"));
					out.setLessondesc(resultSet.getString("lessondesc"));
					out.setLessonthumb(resultSet.getString("lessonthumb"));

					try {
						if (resultSet.getString("seenstatus") == null || resultSet.getString("seenstatus").equals(null)
								|| resultSet.getString("seenstatus") == ""
								|| resultSet.getString("seenstatus").equals("")) {
							out.setSeenstatus(0);
						} else {
							out.setSeenstatus(resultSet.getInt("seenstatus"));
						}
					} catch (Exception e) {
						// System.out.println(""+e);
						out.setSeenstatus(0);
					}

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

	public LessonAttachmentListResponse GetLessonAttachmentLists(int paction, int plessonid, String pattachtype,
			int puserid, int poffset, int plimit, String psearch) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		LessonAttachmentListResponse outResponse = new LessonAttachmentListResponse();
		ArrayList<LessonsAttachmentHolder> list = new ArrayList<>();

		try {
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("edu_LessonAttachmentFetch", 7));

			callableStatement.setInt(1, paction);
			callableStatement.setInt(2, plessonid);
			callableStatement.setString(3, pattachtype);
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
					LessonsAttachmentHolder out = new LessonsAttachmentHolder();

					out.setAttachid(resultSet.getInt("attachid"));
					out.setLessonid(resultSet.getInt("lessonid"));
					out.setAttachtype(resultSet.getString("attachtype"));
					out.setAttachtitle(resultSet.getString("attachtitle"));
					out.setAttachdesc(resultSet.getString("attachdesc"));
					out.setUploadername(resultSet.getString("fullname"));
					out.setUploaderprofilepic(resultSet.getString("profilepic"));
					out.setCreated(resultSet.getString("created"));
					out.setCreatedBy(resultSet.getInt("createdBy"));
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

	public ApiResponse ManageLessonAttachments(LessonsAttachmentRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("edu_Lessons_Attachments", 9));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPattachid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPlessonid()) ? request.getPlessonid() : 0);

			callableStatement.setString(4,
					Utility.isNullOrEmpty(request.getPattachtype()) ? request.getPattachtype() : "");
			callableStatement.setString(5,
					Utility.isNullOrEmpty(request.getPattachtitle()) ? request.getPattachtitle() : "");

			callableStatement.setString(6,
					Utility.isNullOrEmpty(request.getPattachdesc()) ? request.getPattachdesc() : "");

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

	// region insertion
	public ApiResponse ManageLessonImages(InputStream profilepicStream, FormDataContentDisposition fileMetaData,
			LessonsAttachmentRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			String UPLOAD_PATH = "C:\\inetpub\\wwwroot\\clazzup\\lessons\\attachments\\images\\";
			String ACCESS_PATH = "http://148.72.213.157/clazzup/lessons/attachments/images/";
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("edu_Lessons_Attachments", 9));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPattachid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPlessonid()) ? request.getPlessonid() : 0);

			callableStatement.setString(4,
					Utility.isNullOrEmpty(request.getPattachtype()) ? request.getPattachtype() : "");
			callableStatement.setString(5,
					Utility.isNullOrEmpty(request.getPattachtitle()) ? request.getPattachtitle() : "");

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

	public ApiResponse ManageLessonPDFs(InputStream profilepicStream, FormDataContentDisposition fileMetaData,
			LessonsAttachmentRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			String UPLOAD_PATH = "C:\\inetpub\\wwwroot\\clazzup\\lessons\\attachments\\pdf\\";
			String ACCESS_PATH = "http://148.72.213.157/clazzup/lessons/attachments/pdf/";
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("edu_Lessons_Attachments", 9));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPattachid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPlessonid()) ? request.getPlessonid() : 0);

			callableStatement.setString(4,
					Utility.isNullOrEmpty(request.getPattachtype()) ? request.getPattachtype() : "");
			callableStatement.setString(5,
					Utility.isNullOrEmpty(request.getPattachtitle()) ? request.getPattachtitle() : "");

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

}
