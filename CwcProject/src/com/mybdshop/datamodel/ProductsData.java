package com.mybdshop.datamodel;

import java.util.ArrayList;

public class ProductsData {
	private int productId;
	private String productCatId;
	private String productSubCatId;
	private String productTitle;
	private String productPrice;
	private String productBrand;
	private String uploadedDate;
	private String shareLink;
	private String location;
	private ArrayList<ImageData> imageDataList;
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductCatId() {
		return productCatId;
	}
	public void setProductCatId(String productCatId) {
		this.productCatId = productCatId;
	}
	public String getProductSubCatId() {
		return productSubCatId;
	}
	public void setProductSubCatId(String productSubCatId) {
		this.productSubCatId = productSubCatId;
	}
	public String getProductTitle() {
		return productTitle;
	}
	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductBrand() {
		return productBrand;
	}
	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
	public String getUpLoadedDate() {
		return uploadedDate;
	}
	public void setUpLoadedDate(String upLoadedDate) {
		this.uploadedDate = upLoadedDate;
	}
	public String getShareLink() {
		return shareLink;
	}
	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public ArrayList<ImageData> getImageDataList() {
		return imageDataList;
	}
	public void setImageDataList(ArrayList<ImageData> imageDataList) {
		this.imageDataList = imageDataList;
	}

}
