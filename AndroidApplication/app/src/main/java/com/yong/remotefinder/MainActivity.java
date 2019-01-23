package com.yong.remotefinder;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;

public class MainActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
	
	public void btActivity(View v){
		startActivity(new Intent(this, BTActivity.class));
	}
	
	public void helpActivity(View v){
		startActivity(new Intent(this, HelpActivity.class));
	}
}
