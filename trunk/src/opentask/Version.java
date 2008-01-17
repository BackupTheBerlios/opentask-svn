/**
 * 
 */
package opentask;

/**
 * @author rassler
 *
 */
public class Version {
	public static int major = 0;
	public static int minor = 1;
	public static int buglevel = 2;
	
	public String getVersion() {
		return (new Integer(major)).toString() + "." + (new Integer(minor)).toString() + "." + (new Integer(buglevel)).toString();
	}
}
