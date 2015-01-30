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

public class LatestProductAdapter extends BaseAdapter{
	
	private ArrayList<LatestProductData> latestProductList;
	private Context context;
	private LayoutInflater mInflater;
	
	public LatestProductAdapter(Context context, ArrayList<LatestProductData> hottestInfo) {
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
			convertView = mInflater.inflate(R.layout.row_latest_product, null);

			holder = new ViewHolder();
			holder.txtViewProductTitle = (TextView) convertView.findViewById(R.id.txt_view_title);
			holder.txtViewProductPrice = (TextView) convertView.findViewById(R.id.txt_view_price);
			holder.txtViewTime = (TextView) convertView.findViewById(R.id.txt_view_time);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtViewProductTitle.setText(AppController.getInstance().getLatestProductList().get(position).getProductTitle());
		holder.txtViewProductPrice.setText(AppController.getInstance().getLatestProductList().get(position).getProductPrice());
		holder.txtViewTime.setText(AppController.getInstance().getLatestProductList().get(position).getUpLoadedDate());
		return convertView;
	}
	
	static class ViewHolder {
		TextView txtViewProductTitle;
		TextView txtViewProductPrice;
		TextView txtViewTime;
		
		ImageView imgViewProduct;
	}

}
