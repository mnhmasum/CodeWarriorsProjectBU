package com.mybdshop.jsonparser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.mybdshop.appinfo.AppController;
import com.mybdshop.appinfo.CategoryData;
import com.mybdshop.appinfo.SubCategoryData;
import com.mybdshop.datamodel.ImageData;
import com.mybdshop.datamodel.ProductsData;
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
				userInfo.setUserFirstName(jsonDataArray.getJSONObject(i).getString("full_name"));
				userInfo.setUserEmail(jsonDataArray.getJSONObject(i).getString("email"));
				userInfo.setUserPassword(jsonDataArray.getJSONObject(i).getString("password"));
				AppController.getInstance().setUserInfo(userInfo);
				
			}
		}
		
		return status;

	}
	
	public static int parseCategoryData(String response) throws JSONException {
		JSONObject jsonRespObj = new JSONObject(response);
		int success = jsonRespObj.getInt("success");
		int status = jsonRespObj.getInt("status");
		Log.d("CATEORy", "-----" + success + "---- Status: " + status);
		
		if (success == 1 && status == 1) {
			ArrayList<CategoryData> arrayListCategery = new ArrayList<CategoryData>();
			JSONArray jsonDataArray = jsonRespObj.getJSONArray("data");
			for (int i = 0; jsonDataArray.length() > i; i++) {
				CategoryData catInfo = new CategoryData();
				catInfo.setCatId(jsonDataArray.getJSONObject(i).getInt("cat_id"));
				catInfo.setCatTitle(jsonDataArray.getJSONObject(i).getString("cat_name"));
				//catInfo.setCatItemImage(jsonDataArray.getJSONObject(i).getString("image_url"));
				
				int subCatLength = jsonDataArray.getJSONObject(i).getJSONArray("sub_category").length();
				ArrayList<SubCategoryData> arrayListSubCategory = new ArrayList<SubCategoryData>();
				for (int j = 0; j < subCatLength; j++) {
					SubCategoryData subCatData = new SubCategoryData();
					
					subCatData.setSubCatId(jsonDataArray.getJSONObject(i).getJSONArray("sub_category").getJSONObject(j).getInt("sub_cat_id"));
					subCatData.setCatTitle(jsonDataArray.getJSONObject(i).getJSONArray("sub_category").getJSONObject(j).getString("sub_cat_name"));
					Log.i("SUB_CAT_NAME", "***" + jsonDataArray.getJSONObject(i).getJSONArray("sub_category").getJSONObject(j).getString("sub_cat_name") );
					arrayListSubCategory.add(subCatData);
				}
				
				catInfo.setArrayListSubCategory(arrayListSubCategory);
				arrayListCategery.add(catInfo);
				
			}
			
			AppController.getInstance().setArrayListCategory(arrayListCategery);
			
		}
		
		return status;

	}
	
	public static int parseProductData(String response) throws JSONException {
		
		ArrayList<ProductsData> productDataList = new ArrayList<ProductsData>();
		JSONObject jsonRespObj = new JSONObject(response);
		int success = jsonRespObj.getInt("success");
		int status = jsonRespObj.getInt("status");
		Log.d("CATEORy", "-----" + success + "---- Status: " + status);
		
		if (success == 1 && status == 1) {
			ArrayList<CategoryData> arrayListCategery = new ArrayList<CategoryData>();
			JSONArray jsonDataArray = jsonRespObj.getJSONArray("data");
			JSONArray jsonProductsArray = jsonDataArray.getJSONObject(0).getJSONArray("products");
			
			for (int i = 0; jsonProductsArray.length() > i; i++) {
				Log.i("products_name" , "***" + jsonProductsArray.getJSONObject(i).getString("product_title") + "--" + jsonProductsArray.getJSONObject(i).getString("prod_id"));
				ProductsData productData = new ProductsData();
				productData.setProductId(Integer.parseInt(jsonProductsArray.getJSONObject(i).getString("prod_id")));
				productData.setProductTitle(jsonProductsArray.getJSONObject(i).getString("product_title"));
				productData.setProductPrice(jsonProductsArray.getJSONObject(i).getString("price"));
				productData.setProductCatId(jsonProductsArray.getJSONObject(i).getString("cat_id"));
				productData.setProductSubCatId(jsonProductsArray.getJSONObject(i).getString("cat_id"));
				
				ArrayList<ImageData> listImageData = new ArrayList<ImageData>();
				int imageCount = jsonProductsArray.getJSONObject(i).getJSONArray("image").length();
				for (int j = 0; j < imageCount; j++) {
					ImageData imageData = new ImageData();
					imageData.setImagePath(jsonProductsArray.getJSONObject(i).getJSONArray("image").getString(j));
					listImageData.add(imageData);
					
				}
				productData.setImageDataList(listImageData);
				productDataList.add(productData);
				//productData.setShareLink(jsonProductsArray.getJSONObject(i).getString("product_title"));
				//productData.setLocation("");
			}
			
			AppController.getInstance().setListProductData(productDataList);
		
		}
		
		return status;

	}
	
	
	
}
