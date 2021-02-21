package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import helpers.DBUtil;
import helpers.QueryMaker;
import helpers.Utility;
import models.ApiResponse;
import models.fees.FeesHolder;
import models.fees.FeesListResponse;
import models.fees.FeesRequest;

public class FeesServices {

	private static final String TAG = FeesServices.class.getCanonicalName();

	private static FeesServices instance;

	public static FeesServices getInstance() {
		if (instance == null) {
			instance = new FeesServices();
		}
		return instance;
	}

	public ApiResponse ManageFees(FeesRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("auth_Fees", 13));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPfeesid());

			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPclassid()) ? request.getPclassid() : 0);

			callableStatement.setString(4, Utility.isNullOrEmpty(request.getPtitle()) ? request.getPtitle() : "");

			callableStatement.setString(5,
					Utility.isNullOrEmpty(request.getPstartdate()) ? request.getPstartdate() : "");
			callableStatement.setString(6, Utility.isNullOrEmpty(request.getPenddate()) ? request.getPenddate() : "");

			callableStatement.setDouble(7,
					Utility.isNullOrEmpty(request.getPfeesamount()) ? request.getPfeesamount() : 0);

			callableStatement.setString(8,
					Utility.isNullOrEmpty(request.getPstartdatemsg()) ? request.getPstartdatemsg() : "");

			callableStatement.setString(9,
					Utility.isNullOrEmpty(request.getPdeadlinemsg()) ? request.getPdeadlinemsg() : "");
			callableStatement.setDouble(10, Utility.isNullOrEmpty(request.getPlatefees()) ? request.getPlatefees() : 0);

			callableStatement.setInt(11, Utility.isNullOrEmpty(request.getPstatus()) ? request.getPstatus() : 0);

			callableStatement.setString(12, Utility.isNullOrEmpty(request.getPremark()) ? request.getPremark() : "");

			callableStatement.setInt(13, request.getPlogedinuserid());

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

	public FeesListResponse GetFees(int paction, int pclassid) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		FeesListResponse outResponse = new FeesListResponse();
		ArrayList<FeesHolder> list = new ArrayList<>();

		try {
			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("auth_Feesfetch", 2));

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
					FeesHolder out = new FeesHolder();

					out.setFeesid(resultSet.getInt("feesid"));
					out.setClassid(resultSet.getInt("classid"));
					out.setClassname(resultSet.getString("classname"));
					out.setTitle(resultSet.getString("title"));
					out.setStartdate(resultSet.getString("startdate"));
					out.setEnddate(resultSet.getString("enddate"));

					out.setFeesamount(resultSet.getDouble("feesamount"));
					out.setStartdatemsg(resultSet.getString("startdatemsg"));
					out.setDeadlinemsg(resultSet.getString("deadlinemsg"));
					out.setLatefees(resultSet.getDouble("latefees"));

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
