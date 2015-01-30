package com.mybdshop.app;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import com.mybdshop.app.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.mybdshop.utils.Utility;

public class RegistrationActivity extends Activity implements OnClickListener {
	private EditText edtTextFirstName, edtTextLastName, edtTextEmail, edtTextPassword;
	private Button btnLogin, btnRegister;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		initView();
		setActionListener();
	}

	private void setActionListener() {
		btnRegister.setOnClickListener(this);
	}

	private void initView() {
		edtTextFirstName = (EditText) findViewById(R.id.edit_text_first_name);
		edtTextLastName = (EditText) findViewById(R.id.edit_text_last_name);
		edtTextEmail = (EditText) findViewById(R.id.edit_text_email);
		edtTextPassword = (EditText) findViewById(R.id.edit_text_password);
		btnRegister = (Button) findViewById(R.id.btn_register);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			break;
		case R.id.btn_register: // Registration form Authentication
			registrationValidation(); 
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
		}
		return true;
	}

	public void registrationValidation() {
		boolean isValid = true;
		if (edtTextFirstName.getText().toString().trim().length() > 0) {
			//Toast.makeText(getApplicationContext(), "First Name is valid", Toast.LENGTH_SHORT).show();
			edtTextFirstName.setError(null);
			
		} else {
			edtTextFirstName.setError("Please enter your first name");
			isValid = false;
		}
		
		if (edtTextLastName.getText().toString().trim().length() > 0) {
			//Toast.makeText(getApplicationContext(), "Last Name is valid", Toast.LENGTH_SHORT).show();
			edtTextLastName.setError(null);
			
		} else {
			edtTextLastName.setError("Please enter your last name");
			isValid = false;
		}
		
		if (edtTextEmail.getText().toString().trim().length() > 0 && Utility.isEmailValid(edtTextEmail.getText().toString().trim())) {
			edtTextEmail.setError(null);
			
		} else {
			edtTextEmail.setError("Please enter valid email");
			isValid = false;
		}
		
		if (edtTextPassword.getText().toString().trim().length() > 0) {
			edtTextPassword.setError(null);
			//Toast.makeText(getApplicationContext(), "Password is Valid", Toast.LENGTH_SHORT).show();
		} else {
			edtTextPassword.setError("Please enter your password");
			isValid = false;
		}
		
		if (isValid) {
			registration();
		}
		
	}

	private void registration() {
		RequestQueue queue = Volley.newRequestQueue(this);

		StringRequest myReq = new StringRequest(Method.POST,
				"http://radioonlinelive.com/services/api.php",
				createMyReqSuccessListener(), createMyReqErrorListener()) {

			protected Map<String, String> getParams()
					throws com.android.volley.AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("func_id", "1001");
				params.put("first_name", edtTextFirstName.getText().toString().trim());
				params.put("last_name", edtTextLastName.getText().toString().trim());
				params.put("email", edtTextEmail.getText().toString().trim());
				params.put("password", edtTextPassword.getText().toString().trim());
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
            	Log.e("Api Registration", "*****" + response);
            	try {
            		int status = JsonParser.parseUserData(response);
					if (status == 1) {
						startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
						finish();
					
					} else if (status == 2) {
						Toast.makeText(RegistrationActivity.this, "Email address already exist", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(RegistrationActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
					}
					
				} catch (JSONException e) {
					Toast.makeText(RegistrationActivity.this, "Server response error", Toast.LENGTH_SHORT).show();
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
