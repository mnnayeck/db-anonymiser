/**
 * 
 */
package group.acensi.solutions.dbanonymiser.cache;

import java.io.Serializable;

/**
 * @author Nadeem
 *
 */
public abstract class CacheManager {
	
	private static CacheManager cacheManager = null;
	
	public abstract void putInCache(String key, Serializable value);
	
	public abstract Serializable retrieveFromCache(String key);

	public abstract void close();

	public static CacheManager getInstance() {
		if (cacheManager == null) {
			cacheManager = new MapDBCacheImpl();
		}
		if (cacheManager.isClosed()) {
			cacheManager.open();
		}
		return cacheManager;
	}

	/**
	 * @return
	 */
	protected abstract boolean isClosed();

	/**
	 * 
	 */
	protected abstract void open();

}
