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
import java.util.Date;

/**
 * @author rassler
 *
 */
public class ItemListModel extends AbstractTableModel {
	String[] columnNames = {"Date", "Time", "Description", "Duration", "Remarks"};
	String[] line;
	Vector<String[]> data;
	static final long serialVersionUID = 1;
	
	public ItemListModel() {
		data = new Vector<String[]>();
	}
	
	public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return (data.elementAt(row))[col];
    }
    
    public void setValueAt(ActionItem obj, int row) {
    	line = new String[columnNames.length];
    	Date date = obj.getSchedule().getTime();
    	line[0] = date.toString();
    	line[1] = date.toString();
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
}
