package helpers;

public class DatabaseAccess {

	private static DatabaseAccess instance;

	public static DatabaseAccess getInstance() {
		if (instance == null) {
			instance = new DatabaseAccess();
		}
		return instance;
	}

	public void openDatabase() {

	}

	public void closeDatabase() {

	}

}