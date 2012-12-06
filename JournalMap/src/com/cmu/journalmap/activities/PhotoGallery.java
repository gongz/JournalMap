package com.cmu.journalmap.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

public class PhotoGallery extends Activity
{
	private LinearLayout m_LinLayout;
	private ScrollView m_Scroll;
	private ImageView m_Img, m_Img2, m_Img3, m_Img4, m_Img5, m_Img6;
	private TextView m_TxtCol;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		m_LinLayout = new LinearLayout(this);
		m_LinLayout.setOrientation(LinearLayout.VERTICAL);

		// ///////////////////////////////////////////////////////////////
		// Add an image etc etc
		m_Img = new ImageView(this);
		m_Img.setImageResource(R.drawable.jm_launcher);
		m_Img.setPadding(5, 5, 0, 0);

		m_Img2 = new ImageView(this);
		m_Img2.setImageResource(R.drawable.jm_launcher);
		m_Img2.setPadding(5, 5, 0, 0);

		m_Img3 = new ImageView(this);
		m_Img3.setImageResource(R.drawable.jm_launcher);
		m_Img3.setPadding(5, 5, 0, 0);

		m_Img4 = new ImageView(this);
		m_Img4.setImageResource(R.drawable.jm_launcher);
		m_Img4.setPadding(5, 5, 0, 0);

		m_Img5 = new ImageView(this);
		m_Img5.setImageResource(R.drawable.jm_launcher);
		m_Img5.setPadding(5, 5, 0, 0);

		m_Img6 = new ImageView(this);
		m_Img6.setImageResource(R.drawable.jm_launcher);
		m_Img6.setPadding(5, 5, 0, 0);

		m_TxtCol = new TextView(this);
		m_TxtCol.setText("Text comes here.");
		m_TxtCol.setPadding(15, 5, 0, 0);
		m_TxtCol.setTextColor(Color.parseColor("#FF0000"));
		// You can create other controls as well.

		m_LinLayout.addView(m_Img, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		m_LinLayout.addView(m_Img2, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		m_LinLayout.addView(m_Img3, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		m_LinLayout.addView(m_Img4, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		m_LinLayout.addView(m_Img5, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		m_LinLayout.addView(m_Img6, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		m_LinLayout.addView(m_TxtCol, new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		// If you have other controls, you should add them here.

		m_Scroll = new ScrollView(this);
		m_Scroll.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		m_Scroll.addView(m_LinLayout, new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		// addContentView(m_Scroll, new LayoutParams(LayoutParams.FILL_PARENT,
		// LayoutParams.WRAP_CONTENT));

		setContentView(m_Scroll);
	}
}
