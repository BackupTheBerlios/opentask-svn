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
	private static String settingsFile = "opentask.ini";
	
	public static String getDataFile()
	{
		return dataPath + fileSeparator + dataFile;
	}
	
	public static String getSettingsFile()
	{
		return dataPath + fileSeparator + settingsFile;
	}
}
