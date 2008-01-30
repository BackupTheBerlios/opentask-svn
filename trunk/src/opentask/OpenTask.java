/* 
 * OpenTask.java
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

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Calendar;

import opentask.data.*;

/**
 * @author rassler
 *
 */
public class OpenTask implements ActionListener{

	// important GUI elements
	public JPanel mainPanel;
	JMenuBar menuBar;
	JTable table;
	Timer timer;
	
	// important Data 
	private boolean dirty;
	private ItemList itemList;
	private ItemListModel model;
	
	// constants
	private final int DELAY = 1000;	// milliseconds

	/**
	 * 
	 */
	public OpenTask() {
		model = new ItemListModel();
		itemList = new ItemList(model);	
		if (!itemList.load())
			JOptionPane.showMessageDialog(mainPanel, "Error: Reading data from disk was not successful!", "Disk Error", JOptionPane.ERROR_MESSAGE);
		TimerAction timerListener = new TimerAction(this, itemList);
		timer = new Timer(DELAY, timerListener);
		timer.start();
	}
	
	/**
	 * @param item
	 */
	public void notifyTask(ActionItem item) {
		NotifyDialog dialog = new NotifyDialog(null, "Notification", item, itemList);
		dialog.setVisible(true);
	}
	
	/**
	 * 
	 */
	public void saveData() {
//		if (dirty)
			if (!itemList.save())
				JOptionPane.showMessageDialog(mainPanel, "Error: Writing data to disk was not successful!", "Disk Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * 
	 */
	private void createMenu() {
		JMenu menu;
		JMenuItem menuItem;
		
		menuBar = new JMenuBar();
		// File Menu
		menu = new JMenu("File");
		menuBar.add(menu);
		menuItem = new JMenuItem("Exit", KeyEvent.VK_I);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        //menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        menuItem.addActionListener(new CloseAction(this));
        menu.add(menuItem);

        // Task Menu
        menu = new JMenu("Task");
		menuItem = new JMenuItem("New", KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(new ItemDialogAction(null, "New Task", itemList));
        menu.add(menuItem);
		menuItem = new JMenuItem("Edit", KeyEvent.VK_E);
		menuItem.addActionListener(new ItemEditAction(null, "Edit Task", table, itemList));
        menu.add(menuItem);
		menuItem = new JMenuItem("Delete", KeyEvent.VK_D);
		menuItem.addActionListener(new ItemDeleteAction(null, table, itemList));
        menu.add(menuItem);
        menuBar.add(menu);

        // Options Menu
        menu = new JMenu("Options");
        menuBar.add(menu);

        // About Menu
        menu = new JMenu("About");
        menuBar.add(menu);
	}
	
	/**
	 * 
	 */
	private void createMainPane() {
		mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(23);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(scrollPane);
	}
	
	
	/**
	 * 
	 */
	private static void createAndShowGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame window = new JFrame("OpenTask -- " + Version.getVersion());

		OpenTask task = new OpenTask();
		task.createMainPane();
		task.createMenu();	
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.setJMenuBar(task.menuBar);
		window.setContentPane(task.mainPanel);
		
		window.pack();
		window.setSize(800, 400);
		window.setVisible(true);
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		
    }
	/**
	 * @param e
	 */
	public void windowClosing(WindowEvent e) {		
		saveData();
	}
	
}

/**
 * @author rassler
 *
 */
class CloseAction implements ActionListener {
	Object app;
	/**
	 * @param app
	 */
	public CloseAction(Object app) {
		this.app = app;
	}
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
    	if (app instanceof OpenTask) ((OpenTask)app).saveData();
        System.exit(0);
    }
}

/**
 * @author rassler
 *
 */
class ItemDialogAction implements ActionListener {
	JFrame window;
	String title;
	ActionItem item;
	ItemList itmList;
	/**
	 * @param window
	 * @param title
	 * @param list
	 */
	public ItemDialogAction(JFrame window, String title, ItemList list) {
		this.window = window;
		this.title = title;
		this.itmList = list;
	}
	/**
	 * @param window
	 * @param title
	 * @param item
	 * @param list
	 */
	public ItemDialogAction(JFrame window, String title, ActionItem item, ItemList list) {
		this.window = window;
		this.title = title;
		this.item = item;
		this.itmList = list;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		ItemDialog dialog = new ItemDialog(window, title);
		if (item != null) dialog.setData(item);
		dialog.setVisible(true);
		ActionItem itm = dialog.getData();
		if (itm != null) 
			itmList.add(itm);
	}
}


/**
 * @author rassler
 *
 */

class ItemDeleteAction implements ActionListener
{
	JFrame window;
	ItemList list;
	JTable table;
	
	/**
	 * @param window
	 */
	public ItemDeleteAction(JFrame window, JTable table, ItemList list)
	{
		this.window = window;
		this.table = table;
		this.list = list;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		int sel = table.getSelectedRow();
		if (sel > -1)
		{
			ActionItem item = null;
			int cnt = -1;
			Iterator<ActionItem> it = list.iterator();
			while (cnt < sel) {
				item = it.next();
				++cnt;
			}
			list.remove(item);
		}
	}
	
}


class ItemEditAction implements ActionListener
{
	JFrame window;
	ItemList list;
	JTable table;
	String title;

	public ItemEditAction(JFrame window, String title, JTable table, ItemList list)
	{
		this.window = window;
		this.table = table;
		this.list = list;
		this.title = title;
	}

	public void actionPerformed(ActionEvent e)
	{
		int sel = table.getSelectedRow();
		if (sel > -1)
		{
			ActionItem item = null;
			int cnt = -1;
			Iterator<ActionItem> it = list.iterator();
			while (cnt < sel) {
				item = it.next();
				++cnt;
			}
			ItemDialog dialog = new ItemDialog(window, title);
			dialog.setData(item);
			dialog.setVisible(true);
			if (dialog.getCloseOP() == ItemDialog.OK)
			{
				list.remove(item);
				item = null;
				item = dialog.getData();
				if (item != null)
					list.add(item);
			}
		}
	}
	
}


/**
 * @author rassler
 *
 */
class TimerAction implements ActionListener {
	private OpenTask app;
	private ItemList list;
	/**
	 * @param app
	 * @param list
	 */
	public TimerAction(Object app, ItemList list) {
		this.app = (OpenTask)app;
		this.list = list;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Calendar now = Calendar.getInstance();
		Iterator<ActionItem> it = list.iterator();
		while (it.hasNext()) {
			ActionItem item = it.next();
			Calendar notify = item.getNotifyTime();
			if (notify.get(Calendar.YEAR) == now.get(Calendar.YEAR)
					&& notify.get(Calendar.MONTH) == now.get(Calendar.MONTH)
					&& notify.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH)
					&& notify.get(Calendar.HOUR_OF_DAY) == now.get(Calendar.HOUR_OF_DAY)
					&& notify.get(Calendar.MINUTE) == now.get(Calendar.MINUTE)
					)
			{
				if (item.isNotified() == false)
					app.notifyTask(item);
			}
			Calendar schedule = item.getSchedule();
			if (schedule.getTimeInMillis() < now.getTimeInMillis() || schedule.getTimeInMillis() == now.getTimeInMillis())
			{
				// notify, although time is over
				app.notifyTask(item);
				// delete item anyway
				list.remove(item);
			}
		}
	}
}