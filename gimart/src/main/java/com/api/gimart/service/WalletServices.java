package com.api.gimart.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.api.gimart.model.ApiListResponse;
import com.api.gimart.model.ApiResponse;
import com.api.gimart.model.wallet.CoinRequest;
import com.api.gimart.model.wallet.CoinTransHolder;
import com.api.gimart.model.wallet.CoinTransListRequest;
import com.api.gimart.model.wallet.CoinTransRequest;
import com.api.gimart.model.wallet.CoinsHolder;
import com.api.gimart.model.wallet.generate.GenerateCoinHolder;
import com.api.gimart.model.wallet.generate.GenerateCoinListResponse;
import com.api.gimart.model.wallet.generate.GenerateCoinReqeust;
import com.api.gimart.repository.WalletRepository;
import com.api.gimart.util.DBUtil;
import com.api.gimart.util.QueryMaker;
import com.api.gimart.util.Utility;

public class WalletServices implements WalletRepository {

	private static final String TAG = WalletServices.class.getCanonicalName();

	private static WalletServices instance;

	public static WalletServices getInstance() {
		if (instance == null) {
			instance = new WalletServices();
		}
		return instance;
	}

	@Override
	public ApiListResponse GetCoints(int paction) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		ApiListResponse outResponse = new ApiListResponse();
		ArrayList<CoinsHolder> list = new ArrayList<>();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("master_CoinsFetch", 1));

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

					CoinsHolder out = new CoinsHolder();
					out.setCoinid(resultSet.getInt("coinid"));
					out.setPrice(resultSet.getInt("price"));
					out.setGeneratedcoins(resultSet.getInt("generatedcoins"));
					out.setRowcount(resultSet.getInt("rowcount"));
					out.setIsactive(resultSet.getInt("isactive"));
					out.setCreated(resultSet.getString("created"));
					out.setModified(resultSet.getString("modified"));

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
	public ApiResponse ManageCoins(CoinRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("master_Coins", 6));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPcoinid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPprice()) ? request.getPprice() : 0);

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

	public GenerateCoinListResponse GetGeneratedCoints(int paction, int pcoinid) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		GenerateCoinListResponse outResponse = new GenerateCoinListResponse();
		ArrayList<GenerateCoinHolder> list = new ArrayList<>();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("generate_CoinsFetch", 2));

			callableStatement.setInt(1, paction);
			callableStatement.setInt(2, pcoinid);

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

					GenerateCoinHolder out = new GenerateCoinHolder();

					out.setGcid(resultSet.getInt("gcid"));
					out.setCoinid(resultSet.getInt("coinid"));
					out.setQuantity(resultSet.getInt("quantity"));
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

	public ApiResponse GenerateCoins(GenerateCoinReqeust request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("generate_Coins", 7));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPgcid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPcoinid()) ? request.getPcoinid() : 0);
			callableStatement.setInt(4, Utility.isNullOrEmpty(request.getPquantity()) ? request.getPquantity() : 0);

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

	@Override
	public ApiResponse ManageTransferCoins(CoinTransRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		ApiResponse outResponse = new ApiResponse();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("trans_Coins", 8));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPtcid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPcoinid()) ? request.getPcoinid() : 0);

			callableStatement.setInt(4, Utility.isNullOrEmpty(request.getPquantity()) ? request.getPquantity() : 0);

			callableStatement.setInt(5, Utility.isNullOrEmpty(request.getPreceiverid()) ? request.getPreceiverid() : 0);

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

		return outResponse;
	}

	@Override
	public ApiListResponse GetCoinTrans(CoinTransListRequest request) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		ApiListResponse outResponse = new ApiListResponse();
		ArrayList<CoinTransHolder> list = new ArrayList<>();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("trans_CoinsFetch", 9));

			callableStatement.setInt(1, request.getPaction());
			callableStatement.setInt(2, request.getPcoinid());
			callableStatement.setInt(3, Utility.isNullOrEmpty(request.getPuserid()) ? request.getPuserid() : 0);

			callableStatement.setInt(4, Utility.isNullOrEmpty(request.getPreceiverid()) ? request.getPreceiverid() : 0);

			callableStatement.setInt(5, Utility.isNullOrEmpty(request.getPreceiverid()) ? request.getPreceiverid() : 0);

			callableStatement.setInt(6, Utility.isNullOrEmpty(request.getPstatus()) ? request.getPstatus() : 0);

			callableStatement.setInt(7, Utility.isNullOrEmpty(request.getPoffset()) ? request.getPoffset() : 0);

			callableStatement.setInt(8, Utility.isNullOrEmpty(request.getPlimit()) ? request.getPlimit() : 0);

			callableStatement.setString(9, Utility.isNullOrEmpty(request.getPsearch()) ? request.getPsearch() : "");

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

					CoinTransHolder out = new CoinTransHolder();

					out.setTcid(resultSet.getInt("tcid"));
					out.setCoinid(resultSet.getInt("coinid"));
					out.setQuantity(resultSet.getInt("quantity"));
					out.setReceiverid(resultSet.getInt("receiverid"));

					out.setReceiver(resultSet.getString("receiver"));
					out.setReceiverprofilepic(resultSet.getString("receiverprofilepic"));
					out.setSender(resultSet.getString("sender"));

					out.setSendcoins(resultSet.getInt("sendcoins"));
					out.setReceivedcoins(resultSet.getInt("receivedcoins"));

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

}
