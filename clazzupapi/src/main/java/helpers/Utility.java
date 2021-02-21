package helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Utility {

	public static boolean isNullOrEmpty(String str) {
		if (str != null && !str.isEmpty())
			return true;
		return false;
	}

	public static boolean isNullOrEmpty(int number) {
		if (number > 0)
			return true;
		return false;
	}

	public static boolean isNullOrEmpty(double number) {
		if (number > 0)
			return true;
		return false;
	}

	public static void showMessage(String TAG, String data) {
		System.out.println(TAG + ":" + data);
	}

	public static void showMessage(String TAG, int data) {
		System.out.println(TAG + ":" + data);
	}

	public static void showErrorMessage(String TAG, Exception e) {
		System.out.println(TAG + ":" + e);
	}

	public static void showErrorMessage(String TAG, String funcName, Exception e) {
		System.out.println(TAG + " :funcName():" + e);
	}

	public static String hashPassword(String plainTextPassword) {
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}

	public static boolean checkPass(String plainTextPassword, String hashedPassword) {

		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// Add password bytes to digest
			md.update(plainTextPassword.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest();
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();

		}

		if (generatedPassword.equals(hashedPassword))
			return true;
		else
			return false;

	}

	public static String CreateHashPassword(String plainTextPassword) {

		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// Add password bytes to digest
			md.update(plainTextPassword.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest();
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println(generatedPassword);

		return generatedPassword;
	}

	public static String GetRandomNumberString() {
		// It will generate 6 digit random Number.
		// from 0 to 999999
		Random rnd = new Random();
		int number = rnd.nextInt(999999);

		// this will convert any number sequence into 6 character.
		return String.format("%06d", number);
	}
	
	
	public static String generateRandomString(int count) {

		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = new Random().nextInt(9);
			builder.append(character);
		}
		return builder.toString();
	}

	public static String GetCurrentDateTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMMyyyyHHmmss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
		return dtf.format(now);
	}
	
//	public static void main(String[] args) {
//		System.out.println(Utility.generateRandomString(10));
//	}

}
