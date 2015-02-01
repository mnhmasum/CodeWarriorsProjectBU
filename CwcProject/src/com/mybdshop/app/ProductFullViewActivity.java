package com.mybdshop.app;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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

import com.mybdshop.adapter.ViewPagerAdapter;
import com.mybdshop.adapter.ViewPagerFullAdapter;

public class ProductFullViewActivity extends Activity implements OnClickListener, OnItemSelectedListener {
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
	
	ViewPager viewPager;
	PagerAdapter adapter;
	int[] flag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_full_view);
		flag = new int[] { R.drawable.car, R.drawable.freezer,
				R.drawable.mobile, R.drawable.motorcycle,
				R.drawable.pendrive};
		
		
		int position = getIntent().getExtras().getInt("click_position");
		
		viewPager = (ViewPager) findViewById(R.id.pager);
		// Pass results to ViewPagerAdapter Class
		adapter = new ViewPagerFullAdapter(ProductFullViewActivity.this, flag);
		// Binds the Adapter to the ViewPager
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(position);
		//addItemsOnSpinner2();
		//initView();
		//setActionListener();
	}
	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_view_pick:
			break;
		case R.id.btn_register:
			Intent intent = new Intent(ProductFullViewActivity.this, RegistrationActivity.class);
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
