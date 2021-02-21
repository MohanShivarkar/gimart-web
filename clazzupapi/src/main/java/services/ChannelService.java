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
import models.channel.core.ChannelHolder;
import models.channel.core.ChannelListRequest;
import models.channel.core.ChannelListResponse;
import models.channel.core.ChannelRequest;
import models.channel.member.MemberHolder;
import models.channel.member.MemberListResponse;
import models.channel.member.MemberRequest;
import models.channel.msg.ChannelMsgCommentHolder;
import models.channel.msg.ChannelMsgCommentListReposne;
import models.channel.msg.ChannelMsgCommentRequest;
import models.channel.msg.ChannelMsgHolder;
import models.channel.msg.ChannelMsgLikeRequest;
import models.channel.msg.ChannelMsgListResponse;
import models.channel.msg.ChannelMsgRequest;
import models.channel.msg.ChannelMsgSeenRequest;

public class ChannelService {

	private static final String TAG = ChannelService.class.getCanonicalName();

	private static ChannelService instance;

	public static ChannelService getInstance() {
		if (instance == null) {
			instance = new ChannelService();
		}
		return instance;
	}

	public ApiResponse ManageChannels(ChannelRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("ch_Details", 11));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPchid());

			callableStatement.setString(3, Utility.isNullOrEmpty(request.getPchtype()) ? request.getPchtype() : "");

			callableStatement.setString(4, Utility.isNullOrEmpty(request.getPchtitle()) ? request.getPchtitle() : "");

			callableStatement.setString(5,
					Utility.isNullOrEmpty(request.getPchprofilepic()) ? request.getPchprofilepic() : "");

			callableStatement.setInt(6, Utility.isNullOrEmpty(request.getPstatus()) ? request.getPstatus() : 0);

			callableStatement.setString(7, Utility.isNullOrEmpty(request.getPremark()) ? request.getPremark() : "");

			callableStatement.setInt(8, request.getPlogedinuserid());

			callableStatement.registerOutParameter(9, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(10, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(11, java.sql.Types.INTEGER);

			callableStatement.execute();
			connection.commit();

			int result = callableStatement.getInt(9);
			String message = callableStatement.getString(10);
			int haserror = callableStatement.getInt(11);

			Utility.showMessage(TAG, "" + new ApiResponse(result, message, haserror));

			return new ApiResponse(result, message, haserror);

		} catch (Exception e) {
			Utility.showErrorMessage(TAG, e);
			return new ApiResponse(0, e.getMessage(), 1);
		} finally {
			DBUtil.closeConnection(connection, callableStatement);
		}
	}

	public ApiResponse ManageChannelWithThumbnail(InputStream profilepicStream, FormDataContentDisposition fileMetaData,
			ChannelRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			String UPLOAD_PATH = "C:\\inetpub\\wwwroot\\clazzup\\channels\\profilepics\\";
			String ACCESS_PATH = "http://148.72.213.157/clazzup/channels/profilepics/";
			connection = DBUtil.openConnection();

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("ch_Details", 10));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPchid());

			callableStatement.setInt(3,
					Utility.isNullOrEmpty(request.getPinstituteid()) ? request.getPinstituteid() : 0);

			callableStatement.setString(4, Utility.isNullOrEmpty(request.getPchtype()) ? request.getPchtype() : "");

			callableStatement.setString(5, Utility.isNullOrEmpty(request.getPchtitle()) ? request.getPchtitle() : "");

			callableStatement.setString(6, Utility.isNullOrEmpty(request.getPchdesc()) ? request.getPchdesc() : "");

			// callableStatement.setString(5,
			// Utility.isNullOrEmpty(request.getPchprofilepic()) ?
			// request.getPchprofilepic() : "");

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
		Utility.showMessage(TAG, outResponse.toString());

		return outResponse;
	}

	public ChannelListResponse GetChannelLists(ChannelListRequest request) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		ChannelListResponse outResponse = new ChannelListResponse();
		ArrayList<ChannelHolder> list = new ArrayList<>();

		try {
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("ch_DetailsFetch", 7));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPinstituteid());
			callableStatement.setString(3, request.getPchtype());
			callableStatement.setInt(4, request.getPuserid());
			callableStatement.setInt(5, request.getPoffset());
			callableStatement.setInt(6, request.getPlimit());
			callableStatement.setString(7, Utility.isNullOrEmpty(request.getPsearch()) ? request.getPsearch() : "");

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
					ChannelHolder out = new ChannelHolder();

					out.setChid(resultSet.getInt("chid"));
					out.setInstituteid(resultSet.getInt("instituteid"));
					out.setChtype(resultSet.getString("chtype"));

					out.setChtitle(resultSet.getString("chtitle"));
					out.setChdesc(resultSet.getString("chdesc"));
					out.setChprofilepic(resultSet.getString("chprofilepic"));

