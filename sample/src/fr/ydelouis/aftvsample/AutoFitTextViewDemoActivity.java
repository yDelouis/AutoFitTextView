package fr.ydelouis.aftvsample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import fr.ydelouis.widget.AutoFitTextView;

public class AutoFitTextViewDemoActivity
		extends Activity
		implements
		View.OnClickListener
{
	private AutoFitTextView aftv1;
	private AutoFitTextView aftv2;
	private AutoFitTextView aftv3;
	private AutoFitTextView aftv4;
	private AutoFitTextView aftv5;
	private AutoFitTextView aftv6;
	private AutoFitTextView aftv7;
	private AutoFitTextView aftv8;
	private AutoFitTextView aftv9;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.autofitextview_demo);
		findViews();
		setListeners();
	}

	private void findViews() {
		aftv1 = (AutoFitTextView) findViewById(R.id.aftvDemo_aftv1);
		aftv2 = (AutoFitTextView) findViewById(R.id.aftvDemo_aftv2);
		aftv3 = (AutoFitTextView) findViewById(R.id.aftvDemo_aftv3);
		aftv4 = (AutoFitTextView) findViewById(R.id.aftvDemo_aftv4);
		aftv5 = (AutoFitTextView) findViewById(R.id.aftvDemo_aftv5);
		aftv6 = (AutoFitTextView) findViewById(R.id.aftvDemo_aftv6);
		aftv7 = (AutoFitTextView) findViewById(R.id.aftvDemo_aftv7);
		aftv8 = (AutoFitTextView) findViewById(R.id.aftvDemo_aftv8);
		aftv9 = (AutoFitTextView) findViewById(R.id.aftvDemo_aftv9);
	}

	private void setListeners() {
		aftv1.setOnClickListener(this);
		aftv2.setOnClickListener(this);
		aftv3.setOnClickListener(this);
		aftv4.setOnClickListener(this);
		aftv5.setOnClickListener(this);
		aftv6.setOnClickListener(this);
		aftv7.setOnClickListener(this);
		aftv8.setOnClickListener(this);
		aftv9.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.equals(aftv1))
			changeText(aftv1, R.string.widthMatchParent);
		if(v.equals(aftv2))
			changeText(aftv2, R.string.sizeWrapContent);
		if(v.equals(aftv3))
			changeText(aftv3, R.string.widthFixed);
		if(v.equals(aftv4))
			changeText(aftv4, R.string.heightFixed);
		if(v.equals(aftv6))
			changeMinTextSize(aftv6, 30, 20);
		if(v.equals(aftv7))
			changePaddingTop(aftv7, 0, 15);
		if(v.equals(aftv8))
			changeMaxTextSize(aftv8, 15, 20);
	}

	private void changeText(AutoFitTextView aftv, int originalTextId) {
		if(aftv.getText().equals(getString(originalTextId)))
			aftv.setText(R.string.changingText);
		else
			aftv.setText(originalTextId);
	}

	private void changePaddingTop(AutoFitTextView aftv, int aPaddingTop, int otherPaddingTop) {
		if(aftv.getPaddingTop() == aPaddingTop)
			aftv.setPadding(aftv.getPaddingLeft(), otherPaddingTop, aftv.getPaddingRight(), aftv.getPaddingBottom());
		else
			aftv.setPadding(aftv.getPaddingLeft(), aPaddingTop, aftv.getPaddingRight(), aftv.getPaddingBottom());
	}

	private void changeMinTextSize(AutoFitTextView aftv, int aTextSize, int otherTextSize) {
		if(aftv.getMinTextSize() == aTextSize)
			aftv.setMinTextSize(otherTextSize);
		else
			aftv.setMinTextSize(aTextSize);
	}

	private void changeMaxTextSize(AutoFitTextView aftv, int aTextSize, int otherTextSize) {
		if(aftv.getMaxTextSize() == aTextSize)
			aftv.setMaxTextSize(otherTextSize);
		else
			aftv.setMaxTextSize(aTextSize);
	}
}
