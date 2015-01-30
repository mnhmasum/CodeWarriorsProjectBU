package com.mybdshop.framents;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import com.mybdshop.app.R;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mybdshop.appinfo.AppController;
import com.mybdshop.jsonparser.JsonParser;
import com.mybdshop.utils.Utility;

public class FragmentProfileUpdate extends Fragment implements OnClickListener{
	private EditText edtTextFirstName, edtTextLastName, edtTextEmail,
			edtTextPassword, edtTextPasswordConfirm;
	private Button btnUpdate, btnRegister;

	private ProgressDialog progressDialog;
	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";

	public FragmentProfileUpdate() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile_update, container, false);
		initView(view);
		setListener();
		loadData();
		return view;
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_update) {
			processUpdate();
		}
	}
	
	 private void setListener() {
		 btnUpdate.setOnClickListener(this);
	}

	private void initView(View view) {
         edtTextFirstName = (EditText)view.findViewById(R.id.edit_text_first_name);
         edtTextLastName = (EditText)view.findViewById(R.id.edit_text_last_name);
         edtTextEmail = (EditText) view.findViewById(R.id.edit_text_email);
         edtTextPassword = (EditText)view.findViewById(R.id.edit_text_password);
         edtTextPasswordConfirm = (EditText)view.findViewById(R.id.edit_text_password_confirm);
         btnUpdate = (Button)view.findViewById(R.id.btn_update);
 }
	
	private void loadData() {
		edtTextFirstName.setText(AppController.getInstance().getUserInfo().getUserFirstName());
		edtTextLastName.setText(AppController.getInstance().getUserInfo().getUserLastName());
		edtTextEmail.setText(AppController.getInstance().getUserInfo().getUserEmail());
	}
	
	public void processUpdate() {
		boolean isValid = true;
		if (edtTextFirstName.getText().toString().trim().length() > 0) {
			//Toast.makeText(getActivity(), "First Name is valid", Toast.LENGTH_SHORT).show();
			edtTextFirstName.setError(null);
		} else {
			edtTextFirstName.setError("Please enter your first name");
			isValid = false;
		}
		if (edtTextLastName.getText().toString().trim().length() > 0) {
			//Toast.makeText(getActivity(), "Last Name is valid", Toast.LENGTH_SHORT).show();
			edtTextLastName.setError(null);
		} else {
			edtTextLastName.setError("Please enter your last name");
			isValid = false;
		}
		if (edtTextEmail.getText().toString().trim().length() > 0 && Utility.isEmailValid(edtTextEmail.getText().toString().trim())) {
			//Toast.makeText(getActivity(), "Email is valid", Toast.LENGTH_SHORT).show();
			edtTextEmail.setError(null);
		} else {
			edtTextEmail.setError("Please enter valid email");
			isValid = false;
		}
		
		if (edtTextPasswordConfirm.getText().toString().trim().length() > 0) {
			AppController.getInstance().getUserInfo().setUserPassword(edtTextPasswordConfirm.getText().toString().trim());
			edtTextPasswordConfirm.setError(null);
		} 
		
		if (isValid) {
			update();
		}
	}
	
	//update user information
	private void update() {
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		StringRequest myReq = new StringRequest(Method.POST,
				"http://radioonlinelive.com/services/api.php",
				createMyReqSuccessListener(), createMyReqErrorListener()) {

			protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("func_id", "1003");
				params.put("id", Integer.toString(AppController.getInstance().getUserInfo().getUserId()));
				params.put("first_name", edtTextFirstName.getText().toString().trim() );
				params.put("last_name", edtTextLastName.getText().toString().trim());
				params.put("email", edtTextEmail.getText().toString().trim());
				params.put("password", AppController.getInstance().getUserInfo().getUserPassword());
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
            	Log.e("Update response", "*****" + response);
            	try {
            		int status = JsonParser.parseUserData(response);
					if (status == 1) {
						//startActivity(new Intent(getActivity(), MainActivity.class));
						Toast.makeText(getActivity(), "Profile has been updated", Toast.LENGTH_SHORT).show();
						edtTextPassword.setText("");
						edtTextPasswordConfirm.setText("");
					} else if (status == 2) {
						Toast.makeText(getActivity(), "Email address aready exist", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
					}
					
				} catch (JSONException e) {
					Toast.makeText(getActivity(), "Server response error", Toast.LENGTH_SHORT).show();
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


}
