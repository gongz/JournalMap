package droid.monkeys;

import java.io.File;
import java.io.IOException;

import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

/**
 * @author Kathy and Archer
 *
 */
public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		//Bundle bundle = getIntent().getExtras();

		File sdcard = Environment.getExternalStorageDirectory();
		//Get the text file
		File file = new File(sdcard,"DCIM/Camera/IMG396.jpg");
		String filePath = file.getAbsolutePath();

		try {
			ExifInterface exif = new ExifInterface(filePath);
			StringBuilder builder = new StringBuilder();

			System.out.println("Date & Time: " + getExifTag(exif,ExifInterface.TAG_DATETIME) + "\n\n");
			//builder.append("Date & Time: " + getExifTag(exif,ExifInterface.TAG_DATETIME) + "\n\n");
			//                        builder.append("Flash: " + getExifTag(exif,ExifInterface.TAG_FLASH) + "\n");
			//                        builder.append("Focal Length: " + getExifTag(exif,ExifInterface.TAG_FOCAL_LENGTH) + "\n\n");
			//                        builder.append("GPS Datestamp: " + getExifTag(exif,ExifInterface.TAG_FLASH) + "\n");
			//                        builder.append("GPS Latitude: " + getExifTag(exif,ExifInterface.TAG_GPS_LATITUDE) + "\n");
			//                        builder.append("GPS Latitude Ref: " + getExifTag(exif,ExifInterface.TAG_GPS_LATITUDE_REF) + "\n");
			//                        builder.append("GPS Longitude: " + getExifTag(exif,ExifInterface.TAG_GPS_LONGITUDE) + "\n");
			//                        builder.append("GPS Longitude Ref: " + getExifTag(exif,ExifInterface.TAG_GPS_LONGITUDE_REF) + "\n");
			//                        builder.append("GPS Processing Method: " + getExifTag(exif,ExifInterface.TAG_GPS_PROCESSING_METHOD) + "\n");
			//                        builder.append("GPS Timestamp: " + getExifTag(exif,ExifInterface.TAG_GPS_TIMESTAMP) + "\n\n");
			//                        builder.append("Image Length: " + getExifTag(exif,ExifInterface.TAG_IMAGE_LENGTH) + "\n");
			//                        builder.append("Image Width: " + getExifTag(exif,ExifInterface.TAG_IMAGE_WIDTH) + "\n\n");
			//                        builder.append("Camera Make: " + getExifTag(exif,ExifInterface.TAG_MAKE) + "\n");
			//                        builder.append("Camera Model: " + getExifTag(exif,ExifInterface.TAG_MODEL) + "\n");
			//                        builder.append("Camera Orientation: " + getExifTag(exif,ExifInterface.TAG_ORIENTATION) + "\n");
			//                        builder.append("Camera White Balance: " + getExifTag(exif,ExifInterface.TAG_WHITE_BALANCE) + "\n");

			TextView info = (TextView)findViewById(R.id.cameratext);

			info.setText(builder.toString());

			builder = null;
		} catch (IOException e) {
			e.printStackTrace();
		}    

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}


	private String getExifTag(ExifInterface exif,String tag){
		String attribute = exif.getAttribute(tag);

		return (null != attribute ? attribute : "");
	}

}
