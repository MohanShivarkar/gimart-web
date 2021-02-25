package com.api.gimart.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.api.gimart.model.appversions.AppversionHolder;
import com.api.gimart.model.appversions.AppversionsListResponse;
import com.api.gimart.util.DBUtil;
import com.api.gimart.util.QueryMaker;
import com.api.gimart.util.Utility;

public class AppversionService {

	private static final String TAG = AppversionService.class.getCanonicalName();

	private static AppversionService instance;

	public static AppversionService getInstance() {
		if (instance == null) {
			instance = new AppversionService();
		}
		return instance;
	}

	public AppversionsListResponse GetAppVersionsLists(int paction, int puserid) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		AppversionsListResponse outResponse = new AppversionsListResponse();
		ArrayList<AppversionHolder> list = new ArrayList<>();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("generate_AppversionsFetch", 2));

			callableStatement.setInt(1, paction);
			callableStatement.setInt(2, puserid);

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
					AppversionHolder out = new AppversionHolder();

					out.setAppversionid(resultSet.getInt("appversionid"));
					out.setRoleid(resultSet.getInt("roleid"));
					out.setVersionno(resultSet.getString("versionno"));
					out.setDescription(resultSet.getString("description"));
					out.setIsactive(resultSet.getInt("isactive"));
					out.setCreated(resultSet.getString("created"));
					// out.setStatus(resultSet.getInt("status"));

					Utility.showMessage(TAG, resultSet.getString("status"));

					if (resultSet.getString("status") == null || resultSet.getString("status").equals(null)) {
						out.setStatus(0);
					} else {
						out.setStatus(resultSet.getInt("status"));
					}

					if (resultSet.getString("remark") == null || resultSet.getString("remark").equals(null)) {
						out.setRemark("none");
					} else {
						out.setRemark(resultSet.getString("remark"));
					}

					// out.setRemark(resultSet.getString("remark"));

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

	public static void main(String[] args) {
		AppversionService.getInstance().GetAppVersionsLists(2, 9);
	}

}
