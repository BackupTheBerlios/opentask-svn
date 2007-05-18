/* 
 * ItemDialog.java
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
package opentask;

import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;

import opentask.data.*;

/**
 * @author rassler
 *
 */
public class ItemDialog extends JDialog implements ActionListener {
	static final long serialVersionUID = 1;
	
	public ItemDialog(Frame owner, String title)
	{
		super(owner, title, true);
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		this.setLayout(grid);
		
		Calendar past = Calendar.getInstance();
		past.add(Calendar.MONDAY, -1);
		Calendar now = Calendar.getInstance();

		// first row : task name
		c.weightx = 2;
		c.gridwidth = 1;
		JLabel lName = new JLabel("Task Name");
		grid.setConstraints(lName, c);
		add(lName);
		//c.weightx = 2;
		c.gridwidth = GridBagConstraints.REMAINDER;
		JTextField tName = new JTextField(15);
		grid.setConstraints(tName, c);
		add(tName);
		// second row : schedule label
		c.weightx = 4;
		c.gridwidth = GridBagConstraints.REMAINDER;
		JLabel lSchedule = new JLabel("Schedule");
		grid.setConstraints(lSchedule, c);
		add(lSchedule);
		// third row : schedule fields
		c.weightx = 1;
		c.gridwidth = 1;
		JLabel lSchedDate = new JLabel("Date");
		grid.setConstraints(lSchedDate, c);
		add(lSchedDate);
			// Schedule Date Editor
		JSpinner dSchedDateEditor = new JSpinner();
		dSchedDateEditor.setModel(new SpinnerDateModel(now.getTime(), past.getTime(), null, Calendar.YEAR));
		dSchedDateEditor.setEditor(new JSpinner.DateEditor(dSchedDateEditor, "dd.MM.yyyy"));
		grid.setConstraints(dSchedDateEditor, c);
		add(dSchedDateEditor);
		
		JLabel lSchedTime = new JLabel("Time");
		grid.setConstraints(lSchedTime, c);
		add(lSchedTime);
		c.gridwidth = GridBagConstraints.REMAINDER;

		JSpinner dSchedTimeEditor = new JSpinner();
		dSchedTimeEditor.setModel(new SpinnerDateModel(now.getTime(), null, null, Calendar.MINUTE));
		dSchedTimeEditor.setEditor(new JSpinner.DateEditor(dSchedTimeEditor, "hh:mm"));
		grid.setConstraints(dSchedTimeEditor, c);
		add(dSchedTimeEditor);
		// fourth row : duration
		c.weightx = 2;
		c.gridwidth = 1;
		JLabel lDuration = new JLabel("Duration");
		grid.setConstraints(lDuration, c);
		add(lDuration);
		c.gridwidth = GridBagConstraints.REMAINDER;
		JTextField tDuration = new JTextField(5);
		grid.setConstraints(tDuration, c);
		add(tDuration);
		// fifth row : notification label
		c.weightx = 4;
		c.gridwidth = GridBagConstraints.REMAINDER;
		JLabel lNotification = new JLabel("Notification");
		grid.setConstraints(lNotification, c);
		add(lNotification);
		// sixth row : notification fields
		c.weightx = 1;
		c.gridwidth = 1;
		JLabel lNotDate = new JLabel("Date");
		grid.setConstraints(lNotDate, c);
		add(lNotDate);
			// Notification Date Editor
		JSpinner dNoteDateEditor = new JSpinner();
		dNoteDateEditor.setModel(new SpinnerDateModel(now.getTime(), past.getTime(), null, Calendar.YEAR));
		dNoteDateEditor.setEditor(new JSpinner.DateEditor(dNoteDateEditor, "dd.MM.yyyy"));
		grid.setConstraints(dNoteDateEditor, c);
		add(dNoteDateEditor);

		JLabel lNotTime = new JLabel("Time");
		grid.setConstraints(lNotTime, c);
		add(lNotTime);
		c.gridwidth = GridBagConstraints.REMAINDER;

		JSpinner dNoteTimeEditor = new JSpinner();
		dNoteTimeEditor.setModel(new SpinnerDateModel(now.getTime(), null, null, Calendar.MINUTE));
		dNoteTimeEditor.setEditor(new JSpinner.DateEditor(dNoteTimeEditor, "hh:mm"));
		grid.setConstraints(dNoteTimeEditor, c);
		add(dNoteTimeEditor);
		// seventh row : nex Notification
		c.weightx = 2;
		c.gridwidth = 1;
		JLabel lNextNotify = new JLabel("Next Notification");
		grid.setConstraints(lNextNotify, c);
		add(lNextNotify);
		c.gridwidth = GridBagConstraints.REMAINDER;
		JTextField tNextNotfy = new JTextField(5);
		grid.setConstraints(tNextNotfy, c);
		add(tNextNotfy);
		// eightth row : description
		c.weightx = 4;
		c.gridwidth = GridBagConstraints.REMAINDER;
		JLabel lDescription = new JLabel("Description");
		grid.setConstraints(lDescription, c);
		add(lDescription);
		// nineth row : description field
		c.weightx = 4;
		c.gridwidth = GridBagConstraints.REMAINDER;
		JTextArea tDescription = new JTextArea(5,30);
		tDescription.setBorder(new EtchedBorder());
		tDescription.setLineWrap(true);
		grid.setConstraints(tDescription, c);
		add(tDescription);
		
		// tenth row: buttons
		c.weightx = 2;
		c.gridwidth = 2;
		JLabel lNone = new JLabel();
		grid.setConstraints(lNone, c);
		add(lNone);
		c.weightx = 1;
		JButton bOk = new JButton("Ok");
		grid.setConstraints(bOk, c);
		add(bOk);
		c.gridwidth = GridBagConstraints.REMAINDER;
		JButton bCancel = new JButton("Cancel");
		grid.setConstraints(bCancel, c);
		add(bCancel);

		pack();
	}
	
	public void setData(ActionItem item) {
		
	}

	public ActionItem getData() {
		return new ActionItem(); //FIXME
	}
	
	public void actionPerformed(ActionEvent e) {
        
    }
	
}
