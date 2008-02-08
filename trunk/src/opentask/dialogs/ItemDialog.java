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
package opentask.dialogs;

import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

import opentask.data.*;

/**
 * @author rassler
 *
 */
public class ItemDialog extends JDialog implements ActionListener, ChangeListener, KeyListener {
	static final long serialVersionUID = 1;
	private ActionItem item;
	private boolean dirty;
	private int closeOP;
	
	public static int NONE = 0;
	public static int OK = 1;
	public static int CANCEL = -1;
	
	// GUI fields
	private JTextField tName;
	private JSpinner dSchedDateEditor;
	private JSpinner dSchedTimeEditor;
	private JTextField tDuration;
	private JSpinner dNoteDateEditor;
	private JSpinner dNoteTimeEditor;
	private JTextField tNextNotfy;
	private JTextArea tDescription;
	private JButton bCancel;
	private JButton bOk;
	
	/**
	 * @param owner
	 * @param title
	 */
	public ItemDialog(Frame owner, String title)
	{
		super(owner, title, true);
		dirty = false;
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
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
		tName = new JTextField(15);
		grid.setConstraints(tName, c);
		tName.addKeyListener(this);
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
		dSchedDateEditor = new JSpinner();
		dSchedDateEditor.setModel(new SpinnerDateModel(now.getTime(), past.getTime(), null, Calendar.YEAR));
		dSchedDateEditor.setEditor(new JSpinner.DateEditor(dSchedDateEditor, "dd.MM.yyyy"));
		grid.setConstraints(dSchedDateEditor, c);
		dSchedDateEditor.addChangeListener(this);
		add(dSchedDateEditor);
		
		JLabel lSchedTime = new JLabel("Time");
		grid.setConstraints(lSchedTime, c);
		add(lSchedTime);
		c.gridwidth = GridBagConstraints.REMAINDER;

		dSchedTimeEditor = new JSpinner();
		dSchedTimeEditor.setModel(new SpinnerDateModel(now.getTime(), null, null, Calendar.MINUTE));
		dSchedTimeEditor.setEditor(new JSpinner.DateEditor(dSchedTimeEditor, "HH:mm"));
		grid.setConstraints(dSchedTimeEditor, c);
		dSchedTimeEditor.addChangeListener(this);
		add(dSchedTimeEditor);
		// fourth row : duration
		c.weightx = 2;
		c.gridwidth = 1;
		JLabel lDuration = new JLabel("Duration");
		grid.setConstraints(lDuration, c);
		add(lDuration);
		c.gridwidth = GridBagConstraints.REMAINDER;
		tDuration = new JTextField(5);
		grid.setConstraints(tDuration, c);
		tDuration.addKeyListener(this);
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
		dNoteDateEditor = new JSpinner();
		dNoteDateEditor.setModel(new SpinnerDateModel(now.getTime(), past.getTime(), null, Calendar.YEAR));
		dNoteDateEditor.setEditor(new JSpinner.DateEditor(dNoteDateEditor, "dd.MM.yyyy"));
		grid.setConstraints(dNoteDateEditor, c);
		dNoteDateEditor.addChangeListener(this);
		add(dNoteDateEditor);

		JLabel lNotTime = new JLabel("Time");
		grid.setConstraints(lNotTime, c);
		add(lNotTime);
		c.gridwidth = GridBagConstraints.REMAINDER;

		dNoteTimeEditor = new JSpinner();
		dNoteTimeEditor.setModel(new SpinnerDateModel(now.getTime(), null, null, Calendar.MINUTE));
		dNoteTimeEditor.setEditor(new JSpinner.DateEditor(dNoteTimeEditor, "HH:mm"));
		grid.setConstraints(dNoteTimeEditor, c);
		dNoteTimeEditor.addChangeListener(this);
		add(dNoteTimeEditor);
		// seventh row : nex Notification
		c.weightx = 2;
		c.gridwidth = 1;
		JLabel lNextNotify = new JLabel("Next Notification");
		grid.setConstraints(lNextNotify, c);
		add(lNextNotify);
		c.gridwidth = GridBagConstraints.REMAINDER;
		tNextNotfy = new JTextField(5);
		grid.setConstraints(tNextNotfy, c);
		tNextNotfy.addKeyListener(this);
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
		tDescription = new JTextArea(5,30);
		tDescription.setBorder(new EtchedBorder());
		tDescription.setLineWrap(true);
		grid.setConstraints(tDescription, c);
		tDescription.addKeyListener(this);
		add(tDescription);
		
		// tenth row: buttons
		c.weightx = 2;
		c.gridwidth = 2;
		JLabel lNone = new JLabel();
		grid.setConstraints(lNone, c);
		add(lNone);
		c.weightx = 1;
		bOk = new JButton("Ok");
		grid.setConstraints(bOk, c);
		bOk.addActionListener(this);
		add(bOk);
		c.gridwidth = GridBagConstraints.REMAINDER;
		bCancel = new JButton("Cancel");
		grid.setConstraints(bCancel, c);
		bCancel.addActionListener(this);
		add(bCancel);

		closeOP = NONE;
		
		pack();
	}
	
