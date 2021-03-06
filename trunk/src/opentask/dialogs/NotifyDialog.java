/* 
 * NotifyDialog.java
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

import java.awt.Frame;
import javax.swing.*;
import java.awt.event.*;
import java.util.Calendar;

import opentask.data.*;

/**
 * @author rassler
 *
 */
public class NotifyDialog extends JDialog implements ActionListener{
	public static final long serialVersionUID = 1;
	
	private ActionItem item;
	private ItemList itemList;
	
	JButton bDone;
	JButton bLater;
	
	/**
	 * @param owner
	 * @param title
	 * @param itm
	 * @param list
	 */
	public NotifyDialog(Frame owner, String title, ActionItem itm, ItemList list, boolean remind) {
		super(owner, title, true);
		item = itm;
		itemList = list;
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JLabel name = new JLabel(item.getItemName());
		name.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		add(name);
		JLabel date = new JLabel(item.getSchedule().getTime().toString());
		add(date);
		JTextArea description = new JTextArea(item.getDescription(), 5, 30);
		description.setEditable(false);
		add(description);
		JSeparator separator = new JSeparator();
		add(separator);
		
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
		bDone = new JButton("Done!");
		bDone.addActionListener(this);
		pane.add(bDone);
		if (!remind)
		{
			bLater = new JButton("Remind me later again!");
			bLater.addActionListener(this);
			pane.add(bLater);
		}
		add(pane);
		
		pack();
		toFront();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(bDone)) {
			itemList.remove(item);
			item.setNotified(true);
			itemList.add(item);
			// do nothing... wait for item to be invalid
			setVisible(false);
		}
		else if (e.getSource().equals(bLater)){
			itemList.remove(item);
			Calendar cal = Calendar.getInstance();
			// FIXME: use real delay value (currently only default delay is used)
			cal.add(Calendar.MINUTE, ActionItem.DEFAULT_DELAY); 
			item.setNotifyTime(cal);
			item.setNotified(false);
			itemList.add(item);
			setVisible(false);
		}
	}
}
