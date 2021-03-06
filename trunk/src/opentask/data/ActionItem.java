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
	private boolean valid;
	private boolean notified;
	
	public static int MINUTE = 1; 
	public static int HOUR = MINUTE * 60;
	public static int DAY = HOUR * 24;
	public static int WEEK = DAY * 7;
	public static int DEFAULT_DELAY = 5 * MINUTE;	// Minutes
	
	/**
	 * 
	 */
	public ActionItem() {
		super();
		schedule = Calendar.getInstance();
		notifyTime = Calendar.getInstance();
		valid = true;
		notified = false;
	}

	/**
	 * @param itemName
	 * @param schedule
	 * @param notifyTime
	 * @param duration
	 * @param nextNotification
	 * @param description
	 */
	public ActionItem(String itemName, Calendar schedule, Calendar notifyTime, int duration, int nextNotification, String description) {
		super();
		this.itemName = itemName;
		this.schedule = schedule;
		this.notifyTime = notifyTime;
		this.duration = duration;
		if (nextNotification == 0)
			this.nextNotification = DEFAULT_DELAY;
		else
			this.nextNotification = nextNotification;
		this.description = description;
		valid = true;
		notified = false;
	}
	
	/**
	 * @param itemName
	 * @param schedule
	 */
	public ActionItem(String itemName, Calendar schedule) {
		super();
		this.itemName = itemName;
		this.schedule = schedule;
		notifyTime = schedule;
		duration = 0;
		nextNotification = DEFAULT_DELAY;
		valid = true;
		notified = false;
	}
	
	/**
	 * @return
	 */
	public int getDuration() {
		return duration;
	}
	
	/**
	 * @param duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	/**
	 * @return
	 */
	public int getNextNotification() {
		return nextNotification;
	}
	
	/**
	 * @param nextNotification
	 */
	public void setNextNotification(int nextNotification) {
		this.nextNotification = nextNotification;
	}
	
	/**
	 * @return
	 */
	public Calendar getNotifyTime() {
		return notifyTime;
	}
	
	/**
	 * @param notifyTime
	 */
	public void setNotifyTime(Calendar notifyTime) {
		this.notifyTime = notifyTime;
	}
	
	/**
	 * @return
	 */
	public Calendar getSchedule() {
		return schedule;
	}
	
	/**
	 * @param schedule
	 */
	public void setSchedule(Calendar schedule) {
		this.schedule = schedule;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * @return
	 */
	public boolean isNotified() {
		return notified;
	}

	/**
	 * @param notified
	 */
	public void setNotified(boolean notified) {
		this.notified = notified;
	}
	
}
