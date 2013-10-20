package com.github.lsiu.tails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.github.lsiu.tails.model.Pet;
import com.github.lsiu.tails.qr.IntentIntegrator;
import com.github.lsiu.tails.qr.IntentResult;
import com.github.lsiu.tails.util.PetUtil;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		final ListView listView = (ListView) findViewById(R.id.pet_list_view);
		
		AsyncTask<String, Void, List<Pet>> getPets = new AsyncTask<String, Void, List<Pet>>() {
			@Override
			protected List<Pet> doInBackground(String... url) {
				List<Pet> petList;
				try {
					petList = PetUtil.getPets();
				} catch (ClientProtocolException e) {
					petList = new ArrayList<Pet>();
					petList.add(new Pet("Error:" + e.getMessage()));
					Log.e("get-pets", e.getMessage(), e);
				} catch (IOException e) {
					petList = new ArrayList<Pet>();
					petList.add(new Pet("Error" + e.getMessage()));
					Log.e("get-pets", e.getMessage(), e);
				}
				return petList;
			}
			@Override
			protected void onPostExecute(List<Pet> result) {
				listView.setAdapter(new PetArrayAdapter(MainActivity.this,
						R.layout.activity_list_pet, result.toArray(new Pet[0])));
			}
		};
		
		getPets.execute();

		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});

		final Button button = (Button) findViewById(R.id.scan_qr);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				IntentIntegrator integrator = new IntentIntegrator(
						MainActivity.this);
				integrator.initiateScan();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		IntentResult scanResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, data);
		if (scanResult != null) {
			Log.d("Scan Result", "Scan Result: " + scanResult.getContents());
		}
	}
}
