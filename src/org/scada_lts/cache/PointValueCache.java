/*
 * (c) 2015 Abil'I.T. http://abilit.eu/
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

package org.scada_lts.cache;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scada_lts.config.ScadaConfig;
import org.scada_lts.dao.model.point.PointValue;
import org.scada_lts.dao.model.point.PointValueAdnnotation;
import org.scada_lts.dao.pointvalues.PointValueAdnnotationsDAO;
import org.scada_lts.dao.pointvalues.PointValueDAO;

/** 
 * Class responsible for buffering data of PointValue
 * 
 * @author grzegorz bylica Abil'I.T. development team, sdt@abilit.eu
 */

public class PointValueCache {
	
	private static final Log LOG = LogFactory.getLog(PointValueCache.class);
	
	private static PointValueCache instance = null;
	private PointValueDAO pointValueDAO;
	private PointValueAdnnotationsDAO pointValueAdnnotationDAO;
	
	
	// key id dataPointValue
	private TreeMap<Long, PointValue> cachePointValues = new TreeMap<Long, PointValue>();
	// key id dataPointValue
	private TreeMap<Long, List<PointValueAdnnotation>> cachePointValueAdnnotations = new TreeMap<Long, List<PointValueAdnnotation>>();
	
	private PointValueCache() {
		// initalize in 
		// load data base on config for example 30 days
		pointValueDAO = new PointValueDAO();
		Long start = System.currentTimeMillis();
		long buffer;
		try {
			buffer = ScadaConfig.getInstance().getLong(ScadaConfig.TIME_BUFFER_POINT_VALUES, ScadaConfig.DEFAULT_BUFFER_POINT_VALUES);
		} catch (IOException e) {
			LOG.error(e);
			buffer = ScadaConfig.DEFAULT_BUFFER_POINT_VALUES;
		}
		Long end = (start - buffer);
		List<PointValue> pointValues = pointValueDAO.filtered(
				PointValueDAO.POINT_VALUE_FILTER_BASE_ON_TIME_STAMP, new Object[]{start,end});
		
		for (PointValue pv: pointValues) {
			cachePointValues.put(pv.getId(), pv);
			cachePointValueAdnnotations.put(pv.getId(), pointValueAdnnotationDAO.findByPointValueId(pv.getId()));
		}
	
	}
	
	private void expireAfterWrite() {
		for (Map.Entry<Long, PointValue> entry : cachePointValues.entrySet()) {
			if (calculateToRemove(entry.getValue().getPointValue().getTime())) {
				cachePointValues.remove(entry.getKey());
				cachePointValueAdnnotations.remove(entry.getValue().getDataPointId());
			}
		}	
	}
	
	private boolean calculateToRemove(Long ts) {
		Long now = System.currentTimeMillis();
		long buffer;
		try {
			buffer = ScadaConfig.getInstance().getLong(ScadaConfig.TIME_BUFFER_POINT_VALUES, ScadaConfig.DEFAULT_BUFFER_POINT_VALUES);
		} catch (IOException e) {
			LOG.error(e);
			buffer = ScadaConfig.DEFAULT_BUFFER_POINT_VALUES;
		}
		
		long startBuffer = (now-buffer);
		boolean res = (startBuffer>ts);
		
		return res;
	}
	
	public static PointValueCache getInstance() throws Exception {
		if (LOG.isTraceEnabled()) {
		  LOG.trace("Get PointValue and PointValueAdnnotation instance ");
		}
		if (instance == null) {
			instance = new PointValueCache();
		}
		return instance;
	}
	
	public void write(PointValue pv, PointValueAdnnotation pva) {
		
		// save return idValue
		// add to cache
		// save adnnotation
		// add to cache
		
		expireAfterWrite();
	}
	
}
