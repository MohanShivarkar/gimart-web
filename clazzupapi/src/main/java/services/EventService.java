package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import helpers.DBUtil;
import helpers.QueryMaker;
import helpers.Utility;
import models.events.EventListResponse;
import models.events.EventsHolder;

public class EventService {

	private static final String TAG = EventService.class.getCanonicalName();

	private static EventService instance;

	public static EventService getInstance() {
		if (instance == null) {
			instance = new EventService();
		}
		return instance;
	}

	public EventListResponse GetEvents(int paction, String ptablename) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;

		EventListResponse outResponse = new EventListResponse();
		ArrayList<EventsHolder> list = new ArrayList<>();

		try {

			connection = DBUtil.openConnection();
			callableStatement = connection.prepareCall(QueryMaker.createQuery("evt_DetailsFetch", 2));

			callableStatement.setInt(1, paction);
			callableStatement.setString(2, ptablename);

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
					EventsHolder out = new EventsHolder();
					out.setEvtid(resultSet.getInt("evtid"));
					out.setTablename(resultSet.getString("tablename"));
					out.setCreated(resultSet.getString("created"));
					out.setStatus(resultSet.getInt("status"));

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
