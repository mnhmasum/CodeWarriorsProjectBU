package com.mybdshop.framents;


import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.mybdshop.adapter.CategoryGridAdapter;
import com.mybdshop.app.R;
import com.mybdshop.app.SubCategoryActivity;
import com.mybdshop.appinfo.CategoryData;

public class FragmentCategories extends Fragment implements OnClickListener, OnItemClickListener{
	private EditText edtTextFirstName, edtTextLastName, edtTextEmail,
			edtTextPassword, edtTextPasswordConfirm;
	private Button btnUpdate, btnRegister;
	private GridView categoryGridView;
	private ProgressDialog progressDialog;
	private ArrayList<CategoryData> arrayListCategory;
	private CategoryGridAdapter categoryGridAdapter; 

	public FragmentCategories() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_sub_category, container, false);
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
		 categoryGridView.setOnItemClickListener(this);
	}

	private void initView(View view) {
     /*    edtTextFirstName = (EditText)view.findViewById(R.id.edit_text_first_name);
         edtTextLastName = (EditText)view.findViewById(R.id.edit_text_last_name);
         edtTextEmail = (EditText) view.findViewById(R.id.edit_text_email);
         edtTextPassword = (EditText)view.findViewById(R.id.edit_text_password);
         edtTextPasswordConfirm = (EditText)view.findViewById(R.id.edit_text_password_confirm);
         btnUpdate = (Button)view.findViewById(R.id.btn_update);*/
         categoryGridView = (GridView) view.findViewById(R.id.grid_view_category);
 }
	
	private void loadData() {
		
		arrayListCategory = new ArrayList<CategoryData>();
		
		for (int i = 0; i <5; i++) {
			CategoryData categoryData = new CategoryData();
			categoryData.setCatTitle("hello" + i);
			arrayListCategory.add(categoryData);
		}
		categoryGridAdapter = new CategoryGridAdapter(getActivity(), arrayListCategory);
		categoryGridView.setAdapter(categoryGridAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		startActivity(new Intent(getActivity(), SubCategoryActivity.class));
		 
	}

}
