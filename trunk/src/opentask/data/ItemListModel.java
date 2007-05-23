/* 
 * ItemListModel.java
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

import javax.swing.table.AbstractTableModel;
import java.util.Vector;
import java.util.Calendar;

/**
 * @author rassler
 *
 */
public class ItemListModel extends AbstractTableModel {
	String[] columnNames = {"Date", "Time", "Description", "Duration", "Remarks"};
	String[] line;
	Vector<String[]> data;
	static final long serialVersionUID = 1;
	
	/**
	 * 
	 */
	public ItemListModel() {
		data = new Vector<String[]>();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
        return columnNames.length;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return data.size();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int row, int col) {
        return (data.elementAt(row))[col];
    }
    
    /**
     * @param obj
     * @param row
     */
    public void setValueAt(ActionItem obj, int row) {
    	line = new String[columnNames.length];
    	Calendar date = obj.getSchedule();
    	line[0] = (new Integer(date.get(Calendar.DAY_OF_MONTH)).toString()) + "." + (new Integer(date.get(Calendar.MONTH)+1).toString()) + "." + (new Integer(date.get(Calendar.YEAR)).toString()); 
    	line[1] = (new Integer(date.get(Calendar.HOUR_OF_DAY)).toString()) + ":" + (new Integer(date.get(Calendar.MINUTE)).toString());
    	line[2] = obj.getItemName();
    	line[3] = new Integer(obj.getDuration()).toString();
    	line[4] = obj.getDescription();
    	
    	if (row > data.size() || row < 0)
    	{
    		data.add(line);
    		fireTableRowsInserted(data.size()-1, data.size());
    	}
    	else 
    	{
    		data.add(row, line);
    		fireTableRowsInserted(row-1, row);
    	}
    }
    
    /**
     * @param item
     */
    public void addItem(ActionItem item) {
    	setValueAt(item, data.size());
    }
}
