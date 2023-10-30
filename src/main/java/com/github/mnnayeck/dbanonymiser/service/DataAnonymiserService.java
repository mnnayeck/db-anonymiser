/*
 * Copyright (C) Acensi Group
 * 
 * All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited.
 * Proprietary and confidential.
 *
 */
package com.github.mnnayeck.dbanonymiser.service;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

import com.github.mnnayeck.dbanonymiser.cache.CacheManager;

/**
 *
 *
 * @author Nadeem Nayeck <nadeem.nayeck@acensi.group>
 *
 */
public abstract class DataAnonymiserService<V extends Serializable> {
	
	private MessageDigest digest = DigestUtils.getSha256Digest();
	
	private CacheManager cacheManager = CacheManager.getInstance();
		
	
	/**
	 * Takes as parameter the string to anonymize.
	 * Returns the anonymised data
	 * @param source
	 * @param anonymisationConfig 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public V anonymize(V source, Map<String, Object> anonymisationConfig) {
		
		if (this.isEmpty(source)) {
			return source;
		}

		String key = this.generateKey(source);
		 
		V anonymizedValue = (V) cacheManager.retrieveFromCache(key);
		if (anonymizedValue == null) {
			anonymizedValue = this.doAnonymize(source, anonymisationConfig);
			anonymizedValue = this.sanitize(anonymizedValue);
			cacheManager.putInCache(key, anonymizedValue);
		}
		
		return anonymizedValue;
	}


	/**
	 * @param source
	 */
	@SuppressWarnings("unchecked")
	private V sanitize(V source) {
		if (source instanceof String) {
			String sanitized = (String) source;
			sanitized = StringUtils.remove(sanitized, "'");
			sanitized = StringUtils.remove(sanitized, "%");
			return (V) sanitized;
		}
		return source;
	}
	
	protected String generateKey(V source) {
		String key = null;
		if (source instanceof String) {
			key = (String) source;
		}
		else {
			byte[] serialized = SerializationUtils.serialize(source);
			byte[] digest = this.digest.digest(serialized);
			key = Base64.encodeBase64String(digest);
		}
		return key;
	}


	protected boolean isEmpty(V source) {
		return source == null;
	}

	protected abstract V doAnonymize(V source, Map<String, Object> anonymisationConfig) ;

}
