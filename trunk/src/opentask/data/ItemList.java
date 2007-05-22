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

import java.util.TreeSet;
import java.util.Iterator;


/**
 * @author rassler
 *
 */
public class ItemList {
	private TreeSet<ActionItem> list;
	private ItemListModel model;
	
	public ItemList(ItemListModel model) {
		list = new TreeSet<ActionItem>(new ActionItemComparator());
		this.model = model;
	}
	
	public boolean add(ActionItem item) {
		boolean success = list.add(item);
		if (success) model.addItem(item);
		return success;
	}

	public void clear() {
		list.clear();
	}

	public boolean contains(ActionItem item) {
		return list.contains(item);
	}
	
	public Iterator<ActionItem> descendingIterator() {
		return list.descendingIterator();
	}
	
	public ActionItem first() {
		return list.first();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public Iterator<ActionItem> iterator() {
		return list.iterator();
	}
	
	public ActionItem last() {
		return list.last();
	}
	
	public boolean remove(ActionItem item) {
		return list.remove(item);
	}
	
	public int size() {
		return list.size();
	}
	
	
}
	

