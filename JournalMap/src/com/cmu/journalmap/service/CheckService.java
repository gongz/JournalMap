package com.cmu.journalmap.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

import com.cmu.journalmap.activities.R;
import com.cmu.journalmap.activities.StartPage;
import com.cmu.journalmap.utilities.PictureUtility;
import com.cmu.journalmap.utilities.PropertiesUtility;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class CheckService extends Service {

	private NotificationManager notificationMgr;

	@Override
	public void onCreate() {
		super.onCreate();
		notificationMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		displayNotificationMessage("JournalMap Service is Running");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		ThreadDemo td = new ThreadDemo();
		td.start();
		return super.onStartCommand(intent, flags, startId);
	}

	private class ThreadDemo extends Thread {
		@Override
		public void run() {
			super.run();
			while (true) {
				try {
					sleep(1000 * 10);
					handler.sendEmptyMessage(0);
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String imgPath = Environment.getExternalStorageDirectory()
					.getPath() + "/DCIM/Camera/";

			HashMap<String, Boolean> hashTable = new HashMap<String, Boolean>();
			File dir = new File(imgPath);
			String[] files = dir.list();

			for (String s : files) {
				String[] tmp = s.split("\\.");
				if (tmp.length > 0) {
					if (tmp[1].equals("jpg")) {

						if (PictureUtility.isCoordinatesValid(PictureUtility
								.getCoordsFromPhoto(imgPath + s))) {
							hashTable.put(imgPath + s, false);
						}
					}
				}
			}

			int numOfPlaces = 0;
			String tempNumOfPlaces = PropertiesUtility.getProperty(
					getApplicationContext(), "numOfPlaces");
			if ((tempNumOfPlaces != null) && (!tempNumOfPlaces.isEmpty())) {
				numOfPlaces = Integer.parseInt(tempNumOfPlaces);
			}

			for (int i = 0; i < numOfPlaces; i++) {
				String tempPhotoLocation = PropertiesUtility.getProperty(
						getApplicationContext(), "photoLocation" + i);
				if (!tempPhotoLocation.isEmpty()) {
					Boolean entry = hashTable.get(tempPhotoLocation);
					if (entry != null) {
						if (entry == false)
							hashTable.put(tempPhotoLocation, true);
					}
				}
			}
			StringBuffer buf = new StringBuffer();
			for (Entry<String, Boolean> entry : hashTable.entrySet()) {
				if (entry.getValue() == false) {
					buf.append(entry.getKey()).append("\n");
				}
			}

//			Toast toast = Toast.makeText(getApplicationContext(),
//					buf.toString(), Toast.LENGTH_SHORT);
//			toast.show();
		}
	};

	@Override
	public void onDestroy() {
		displayNotificationMessage("stopping JournalMap Service");
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	public IBinder onBind(Intent intent) {
		return null;
	}

	private void displayNotificationMessage(String message) {
		Notification notification = new Notification(R.drawable.jm_launcher,
				message, System.currentTimeMillis());
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, StartPage.class), 0);
		notification.setLatestEventInfo(this, "JournalMap Service", message,
				contentIntent);
		notificationMgr.notify(100, notification);
	}
}