package com.mybdshop.jsonparser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.mybdshop.appinfo.AppController;
import com.mybdshop.datamodel.UserInfoData;


public class JsonParser {
	
	public static int parseUserData(String response) throws JSONException {
		JSONObject jsonRespObj = new JSONObject(response);
		int success = jsonRespObj.getInt("success");
		int status = jsonRespObj.getInt("status");
		Log.d("SUCCESS", "-----" + success + "---- Status: " + status);
		if (success == 1 && status == 1) {
			JSONArray jsonDataArray = jsonRespObj.getJSONArray("data");
			for (int i = 0; jsonDataArray.length() > i; i++) {
				UserInfoData userInfo = new UserInfoData();
				userInfo.setUserId(jsonDataArray.getJSONObject(i).getInt("id"));
				userInfo.setUserFirstName(jsonDataArray.getJSONObject(i).getString("first_name"));
				userInfo.setUserLastName(jsonDataArray.getJSONObject(i).getString("last_name"));
				userInfo.setUserEmail(jsonDataArray.getJSONObject(i).getString("email"));
				userInfo.setUserPassword(jsonDataArray.getJSONObject(i).getString("password"));
				AppController.getInstance().setUserInfo(userInfo);
				
			}
		}
		
		return status;

	}
	
}
