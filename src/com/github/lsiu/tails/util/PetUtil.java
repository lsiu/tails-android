package com.github.lsiu.tails.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import android.util.Log;

import com.github.lsiu.tails.model.Pet;
import com.google.gson.Gson;

public class PetUtil {
	
	private static Gson gson = new Gson();
	
	public static List<Pet> parseJson(String json) {
		Pet[] pets = gson.fromJson(json, Pet[].class);
	    List<Pet> petList = Arrays.asList(pets);
	    return petList;
	}

	public static List<Pet> getPets() throws ClientProtocolException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
		HttpGet get = new HttpGet("http://tails-lsiu.rhcloud.com/mockjson/list.json");
		get.setHeader("Content-Type", "application/json");
		
		BufferedReader reader = null;
		try {
		    HttpResponse response = httpclient.execute(get);           
		    HttpEntity entity = response.getEntity();
		    reader = new BufferedReader(new InputStreamReader(entity.getContent()));
		    
		    StringBuilder sb = new StringBuilder();
		    String line = null;
		    while((line = reader.readLine()) != null) {
		    	sb.append(line).append("\n");
		    }
		    String jsonString = sb.toString();
		    
		    Log.d("return-json", String.valueOf(jsonString));

		    List<Pet> petList = parseJson(jsonString);
		    
		    return petList;
		} finally {
		    try{if(reader != null)reader.close();}catch(Exception squish){}
		}
	}

}
