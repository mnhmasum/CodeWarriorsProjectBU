package com.mybdshop.app;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.mybdshop.jsonparser.JsonParser;
import com.mybdshop.utils.ConstantValues;
import com.mybdshop.utils.Utility;

public class LoginActivity extends Activity implements OnClickListener{
	private EditText edtTextEmail, edtTextPassword;
	private Button btnLogin, btnRegister;
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		setActionListener();
	}

	private void setActionListener() {
		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
	}

	private void initView() {
		edtTextEmail = (EditText) findViewById(R.id.edit_text_user_name);
		edtTextPassword = (EditText) findViewById(R.id.edit_text_password);
		btnLogin = (Button) findViewById(R.id.btn_login);
		btnRegister = (Button) findViewById(R.id.btn_register);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			loginValidation();
			break;
		case R.id.btn_register:
			Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	
	//login input validation @saiful
	public void loginValidation() {
		boolean isValid = true;
		if (edtTextEmail.getText().toString().trim().length() > 0 && Utility.isEmailValid(edtTextEmail.getText().toString().trim())) {
			  edtTextEmail.setError(null);
			
		} else {
			edtTextEmail.setError("Please enter valid email");
			isValid = false;
		}
		
		if (edtTextPassword.getText().toString().trim().length() > 0) {
			edtTextPassword.setError(null);
		} else {
			edtTextPassword.setError("Please enter valid password");
			isValid = false;
		}
		
		if (isValid) {
			login();
		}
		
	}
	
	private void login() {
		RequestQueue queue = Volley.newRequestQueue(this);
		StringRequest myReq = new StringRequest(Method.POST,
				ConstantValues.API_URL,
				createMyReqSuccessListener(), createMyReqErrorListener()) {

			protected Map<String, String> getParams()
					throws com.android.volley.AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("email", edtTextEmail.getText().toString().trim());
				params.put("password", edtTextPassword.getText().toString().trim());
				params.put("func_id", "1002");
				return params;
			};
		};
        
        int socketTimeout = 30000;//30 seconds - change to what you want
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
            	Log.e("Library Test", "*****" + response);
            	try {
            		int status = JsonParser.parseUserData(response);
					if (status == 1) {
						startActivity(new Intent(LoginActivity.this, MainActivity.class));
						finish();
					
					} else {
						Toast.makeText(LoginActivity.this, "Username or password is not correct", Toast.LENGTH_SHORT).show();
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

}
