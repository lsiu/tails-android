package com.github.lsiu.tails.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.os.AsyncTask;
import android.util.Log;

import com.github.lsiu.tails.model.Pet;
import com.github.lsiu.tails.util.PetUtil;

public class GetPets extends AsyncTask<String, Void, List<Pet>> {

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

}
