package com.mybdshop.app;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mybdshop.adapter.ProductsAdapter;
import com.mybdshop.datamodel.ProductsData;

public class ProductViewActivity extends Activity implements OnClickListener, OnItemClickListener {
	private EditText edtTextEmail, edtTextPassword;
	private Button btnLogin, btnRegister;
	private ListView productListView;
	private ProductsAdapter productAdapter;
	private ArrayList<ProductsData> productList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products_view);
		//addItemsOnSpinner2();
		initView();
		loadData();
		//setActionListener();
	}
	
	private void loadData() {
		int position = getIntent().getExtras().getInt("position");
		productList = new ArrayList<ProductsData>();
		
		for (int i = 0; i < 10; i++) {
			ProductsData productData = new ProductsData();
			productData.setProductTitle("Apple Mac Book V " + i);
			productList.add(productData);
		}
		
		productAdapter = new ProductsAdapter(ProductViewActivity.this, productList);
		productListView.setAdapter(productAdapter);
		
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

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
}
