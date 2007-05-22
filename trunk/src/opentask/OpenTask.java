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

import opentask.data.*;

/**
 * @author rassler
 *
 */
public class OpenTask implements ActionListener{

	// important GUI elements
	JPanel mainPanel;
	JMenuBar menuBar;
	JTable table;
	
	// important Data 
	private boolean dirty;
	private ItemList itemList;
	private ItemListModel model;

	public OpenTask() {
		model = new ItemListModel();
		itemList = new ItemList(model);	
		if (!itemList.load())
			JOptionPane.showMessageDialog(mainPanel, "Error: Reading data from disk was not successful!", "Disk Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void saveData() {
//		if (dirty)
			if (!itemList.save())
				JOptionPane.showMessageDialog(mainPanel, "Error: Writing data to disk was not successful!", "Disk Error", JOptionPane.ERROR_MESSAGE);
	}
	
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
        menu.add(menuItem);
		menuItem = new JMenuItem("Delete", KeyEvent.VK_D);
        menu.add(menuItem);
        menuBar.add(menu);

        // Options Menu
        menu = new JMenu("Options");
        menuBar.add(menu);

        // About Menu
        menu = new JMenu("About");
        menuBar.add(menu);
	}
	
	private void createMainPane() {
		mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(scrollPane);
	}
	
	
	private static void createAndShowGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame window = new JFrame("OpenTask");

		OpenTask task = new OpenTask();
		task.createMainPane();
		task.createMenu();	
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setJMenuBar(task.menuBar);
		window.setContentPane(task.mainPanel);
		
		window.pack();
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

	public void actionPerformed(ActionEvent e) {
		
    }
	public void windowClosing(WindowEvent e) {		
		saveData();
	}
	
}

class CloseAction implements ActionListener {
	Object app;
	public CloseAction(Object app) {
		this.app = app;
	}
    public void actionPerformed(ActionEvent e) {
    	if (app instanceof OpenTask) ((OpenTask)app).saveData();
        System.exit(0);
    }
}

class ItemDialogAction implements ActionListener {
	JFrame window;
	String title;
	ActionItem item;
	ItemList itmList;
	public ItemDialogAction(JFrame window, String title, ItemList list) {
		this.window = window;
		this.title = title;
		this.itmList = list;
	}
	public ItemDialogAction(JFrame window, String title, ActionItem item, ItemList list) {
		this.window = window;
		this.title = title;
		this.item = item;
		this.itmList = list;
	}
	public void actionPerformed(ActionEvent e) {
		ItemDialog dialog = new ItemDialog(window, title);
		if (item != null) dialog.setData(item);
		dialog.setVisible(true);
		ActionItem itm = dialog.getData();
		if (itm != null) 
			itmList.add(itm);
	}
}