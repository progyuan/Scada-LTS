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

package org.scada_lts.dao.pointvalues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scada_lts.dao.DAO;
import org.scada_lts.dao.GenericDaoCR;
import org.scada_lts.dao.model.point.PointValue;
import org.scada_lts.dao.model.point.PointValueAdnnotation;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.serotonin.mango.rt.dataImage.AnnotatedPointValueTime;
import com.serotonin.mango.rt.dataImage.SetPointSource;

/** 
 * 
 * @author grzegorz bylica Abil'I.T. development team, sdt@abilit.eu
 * 
 */
public class PointValueAdnnotationsDAO implements GenericDaoCR<PointValueAdnnotation> {
	
	private static final Log LOG = LogFactory.getLog(PointValueAdnnotationsDAO.class);
	
	private final static String  COLUMN_NAME_POINT_VALUE_ID = "pointValueId";
	private final static String  COLUMN_NAME_TEXT_POINT_VALUE_SHORT = "textPointValueShort";
	private final static String  COLUMN_NAME_TEXT_POINT_VALUE_LONG = "textPointValueLong";
	private final static String  COLUMN_NAME_SOURCE_TYPE = "sourceType";
	private final static String  COLUMN_NAME_SOURCE_ID = "sourceId";
	
	// @formatter:off
	private static final String POINT_VALUE_ADNNOTATIONS_SELECT = ""
			+ "select "
				+COLUMN_NAME_POINT_VALUE_ID + ", "
				+COLUMN_NAME_TEXT_POINT_VALUE_SHORT + ", "
				+COLUMN_NAME_TEXT_POINT_VALUE_LONG + ", "
				+COLUMN_NAME_SOURCE_TYPE + ", "
				+COLUMN_NAME_SOURCE_ID + " "
			+ "from "
				+"pointValueAnnotations ";
	
	
	private static final String POINT_VALUE_ADNNOTATIONS_INSERT = ""
			+ "insert pointValueAnnotations  ("
				+ COLUMN_NAME_POINT_VALUE_ID + "," 
				+ COLUMN_NAME_TEXT_POINT_VALUE_SHORT + "," 
				+ COLUMN_NAME_TEXT_POINT_VALUE_LONG + "," 
				+ COLUMN_NAME_SOURCE_TYPE + ","
				+ COLUMN_NAME_SOURCE_ID
			+") "
			+ "values (?,?,?,?,?)";
		
	public static final String POINT_VALUE_ADNNOTATIONS_FILTER_BASE_ON_POINT_VALUES_ID = " "
			+COLUMN_NAME_POINT_VALUE_ID+"=? ";
	
	// @formatter:on
	
	//RowMapper
	private class PointValueAdnnotationRowMapper implements RowMapper<PointValueAdnnotation> {
		
		public PointValueAdnnotation mapRow(ResultSet rs, int rowNum) throws SQLException {
			PointValueAdnnotation pva = new PointValueAdnnotation();
			
			pva.setPointValueId(rs.getLong(COLUMN_NAME_POINT_VALUE_ID));
			pva.setTextPointValueShort(rs.getString(COLUMN_NAME_TEXT_POINT_VALUE_SHORT));
			pva.setTextPointValueLong(rs.getString(COLUMN_NAME_TEXT_POINT_VALUE_LONG));
			pva.setSourceType(rs.getInt(COLUMN_NAME_SOURCE_TYPE));
			pva.setSourceId(rs.getLong(COLUMN_NAME_SOURCE_ID));
			return pva;
		}
	}


	@Override
	public List<PointValueAdnnotation> findAll() {
		return (List<PointValueAdnnotation>) DAO.getInstance().getJdbcTemp().query(POINT_VALUE_ADNNOTATIONS_SELECT, new Object[]{ }, new PointValueAdnnotationRowMapper());
	}

	
	/*
	 * @arguments id - idPointValue
	 */
	public List<PointValueAdnnotation> findByPointValueId(long id) {
		return (List<PointValueAdnnotation>) DAO.getInstance().getJdbcTemp().query(POINT_VALUE_ADNNOTATIONS_SELECT +" where "+POINT_VALUE_ADNNOTATIONS_FILTER_BASE_ON_POINT_VALUES_ID, new Object[]  { id }, new PointValueAdnnotationRowMapper());
	}

	@Override
	public List<PointValueAdnnotation> filtered(String filter, Object[] argsFilter, long limit) {
		String myLimit="";
		if (limit != NO_LIMIT) {
			myLimit = LIMIT+limit;
		}
		return (List<PointValueAdnnotation>) DAO.getInstance().getJdbcTemp().query(POINT_VALUE_ADNNOTATIONS_SELECT+" where "+ filter + myLimit, argsFilter, new PointValueAdnnotationRowMapper());
	}
	
	public List<PointValueAdnnotation> filtered(String filter, Object[] argsFilter) {
		return (List<PointValueAdnnotation>) DAO.getInstance().getJdbcTemp().query(POINT_VALUE_ADNNOTATIONS_SELECT+" where "+ filter, argsFilter, new PointValueAdnnotationRowMapper());
	}

	@Transactional(readOnly = false,propagation= Propagation.REQUIRES_NEW,isolation= Isolation.READ_COMMITTED,rollbackFor=SQLException.class)
	@Override
	public long create(final PointValueAdnnotation entity) {
		if (LOG.isTraceEnabled()) {
			LOG.trace(entity);
		}
		
		DAO.getInstance().getJdbcTemp().update(new PreparedStatementCreator() {
			 			@Override
			 			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			 				PreparedStatement ps = connection.prepareStatement(POINT_VALUE_ADNNOTATIONS_INSERT, Statement.RETURN_GENERATED_KEYS);
			 				new ArgumentPreparedStatementSetter( new Object[] { 
			 						entity.getPointValueId(), 
			 						entity.getTextPointValueShort(),
			 						entity.getTextPointValueLong(), 
			 						entity.getSourceType(),
			 						entity.getSourceId()
			 				}).setValues(ps);
			 				return ps;
			 			}
		});
		
		// table hav'nt pk //TODO add key
		return 0;
	}
	
	public void updateAnnotations(List<PointValue> values) {
		Map<Integer, List<AnnotatedPointValueTime>> userIds = new HashMap<Integer, List<AnnotatedPointValueTime>>();
		List<AnnotatedPointValueTime> alist;

		// Look for annotated point values.
		AnnotatedPointValueTime apv;
		for (PointValue pv : values) {
			if (pv.getPointValue() instanceof AnnotatedPointValueTime) {
				apv = (AnnotatedPointValueTime) pv.getPointValue();
				if (apv.getSourceType() == SetPointSource.Types.USER) {
					alist = userIds.get(apv.getSourceId());
					if (alist == null) {
						alist = new ArrayList<AnnotatedPointValueTime>();
						userIds.put(apv.getSourceId(), alist);
					}
					alist.add(apv);
				}
			}
		}
	}
	
}