/* 
 * Two background threads create a 4 digit random number.
 * It a magic number if it's divisible by 7, or divisible by 4 and last digit is 2.
 * Notice no synchronization between run() and handleMessage().
 **/
package com.course.homework.magicnumber;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.util.Log;

public class MagicNumber extends Activity {

	private Thread t1, t2;
	private TextView text;
	private ProgressBar progress;
	private boolean letrun = true;  
	private boolean gotIt = false;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
				
			if (gotIt) return;  //has magic number already been found?
			
			String str = (Integer.valueOf(msg.arg1)).toString();
			String strth = (String)msg.obj;
			Log.i("MagicNumber", strth + "  " + str);
			
			if (letrun) {
			if ((msg.arg1 % 7 == 0) || (msg.arg1 % 4 == 0 && str.charAt(3) == '2')) {
				letrun = false;
				gotIt = true;
				progress.setVisibility(ProgressBar.INVISIBLE);
				text.setText(str);
				
				//send broadcast that magic number has been found
				Intent intent = new Intent("CS480.magicNumberFound");
				intent.putExtra("number", str);
				intent.putExtra("thread",  strth);
				sendBroadcast(intent);
			}}
		}
	};
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		text = (TextView) findViewById(R.id.text);
		progress = (ProgressBar) findViewById(R.id.progress);		
	}

	@Override
	public void onStart() {
		super.onStart();
		
		//instantiate threads and start
		t1 = new Thread(background, "first");
		t2 = new Thread(background, "second");
		t1.start();
		t2.start();
		 
		text.setText("Working...");
		
		progress.setVisibility(ProgressBar.VISIBLE);
		progress.setProgress(0);
	}

	//declare background threads as anonymous class
	Runnable background = new Runnable() {
		public void run() {
			while (letrun) {
							
				//msg.arg1 holds number, msg.arg2 holds which thread generated it
				int number = (int) (Math.random() * 8999) + 1000;
				Message msg = handler.obtainMessage();
				msg.arg1 = number;
				msg.obj=Thread.currentThread().getName();
				handler.sendMessage(msg);
				Integer value = number;
				String str = value.toString();
				Log.i("MagicNumber", Thread.currentThread().getName() + "  " + 
						Thread.currentThread().getPriority()  + "  " + str);	
							   
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					Log.e("MagicNumber", "Exception thrown");
				}
             
			}// while
		}// run
	}; // runnable
	
}