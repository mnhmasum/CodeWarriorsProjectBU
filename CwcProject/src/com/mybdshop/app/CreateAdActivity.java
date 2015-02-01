package com.mybdshop.app;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

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
import android.view.MenuItem;
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;
import com.androidquery.callback.BitmapAjaxCallback;
import com.apps.customview.DialogController;
import com.mybdshop.adapter.CategoryGridAdapter;
import com.mybdshop.appinfo.AppController;
import com.mybdshop.jsonparser.JsonParser;
import com.mybdshop.utils.ConstantValues;
import com.mybdshop.utils.GPSTracker;
import com.mybdshop.utils.Utility;

public class CreateAdActivity extends Activity implements OnClickListener, OnItemSelectedListener{
	private EditText edtTextEmail, edtTextPassword, edtTextProductTitle, edtTextDetails;
	private Button btnLogin, btnRegister;
	private Button imgViewBtnGallery;
	private Button imgViewBtnCamera;
	private Button btnCreateAd;
	private Spinner dropSpinSelectCategory;
	private Spinner dropSpinSubCategory;
	private ImageView imgViewPick;
	private ProgressDialog progressDialog;
	private Dialog imagePickDialog;
	private Uri mImageCaptureUri;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_GALLERY = 2;
	private String postLocation;
	private GPSTracker gps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_ad);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		initView();
		loadData();
		setActionListener();
	}


	private void setActionListener() {
		imgViewPick.setOnClickListener(this);
		btnCreateAd.setOnClickListener(this);
		dropSpinSelectCategory.setOnItemSelectedListener(this);
		dropSpinSubCategory.setOnItemSelectedListener(this);
	}

	private void initView() {
		imgViewPick = (ImageView) findViewById(R.id.image_view_pick);
		btnCreateAd = (Button) findViewById(R.id.btn_create_ad);
		
		edtTextProductTitle = (EditText) findViewById(R.id.text_view_title);
		edtTextDetails = (EditText) findViewById(R.id.text_view_description);
		
		dropSpinSelectCategory = (Spinner) findViewById(R.id.btn_select_cat);
		dropSpinSubCategory = (Spinner) findViewById(R.id.btn_subcat_select);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_select_cat:
			break;
		case R.id.image_view_pick:
			pickImage();
			break;
		case R.id.btn_create_ad:
			inputValidation();
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
	
	public void inputValidation() {
		boolean isValid = true;
		
		if (!dropSpinSelectCategory.getSelectedItem().toString().trim().equalsIgnoreCase("Select Category")) {
			
		} else {
			Toast.makeText(getApplicationContext(), "Please select a category", Toast.LENGTH_SHORT).show();
			isValid = false;
		}
		
		if (edtTextProductTitle.getText().toString().trim().length() > 0) {
			edtTextProductTitle.setError(null);
			
		} else {
			edtTextProductTitle.setError("Please enter ad title");
			isValid = false;
		}
		
		
		if (edtTextDetails.getText().toString().trim().length() > 0 ) {
			edtTextDetails.setError(null);
			
		} else {
			edtTextDetails.setError("Please enter details about your ad");
			isValid = false;
		}
	
		
		if (isValid) {
			
			if (AppController.getInstance().getUserInfo() == null) {
				Intent intent = new Intent(CreateAdActivity.this, LoginActivity.class);
				startActivity(intent);
				return;
			} 
			
			if (AppController.getInstance().getUserInfo().getUserEmail().length() == 0) {
				Intent intent = new Intent(CreateAdActivity.this, LoginActivity.class);
				startActivity(intent);
				return;
			}
			
			postAd();
		}
		
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
	
	private void loadData() {
		gps = new GPSTracker(CreateAdActivity.this);
		List<String> list = new ArrayList<String>();
		list.add("Select Category");
		list.add("Vehicles");
		list.add("Mobile");
		list.add("Computer");
		list.add("Book");
		list.add("Modem");
		list.add("Freeze");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		dropSpinSelectCategory.setAdapter(dataAdapter);
		
	}
	
	private void postAd() {
		RequestQueue queue = Volley.newRequestQueue(CreateAdActivity.this);
		StringRequest myReq = new StringRequest(Method.POST, ConstantValues.API_CAT_URL, createMyReqSuccessListener(), createMyReqErrorListener()) {
			protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("func_id", "1004");
				params.put("cat_id", "1");
				params.put("sub_cat_id", "1");
				params.put("lat", Double.toString(gps.getLatitude()));
				params.put("lon", Double.toString(gps.getLongitude()));
				params.put("product_title", edtTextDetails.getText().toString());
				params.put("brand", "Hewlet packard");
				params.put("image_name", "http://radioonlinelive.com/services/image/2.png, http://radioonlinelive.com/services/image/2.png");
				return params;
			};
		};
        
        int socketTimeout = 30000;//30 seconds - change to what you want
    	RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    	myReq.setRetryPolicy(policy);
        queue.add(myReq);
        
        progressDialog = new ProgressDialog(CreateAdActivity.this);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
	}
	
	private Response.Listener<String> createMyReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            	progressDialog.dismiss();
            	Log.i("Category Response Test", "*****" + response);
            	try {
            		int status = JsonParser.parseCategoryData(response);
					if (status == 1) {
						Toast.makeText(CreateAdActivity.this, getResources().getString(R.string.data_post_success), Toast.LENGTH_SHORT).show();
					
					} else {
						Toast.makeText(CreateAdActivity.this, getResources().getString(R.string.data_not_found), Toast.LENGTH_SHORT).show();
					} 
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
            	
            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        };
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
		}
		return true;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		if (arg2 == 0) {
			return;
		}
		
		List<String> list = new ArrayList<String>();
		list.add("Select Sub Category");
		
		if (arg2 == 1) {
			list.add("Motor Cycle");
			list.add("BUS");
			list.add("CNG");
			list.add("Rickshaw");
		} else if (arg2 == 2) {
			list.add("Samsung");
			list.add("Nokia");
			list.add("Symphony");
			list.add("Xperia");
			list.add("oppo");
			
		}
		else if(arg2==3)
		{
			list.add("Samsung");
			list.add("Accer");
			list.add("Dell");
			list.add("Sony");
			list.add("Apple");
		}
		
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		dropSpinSubCategory.setAdapter(dataAdapter);
	}
	
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