	/**
	 * @param item
	 */
	public void setData(ActionItem item) {
		tName.setText(item.getItemName());
		dSchedDateEditor.setValue(item.getSchedule().getTime());
		dSchedTimeEditor.setValue(item.getSchedule().getTime());
		tDuration.setText(new Integer(item.getDuration()).toString());
		dNoteDateEditor.setValue(item.getNotifyTime().getTime());
		dNoteTimeEditor.setValue(item.getNotifyTime().getTime());
		tNextNotfy.setText(new Integer(item.getNextNotification()).toString());
		tDescription.setText(item.getDescription());
	}

	public int getCloseOP() {
		return closeOP;
	}
	
	/**
	 * @return
	 */
	public ActionItem getData() {
		return item;
	}
	
	/**
	 * @return
	 */
	private boolean checkFields() {
		if (tName.getText().length() == 0)
		{
			JOptionPane.showMessageDialog(this, "A name for the task is mandatory!", "Name needed!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(bCancel))
		{
			setVisible(false);
			closeOP = CANCEL;
		}
		else if (obj.equals(bOk))
		{
			if (dirty) {
				if (checkFields())
				{
					Calendar schedule = Calendar.getInstance();
					Calendar notification = Calendar.getInstance();
					JSpinner.DateEditor editor;
					SpinnerDateModel dModel;
					SpinnerDateModel tModel;
					Calendar dCal = Calendar.getInstance();
					Calendar tCal = Calendar.getInstance();
					
					editor = (JSpinner.DateEditor)dSchedDateEditor.getEditor();
					dModel = editor.getModel();
					dCal.setTime(dModel.getDate());
					editor = (JSpinner.DateEditor)dSchedTimeEditor.getEditor();
					tModel = editor.getModel();
					tCal.setTime(tModel.getDate());
					schedule.set(dCal.get(Calendar.YEAR), dCal.get(Calendar.MONTH), dCal.get(Calendar.DAY_OF_MONTH), tCal.get(Calendar.HOUR_OF_DAY), tCal.get(Calendar.MINUTE), 0);
					
					editor = (JSpinner.DateEditor)dNoteDateEditor.getEditor();
					dModel = editor.getModel();
					dCal.setTime(dModel.getDate());
					editor = (JSpinner.DateEditor)dNoteTimeEditor.getEditor();
					tModel = editor.getModel();
					tCal.setTime(tModel.getDate());
					notification.set(dCal.get(Calendar.YEAR), dCal.get(Calendar.MONTH), dCal.get(Calendar.DAY_OF_MONTH), tCal.get(Calendar.HOUR_OF_DAY), tCal.get(Calendar.MINUTE), 0);
					
					item = new ActionItem(tName.getText(), schedule, notification, new Integer(tDuration.getText()), new Integer(tNextNotfy.getText()), tDescription.getText());
					setVisible(false);
				}
			}
			else setVisible(false);
			closeOP = OK;
		}
    }

	// Listeners to detect modification of data
	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		dirty = true;
	}
}

