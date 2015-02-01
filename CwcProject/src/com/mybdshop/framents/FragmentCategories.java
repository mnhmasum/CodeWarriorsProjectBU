package com.mybdshop.framents;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mybdshop.adapter.CategoryGridAdapter;
import com.mybdshop.app.LoginActivity;
import com.mybdshop.app.MainActivity;
import com.mybdshop.app.R;
import com.mybdshop.app.SubCategoryActivity;
import com.mybdshop.appinfo.AppController;
import com.mybdshop.appinfo.CategoryData;
import com.mybdshop.jsonparser.JsonParser;
import com.mybdshop.utils.ConstantValues;

public class FragmentCategories extends Fragment implements OnClickListener, OnItemClickListener{
	
	private GridView categoryGridView;
	private ProgressDialog progressDialog;
	private ArrayList<CategoryData> arrayListCategory;
	private CategoryGridAdapter categoryGridAdapter; 

	public FragmentCategories() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_category, container, false);
		initView(view);
		setListener();
		loadData();
		loadCategory();
		return view;
	}
	
	@Override
	public void onClick(View v) {
		/*if (v.getId() == R.id.btn_update) {
		}*/
	}
	
	 private void setListener() {
		 categoryGridView.setOnItemClickListener(this);
	}

	private void initView(View view) {
         categoryGridView = (GridView) view.findViewById(R.id.grid_view_category);
 }
	
	private void loadData() {
		
	}
	
	private void loadCategory() {
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		StringRequest myReq = new StringRequest(Method.POST, ConstantValues.API_CAT_URL, createMyReqSuccessListener(), createMyReqErrorListener()) {
			protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("func_id", "0");
				return params;
			};
		};
        
        int socketTimeout = 30000;//30 seconds - change to what you want
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
            	Log.i("Category Response Test", "*****" + response);
            	try {
            		int status = JsonParser.parseCategoryData(response);
					if (status == 1) {
						categoryGridAdapter = new CategoryGridAdapter(getActivity(), AppController.getInstance().getArrayListCategory());
						categoryGridView.setAdapter(categoryGridAdapter);
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
            }
        };
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(getActivity(), SubCategoryActivity.class);
		intent.putExtra("position", arg2);
		startActivity(intent);
		 
	}

}
