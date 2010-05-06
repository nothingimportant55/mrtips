package mrtips;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.LayoutAnimationController;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yaupand1.R;


public class TipsDisplayer implements DialogInterface.OnClickListener {
	private static final String PREFERENCE_MRTIPS = "mrtips";
	
	private Context context;
	private CheckBox checkBox;
	private ArrayList<String> idList;
	
	private String tipId = "";
	
	
	// Elements to populate the dialog
	private String titleDialog;
	private String imgDialog;
	private String textDialog;
	private String checkBoxDialog;
	
	
	public TipsDisplayer(Context context, String[] idArray) {
		super();
		idList = new ArrayList<String>();
		
		for (int i = 0; i < idArray.length; i++) {
			 idList.add(idArray[i]);
		}
		
		this.context = context;
	}

	
	public void showTipsDialog(Context context){
		
		Iterator<String> idIterator = idList.iterator();
		while (idIterator.hasNext()){
			String currentId = idIterator.next();
			if(!isHidden(currentId)){
				tipId = currentId;
				break;
			}
		}
		
		Log.d("TipsDisplayer.showTipsDialog()", "currentID = " + tipId);
		
			
		if(tipId!=""){
			String[] myTipItems = context.getResources().getStringArray(context.getResources().getIdentifier(tipId, "array", context.getPackageName()));

			titleDialog = myTipItems[0];
			imgDialog = myTipItems[1];
			textDialog = myTipItems[2];
			checkBoxDialog = myTipItems[3];
			

			//LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			//View layout = inflater.inflate(R.layout.tip_layout, null); 
			//layout.findViewById(R.id.layout_root);		
			
			LinearLayout layout = new LinearLayout(context);
			layout.setOrientation(LinearLayout.VERTICAL);
			layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
	
			ScrollView mScrollView = new ScrollView(context);
			
			//ImageView image = (ImageView) layout.findViewById(R.id.tip_image);
			ImageView image = (ImageView) new ImageView(context);
			image.setImageResource(context.getResources().getIdentifier("drawable/"+imgDialog, null, context.getPackageName()));
			if(titleDialog!=""){
				image.setPadding(0, -30, 0, 10);
			} else {
				image.setPadding(0, 0, 0, 10);
			}
			//Gravity imageGravity = new Gravity();
			//imageGravity.apply(Gravity.CENTER, w, h, container, outRect);
			layout.addView(image);
			
			//TextView text = (TextView) layout.findViewById(R.id.tip_text);
			TextView text = (TextView) new TextView(context);
			text.setText(textDialog);
			text.setPadding(10, 10, 10, 10);
			layout.addView(text);
			
			
			//checkBox = (CheckBox) layout.findViewById(R.id.tip_checkbox);
			checkBox = new CheckBox(context);
			checkBox.setText(checkBoxDialog);
			checkBox.setPadding(50, 0, 0, 0);
			
			layout.addView(checkBox);
			mScrollView.addView(layout);
	        
			
			Builder tipDialogBuilder = new AlertDialog.Builder(context);
	        
			if(titleDialog!=""){
				tipDialogBuilder.setTitle(titleDialog);
			}
	        
	        //tipDialogBuilder.setIcon(context.getResources().getIdentifier("drawable/"+imgDialog, null, context.getPackageName()));
			tipDialogBuilder.setView(mScrollView);
			tipDialogBuilder.setCancelable(false);
			tipDialogBuilder.setPositiveButton(R.string.button_ok, this);
			
			tipDialogBuilder.create().show();	
		}
		
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which){
			case Dialog.BUTTON_POSITIVE :
				if(checkBox!=null && checkBox.isChecked()){
					Log.i("TipsDisplayer.onClick()", "checked !");
					setHiddenId(tipId);
				}
				dialog.dismiss();
			break;
		}
	}

	public Boolean isHidden(String id){
		SharedPreferences mPreferences = context.getSharedPreferences(PREFERENCE_MRTIPS, Context.MODE_PRIVATE);
		return mPreferences.getBoolean(id, false);
	}
	
	public void setHiddenId(String id){
		SharedPreferences mPreferences = context.getSharedPreferences(PREFERENCE_MRTIPS, Context.MODE_PRIVATE);
		Editor editor = mPreferences.edit();
		editor.putBoolean(id, true);
		editor.commit();
	}
	
}
