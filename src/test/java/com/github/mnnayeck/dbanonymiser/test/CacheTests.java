/**
 * 
 * @author nadeem.m.nayeck
 */
package com.github.mnnayeck.dbanonymiser.test;

import java.io.Serializable;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.github.mnnayeck.dbanonymiser.cache.CacheManager;


/**
 * 
 * @author Nadeem
 */
public class CacheTests {
	
	private static String TEST_STRING = UUID.randomUUID().toString();
	
	private static String KEY = "key";
	
	@Test
	public void testLiveCache() throws Exception {
		CacheManager cacheManager = CacheManager.getInstance();
		cacheManager.putInCache(KEY, TEST_STRING);
		Serializable value = cacheManager.retrieveFromCache(KEY);
		Assert.assertEquals(TEST_STRING, value);
		cacheManager.close();
	}


}
