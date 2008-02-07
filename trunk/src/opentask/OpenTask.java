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
import opentask.gui.*;

/**
 * @author rassler
 *
 */
public class OpenTask implements ActionListener{

	// important GUI elements
	static JFrame mainWindow;
	public JPanel mainPanel;
	JMenuBar menuBar;
	JTable table;
	Timer notifyTimer, autoRepeatTimer;
	
	// important Data 
	private Settings settings;
	private boolean dirty;
	private ItemList itemList;
	private ItemListModel model;
	
	// constants
	private final int SECOND = 1000;	// milliseconds
	private final int DELAY = 5 * SECOND; 
	private final int AUTO_REPEAT_DELAY = SECOND * 60 * 5;	// currently 5 minutes

	/**
	 * 
	 */
	public OpenTask() {
		settings = new Settings();
		
		model = new ItemListModel();
		itemList = new ItemList(model);	
		if (!itemList.load())
			JOptionPane.showMessageDialog(mainPanel, "Error: Reading data from disk was not successful!", "Disk Error", JOptionPane.ERROR_MESSAGE);

		TimerNotifyAction timerNotifyListener = new TimerNotifyAction(this, itemList);
		notifyTimer = new Timer(DELAY, timerNotifyListener);
		notifyTimer.start();

		TimerAutoRepeatAction timerAutoRepeatListener = new TimerAutoRepeatAction(this);
		autoRepeatTimer = new Timer(AUTO_REPEAT_DELAY, timerAutoRepeatListener);
		autoRepeatTimer.start();
	}
	
	/**
	 * @param item
	 */
	public void notifyTask(ActionItem item, boolean remind) {
		NotifyDialog dialog = new NotifyDialog(null, "Notification", item, itemList, remind);
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
	public void saveSettings() {
		settings.setPosition(mainWindow.getX(), mainWindow.getY());
		settings.setSize(mainWindow.getWidth(), mainWindow.getHeight());
		settings.save();
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
        menuItem.addActionListener(new ItemDialogAction(null, "New Task", table, itemList));
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
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(23);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setDefaultRenderer(Object.class, new CustomizedTableRenderer());
		TableColumnPacker cPacker = new TableColumnPacker(table);
		cPacker.packColumns();

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(scrollPane);
	}
	
	
	private void applySettings() //(JFrame window)
	{
		if (settings.load())
		{
			mainWindow.setLocation(settings.getX(), settings.getY());
			mainWindow.setSize(settings.getWidth(), settings.getHeight());
		}
		else
		{
			mainWindow.setSize(800, 400);
		}

	}
	
	
	/**
	 * 
	 */
	private static void createAndShowGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame window = new JFrame("OpenTask -- " + Version.getVersion());
		mainWindow = window;
		
		OpenTask task = new OpenTask();
		task.createMainPane();
		task.createMenu();	
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.setJMenuBar(task.menuBar);
		window.setContentPane(task.mainPanel);
		
		window.pack();
		task.applySettings();//(window);
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
    	if (app instanceof OpenTask) 
    	{
    		((OpenTask)app).saveData();
    		((OpenTask)app).saveSettings();
    	}
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
	JTable table;
	
	/**
	 * @param window
	 * @param title
	 * @param list
	 */
	public ItemDialogAction(JFrame window, String title, JTable table, ItemList list) {
		this.window = window;
		this.title = title;
		this.itmList = list;
		this.table = table;
	}
	/**
	 * @param window
	 * @param title
	 * @param item
	 * @param list
	 */
	public ItemDialogAction(JFrame window, String title, JTable table, ActionItem item, ItemList list) {
		this.window = window;
		this.title = title;
		this.item = item;
		this.itmList = list;
		this.table = table;
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
		TableColumnPacker cPacker = new TableColumnPacker(table);
		cPacker.packColumns();
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
		TableColumnPacker cPacker = new TableColumnPacker(table);
		cPacker.packColumns();
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
		TableColumnPacker cPacker = new TableColumnPacker(table);
		cPacker.packColumns();
	}
	
}


/**
 * @author rassler
 *
 */
class TimerNotifyAction implements ActionListener {
	private OpenTask app;
	private ItemList list;
	/**
	 * @param app
	 * @param list
	 */
	public TimerNotifyAction(Object app, ItemList list) {
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
			if (notify.getTimeInMillis() < now.getTimeInMillis() || notify.getTimeInMillis() == now.getTimeInMillis())
			{
				if (item.isNotified() == false)
				{
					item.setNotified(true);
					app.notifyTask(item, false);
					// break, because list has been modified -- avoid concurrency exception
					break;
				}
				else
				{
					Calendar schedule = item.getSchedule();
					if (schedule.getTimeInMillis() < now.getTimeInMillis() || schedule.getTimeInMillis() == now.getTimeInMillis())
					{
						app.notifyTask(item, true);
						// delete item anyway
						list.remove(item);
						// break, because list has been modified -- avoid concurrency exception
						break;
					}
				}
			}
		}
	}
}

/**
 * @author rassler
 *
 */
class TimerAutoRepeatAction implements ActionListener {
	private OpenTask app;
	
	public TimerAutoRepeatAction(Object app) {
		this.app = (OpenTask)app;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		app.saveData();
	}
	
}