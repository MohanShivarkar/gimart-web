package helpers;

import javax.sql.DataSource;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

public class DBUtil {

	private static final String TAG = DBUtil.class.getCanonicalName();
	private static HikariDataSource dataSource;

	/****** Without Parameters **********/
	public static DataSource getDataSource() {

		try {
			// ******Working Region******
			dataSource = new HikariDataSource();
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");

			/*** Offline Server ***/
//			dataSource.setJdbcUrl(
//					"jdbc:mysql://localhost:3306/clazzup_management?autoReconnect=true&useSSL=false&noAccessToProcedureBodies=true");
//			dataSource.setUsername("root");
//			dataSource.setPassword("1234");

			/*** VPS ROOT Server ***/
//			dataSource.setJdbcUrl(
//					"jdbc:mysql://148.66.129.47:3307/clazzup_management?autoReconnect=true&useSSL=false&noAccessToProcedureBodies=true");
//			dataSource.setUsername("mohan");
//			dataSource.setPassword("Mohan@12345");

			// jdbc:mysql://151.106.40.94:3306/clazzup_management?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
			
			/*** Dedicated Server ***/
//			dataSource.setJdbcUrl(
//					"jdbc:mysql://151.106.40.94:3306/clazzup_management?autoReconnect=true&useSSL=false&noAccessToProcedureBodies=true");
//			dataSource.setUsername("clazzup_mohan");
//			dataSource.setPassword("MohanClazzup@2020");
			
			dataSource.setJdbcUrl(
					"jdbc:mysql://208.109.8.170:3306/gimart?autoReconnect=true&useSSL=false&noAccessToProcedureBodies=true");
			dataSource.setUsername("gimartuser");
			dataSource.setPassword("Gimart@2021");

			/*** VPS Server ***/
			// dataSource.setJdbcUrl("jdbc:mysql://148.66.129.47:3307/clazzup_lemer?autoReconnect=true&useSSL=false");
			// dataSource.setUsername("clazzup_lemeravinash");
			// dataSource.setPassword("Lemer@3115");

			/*** My Server ***/
//			dataSource.setJdbcUrl("jdbc:mysql://148.72.213.157:3306/clazzup_management?autoReconnect=true&useSSL=false&noAccessToProcedureBodies=true&useUnicode=yes&characterEncoding=UTF-8");
//			dataSource.setUsername("mandroid001");
//			dataSource.setPassword("mandroid001@4321");

			dataSource.setMinimumIdle(100);
			dataSource.setMaximumPoolSize(4);
			dataSource.setLoginTimeout(3);
			dataSource.setMinimumIdle(0);
			dataSource.setIdleTimeout(30000);
			dataSource.setLeakDetectionThreshold(60000);
			dataSource.setConnectionTimeout(60000);
			dataSource.addDataSourceProperty("cachePrepStmts", true);
			dataSource.addDataSourceProperty("prepStmtCacheSize", 250);
			dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
			dataSource.addDataSourceProperty("useServerPrepStmts", true);
			dataSource.setAutoCommit(false);
			// *****end working region*****

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataSource;
	}

	/****** With Parameters **********/
	public static DataSource getDataSource(String JdbcUrl, String Username, String Password) {

		try {
			// ******Working Region******
			dataSource = new HikariDataSource();

			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dataSource.setJdbcUrl(JdbcUrl);
			dataSource.setUsername(Username);
			dataSource.setPassword(Password);
			dataSource.setMinimumIdle(2);
			dataSource.setMaximumPoolSize(20);
			dataSource.setIdleTimeout(30000);
			dataSource.setLeakDetectionThreshold(60000);
			dataSource.setConnectionTimeout(60000);
			dataSource.addDataSourceProperty("cachePrepStmts", true);
			dataSource.addDataSourceProperty("prepStmtCacheSize", 250);
			dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
			dataSource.addDataSourceProperty("useServerPrepStmts", true);
			dataSource.setAutoCommit(false);
			// *****end working region*****

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataSource;
	}

	public static Connection openConnection() {
		Connection connection = null;
		try {
			// connection = dataSource.getConnection();
			connection = DBUtil.getDataSource().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Utility.showErrorMessage(TAG, e);
		}
		return connection;
	}

	public static Connection openConnection(String JdbcUrl, String Username, String Password) {
		Connection connection = null;
		try {
			// connection = dataSource.getConnection();
			connection = DBUtil.getDataSource(JdbcUrl, Username, Password).getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Utility.showErrorMessage(TAG, e);
		}
		return connection;
	}

	public static void closeConnection(Connection connection, PreparedStatement pstmt, ResultSet resultSet) {
		if (resultSet != null) {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					Utility.showErrorMessage(TAG, e);
				} // This will print exception to help better
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					Utility.showErrorMessage(TAG, e);
				} // This will print exception to help better
			}
		}
	}

	public static void closeConnection(Connection connection, CallableStatement callableStatement) {

		if (callableStatement != null) {
			try {
				callableStatement.close();
			} catch (SQLException e) {
				Utility.showErrorMessage(TAG, e);
			} // This will print exception to help better
		}

		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				Utility.showErrorMessage(TAG, e);
			} // This will print exception to help better
		}

	}

	public static void main(String[] args) {

		Connection connection = null;
		CallableStatement callableStatement = null;
		try {
			connection = DBUtil.getDataSource().getConnection();
			System.out.println(connection);
			System.out.println("Connection Successfull..." + DBUtil.getDataSource());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection, callableStatement);
		}
	}

//	public static void main(String[] args) {
//
//		try {
//			Connection connection = DBUtil.getDataSource("jdbc:mysql://localhost:3306/smartoplus","root","1234").getConnection();
//			System.out.println(connection);
//			System.out.println("Connection Successfull...");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	public static void main(String[] args) {
//
//		try {
//			Connection connection = DBUtil.getDataSource("jdbc:mysql://148.72.213.157:3306/smartoplus","mandroid001","mandroid001@4321").getConnection();
//			System.out.println(connection);
//			System.out.println("Connection Successfull...");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
