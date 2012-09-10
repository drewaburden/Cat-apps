package com.hogg.catapps;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public boolean onOptionsItemSelect(MenuItem item) {
    	// Handle item selection
        switch (item.getItemId()) {
	        case R.id.menu_settings:
	        	Intent myIntent = new Intent(this, SettingsActivity.class);
	            startActivity(myIntent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
        }        
    }
}
