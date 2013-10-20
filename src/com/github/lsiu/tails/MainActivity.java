package com.github.lsiu.tails;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
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

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		List<Pet> petList = new ArrayList<Pet>();
		petList.add(new Pet("Paul Neil"));
		petList.add(new Pet("Kelvin Chu"));
		petList.add(new Pet("Leonard Siu"));

		final ListView listView = (ListView) findViewById(R.id.pet_list_view);

		listView.setAdapter(new PetArrayAdapter(this,
				R.layout.activity_list_pet, petList.toArray(new Pet[0])));
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
	
	static List<Pet> getPets() {
		DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
		HttpGet get = new HttpGet("http://tails-lsiu.rhcloud.com/mockjson/list.json");
		get.setHeader("Content-Type", "application/json");
		
		InputStream inputStream = null;
		String result = null;
		try {
		    HttpResponse response = httpclient.execute(get);           
		    HttpEntity entity = response.getEntity();
		    
		    long length = entity.getContentLength();
		    
		    inputStream = entity.getContent();
		    
		    byte[] buf = new byte[(int)length];
		    int byteRead = inputStream.read(buf);
		    JSONObject o = new JSONObject(new String(buf, "UTF-8"));
		    
		    Log.d("return-json", String.valueOf(o));
		} catch (Exception e) { 
		    // Oops
		}
		finally {
		    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
		}
		
		return null;
	}

}
