package fr.ydelouis.aftvsample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup;
import fr.ydelouis.widget.AutoFitTextView;

public class AutoFitTextViewDemoActivity extends Activity
{
	private static final long UPDATE_DELAY = 1000;
	private static final int UPDATE_NB = 7;
	
	private boolean update = true;
	private int updateIndex = 0;
	
	private ViewGroup container;
	private AutoFitTextView aftv1;
	private AutoFitTextView aftv2;
	private AutoFitTextView aftv3;
	private AutoFitTextView aftv4;
	private AutoFitTextView aftv5;
	private AutoFitTextView aftv6;
	private AutoFitTextView aftv7;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.autofitextview_demo);
		findViews();
	}
	
	private void findViews() {
		container = (ViewGroup) findViewById(R.id.aftvDemo_container);
		aftv1 = (AutoFitTextView) findViewById(R.id.aftvDemo_aftv1);
		aftv2 = (AutoFitTextView) findViewById(R.id.aftvDemo_aftv2);
		aftv3 = (AutoFitTextView) findViewById(R.id.aftvDemo_aftv3);
		aftv4 = (AutoFitTextView) findViewById(R.id.aftvDemo_aftv4);
		aftv5 = (AutoFitTextView) findViewById(R.id.aftvDemo_aftv5);
		aftv6 = (AutoFitTextView) findViewById(R.id.aftvDemo_aftv6);
		aftv7 = (AutoFitTextView) findViewById(R.id.aftvDemo_aftv7);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		update = true;
		new Handler().postDelayed(updateRunnable, UPDATE_DELAY);
	}
	
	@Override
	protected void onPause() {
		update = false;
		super.onPause();
	}
	
	private Runnable updateRunnable = new Runnable() {
		public void run() {
			int nbUpdate = updateIndex/UPDATE_NB;
			switch (updateIndex % UPDATE_NB) {
				case 0:
					aftv1.setText(nbUpdate%2 == 0 ? R.string.aftvTextDemo2 : R.string.aftvTextDemo);
					break;
					
				case 1:
					aftv2.setPadding(0, nbUpdate%2 == 0 ? 0 : 10, 0, 0);
					break;
					
				case 2:
					aftv3.getLayoutParams().width = (int)((nbUpdate%2 == 0 ? 150 : 100)*getResources().getDisplayMetrics().density);
					break;
					
				case 3:
					aftv4.setTextSize((int)((nbUpdate%2 == 0 ? 20 : 30)*getResources().getDisplayMetrics().density));
					break;
					
				case 4:
					aftv5.setGravity(nbUpdate%3 == 0 ? Gravity.CENTER : (nbUpdate%3 == 1 ? Gravity.LEFT : Gravity.RIGHT));
					break;
					
				case 5:
					aftv6.setMinTextSize((int)((nbUpdate%2 == 0 ? 10 : 35)*getResources().getDisplayMetrics().density));
					break;
					
				case 6:
					aftv7.setMaxTextSize((int)((nbUpdate%2 == 0 ? 35 : 10)*getResources().getDisplayMetrics().density));
					break;
			}
			container.requestLayout();
			updateIndex++;
			if(update)
				new Handler().postDelayed(updateRunnable, UPDATE_DELAY);
		}
	};
}
