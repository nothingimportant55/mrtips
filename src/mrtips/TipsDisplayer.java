package mrtips;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;



public class TipsDisplayer implements DialogInterface.OnClickListener {
	private static final String PREFERENCE_MRTIPS = "mrtips";
	
	private String tipId = "";
	
	public static Boolean clicked = false;
	
	private CheckBox checkBox;
	private static ArrayList<String> idList;
	
	// Elements to populate the dialog
	private String titleDialogRes;
	private String imgDialog;
	private String textDialogRes;
	private String checkBoxDialogRes;
	
	
	private static TipsDisplayer instance;
	private Context context;
	
	
	private TipsDisplayer() {
		super();
		
	}
	
	public static TipsDisplayer getInstanceOf(Context mContext){
		if(instance==null){
			instance = new TipsDisplayer();
			idList = new ArrayList<String>();
		}
		
		return instance;
	}
	
	
	public void setIdArrays(String[] idArray) {
		for (int i = 0; i < idArray.length; i++) {
			 idList.add(idArray[i]);
		}
	}
	
	
	public void showTipsDialog(Context mContext){
		this.context = mContext; 
		Log.d("TipsDisplayer.showTipsDialog()", "clicked : " +clicked);
		if(!clicked){
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

				titleDialogRes = myTipItems[0];
				imgDialog = myTipItems[1];
				textDialogRes = myTipItems[2];
				checkBoxDialogRes = myTipItems[3];
	
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
				if(titleDialogRes!=""){
					image.setPadding(0, 0, 0, 0);
				} else {
					image.setPadding(0, 0, 0, 0);
				}
				//Gravity imageGravity = new Gravity();
				//imageGravity.apply(Gravity.CENTER, w, h, container, outRect);
				layout.addView(image);
				
				//TextView text = (TextView) layout.findViewById(R.id.tip_text);
				TextView text = (TextView) new TextView(context);
				String textDialogString = context.getString(context.getResources().getIdentifier("string/"+textDialogRes, null, context.getPackageName()));
				text.setText(textDialogString);
				text.setPadding(10, 10, 10, 10);
				layout.addView(text);
				
				
				//checkBox = (CheckBox) layout.findViewById(R.id.tip_checkbox);
				String checkBoxDialogString = context.getString(context.getResources().getIdentifier("string/"+checkBoxDialogRes, null, context.getPackageName()));
				checkBox = new CheckBox(context);
				checkBox.setText(checkBoxDialogString);
				
				layout.addView(checkBox);
				mScrollView.addView(layout);
		        
				
				Builder tipDialogBuilder = new AlertDialog.Builder(context);
		        
				if(titleDialogRes!=""){
					String titleDialogString = context.getString(context.getResources().getIdentifier("string/"+titleDialogRes, null, context.getPackageName()));
					tipDialogBuilder.setTitle(titleDialogString);
				}
		        
		        //tipDialogBuilder.setIcon(context.getResources().getIdentifier("drawable/"+imgDialog, null, context.getPackageName()));
				tipDialogBuilder.setView(mScrollView);
				tipDialogBuilder.setCancelable(false);
				tipDialogBuilder.setPositiveButton("OK", this);
				
				tipDialogBuilder.create().show();
			}
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
				
				clicked = true;
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
