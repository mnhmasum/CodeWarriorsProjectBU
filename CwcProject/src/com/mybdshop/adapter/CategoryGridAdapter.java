package com.mybdshop.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mybdshop.app.R;
import com.mybdshop.appinfo.AppController;
import com.mybdshop.appinfo.CategoryData;

public class CategoryGridAdapter extends BaseAdapter {

	private ArrayList<CategoryData> categoryInfoList;
	private LayoutInflater mInflater;
	private Context context;

	public CategoryGridAdapter(Context context, ArrayList<CategoryData> categoryInfoList) {
		this.context = context;
		this.categoryInfoList = categoryInfoList;
	}

	@Override
	public int getCount() {
		Log.i("GET SIZE", "----" + categoryInfoList.size());
		return categoryInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		return categoryInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.grid_category, null);

			holder = new ViewHolder();

			holder.imageView = (ImageView) convertView.findViewById(R.id.img_view_gallery_single);
			holder.txtViewCatTitle = (TextView) convertView.findViewById(R.id.txt_view_content_number);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtViewCatTitle.setText(AppController.getInstance().getArrayListCategory().get(position).getCatTitle());
		/*if (InternetConnectivity.isConnectedToInternet(context)) {
			new AQuery(context).id(holder.imageView).image(ConstantValues.FILE_BASE_URL+galleryInfoList.get(position).getAlbumImageUrl(), true, true, 0, R.drawable.no_image);
			
		} else {
			SidraPulseApp.getInstance().openDialogForInternetChecking(context);
		}*/
		
		return convertView;
	}

	static class ViewHolder {
		ImageView imageView;
		TextView txtViewCatTitle;
	}
	
}
