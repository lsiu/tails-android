package com.github.lsiu.tails.util;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.apache.http.client.ClientProtocolException;

import android.test.InstrumentationTestCase;

import com.github.lsiu.tails.model.Pet;

public class PetUtilAndroidTest extends InstrumentationTestCase {

	public void testGetList() throws ClientProtocolException, IOException {
		List<Pet> pets = PetUtil.getPets();	
		Assert.assertNotNull(pets);
	}
	
}
