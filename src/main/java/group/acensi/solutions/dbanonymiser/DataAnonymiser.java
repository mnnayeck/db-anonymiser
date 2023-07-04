/*
 * Copyright (C) Acensi Group
 * 
 * All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited.
 * Proprietary and confidential.
 *
 */
package group.acensi.solutions.dbanonymiser;

import java.io.Serializable;
import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import group.acensi.solutions.dbanonymiser.cache.CacheManager;

/**
 *
 *
 * @author Nadeem Nayeck <nadeem.nayeck@acensi.group>
 *
 */
public abstract class DataAnonymiser<V extends Serializable> {
	
	private MessageDigest digest = DigestUtils.getSha256Digest();
	
	private CacheManager cacheManager = CacheManager.getInstance();
		
	
	/**
	 * Takes as parameter the string to anonymize.
	 * Returns the anonymised data
	 * @param source
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public V anonymize(V source) {
		
		if (this.isEmpty(source)) {
			return source;
		}
		
		byte[] serialized = this.serialize(source);
		String key = Base64.encodeBase64String(this.digest.digest(serialized));
		 
		V anonymizedValue = (V) cacheManager.retrieveFromCache(key);
		if (anonymizedValue == null) {
			anonymizedValue = this.doAnonymize(source);
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


	protected boolean isEmpty(V source) {
		return source == null;
	}


	protected byte[] serialize(V source) {
		return SerializationUtils.serialize(source);
	}

	protected abstract V doAnonymize(V source) ;

}
