package com.mybdshop.adapter;

import java.util.ArrayList;

import com.mybdshop.app.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mybdshop.appinfo.AppController;
import com.mybdshop.datamodel.LatestProductData;
import com.mybdshop.datamodel.ProductsData;

public class ProductsAdapter extends BaseAdapter{
	
	private ArrayList<ProductsData> productList;
	private Context context;
	private LayoutInflater mInflater;
	
	public ProductsAdapter(Context context, ArrayList<ProductsData> hottestInfo) {
		this.productList = hottestInfo;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.productList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.productList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		final ViewHolder holder;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_product_view, null);

			holder = new ViewHolder();
			holder.txtViewProductTitle = (TextView) convertView.findViewById(R.id.txt_view_title);
			holder.txtViewProductPrice = (TextView) convertView.findViewById(R.id.txt_view_price);
			holder.txtViewTime = (TextView) convertView.findViewById(R.id.txt_view_time);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtViewProductTitle.setText(productList.get(position).getProductTitle());
		holder.txtViewProductPrice.setText(productList.get(position).getProductPrice());
		holder.txtViewTime.setText(productList.get(position).getUpLoadedDate());
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView txtViewProductTitle;
		TextView txtViewProductPrice;
		TextView txtViewTime;
		
		ImageView imgViewProduct;
	}

}
