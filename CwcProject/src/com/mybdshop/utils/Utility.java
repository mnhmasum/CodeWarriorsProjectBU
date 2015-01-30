package com.mybdshop.utils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Environment;
import android.util.Log;

public class Utility {
	public static boolean isEmailValid(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}
	
	public static void createFolder() {
		try{
			File folder = new File(Environment.getExternalStorageDirectory() + "/cwc");
			boolean success = true;
			if (!folder.exists()) {
			    success = folder.mkdir();
			}
		}catch(Exception e){
			Log.i("EXCEPTION","" + e.getMessage());
		}
	}
}
