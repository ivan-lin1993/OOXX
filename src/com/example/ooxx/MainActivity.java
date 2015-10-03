package com.example.ooxx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity {

	MainBoard mb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);   //全螢幕設定
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	    WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
		//setContentView(R.layout.activity_main);
		mb=new MainBoard(this);
		setContentView(mb);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.Restart) {
			//mb.Restart();
			setContentView(mb);
			return true;
		}
		else if (id==R.id.Exit){
			System.exit(0);
		}
		
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event){
		float a=0,b=0;
		mb.TouchFunc(event,a,b);
		if(mb.isOver()) {
			//CreateDialog();
			//System.exit(0);
			
		}
		
		
		return true;
	}
	public void CreateDialog(){
		
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("獲勝")
		.setMessage("123")
		.setNegativeButton("Restart", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setContentView(mb);
				mb.Restart();
			}
		})
		.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		builder.show();
	}


}
