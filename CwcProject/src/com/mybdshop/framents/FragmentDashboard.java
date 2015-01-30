package com.mybdshop.framents;


import com.mybdshop.app.R;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentDashboard extends Fragment implements OnClickListener{

	private ImageView ivIcon;
	private TextView tvItemName;
	private Button btnShare;

	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";

	public FragmentDashboard() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout_one, container, false);
		btnShare = (Button) view.findViewById(R.id.btn_share);
		ivIcon = (ImageView) view.findViewById(R.id.frag1_icon);
		tvItemName = (TextView) view.findViewById(R.id.frag1_text);
		tvItemName.setText(getArguments().getString(ITEM_NAME));
		setListener();
		return view;
	}

	private void setListener() {
		btnShare.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_share) {
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.setType("text/plain");
			shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Its Awesome");
			shareIntent.putExtra(Intent.EXTRA_TEXT,  "I love it  http://www.akhoni.com/champions.html");
			startActivity(Intent.createChooser(shareIntent, "Share"));
		}
	}

}
