package com.mybdshop.appinfo;

import java.util.ArrayList;

public class CategoryData {
	private int catId;
	private int subCatId;
	private String catTitle;
	private String catItemImage;
	private ArrayList<SubCategoryData> arrayListSubCategory;
	
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public int getSubCatId() {
		return subCatId;
	}
	public void setSubCatId(int subCatId) {
		this.subCatId = subCatId;
	}
	public String getCatTitle() {
		return catTitle;
	}
	public void setCatTitle(String catTitle) {
		this.catTitle = catTitle;
	}
	public String getCatItemImage() {
		return catItemImage;
	}
	public void setCatItemImage(String catItemImage) {
		this.catItemImage = catItemImage;
	}
	public ArrayList<SubCategoryData> getArrayListSubCategory() {
		return arrayListSubCategory;
	}
	public void setArrayListSubCategory(ArrayList<SubCategoryData> arrayListSubCategory) {
		this.arrayListSubCategory = arrayListSubCategory;
	}



}
