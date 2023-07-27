/**
 * 
 */
package group.acensi.solutions.dbanonymiser.dao;

import java.util.Map;
import java.util.stream.Stream;

/**
 * 
 */
public interface SqlStatementDao {

	/**
	 * @param key
	 */
	void addStore(String key);


	/**
	 * @param databaseRefId
	 * @param updateString
	 */
	void persist(String databaseRefId, String updateString);


	/**
	 * @return
	 */
	Map<String, Stream<String>> getFileMap();


	/**
	 * @param fileName
	 * @return
	 */
	int countLines(String fileName);

}
