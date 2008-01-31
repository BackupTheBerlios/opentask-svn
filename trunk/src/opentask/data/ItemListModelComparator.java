/* 
 * ItemListModelComparator.java
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

import java.util.*;

/**
 * @author rassler
 *
 */
public class ItemListModelComparator implements Comparator<String[]> {
	// The next line is just for information access to implement compare()
	//	String[] columnNames = {"Task Date", "Task Time", "Description", "Duration", "Notify Date", "Notify Time", "Remarks"};
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(String[] line1, String[] line2) {
		int comp = 0;
		
		StringTokenizer token1 = new StringTokenizer(line1[0], ".");
		StringTokenizer token2 = new StringTokenizer(line2[0], ".");
		String[] date1 = new String[3];
		String[] date2 = new String[3];
		
		// Task date
		for (int i = 0; i < 3; ++i) {
			date1[i] = token1.nextToken();
			if (date1[i].length() == 1) date1[i] = "0" + date1[i];
			date2[i] = token2.nextToken();
			if (date2[i].length() == 1) date2[i] = "0" + date2[i];
		}
		comp = date1[2].compareTo(date2[2]);
		if (comp != 0) return comp;
		comp = date1[1].compareTo(date2[1]);
		if (comp != 0) return comp;
		comp = date1[0].compareTo(date2[0]);
		if (comp != 0) return comp;
		
		// Task time
		token1 = new StringTokenizer(line1[1], ":");
		token2 = new StringTokenizer(line2[1], ":");
		date1 = new String[2];
		date2 = new String[2];
		for (int i = 0; i < 2; ++i) {
			date1[i] = token1.nextToken();
			date2[i] = token2.nextToken();
		}
		comp = date1[0].compareTo(date2[0]);
		if (comp != 0) return comp;
		comp = date1[1].compareTo(date2[1]);
		if (comp != 0) return comp;

		// Notification date
		token1 = new StringTokenizer(line1[4], ".");
		token2 = new StringTokenizer(line2[4], ".");
		date1 = new String[3];
		date2 = new String[3];
		for (int i = 0; i < 3; ++i) {
			date1[i] = token1.nextToken();
			if (date1[i].length() == 1) date1[i] = "0" + date1[i];
			date2[i] = token2.nextToken();
			if (date2[i].length() == 1) date2[i] = "0" + date2[i];
		}
		comp = date1[2].compareTo(date2[2]);
		if (comp != 0) return comp;
		comp = date1[1].compareTo(date2[1]);
		if (comp != 0) return comp;
		comp = date1[0].compareTo(date2[0]);
		if (comp != 0) return comp;
		
		// Notification time
		token1 = new StringTokenizer(line1[5], ":");
		token2 = new StringTokenizer(line2[5], ":");
		date1 = new String[2];
		date2 = new String[2];
		for (int i = 0; i < 2; ++i) {
			date1[i] = token1.nextToken();
			date2[i] = token2.nextToken();
		}
		comp = date1[0].compareTo(date2[0]);
		if (comp != 0) return comp;
		comp = date1[1].compareTo(date2[1]);
		if (comp != 0) return comp;
		
		// Task name
		comp = line1[2].compareTo(line2[2]);
		if (comp != 0) return comp;

		// Task description
		comp = line1[6].compareTo(line2[6]);
		if (comp != 0) return comp;

		// Fallback: both are the same
		// duration and next notification are ignored!
		return comp;
	}

}
