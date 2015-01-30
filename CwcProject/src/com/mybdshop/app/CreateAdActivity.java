package com.mybdshop.app;


import java.io.File;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.BitmapAjaxCallback;
import com.apps.customview.DialogController;

public class CreateAdActivity extends Activity implements OnClickListener{
	private EditText edtTextEmail, edtTextPassword;
	private Button btnLogin, btnRegister;
	private Button imgViewBtnGallery;
	private Button imgViewBtnCamera;
	private ImageView imgViewPick;
	private ProgressDialog progressDialog;
	private Dialog imagePickDialog;
	private Uri mImageCaptureUri;
	private static final int  PICK_FROM_CAMERA = 1;
	private static final int  PICK_FROM_GALLERY = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_ad);
		initView();
		setActionListener();
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
			pickImage();
			break;
		case R.id.btn_register:
			Intent intent = new Intent(CreateAdActivity.this, RegistrationActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	private void pickImage() {
		imagePickDialog = new DialogController(this).customDialog();
		imgViewBtnGallery = (Button) imagePickDialog.findViewById(R.id.btn_post_gallary);
		imgViewBtnCamera = (Button) imagePickDialog.findViewById(R.id.btn_post_cam);
		imgViewBtnCamera.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					System.gc();
					Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "/cwc/" + "ad_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
					intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
					startActivityForResult(intent, PICK_FROM_CAMERA);
					
				} catch (ActivityNotFoundException e) {
					e.printStackTrace();
				}
				
				imagePickDialog.dismiss();
			}
		});
		
		imgViewBtnGallery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/*Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);
				*/
				startActivity(new Intent(CreateAdActivity.this, GalleryActivity.class));
				imagePickDialog.dismiss();
			}
		});
		imagePickDialog.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (resultCode != CreateAdActivity.RESULT_OK)
			return;
		
		Log.i("path", "data " + data);
		
		String  selectedImagePath = null;
		AQuery aq = new AQuery(this);
		BitmapAjaxCallback bmCallBack = new BitmapAjaxCallback();
		
		switch (requestCode) {
			case PICK_FROM_CAMERA:
				selectedImagePath = mImageCaptureUri.getPath().toString();
				bmCallBack.url(selectedImagePath).targetWidth(100).rotate(true);
				bmCallBack.memCache(true);
				bmCallBack.fileCache(true);
				aq.id(imgViewPick).image(bmCallBack);
				break;
	
			case PICK_FROM_GALLERY:
				mImageCaptureUri = data.getData();
				selectedImagePath = mImageCaptureUri.getPath().toString();
				
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(mImageCaptureUri, filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

				// the path where the image is located is stored in string variable
				selectedImagePath = cursor.getString(columnIndex);
				cursor.close();
				
				Log.e("GALLERY IMAGE", "****" + selectedImagePath);
				
				bmCallBack.url(selectedImagePath).targetWidth(300).rotate(true);
				bmCallBack.memCache(true);
				bmCallBack.fileCache(true);
				aq.id(imgViewPick).image(bmCallBack);
			
				break;
	
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
}
