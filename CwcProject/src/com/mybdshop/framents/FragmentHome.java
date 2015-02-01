package com.mybdshop.framents;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mybdshop.app.ProductDetailsActivity;
import com.mybdshop.app.R;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mybdshop.adapter.CategoryGridAdapter;
import com.mybdshop.adapter.LatestProductAdapter;
import com.mybdshop.adapter.ProductsAdapter;
import com.mybdshop.app.CreateAdActivity;
import com.mybdshop.appinfo.AppController;
import com.mybdshop.datamodel.LatestProductData;
import com.mybdshop.jsonparser.JsonParser;
import com.mybdshop.utils.ConstantValues;

public class FragmentHome extends Fragment implements OnClickListener, OnItemClickListener{
	private EditText edtTextFirstName, edtTextLastName, edtTextEmail,
			edtTextPassword, edtTextPasswordConfirm;
	private Button btnCreateAd, btnRegister;
	private ListView latestProductListView;
	private ProgressDialog progressDialog;
	private ArrayList<LatestProductData> arrayListLatestData;
	private ProductsAdapter latestProductAdapter;

	public FragmentHome() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		initView(view);
		setListener();
		loadData();
		loadProducts();
		return view;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_create_ad:
				startActivity(new Intent(getActivity(), CreateAdActivity.class));
			break;

		default:
			break;
		}
		
	}
	
	 private void setListener() {
		 btnCreateAd.setOnClickListener(this);
		 latestProductListView.setOnItemClickListener(this);
	}

	private void initView(View view) {
     /*    edtTextFirstName = (EditText)view.findViewById(R.id.edit_text_first_name);
         edtTextLastName = (EditText)view.findViewById(R.id.edit_text_last_name);
         edtTextEmail = (EditText) view.findViewById(R.id.edit_text_email);
         edtTextPassword = (EditText)view.findViewById(R.id.edit_text_password);
         edtTextPasswordConfirm = (EditText)view.findViewById(R.id.edit_text_password_confirm);*/
		
         btnCreateAd = (Button)view.findViewById(R.id.btn_create_ad);
         latestProductListView = (ListView) view.findViewById(R.id.list_view_latest_product);
 }
	
	private void loadData() {
		
		arrayListLatestData = new ArrayList<LatestProductData>();
		
		for (int i = 0; i <5; i++) {
			LatestProductData latestProductData = new LatestProductData();
			latestProductData.setProductTitle("hello" + i);
			arrayListLatestData.add(latestProductData);
		}
		
		AppController.getInstance().setLatestProductList(arrayListLatestData);
		
	}
	
	private void loadProducts() {
		RequestQueue queue = Volley.newRequestQueue(getActivity());
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
        
        progressDialog = new ProgressDialog(getActivity());
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
						latestProductAdapter = new ProductsAdapter(getActivity(), AppController.getInstance().getListProductData());
						latestProductListView.setAdapter(latestProductAdapter);
						
					} else {
						Toast.makeText(getActivity(), getResources().getString(R.string.data_not_found), Toast.LENGTH_SHORT).show();
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
            	Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.connection_timeout), Toast.LENGTH_SHORT).show();
            }
        };
    }
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		startActivity(new Intent(getActivity(), ProductDetailsActivity.class));
	}
	
	

}
