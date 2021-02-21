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
import models.classes.ClassHolder;
import models.classes.ClassListResponse;
import models.classes.ClassRequest;
import models.institute.InstituteHolder;
import models.institute.InstituteListRequest;
import models.institute.InstituteListResponse;
import models.institute.InstituteRequest;
import models.subjects.SubjectHolder;
import models.subjects.SubjectListResponse;
import models.subjects.SubjectRequest;

public class MasterService {

	private static final String TAG = MasterService.class.getCanonicalName();

	private static MasterService instance;

	public static MasterService getInstance() {
		if (instance == null) {
			instance = new MasterService();
		}
		return instance;
	}

	public InstituteListResponse GetInstituteLists(InstituteListRequest request) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		InstituteListResponse outResponse = new InstituteListResponse();
		ArrayList<InstituteHolder> list = new ArrayList<>();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("mst_InstitutesFetch", 4));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, Utility.isNullOrEmpty(request.getPoffset()) ? request.getPoffset() : 0);
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPlimit()) ? request.getPlimit() : 0);

			callableStatement.setString(4, Utility.isNullOrEmpty(request.getPsearch()) ? request.getPsearch() : "");

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
					InstituteHolder out = new InstituteHolder();

					out.setInstituteid(resultSet.getInt("instituteid"));
					out.setInstitutename(resultSet.getString("institutename"));
					out.setPhone(resultSet.getString("phone"));
					out.setEmail(resultSet.getString("email"));
					out.setWebsite(resultSet.getString("website"));
					out.setProfilepic(resultSet.getString("profilepic"));
					out.setDescription(resultSet.getString("description"));
					out.setAddress(resultSet.getString("address"));
					out.setTwitter(resultSet.getString("twitter"));
					out.setFacebook(resultSet.getString("facebook"));
					out.setInstagram(resultSet.getString("instagram"));
					out.setWhatsapp(resultSet.getString("whatsapp"));
					out.setLinkedin(resultSet.getString("linkedin"));
					out.setIsactive(resultSet.getInt("isactive"));
					out.setCreated(resultSet.getString("created"));
					out.setStatus(resultSet.getInt("status"));
					out.setRemark(resultSet.getString("remark"));

//					if (resultSet.getString("status") == null || resultSet.getString("status").equals(null)) {
//						out.setStatus(0);
//					} else {
//						out.setStatus(resultSet.getInt("status"));
//					}
//
//					out.setRemark(resultSet.getString("remark"));

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

	public ApiResponse ManageInstitues(InstituteRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("mst_Institutes", 17));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPinstituteid());
			callableStatement.setString(3,
					Utility.isNullOrEmpty(request.getPinstitutename()) ? request.getPinstitutename() : "");

			callableStatement.setString(4, Utility.isNullOrEmpty(request.getPphone()) ? request.getPphone() : "");
			callableStatement.setString(5, Utility.isNullOrEmpty(request.getPemail()) ? request.getPemail() : "");
			callableStatement.setString(6, Utility.isNullOrEmpty(request.getPwebsite()) ? request.getPwebsite() : "");
			callableStatement.setString(7,
					Utility.isNullOrEmpty(request.getPprofilepic()) ? request.getPprofilepic() : "");
			callableStatement.setString(8,
					Utility.isNullOrEmpty(request.getPdescription()) ? request.getPdescription() : "");
			callableStatement.setString(9, Utility.isNullOrEmpty(request.getPaddress()) ? request.getPaddress() : "");

			callableStatement.setString(10, Utility.isNullOrEmpty(request.getPtwitter()) ? request.getPtwitter() : "");
			callableStatement.setString(11,
					Utility.isNullOrEmpty(request.getPfacebook()) ? request.getPfacebook() : "");
			callableStatement.setString(12,
					Utility.isNullOrEmpty(request.getPinstagram()) ? request.getPinstagram() : "");
			callableStatement.setString(13,
					Utility.isNullOrEmpty(request.getPwhatsapp()) ? request.getPwhatsapp() : "");
			callableStatement.setString(14,
					Utility.isNullOrEmpty(request.getPlinkedin()) ? request.getPlinkedin() : "");

			callableStatement.setInt(15, Utility.isNullOrEmpty(request.getPstatus()) ? request.getPstatus() : 0);

			callableStatement.setString(16, Utility.isNullOrEmpty(request.getPremark()) ? request.getPremark() : "");

			callableStatement.setInt(17, request.getPlogedinuserid());

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

	public ApiResponse ManageInstitutesProfilePics(InputStream profilepicStream,
			FormDataContentDisposition fileMetaData, InstituteRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {
			// String UPLOAD_PATH = "/attachments/profilepics/";
			String UPLOAD_PATH = "C:\\inetpub\\wwwroot\\clazzup\\institutes\\";
			String ACCESS_PATH = "http://148.72.213.157/clazzup/institutes/";
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("auth_UsersProfilePic", 4));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPinstituteid());

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

	public ApiResponse ManageClasses(ClassRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("mst_Classes", 7));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPclassid());
			callableStatement.setInt(3,
					Utility.isNullOrEmpty(request.getPinstituteid()) ? request.getPinstituteid() : 0);
			callableStatement.setString(4,
					Utility.isNullOrEmpty(request.getPclassname()) ? request.getPclassname() : "");

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

	public ClassListResponse GetClassesLists(int paction, int pinstituteid) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		ClassListResponse outResponse = new ClassListResponse();
		ArrayList<ClassHolder> list = new ArrayList<>();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("mst_ClassesFetch", 2));

			callableStatement.setInt(1, paction);
			callableStatement.setInt(2, pinstituteid);

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
					ClassHolder out = new ClassHolder();

					out.setClassid(resultSet.getInt("classid"));
					out.setInstituteid(resultSet.getInt("instituteid"));
					out.setClassname(resultSet.getString("classname"));
					out.setIsactive(resultSet.getInt("isactive"));
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

	public SubjectListResponse GetSubjectsLists(int paction, int pclassid) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		SubjectListResponse outResponse = new SubjectListResponse();
		ArrayList<SubjectHolder> list = new ArrayList<>();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("mst_SubjectFetch", 2));

			callableStatement.setInt(1, paction);
			callableStatement.setInt(2, pclassid);

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
					SubjectHolder out = new SubjectHolder();

					out.setSubjectid(resultSet.getInt("subjectid"));
					out.setClassid(resultSet.getInt("classid"));
					out.setClassname(resultSet.getString("classname"));
					out.setSubjectname(resultSet.getString("subjectname"));
					out.setIsactive(resultSet.getInt("isactive"));
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

	public ApiResponse ManageSubjects(SubjectRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("mst_Subjects", 7));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPsubjectid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPclassid()) ? request.getPclassid() : 0);
			callableStatement.setString(4,
					Utility.isNullOrEmpty(request.getPsubjectname()) ? request.getPsubjectname() : "");

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

	public static void main(String[] args) {
//		InstituteRequest request = new InstituteRequest(1, 0, "institute2", "0", "email", "website", "profilepic",
//				"descripotion", "address", 1, "remarl", 1);
//		ApiResponse apiResponse = InstituteService.getInstance().ManageInstitues(request);
//		System.out.println(apiResponse.toString());

		InstituteListRequest request = new InstituteListRequest(2, 0, 10, "");
		MasterService.getInstance().GetInstituteLists(request);

	}

}