//					try {
//						if (resultSet.getString("seenstatus") == null || resultSet.getString("seenstatus").equals(null)
//								|| resultSet.getString("seenstatus") == ""
//								|| resultSet.getString("seenstatus").equals("")) {
//							out.setSeenstatus(0);
//						} else {
//							out.setSeenstatus(resultSet.getInt("seenstatus"));
//						}
//					} catch (Exception e) {
//						// System.out.println(""+e);
//						out.setSeenstatus(0);
//					}

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

	public ApiResponse ManageChannelMembers(MemberRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("ch_Members", 8));

			callableStatement.setInt(1, request.getPaction());

			callableStatement.setInt(2, request.getPmemberid());

			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPchid()) ? request.getPchid() : 0);

			callableStatement.setInt(4, Utility.isNullOrEmpty(request.getPuserid()) ? request.getPuserid() : 0);

			callableStatement.setInt(5, Utility.isNullOrEmpty(request.getPisadmin()) ? request.getPisadmin() : 0);
			callableStatement.setInt(6, Utility.isNullOrEmpty(request.getPstatus()) ? request.getPstatus() : 0);

			callableStatement.setString(7, Utility.isNullOrEmpty(request.getPremark()) ? request.getPremark() : "");

			callableStatement.setInt(8, request.getPlogedinuserid());

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

	public MemberListResponse GetChannelMembers(int paction, int pchid, int poffset, int plimit, String psearch) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		MemberListResponse outResponse = new MemberListResponse();
		ArrayList<MemberHolder> list = new ArrayList<>();

		try {
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("ch_MembersFetch", 5));

			callableStatement.setInt(1, paction);
			callableStatement.setInt(2, pchid);
			callableStatement.setInt(3, poffset);
			callableStatement.setInt(4, plimit);
			callableStatement.setString(5, Utility.isNullOrEmpty(psearch) ? psearch : "");

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
					MemberHolder out = new MemberHolder();

					out.setMemberid(resultSet.getInt("memberid"));
					out.setChid(resultSet.getInt("chid"));
					out.setUserid(resultSet.getInt("userid"));
					out.setIsadmin(resultSet.getInt("isadmin"));

					out.setFullname(resultSet.getString("fullname"));
					out.setProfilepic(resultSet.getString("profilepic"));
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

	// Channel Messages Section

	public ApiResponse ManageChMsgs(ChannelMsgRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("ch_Messages", 10));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPchmsgid());

			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPchid()) ? request.getPchid() : 0);

			callableStatement.setInt(4, Utility.isNullOrEmpty(request.getPsenderid()) ? request.getPsenderid() : 0);

			callableStatement.setString(5,
					Utility.isNullOrEmpty(request.getPchmsgtype()) ? request.getPchmsgtype() : "");
			callableStatement.setString(6,
					Utility.isNullOrEmpty(request.getPchmsgtitle()) ? request.getPchmsgtitle() : "");
			callableStatement.setString(7,
					Utility.isNullOrEmpty(request.getPchcontent()) ? request.getPchcontent() : "");

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

	public ChannelMsgListResponse GetChannelMsgLists(int paction, int pchid, int puserid, int poffset, int plimit,
			String psearch) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		ChannelMsgListResponse outResponse = new ChannelMsgListResponse();
		ArrayList<ChannelMsgHolder> list = new ArrayList<>();

		try {
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("ch_MessagesFetch", 6));

			callableStatement.setInt(1, paction);
			callableStatement.setInt(2, pchid);
			callableStatement.setInt(3, puserid);
			callableStatement.setInt(4, poffset);
			callableStatement.setInt(5, plimit);
			callableStatement.setString(6, Utility.isNullOrEmpty(psearch) ? psearch : "");

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
					ChannelMsgHolder out = new ChannelMsgHolder();

					out.setChmsgid(resultSet.getInt("chmsgid"));
					out.setChid(resultSet.getInt("chid"));
					out.setSenderid(resultSet.getInt("senderid"));

					out.setChmsgtype(resultSet.getString("chmsgtype"));
					out.setChmsgtitle(resultSet.getString("chmsgtitle"));
					out.setChcontent(resultSet.getString("chcontent"));
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

	public ApiResponse ManageChannelMessage(ChannelMsgRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("ch_Messages", 10));

			callableStatement.setInt(1, request.getPaction());

			callableStatement.setInt(2, request.getPchmsgid());

			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPchid()) ? request.getPchid() : 0);

			callableStatement.setInt(4, Utility.isNullOrEmpty(request.getPsenderid()) ? request.getPsenderid() : 0);

			callableStatement.setString(5,
					Utility.isNullOrEmpty(request.getPchmsgtype()) ? request.getPchmsgtype() : "");

			callableStatement.setString(6,
					Utility.isNullOrEmpty(request.getPchmsgtitle()) ? request.getPchmsgtitle() : "");

			callableStatement.setString(7,
					Utility.isNullOrEmpty(request.getPchcontent()) ? request.getPchcontent() : "");

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

	public ApiResponse ManageChannelMsgImages(InputStream profilepicStream, FormDataContentDisposition fileMetaData,
			ChannelMsgRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			String UPLOAD_PATH = "C:\\inetpub\\wwwroot\\clazzup\\channels\\image\\";
			String ACCESS_PATH = "http://148.72.213.157/clazzup/channels/image/";
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("ch_Messages", 10));

			callableStatement.setInt(1, request.getPaction());

			callableStatement.setInt(2, request.getPchmsgid());

			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPchid()) ? request.getPchid() : 0);

			callableStatement.setInt(4, Utility.isNullOrEmpty(request.getPsenderid()) ? request.getPsenderid() : 0);

			callableStatement.setString(5,
					Utility.isNullOrEmpty(request.getPchmsgtype()) ? request.getPchmsgtype() : "");

			callableStatement.setString(6,
					Utility.isNullOrEmpty(request.getPchmsgtitle()) ? request.getPchmsgtitle() : "");

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

	public ApiResponse ManageChannelMsgPDFs(InputStream profilepicStream, FormDataContentDisposition fileMetaData,
			ChannelMsgRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			String UPLOAD_PATH = "C:\\inetpub\\wwwroot\\clazzup\\channels\\pdf\\";
			String ACCESS_PATH = "http://148.72.213.157/clazzup/channels/pdf/";
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("ch_Messages", 10));

			callableStatement.setInt(1, request.getPaction());

			callableStatement.setInt(2, request.getPchmsgid());

			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPchid()) ? request.getPchid() : 0);

			callableStatement.setInt(4, Utility.isNullOrEmpty(request.getPsenderid()) ? request.getPsenderid() : 0);

			callableStatement.setString(5,
					Utility.isNullOrEmpty(request.getPchmsgtype()) ? request.getPchmsgtype() : "");

			callableStatement.setString(6,
					Utility.isNullOrEmpty(request.getPchmsgtitle()) ? request.getPchmsgtitle() : "");

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

	public ApiResponse ManageChannelMsgLikes(ChannelMsgLikeRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("ch_MessageLikes", 6));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPlikeid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPchmsgid()) ? request.getPchmsgid() : 0);

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

	public ApiResponse ManageChannelMsgComments(ChannelMsgCommentRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("ch_MessageComments", 7));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPcommentid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPchmsgid()) ? request.getPchmsgid() : 0);

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

	public ApiResponse ManageChannelMsgSeen(ChannelMsgSeenRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("ch_MessageSeen", 6));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPseenid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPchmsgid()) ? request.getPchmsgid() : 0);

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

	public ChannelMsgCommentListReposne GetChannelMsgCommentsLists(int paction, int pchmsgid) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		ChannelMsgCommentListReposne outResponse = new ChannelMsgCommentListReposne();
		ArrayList<ChannelMsgCommentHolder> list = new ArrayList<>();

		try {
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("ch_CommentsFetch", 2));

			callableStatement.setInt(1, paction);
			callableStatement.setInt(2, pchmsgid);

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

					ChannelMsgCommentHolder out = new ChannelMsgCommentHolder();

					out.setCommentid(resultSet.getInt("commentid"));
					out.setChmsgid(resultSet.getInt("chmsgid"));
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

}
