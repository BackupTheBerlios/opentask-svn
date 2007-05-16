/* 
 * ActionItem.java
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

import java.util.Calendar;

/**
 * @author rassler
 *
 */
public class ActionItem {

	private Calendar schedule;
	private Calendar notifyTime;
	private int duration;
	private int nextNotification;
	private String itemName;
	private String description;
	
	public static int MINUTE = 1000 * 60; // Milliseconds
	public static int HOUR = MINUTE * 60;
	public static int DAY = HOUR * 24;
	public static int WEEK = DAY * 7;
	public static int DEFAULT_DELAY = 5 * MINUTE;
	
	public ActionItem() {
		super();
		schedule = Calendar.getInstance();
		notifyTime = Calendar.getInstance();
	}
	
	public ActionItem(Calendar schedule, Calendar notifyTime, int duration, int nextNotification) {
		super();
		this.schedule = schedule;
		this.notifyTime = notifyTime;
		this.duration = duration;
		this.nextNotification = nextNotification;
	}
	
	public ActionItem(Calendar schedule) {
		super();
		this.schedule = schedule;
		notifyTime = schedule;
		duration = 0;
		nextNotification = DEFAULT_DELAY;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getNextNotification() {
		return nextNotification;
	}
	
	public void setNextNotification(int nextNotification) {
		this.nextNotification = nextNotification;
	}
	
	public Calendar getNotifyTime() {
		return notifyTime;
	}
	
	public void setNotifyTime(Calendar notifyTime) {
		this.notifyTime = notifyTime;
	}
	
	public Calendar getSchedule() {
		return schedule;
	}
	
	public void setSchedule(Calendar schedule) {
		this.schedule = schedule;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
}
