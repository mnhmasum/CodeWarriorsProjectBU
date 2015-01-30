package com.mybdshop.framents;


import java.util.ArrayList;

import com.mybdshop.app.R;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mybdshop.adapter.LatestProductAdapter;
import com.mybdshop.app.CreateAdActivity;
import com.mybdshop.appinfo.AppController;
import com.mybdshop.datamodel.LatestProductData;

public class FragmentHome extends Fragment implements OnClickListener{
	private EditText edtTextFirstName, edtTextLastName, edtTextEmail,
			edtTextPassword, edtTextPasswordConfirm;
	private Button btnCreateAd, btnRegister;
	private ListView latestProductListView;
	private ProgressDialog progressDialog;
	private ArrayList<LatestProductData> arrayListLatestData;
	private LatestProductAdapter latestProductAdapter; 

	public FragmentHome() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		initView(view);
		setListener();
		loadData();
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
		latestProductAdapter = new LatestProductAdapter(getActivity(), AppController.getInstance().getLatestProductList());
		latestProductListView.setAdapter(latestProductAdapter);
	}

}
