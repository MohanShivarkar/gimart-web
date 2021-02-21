package com.api.gimart.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.api.gimart.model.ApiListResponse;
import com.api.gimart.model.ApiResponse;
import com.api.gimart.model.roles.RolesHolder;
import com.api.gimart.model.roles.RolesRequest;
import com.api.gimart.repository.RolesRepository;
import com.api.gimart.util.DBUtil;
import com.api.gimart.util.QueryMaker;
import com.api.gimart.util.Utility;

public class RoleService implements RolesRepository {

	private static final String TAG = RoleService.class.getCanonicalName();

	private static RoleService instance;

	public static RoleService getInstance() {
		if (instance == null) {
			instance = new RoleService();
		}
		return instance;
	}

	@Override
	public ApiListResponse GetRoles(int paction) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		ApiListResponse outResponse = new ApiListResponse();
		ArrayList<RolesHolder> list = new ArrayList<>();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("auth_RolesFetch", 1));

			callableStatement.setInt(1, paction);

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
					RolesHolder out = new RolesHolder();
					out.setRoleid(resultSet.getInt("roleid"));
					out.setRolename(resultSet.getString("rolename"));
					out.setIsactive(resultSet.getInt("isactive"));

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

	@Override
	public ApiResponse ManageRoles(RolesRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("auth_Roles", 9));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getProleid());
			callableStatement.setString(3, Utility.isNullOrEmpty(request.getProlename()) ? request.getProlename() : "");

			callableStatement.setInt(4, Utility.isNullOrEmpty(request.getPstatus()) ? request.getPstatus() : 0);

			callableStatement.setString(5, Utility.isNullOrEmpty(request.getPremark()) ? request.getPremark() : "");

			callableStatement.setInt(6, request.getPlogedinuserid());

			callableStatement.registerOutParameter(7, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(8, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(9, java.sql.Types.INTEGER);

			callableStatement.execute();
			connection.commit();

			int result = callableStatement.getInt(7);
			String message = callableStatement.getString(8);
			int haserror = callableStatement.getInt(9);

			Utility.showMessage(TAG, "" + new ApiResponse(result, message, haserror));

			return new ApiResponse(result, message, haserror);

		} catch (Exception e) {
			Utility.showErrorMessage(TAG, e);
			return new ApiResponse(0, e.getMessage(), 1);
		} finally {
			DBUtil.closeConnection(connection, callableStatement);
		}
	}

}
