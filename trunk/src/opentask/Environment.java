/**
 * 
 */
package opentask;

/**
 * @author rassler
 *
 */
public class Environment {
	private static String dataPath = System.getProperty("user.home");
	private static String fileSeparator = System.getProperty("file.separator");
	
	private static String dataFile = "opentask.dat";
	
	public static String getDataFile()
	{
		return dataPath + fileSeparator + dataFile;
	}
}
