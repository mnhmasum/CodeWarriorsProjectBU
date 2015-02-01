package com.mybdshop.app;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.mybdshop.adapter.SubCategoryAdapter;
import com.mybdshop.appinfo.AppController;
import com.mybdshop.appinfo.SubCategoryData;

public class SubCategoryActivity extends Activity implements OnClickListener, OnItemClickListener {
	private ProgressDialog progressDialog;
	private SubCategoryAdapter latestProductAdapter;
	private ListView subCategoryListView;
	private TextView txtViewSubCatLabel;
	int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_category_sub);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		initView();
		setListener();
		loadData();
	}
	
	@Override
	public void onClick(View v) {
		
	}
	
	 private void setListener() {
		 subCategoryListView.setOnItemClickListener(this);
	}

	private void initView() {
         subCategoryListView = (ListView) findViewById(R.id.list_view_latest_product);
         txtViewSubCatLabel  = (TextView) findViewById(R.id.txt_view_sub_cat_label);
         
	}
	
	private void loadData() {
		try {
			position = getIntent().getExtras().getInt("position");
			txtViewSubCatLabel.setText("Sub category of: " + AppController.getInstance().getArrayListCategory().get(position).getCatTitle());
			SubCategoryData subCatforAll = new SubCategoryData();
			subCatforAll.setSubCatId(-1); // This -1 for all of category list without sub category
			subCatforAll.setCatTitle("All of " + AppController.getInstance().getArrayListCategory().get(position).getCatTitle());
			AppController.getInstance().getArrayListCategory().get(position).getArrayListSubCategory().add(0, subCatforAll);
			latestProductAdapter = new SubCategoryAdapter(SubCategoryActivity.this, AppController.getInstance().getArrayListCategory().get(position).getArrayListSubCategory());
			subCategoryListView.setAdapter(latestProductAdapter);
		} catch (Exception e) {
			
		}
		
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(SubCategoryActivity.this, ProductViewActivity.class);
		intent.putExtra("position", arg2);
		startActivity(intent);
		
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
	protected void onDestroy() {
		super.onDestroy();
		AppController.getInstance().getArrayListCategory().get(position).getArrayListSubCategory().remove(0);
	}
	
}
