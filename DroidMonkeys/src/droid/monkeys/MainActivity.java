package droid.monkeys;

import droid.monkeys.map.ActivityMap;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button but = (Button) findViewById(R.id.main_button);
		but.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				// show MapActivity
				Intent intent = new Intent(view.getContext(), ActivityMap.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
