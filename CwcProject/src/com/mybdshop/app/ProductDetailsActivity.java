package com.mybdshop.app;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
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

public class ProductDetailsActivity extends Activity implements OnClickListener, OnItemSelectedListener {
	private EditText edtTextEmail, edtTextPassword;
	private Button btnLogin, btnRegister, btnShare;
	private Button imgViewBtnGallery;
	private Button imgViewBtnCamera;
	private ImageView imgViewPick;
	private ProgressDialog progressDialog;
	private Dialog imagePickDialog;
	private Uri mImageCaptureUri;
	private Spinner spinner1, spinner2;
	private static final int  PICK_FROM_CAMERA = 1;
	private static final int  PICK_FROM_GALLERY = 2;
	private Button btnCall, btnMail;
	
	ViewPager viewPager;
	PagerAdapter adapter;
	int[] flag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_details);
		flag = new int[] { R.drawable.car, R.drawable.freezer,
				R.drawable.mobile, R.drawable.motorcycle,
				R.drawable.pendrive};
		btnCall = (Button)findViewById(R.id.btn_call);
		btnMail = (Button)findViewById(R.id.btn_mail);
		btnShare = (Button)findViewById(R.id.btn_share);
		viewPager = (ViewPager) findViewById(R.id.pager);
		// Pass results to ViewPagerAdapter Class
		adapter = new ViewPagerAdapter(ProductDetailsActivity.this, flag);
		// Binds the Adapter to the ViewPager
		viewPager.setAdapter(adapter);
		btnMail.setOnClickListener(this);
		btnCall.setOnClickListener(this);
		btnShare.setOnClickListener(this);
		//addItemsOnSpinner2();
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
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
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
			Intent intent = new Intent(ProductDetailsActivity.this, RegistrationActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_call:
			AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
			audioManager.setMode(AudioManager.MODE_IN_CALL);
			audioManager.setSpeakerphoneOn(true);
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setPackage("com.android.phone");
			callIntent.setData(Uri.parse("tel:01759495135"));
			startActivity(callIntent);
			break;
		case R.id.btn_mail:
			Intent smsIntent = new Intent(Intent.ACTION_VIEW);
			smsIntent.setType("vnd.android-dir/mms-sms");
			smsIntent.putExtra("address", "12125551212");
			smsIntent.putExtra("sms_body","Body of Message");
			startActivity(smsIntent);
			break;
		case R.id.btn_share:
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.setType("text/plain");
			shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Its Awesome");
			shareIntent.putExtra(Intent.EXTRA_TEXT,  "I love it  http://www.akhoni.com/champions.html");
			startActivity(Intent.createChooser(shareIntent, "Share"));
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
