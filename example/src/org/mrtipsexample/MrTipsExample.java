package org.mrtipsexample;

import mrtips.TipsDisplayer;
import android.app.Activity;
import android.os.Bundle;

public class MrTipsExample extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
        // MRTIPS "Incantation" Section ----------------------------
        
        //create a string array with the tips' ids from values/mrtips_arrays.xml
        String[] idArray = {"Tip1","my_Second_Tip","TipNo3"};
        
        // initialize TipsDisplayer with idArrays and display the tips
        TipsDisplayer myTipsDisplayer = new TipsDisplayer(this,idArray);
        myTipsDisplayer.showTipsDialog(this);
        
        // END -------------------------------------------------------
    
    }
}