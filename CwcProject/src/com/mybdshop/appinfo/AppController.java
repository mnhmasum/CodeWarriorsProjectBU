package com.mybdshop.appinfo;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mybdshop.datamodel.LatestProductData;
import com.mybdshop.datamodel.ProductsData;
import com.mybdshop.datamodel.UserInfoData;

public class AppController {
	public static AppController instance;
	private UserInfoData userInfo;
	private ArrayList<LatestProductData> latestProductList;
	private ArrayList<CategoryData> arrayListCategory;
	private ArrayList<ProductsData> listProductData;
	
	public UserInfoData getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoData userInfo) {
		this.userInfo = userInfo;
	}

	public static AppController getInstance() {
		if (instance == null) {
			instance = new AppController();
		}
		
		return instance;
	}

	public static void destroyInstance() {
		instance = null;
	}

	public void hideKeyboard(Context context, View v) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	public void openInternetSettingsActivity(final Context context) {
		final AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle("Internet Problem");
		alert.setMessage("No internet connection. Please connect to a network first.");
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
			}
		});

		alert.show();
	}
	
	public void openErrorDialog(String err_msg, Context context) {
		err_msg = Html.fromHtml(err_msg).toString();
		final AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setMessage(err_msg);
		alert.setCancelable(true);

		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				
			}
		});
		
		alert.show();
	}

	public ArrayList<LatestProductData> getLatestProductList() {
		return latestProductList;
	}

	public void setLatestProductList(ArrayList<LatestProductData> latestProductList) {
		this.latestProductList = latestProductList;
	}

	public ArrayList<CategoryData> getArrayListCategory() {
		return arrayListCategory;
	}

	public void setArrayListCategory(ArrayList<CategoryData> arrayListCategory) {
		this.arrayListCategory = arrayListCategory;
	}

	public ArrayList<ProductsData> getListProductData() {
		return listProductData;
	}

	public void setListProductData(ArrayList<ProductsData> listProductData) {
		this.listProductData = listProductData;
	}

}
