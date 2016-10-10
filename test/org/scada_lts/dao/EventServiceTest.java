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

package org.scada_lts.dao;

import org.junit.Before;
import org.junit.Test;
import org.scada_lts.mango.adapter.MangoEvent;
import org.scada_lts.mango.service.EventService;

import com.serotonin.mango.rt.event.EventInstance;
import com.serotonin.mango.rt.event.type.DataSourceEventType;
import com.serotonin.mango.rt.event.type.EventType;

import static org.junit.Assert.assertEquals;

/**
 * Event DAO base on before version EventDao 
 *
 * @author Grzesiek Bylica Abil'I.T. development team, sdt@abilit.eu
 */
public class EventServiceTest extends TestDAO {
	
	private MangoEvent eventService;
	
	
	@Before
	public void init() {
		eventService = new EventService();
	}
	
	@Test
	public void saveEvent() {
		
		EventType type = new DataSourceEventType(1,1);
		long activeTS = 10;
		boolean applicable = true;
		int alarmLevel = 3;
					
		EventInstance e = new EventInstance(type, activeTS,	applicable, alarmLevel, null, null);
		
		eventService.saveEvent(e);
	
		int checkCountSaveEvent = eventService.getEventCount();
		
		boolean resSaveEvent = checkCountSaveEvent == 1;
		
		assertEquals(true,resSaveEvent);
	}
	

}