/*
 * (c) 2016 Abil'I.T. http://abilit.eu/
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.scada_lts.dao.model.event;

import java.util.List;

import com.serotonin.mango.rt.event.AlarmLevels;
import com.serotonin.mango.rt.event.EventInstance.RtnCauses;
import com.serotonin.mango.vo.UserComment;

/**
 * Event bean
 *
 * @author Grzesiek Bylica Abil'I.T. development team, sdt@abilit.eu
 */
public class Event {

	private long id;

	/**
	 * Configuration field. Provided by the event producer. Identifies where the
	 * event came from and what it means. (EventType)
	 */
	private int eventType;

	
	private int typeRef1;
	
	private int typeRef2;
	
	/**
	 * State field. The time that the event became active.
	 */
	private long activeTimestamp;

	/**
	 * Configuration field. Is this type of event capable of returning to normal
	 * (true), or is it stateless (false).
	 */
	private boolean rtnApplicable;

	/**
	 * State field. The time that the event returned to normal.
	 */
	private long rtnTimestamp;

	/**
	 * State field. The action that caused the event to RTN. One of
	 * {@link RtnCauses}
	 */
	private int rtnCause;

	/**
	 * Configuration field. The alarm level assigned to the event.
	 * 
	 * @see AlarmLevels
	 */
	private int alarmLevel;

	/**
	 * Configuration field. The message associated with the event.
	 */
	private String message;
	
	/**
	 * ?
	 */
	private long ackTS;

	/**
	 * User comments on the event. Added in the events interface after the event
	 * has been raised.
	 */
	private List<UserComment> eventComments;
	
	private long actUserId;
	
	private String userName;
	
	private long alternateAckSource;
	
	//
    //
    // These fields are used only in the context of access by a particular user, providing state filled in from
    // the userEvents table.
    private boolean userNotified;
	//TODO to rewrite - User have event not event have user silenced;
	private boolean silenced;

	public Event() {
		//
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRtnTimestamp() {
		return rtnTimestamp;
	}

	public void setRtnTimestamp(long rtnTimestamp) {
		this.rtnTimestamp = rtnTimestamp;
	}

	public int getRtnCause() {
		return rtnCause;
	}

	public void setRtnCause(int rtnCause) {
		this.rtnCause = rtnCause;
	}

	public List<UserComment> getEventComments() {
		return eventComments;
	}

	public void setEventComments(List<UserComment> eventComments) {
		this.eventComments = eventComments;
	}

	public int getEventType() {
		return eventType;
	}

	public long getActiveTimestamp() {
		return activeTimestamp;
	}

	public boolean isRtnApplicable() {
		return rtnApplicable;
	}

	public int getAlarmLevel() {
		return alarmLevel;
	}

	public String getMessage() {
		return message;
	}

	public int getTypeRef1() {
		return typeRef1;
	}

	public void setTypeRef1(int typeRef1) {
		this.typeRef1 = typeRef1;
	}

	public int getTypeRef2() {
		return typeRef2;
	}

	public void setTypeRef2(int typeRef2) {
		this.typeRef2 = typeRef2;
	}

	public long getActUserId() {
		return actUserId;
	}

	public void setActUserId(long actUserId) {
		this.actUserId = actUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getAlternateAckSource() {
		return alternateAckSource;
	}

	public void setAlternateAckSource(long alternateAckSource) {
		this.alternateAckSource = alternateAckSource;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public void setActiveTimestamp(long activeTimestamp) {
		this.activeTimestamp = activeTimestamp;
	}

	public void setRtnApplicable(boolean rtnApplicable) {
		this.rtnApplicable = rtnApplicable;
	}

	public void setAlarmLevel(int alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getAckTS() {
		return ackTS;
	}

	public void setAckTS(long ackTS) {
		this.ackTS = ackTS;
	}
	
	public boolean isUserNotified() {
		return userNotified;
	}

	public void setUserNotified(boolean userNotified) {
		this.userNotified = userNotified;
	}

	public boolean isSilenced() {
		return silenced;
	}

	public void setSilenced(boolean silenced) {
		this.silenced = silenced;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (ackTS ^ (ackTS >>> 32));
		result = prime * result + (int) (actUserId ^ (actUserId >>> 32));
		result = prime * result + (int) (activeTimestamp ^ (activeTimestamp >>> 32));
		result = prime * result + alarmLevel;
		result = prime * result + (int) (alternateAckSource ^ (alternateAckSource >>> 32));
		result = prime * result + ((eventComments == null) ? 0 : eventComments.hashCode());
		result = prime * result + eventType;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + (rtnApplicable ? 1231 : 1237);
		result = prime * result + rtnCause;
		result = prime * result + (int) (rtnTimestamp ^ (rtnTimestamp >>> 32));
		result = prime * result + (silenced ? 1231 : 1237);
		result = prime * result + typeRef1;
		result = prime * result + typeRef2;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + (userNotified ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (ackTS != other.ackTS)
			return false;
		if (actUserId != other.actUserId)
			return false;
		if (activeTimestamp != other.activeTimestamp)
			return false;
		if (alarmLevel != other.alarmLevel)
			return false;
		if (alternateAckSource != other.alternateAckSource)
			return false;
		if (eventComments == null) {
			if (other.eventComments != null)
				return false;
		} else if (!eventComments.equals(other.eventComments))
			return false;
		if (eventType != other.eventType)
			return false;
		if (id != other.id)
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (rtnApplicable != other.rtnApplicable)
			return false;
		if (rtnCause != other.rtnCause)
			return false;
		if (rtnTimestamp != other.rtnTimestamp)
			return false;
		if (silenced != other.silenced)
			return false;
		if (typeRef1 != other.typeRef1)
			return false;
		if (typeRef2 != other.typeRef2)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userNotified != other.userNotified)
			return false;
		return true;
	}

}