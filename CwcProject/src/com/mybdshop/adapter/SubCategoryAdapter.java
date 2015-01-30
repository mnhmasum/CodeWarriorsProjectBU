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

public class SubCategoryAdapter extends BaseAdapter{
	
	private ArrayList<String> latestProductList;
	private Context context;
	private LayoutInflater mInflater;
	
	public SubCategoryAdapter(Context context, ArrayList<String> hottestInfo) {
		this.latestProductList = hottestInfo;
		this.context = context;
				
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.latestProductList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.latestProductList.get(position);
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
			convertView = mInflater.inflate(R.layout.row_sub_category, null);

			holder = new ViewHolder();
			holder.txtViewSubCategoryName = (TextView) convertView.findViewById(R.id.txt_view_sub_cat_name);
			
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtViewSubCategoryName.setText(AppController.getInstance().getLatestProductList().get(position).getProductTitle());
		return convertView;
	}
	
	static class ViewHolder {
		TextView txtViewSubCategoryName;
		TextView txtViewProductPrice;
		TextView txtViewTime;
		
		ImageView imgViewProduct;
	}

}
