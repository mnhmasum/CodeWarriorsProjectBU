package com.mybdshop.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mybdshop.app.R;
import com.mybdshop.appinfo.AppController;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mybdshop.adapter.CategoryGridAdapter;
import com.mybdshop.adapter.CustomDrawerAdapter;
import com.mybdshop.datamodel.DrawerItem;
import com.mybdshop.framents.FragmentCategories;
import com.mybdshop.framents.FragmentDashboard;
import com.mybdshop.framents.FragmentHome;
import com.mybdshop.framents.FragmentProducts;
import com.mybdshop.framents.FragmentProfileUpdate;
import com.mybdshop.jsonparser.JsonParser;
import com.mybdshop.utils.ConstantValues;
import com.mybdshop.utils.Utility;

public class MainActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	CustomDrawerAdapter adapter;
	List<DrawerItem> dataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initializing
		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		// Add Drawer Item to dataList
		dataList.add(new DrawerItem(" ")); // adding a header to the list
		dataList.add(new DrawerItem("Home", R.drawable.ic_action_email)); //1
		dataList.add(new DrawerItem("Create an Ad", R.drawable.ic_action_about)); //2

		dataList.add(new DrawerItem("My Favourites", R.drawable.ic_action_good));// adding a header to the list //3
		dataList.add(new DrawerItem("Login", R.drawable.ic_action_search)); //4
		dataList.add(new DrawerItem(" ")); //5
		dataList.add(new DrawerItem("CATEGORIES", R.drawable.ic_action_import_export)); //6
		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,dataList);
		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to // onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to // onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			SelectItem(1);
		}
		
		Utility.createFolder();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);

	}

	public void SelectItem(int possition) {

		Fragment fragment = null;
		Bundle args = new Bundle();
		switch (possition) {
		case 1:
			fragment = new FragmentHome();
			args.putString(FragmentDashboard.ITEM_NAME, dataList.get(possition).getItemName());
			args.putInt(FragmentDashboard.IMAGE_RESOURCE_ID, dataList.get(possition).getImgResID());
			break;
		case 2:
			startActivity(new Intent(MainActivity.this, CreateAdActivity.class));
			break;
		case 3:
			fragment = new FragmentCategories();
			args.putString(FragmentDashboard.ITEM_NAME, dataList.get(possition).getItemName());
			args.putInt(FragmentDashboard.IMAGE_RESOURCE_ID, dataList.get(possition).getImgResID());
			break;
		case 4:
			startActivity(new Intent(MainActivity.this, LoginActivity.class));
			//finish();
			break;
		case 5:
			fragment = new FragmentCategories();
			args.putString(FragmentDashboard.ITEM_NAME, dataList.get(possition).getItemName());
			args.putInt(FragmentDashboard.IMAGE_RESOURCE_ID, dataList.get(possition).getImgResID());
			break;
		case 6:
			fragment = new FragmentCategories();
			args.putString(FragmentDashboard.ITEM_NAME, dataList.get(possition).getItemName());
			break;

		default:
			break;
		}
		
		if (possition == 2) {
			mDrawerLayout.closeDrawer(mDrawerList);
			//finish();
			return;
		}
		
		if (possition == 4) {
			//mDrawerLayout.closeDrawer(mDrawerList);
			//finish();
			return;
		}
		
		fragment.setArguments(args);
		FragmentManager frgManager = getFragmentManager();
		frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

		mDrawerList.setItemChecked(possition, true);
		setTitle(dataList.get(possition).getItemName());
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		
		/*if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}*/
		
		switch (item.getItemId()) {
        case R.id.action_search:
            return true;
        case R.id.action_settings:
        	SelectItem(1);
            return true;
        default:
            return mDrawerToggle.onOptionsItemSelected(item);
		}
		
	}
	

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (dataList.get(position).getTitle() == null) {
				Log.e("Menu Position", "::=" + position);
				SelectItem(position);
			}

		}
	}

}
