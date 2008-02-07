/**
 * 
 */
package opentask.gui;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;

/**
 * @author rassler
 *
 */
public class CustomizedTableRenderer extends DefaultTableCellRenderer {
    
	public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
	{
		super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
		if (row%2 == 1) {
			if (isSelected) setBackground(java.awt.Color.gray);
			else setBackground(java.awt.Color.lightGray);
		} else {
			setBackground(table.getBackground());
		}
		return this;                  	
	}

}
