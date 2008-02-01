/**
 * 
 */
package opentask;

import java.io.*;
import java.util.StringTokenizer;


/**
 * @author rassler
 *
 */
public class Settings {
	private final String SEPARATOR = ",";
	private int x, y;
	private int width, height;
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public boolean load()
	{
		BufferedReader inputStream = null;
		boolean success = true;
		int cnt = 0;

		try {
			inputStream = new BufferedReader(new FileReader(Environment.getSettingsFile()));
			while (inputStream.ready()) {
				String line = inputStream.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, SEPARATOR);
				if (cnt == 0)
				{
					x = new Integer(tokenizer.nextToken()).intValue();
					y = new Integer(tokenizer.nextToken()).intValue();
				}
				if (cnt == 1)
				{
					width = new Integer(tokenizer.nextToken()).intValue();
					height = new Integer(tokenizer.nextToken()).intValue();
				}
				++cnt;
			}
					
		}
		catch (FileNotFoundException e) {
			System.err.println("Caught FileNotFoundException: " +  e.getMessage());
			success = false;
		}
		catch (IOException e) {
			System.err.println("Caught IOException: " +  e.getMessage());
			success = false;
		}
		finally {
            if (inputStream != null) {
            	try {
            		inputStream.close();
            	}
                catch (IOException e) {
                	System.err.println("Caught IOException: " +  e.getMessage());
                	success = false;
                }
            }
        }
		return success;
	}
	
	public boolean save()
	{
		BufferedWriter outputStream = null;
		boolean success = true;
		try {
			File file = new File(Environment.getSettingsFile());
			if (!file.exists())
				file.createNewFile();
			outputStream = new BufferedWriter(new FileWriter(Environment.getSettingsFile()));

			outputStream.write(new Integer(x).toString() + SEPARATOR + new Integer(y).toString());
			outputStream.newLine();
			outputStream.write(new Integer(width).toString() + SEPARATOR + new Integer(height).toString());
			outputStream.newLine();
		}
		catch (IOException e) {
			System.err.println("Caught IOException: " +  e.getMessage());
			success = false;
		}
		finally {
			if (outputStream != null) {
                try {
                	outputStream.close();
                }
                catch (IOException e) {
                	System.err.println("Caught IOException: " +  e.getMessage());
                	success = false;
                }
			}
		}
		return success;
	}
	
}
