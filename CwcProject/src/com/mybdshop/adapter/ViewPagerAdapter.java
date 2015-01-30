package com.mybdshop.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mybdshop.app.ProductFullViewActivity;
import com.mybdshop.app.R;

public class ViewPagerAdapter extends PagerAdapter {
	// Declare Variables
	private Activity context;
	String[] rank;
	String[] country;
	String[] population;
	int[] flag;
	LayoutInflater inflater;

	public ViewPagerAdapter(Activity context, int[] flag) {
		this.context = context;
		this.rank = rank;
		this.country = country;
		this.population = population;
		this.flag = flag;
	}

	@Override
	public int getCount() {
		return flag.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((RelativeLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {

		// Declare Variables
		TextView txtrank;
		TextView txtcountry;
		TextView txtpopulation;
		ImageView imgflag;

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.viewpager_item, container, false);

		// Locate the TextViews in viewpager_item.xml
		/*txtrank = (TextView) itemView.findViewById(R.id.rank);
		txtcountry = (TextView) itemView.findViewById(R.id.country);
		txtpopulation = (TextView) itemView.findViewById(R.id.population);

		// Capture position and set to the TextViews
		txtrank.setText(rank[position]);
		txtcountry.setText(country[position]);
		txtpopulation.setText(population[position]);*/

		// Locate the ImageView in viewpager_item.xml
		imgflag = (ImageView) itemView.findViewById(R.id.flag);
		// Capture position and set to the ImageView
		imgflag.setImageResource(flag[position]);
		
		imgflag.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ProductFullViewActivity.class);
				intent.putExtra("click_position", position);
				//intent.putExtra("type_is", typeIs);
				//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		});
		
		// Add viewpager_item.xml to ViewPager
		((ViewPager) container).addView(itemView);

		return itemView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// Remove viewpager_item.xml from ViewPager
		((ViewPager) container).removeView((RelativeLayout) object);

	}
}
