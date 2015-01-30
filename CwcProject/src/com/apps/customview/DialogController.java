package com.apps.customview;

import com.mybdshop.app.R;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;


public class DialogController {
	private Dialog dialogReply;
	private Dialog dialog;
	private Activity activity;
	
	public DialogController(Activity activity) {
		this.activity = activity;
	}
	
	public Dialog customDialog(){
		dialog = new Dialog(this.activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_image_pick);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setCancelable(true);
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		return dialog;
	}
	
}
