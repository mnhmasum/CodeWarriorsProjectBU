package com.mybdshop.app;


import java.util.ArrayList;

import com.mybdshop.adapter.SubCategoryAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class SubCategoryActivity extends Activity implements OnClickListener, OnItemClickListener {
	private EditText edtTextEmail, edtTextPassword;
	private Button btnLogin, btnRegister;
	private Button imgViewBtnGallery;
	private Button imgViewBtnCamera;
	private ImageView imgViewPick;
	private ProgressDialog progressDialog;
	private Dialog imagePickDialog;
	private Uri mImageCaptureUri;
	private Spinner spinner1, spinner2;
	private static final int  PICK_FROM_CAMERA = 1;
	private static final int  PICK_FROM_GALLERY = 2;
	
	private ArrayList<String> arrayListSubCategory;
	private SubCategoryAdapter latestProductAdapter;
	private ListView subCategoryListView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_category);
		//addItemsOnSpinner2();
		initView();
		setListener();
		loadData();
	}
	
	@Override
	public void onClick(View v) {
		/*if (v.getId() == R.id.btn_update) {
		}*/
	}
	
	 private void setListener() {
		 subCategoryListView.setOnItemClickListener(this);
	}

	private void initView() {
     /*    edtTextFirstName = (EditText)view.findViewById(R.id.edit_text_first_name);
         edtTextLastName = (EditText)view.findViewById(R.id.edit_text_last_name);
         edtTextEmail = (EditText) view.findViewById(R.id.edit_text_email);
         edtTextPassword = (EditText)view.findViewById(R.id.edit_text_password);
         edtTextPasswordConfirm = (EditText)view.findViewById(R.id.edit_text_password_confirm);
         btnUpdate = (Button)view.findViewById(R.id.btn_update);*/
         subCategoryListView = (ListView) findViewById(R.id.list_view_latest_product);
 }
	
	private void loadData() {
		arrayListSubCategory = new ArrayList<String>();
		for (int i = 0; i <5; i++) {
			arrayListSubCategory.add("Hi" + i);
		}
		latestProductAdapter = new SubCategoryAdapter(SubCategoryActivity.this, arrayListSubCategory);
		subCategoryListView.setAdapter(latestProductAdapter);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(SubCategoryActivity.this, ProductViewActivity.class);
		intent.putExtra("position", arg2);
		startActivity(intent);
		
	}
	 
	
}
