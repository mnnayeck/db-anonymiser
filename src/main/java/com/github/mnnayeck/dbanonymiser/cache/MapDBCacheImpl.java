/**
 * 
 */
package com.github.mnnayeck.dbanonymiser.cache;

import java.io.File;
import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Nadeem
 *
 */
public class MapDBCacheImpl extends CacheManager {

	private DB db;

	private HTreeMap<String, Serializable> map = null;

	private static final Logger LOGGER = LoggerFactory.getLogger(MapDBCacheImpl.class);

	private static final String APP_NAME = "anonymizer";
	
	private File databaseFile = null;

	public MapDBCacheImpl() {
		openDb();
		openMap();		
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void openMap() {
		this.map = (HTreeMap<String, Serializable>) this.db.hashMap(APP_NAME).createOrOpen();
	}

	/**
	 * @param databaseFile
	 */
	private void openDb() {
		String tmpdir = System.getProperty("java.io.tmpdir");
		File workingDir = new File(tmpdir, APP_NAME);
		if (!workingDir.exists()) {
			workingDir.mkdirs();
			LOGGER.trace("Created folder {} for storing anonymization data", workingDir.getAbsolutePath());
		} else {
			LOGGER.trace("Folder {} already exists for storing anonymization data", workingDir.getAbsolutePath());
		}

		this.databaseFile = new File(workingDir, UUID.randomUUID().toString() + ".db");
		if (databaseFile.exists()) {
			LOGGER.trace("Database file {} already exists", databaseFile.getAbsolutePath());

		} else {
			LOGGER.trace("Database file {} does not exist. Creating.", databaseFile.getAbsolutePath());
		}
		
		Runtime.getRuntime().addShutdownHook(new Thread ( ) {
			
			@Override
			public void run(){    
				close();
			}
		});

		this.db = DBMaker.fileDB(databaseFile).make();
		
		
		LOGGER.trace("Anonymiser database is {}", databaseFile.getAbsolutePath());
	}

	@Override
	public void putInCache(String key, Serializable value) {
		this.map.put(key, value);

	}

	@Override
	public Serializable retrieveFromCache(String key) {
		return this.map.get(key);
	}

	@Override
	public void close() {
		LOGGER.info("Closing database file");
		this.map.close();
		this.db.close();
		FileUtils.deleteQuietly(databaseFile);

	}

	@Override
	protected boolean isClosed() {
		return this.db.isClosed() || this.map.isClosed();
	}

	@Override
	protected void open() {
		if (this.db.isClosed()) {
			this.openDb();
		}
		if (this.map.isClosed()) {
			this.openMap();
		}

	}

}
