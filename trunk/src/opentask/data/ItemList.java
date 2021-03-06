/* 
 * ItemList.java
 * OpenTask
 *
 * Copyright (C) 2007 Jochen A. Rassler
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package opentask.data;

import opentask.Environment;

import java.util.*;

import java.io.*;


/**
 * @author rassler
 *
 */
public class ItemList {
	private TreeSet<ActionItem> list;
	private ItemListModel model;
	private final String SEPARATOR = "#";
	
	/**
	 * @param model
	 */
	public ItemList(ItemListModel model) {
		list = new TreeSet<ActionItem>(new ActionItemComparator());
		this.model = model;
	}
	
	/**
	 * @param item
	 * @return
	 */
	public boolean add(ActionItem item) {
		boolean success = list.add(item);
		if (success) model.addItem(item);
		return success;
	}

	/**
	 * 
	 */
	public void clear() {
		list.clear();
		model.clear();
	}

	/**
	 * @param item
	 * @return
	 */
	public boolean contains(ActionItem item) {
		return list.contains(item);
	}
	
	/**
	 * @return
	 */
	public Iterator<ActionItem> descendingIterator() {
		return list.descendingIterator();
	}
	
	/**
	 * @return
	 */
	public ActionItem first() {
		return list.first();
	}

	/**
	 * @return
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	/**
	 * @return
	 */
	public Iterator<ActionItem> iterator() {
		return list.iterator();
	}
	
	/**
	 * @return
	 */
	public ActionItem last() {
		return list.last();
	}
	
	/**
	 * @param item
	 * @return
	 */
	public boolean remove(ActionItem item) {
		Iterator<ActionItem> it = iterator();
		int count = 0;
		while (it.hasNext()) {
			ActionItem check = it.next();
			++count;
			if (check.equals(item))
				break;
		}
		boolean success = list.remove(item);
		model.remove(count);
		return success;
	}
	
	/**
	 * @return
	 */
	public int size() {
		return list.size();
	}
	
	/**
	 * @param item
	 * @return
	 */
	private String writeItem(ActionItem item) {
		String stringRep = SEPARATOR;
		stringRep += item.getItemName() + SEPARATOR;
		stringRep += (new Long(item.getSchedule().getTimeInMillis()).toString()) + SEPARATOR;
		stringRep += (new Integer(item.getDuration()).toString()) + SEPARATOR;
		stringRep += (new Long(item.getNotifyTime().getTimeInMillis()).toString()) + SEPARATOR;
		stringRep += (new Integer(item.getNextNotification()).toString()) + SEPARATOR;
		stringRep += item.getDescription() + SEPARATOR;
		return stringRep;
	}
	
	/**
	 * @param stringRep
	 */
	private void readItem(String stringRep) {
		StringTokenizer tokenizer = new StringTokenizer(stringRep, SEPARATOR);
		String name = "";
		String description = "";
		Calendar schedule = Calendar.getInstance();
		Calendar notification = Calendar.getInstance();
		int duration = 0;
		int nextNotify = 0;
		
		if (tokenizer.hasMoreTokens())
			name = tokenizer.nextToken();
		if (tokenizer.hasMoreTokens())
			schedule.setTimeInMillis((new Long(tokenizer.nextToken())).longValue());
		if (tokenizer.hasMoreTokens())
			duration = (new Integer(tokenizer.nextToken())).intValue();
		if (tokenizer.hasMoreTokens())
			notification.setTimeInMillis((new Long(tokenizer.nextToken())).longValue());
		if (tokenizer.hasMoreTokens())
			nextNotify = (new Integer(tokenizer.nextToken())).intValue();
		if (tokenizer.hasMoreTokens())
			description = tokenizer.nextToken();
		
		add(new ActionItem(name, schedule, notification, duration, nextNotify, description));
	}
	
	/**
	 * @return
	 */
	public boolean save() {
		BufferedWriter outputStream = null;
		boolean success = true;
		try {
			File file = new File(Environment.getDataFile());
			if (!file.exists())
				file.createNewFile();
			outputStream = new BufferedWriter(new FileWriter(Environment.getDataFile()));
			Iterator<ActionItem> it = iterator();
			while (it.hasNext()) {
				ActionItem item = it.next();
				outputStream.write(writeItem(item));
				outputStream.newLine();
			}
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
	
	/**
	 * @return
	 */
	public boolean load() {
		BufferedReader inputStream = null;
		boolean success = true;
		try {
			inputStream = new BufferedReader(new FileReader(Environment.getDataFile()));
			while (inputStream.ready()) {
				String line = inputStream.readLine();
				if (line.length() == 0) break; 
				while (line.charAt(line.length()-1) != SEPARATOR.charAt(0))
				{
					String nextLine = " " + inputStream.readLine();
					line += nextLine;
				}
				readItem(line);
			}
					
		}
		catch (FileNotFoundException e) {
			System.err.println("Caught FileNotFoundException: " +  e.getMessage());
			System.err.println("Creating new File");
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
	
}
	

