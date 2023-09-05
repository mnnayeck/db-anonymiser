/**
 * 
 */
package com.github.mnnayeck.dbanonymiser.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 */
public class FileSqlStatementDaoImpl implements SqlStatementDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileSqlStatementDaoImpl.class);
	
	private static final String OUTPUT = "output";
	
	private static final String SDF = "yyyy-MM-dd_HH-mm";
	
	private File parentFolder = null;
	
	private Map<String, File> sqlFiles = new HashMap<>();
	
	
	public FileSqlStatementDaoImpl() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SDF);
		parentFolder = new File(OUTPUT, simpleDateFormat.format(new Date()));
		if (!parentFolder.exists()) {
			parentFolder.mkdirs();
		}
		
		LOGGER.info("Using results folder: {}", parentFolder.getAbsolutePath());
	}


	@Override
	public void addStore(String key) {
		try {
			this.sqlFiles.put(key, this.createFile(key));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		
	}

	/**
	 * @param folder 
	 * @param key
	 * @return
	 * @throws IOException 
	 */
	private File createFile(String key) throws IOException {
		File file = new File(this.parentFolder, key + ".sql");
		if (file.exists()) {
			LOGGER.info("Deleting existing file: {}", file.getAbsolutePath());
		}
		boolean created = file.createNewFile();
		if (!created) {
			LOGGER.error("Unable to create file {}", file.getAbsolutePath());
		}
		return file;
	}


	@Override
	public void persist(String databaseRefId, String updateString) {
		File sqlFile = this.sqlFiles.get(databaseRefId);
		try {
			FileUtils.write(sqlFile, updateString, StandardCharsets.UTF_8, true);
		} catch (IOException ex) {
			LOGGER.error("Error appending to file", ex);
		}
	}


	@Override
	public Map<String, Stream<String>> getFileMap() {
		Map<String, Stream<String>> map = new HashMap<>();
		for(Entry<String, File> entry: this.sqlFiles.entrySet()) {
			String key = entry.getKey();
			File file = entry.getValue();
			Stream<String> stream;
			try {
				stream = Files.lines(file.toPath());
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
			map.put(key, stream);
		}
		return map;
	}


	@Override
	public int countLines(String fileName) {
		BufferedReader reader;
		int lines = 0;
		try {
			reader = new BufferedReader(new FileReader(this.sqlFiles.get(fileName)));
			while (reader.readLine() != null) lines++;
			reader.close();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		return lines;
	}

}
