package com.mybdshop.framents;


import java.util.ArrayList;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import com.mybdshop.adapter.SubCategoryAdapter;
import com.mybdshop.app.R;

public class FragmentSubCategory extends Fragment implements OnClickListener{
	private EditText edtTextFirstName, edtTextLastName, edtTextEmail,
			edtTextPassword, edtTextPasswordConfirm;
	private Button btnUpdate, btnRegister;
	private ListView categoryGridView;
	private ProgressDialog progressDialog;

	private ArrayList<String> arrayListSubCategory;
	private SubCategoryAdapter latestProductAdapter;

	public FragmentSubCategory() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_category, container, false);
		initView(view);
		setListener();
		loadData();
		return view;
	}
	
	@Override
	public void onClick(View v) {
		/*if (v.getId() == R.id.btn_update) {
		}*/
	}
	
	 private void setListener() {
		 //btnUpdate.setOnClickListener(this);
	}

	private void initView(View view) {
     /*    edtTextFirstName = (EditText)view.findViewById(R.id.edit_text_first_name);
         edtTextLastName = (EditText)view.findViewById(R.id.edit_text_last_name);
         edtTextEmail = (EditText) view.findViewById(R.id.edit_text_email);
         edtTextPassword = (EditText)view.findViewById(R.id.edit_text_password);
         edtTextPasswordConfirm = (EditText)view.findViewById(R.id.edit_text_password_confirm);
         btnUpdate = (Button)view.findViewById(R.id.btn_update);*/
         categoryGridView = (ListView) view.findViewById(R.id.list_view_latest_product);
 }
	
	private void loadData() {
		
		arrayListSubCategory = new ArrayList<String>();
		
		for (int i = 0; i <5; i++) {
			arrayListSubCategory.add("Hi" + i);
		}
		latestProductAdapter = new SubCategoryAdapter(getActivity(), arrayListSubCategory);
		categoryGridView.setAdapter(latestProductAdapter);
	}

}
