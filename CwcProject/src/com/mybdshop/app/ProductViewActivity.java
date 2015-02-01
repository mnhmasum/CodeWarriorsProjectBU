package com.mybdshop.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mybdshop.adapter.ProductsAdapter;
import com.mybdshop.appinfo.AppController;
import com.mybdshop.datamodel.ProductsData;
import com.mybdshop.jsonparser.JsonParser;
import com.mybdshop.utils.ConstantValues;

public class ProductViewActivity extends Activity implements OnClickListener, OnItemClickListener {
	private EditText edtTextEmail, edtTextPassword;
	private Button btnLogin, btnRegister;
	private ListView productListView;
	private ProductsAdapter productAdapter;
	private ArrayList<ProductsData> productList;
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products_view);
		initView();
		loadData();
		loadProducts();
		setActionListener();
	}
	
	private void loadData() {
		int position = getIntent().getExtras().getInt("position");
		productList = new ArrayList<ProductsData>();
		
	}

	private void setActionListener() {
		productListView.setOnItemClickListener(this);
	}

	private void initView() {
		productListView = (ListView) findViewById(R.id.list_view_products);
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_view_pick:
			break;
		case R.id.btn_register:
			Intent intent = new Intent(ProductViewActivity.this, RegistrationActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	private void loadProducts() {
		RequestQueue queue = Volley.newRequestQueue(this);
		StringRequest myReq = new StringRequest(Method.POST, ConstantValues.API_PRODUCT_URL, createMyReqSuccessListener(), createMyReqErrorListener()) {
			protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("func_id", "0");
				return params;
			};
		};
        
        int socketTimeout = 10000;//30 seconds - change to what you want
    	RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    	myReq.setRetryPolicy(policy);
        queue.add(myReq);
        
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
	}
	
	private Response.Listener<String> createMyReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            	progressDialog.dismiss();
            	Log.i("Product Response Test", "*****" + response);
            	try {
            		int status = JsonParser.parseProductData(response);
					if (status == 1) {
						productAdapter = new ProductsAdapter(ProductViewActivity.this, AppController.getInstance().getListProductData());
						productListView.setAdapter(productAdapter);
						
					} else {
						Toast.makeText(ProductViewActivity.this, getResources().getString(R.string.data_not_found), Toast.LENGTH_SHORT).show();
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
            	Toast.makeText(ProductViewActivity.this, getResources().getString(R.string.connection_timeout), Toast.LENGTH_SHORT).show();
            }
        };
    }
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(ProductViewActivity.this, ProductDetailsActivity.class);
		intent.putExtra("position", arg2);
		// TODO Auto-generated method stub
		startActivity(intent);
	}
	
}
