package com.cmu.journalmap.activities;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cmu.journalmap.utilities.PictureUtility;
import com.cmu.journalmap.utilities.PropertiesUtility;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

public class PhotoGallery extends Activity
{
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_photogallery);
		
		ScrollView scroll = (ScrollView) this.findViewById(R.id.svPhotoGallery);
		LinearLayout linlayout = (LinearLayout) this.findViewById(R.id.llPhotoGallery);

		int numOfEntries = 0;

		// images
		String strNumOfEntries = PropertiesUtility.getProperty(getApplicationContext(), "numOfPlaces");
		if (strNumOfEntries != null)
		{
			numOfEntries = Integer.parseInt(strNumOfEntries);
		}
		List<ImageView> ivList = new ArrayList<ImageView>();
		List<TextView> tvList = new ArrayList<TextView>();
		
		for (int i = 0; i<numOfEntries; i++)
		{
			ImageView imgView = new ImageView(this);
			TextView txtView = new TextView(this);
			if (imgView != null) {
				String photoPath = PropertiesUtility.getProperty(getApplicationContext(), "photoLocation"+i);
				String note = PropertiesUtility.getProperty(getApplicationContext(), "note"+i);
				//Uri tempUri = Uri.parse(new File(photoPath).toString());
				
//				Bitmap ThumbImage = PictureUtility.decodeSampledBitmapFromPath(
//						PictureUtility.getRealPathFromURI(tempUri, this), 100, 100);
				Bitmap ThumbImage = PictureUtility.decodeSampledBitmapFromPath(
						photoPath, 100, 100);
				imgView.setImageBitmap(ThumbImage);
				txtView.setText(note);
				
			}
			//imgView.setPadding(5, 5, 0, 0);
			ivList.add(imgView);
			tvList.add(txtView);
		}
		
//		TextView tv1 = new TextView(this);
//		tv1.setText("This is tv1");
//		linlayout.addView(tv1);
		
		Iterator<ImageView> itr = ivList.iterator();
		Iterator<TextView> tItr = tvList.iterator();
		while(itr.hasNext()) {
			 LinearLayout llLine = new LinearLayout(this);
	         ImageView element = itr.next();
	         TextView tvElement = tItr.next();
	         llLine.addView(element);
	         llLine.addView(tvElement);
	         linlayout.addView(llLine, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
	      }
		




	}

}
