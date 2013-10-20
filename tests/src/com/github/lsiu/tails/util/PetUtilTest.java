package com.github.lsiu.tails.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.github.lsiu.tails.model.Pet;
import com.google.gson.Gson;

public class PetUtilTest extends TestCase {

	public void testParseJson() throws IOException {
		BufferedReader br = null;
		String json = null;
		try {
//			fis = new FileInputStream(new File(
//					"../tails-server/mockjson/list.json"));
			br = new BufferedReader(new InputStreamReader(
					PetUtilTest.class.getResourceAsStream("/list.json")));
			StringBuilder total = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				total.append(line);
			}
			json = total.toString();
		} finally {
			if (br != null) br.close();
		}
		List<Pet> petList = PetUtil.parseJson(json);

		Assert.assertNotNull(petList);
	}
	
	public void testStringDate() {
		Gson gson = new Gson();
		Pet p = new Pet("dog test");
//		p.setCreationTime(new Date());
		
		System.out.println(gson.toJson(p));
		
	}
}
