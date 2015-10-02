package com.example.ooxx;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	MainBoard mb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	public void CreateDialog(){
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("µ²§ô")
		.setMessage("ok")
		.setNegativeButton("1", null);
		builder.show();
	}

	public void myMethod() {
		// TODO Auto-generated method stub
		
	}

}
