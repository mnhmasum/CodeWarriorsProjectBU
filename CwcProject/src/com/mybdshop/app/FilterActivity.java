package com.mybdshop.app;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.BitmapAjaxCallback;
import com.apps.customview.DialogController;

public class FilterActivity extends Activity implements OnClickListener, OnItemSelectedListener {
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		addItemsOnSpinner2();
		//initView();
		//setActionListener();
	}
	
	public void addItemsOnSpinner2() {
		 
		spinner2 = (Spinner) findViewById(R.id.spinner1);
		spinner2.setOnItemSelectedListener(this);
		List<String> list = new ArrayList<String>();
		list.add("list 1");
		list.add("list 2");
		list.add("list 3");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		spinner2.setAdapter(dataAdapter);
	  }
	
	private void setActionListener() {
		imgViewPick.setOnClickListener(this);
	}

	private void initView() {
		imgViewPick = (ImageView) findViewById(R.id.image_view_pick);
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_view_pick:
			break;
		case R.id.btn_register:
			Intent intent = new Intent(FilterActivity.this, RegistrationActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
		Toast.makeText(parent.getContext(), "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
	  }
	 
	  @Override
	  public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	  }
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
}
