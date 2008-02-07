/**
 * 
 */
package opentask.gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.Component;

/**
 * @author rassler
 *
 */
public class TableColumnPacker {

	private JTable table;
	
	public TableColumnPacker(JTable table)
	{
		this.table = table;
	}
	
	public void packColumns()
	{
	    for (int c = 0; c < table.getColumnCount(); ++c) 
	        packColumn(c, 2);
	}
	
	private void packColumn(int vColIndex, int margin)
	{
//	    TableModel model = table.getModel();
	    DefaultTableColumnModel colModel = (DefaultTableColumnModel)table.getColumnModel();
	    TableColumn col = colModel.getColumn(vColIndex);
	    int width = 0;

	    // Get width of column header
	    TableCellRenderer renderer = col.getHeaderRenderer();
	    if (renderer == null) {
	        renderer = table.getTableHeader().getDefaultRenderer();
	    }
	    Component comp = renderer.getTableCellRendererComponent(
	        table, col.getHeaderValue(), false, false, 0, 0);
	    width = comp.getPreferredSize().width;

	    // Get maximum width of column data
	    for (int r = 0; r < table.getRowCount(); ++r) 
	    {
	        renderer = table.getCellRenderer(r, vColIndex);
	        comp = renderer.getTableCellRendererComponent(
	            table, table.getValueAt(r, vColIndex), false, false, r, vColIndex);
	        width = Math.max(width, comp.getPreferredSize().width);
	    }

	    // Add margin
	    width += 2*margin;

	    // Set the width
	    col.setPreferredWidth(width);
		
	}
}


