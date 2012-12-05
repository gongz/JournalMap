package com.cmu.journalmap.utilities;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

public class AudioUtility {
	private final static String TAG = "AudioUtility";
	public static MediaRecorder getMediaRecorder(){
		return new MediaRecorder();
	}
	
	public static String startRecording(MediaRecorder mRecorder) {		
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		String audioLoc = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+ System.currentTimeMillis();
		mRecorder.setOutputFile(audioLoc);		
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		try {
			mRecorder.prepare();
		} catch (IOException e) {
			Log.e(TAG, "rec prepare() failed");
		}
		mRecorder.start();
		return audioLoc;
	}

	public static void stopRecording(MediaRecorder mRecorder) {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
	}	
	
	public static MediaPlayer startPlaying(String audioLoc) {
		MediaPlayer mPlayer = new MediaPlayer();
		try {			
			mPlayer.setDataSource(audioLoc);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			Log.e(TAG, "prepare() failed");
		}
		return mPlayer;
	}

	public static void stopPlaying(MediaPlayer mPlayer) {
		mPlayer.release();
		mPlayer = null;
	}
}
