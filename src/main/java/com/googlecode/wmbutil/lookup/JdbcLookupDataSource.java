/*
 * Copyright 2007 (C) Callista Enterprise.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *	http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.googlecode.wmbutil.lookup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

import com.googlecode.wmbutil.jdbc.DataSourceLocator;

public class JdbcLookupDataSource implements LookupDataSource {

	private DataSource ds;

	public JdbcLookupDataSource(DataSource ds) {
		this.ds = ds;
	}

	/**
	 * Default constructor. Will use the DataSourceLocator with the data source
	 * name "lookup".
	 */
	public JdbcLookupDataSource() {
		DataSourceLocator dsLocator = DataSourceLocator.getInstance();
		this.ds = dsLocator.lookup("lookup");
	}

	public LookupRows loadComponentData(String componentName)
			throws CacheRefreshException {
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;

		long ttl = 0;
		long ttd = 0;

		boolean ttSet = false;

		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt
					.executeQuery("SELECT lookup.name, lookup.value, component.ttl, component.ttd "
							+ "FROM lookup, component "
							+ "WHERE component.id = lookup.component_id AND component.name='"
							+ componentName + "'");

			Map data = new HashMap();
			while (rs.next()) {
				String key = rs.getString("name");
				String value = rs.getString("value");

				if (!ttSet) {
					ttl = rs.getLong("ttl");
					ttd = rs.getLong("ttd");
					ttSet = true;
				}

				data.put(key, value);
			}

			System.out.println("JC");
			return new LookupRows(componentName, ttl, ttd, data);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CacheRefreshException("Failed to read from database", e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new CacheRefreshException("Failed writing to database", e);
			}
		}
	}

	public void updateComponentData(String componentName, Map values)
			throws CacheRefreshException {
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			conn.setReadOnly(false);
			stmt = conn.createStatement();

			// Get component id
			String sqlGetComponentId = "select * from component where name='"
					+ componentName + "'";
			rs = stmt.executeQuery(sqlGetComponentId);
			Integer componentId = null;
			while (rs.next()) {
				componentId = new Integer(rs.getInt("id"));
			}
			if (componentId == null) {
				throw new CacheRefreshException(
						"Could not fins component with name:" + componentName);
			}

			// Delete existing data
			String sqlDelete = "delete from lookup where component_id="
					+ componentId;
			// String sqlDelete = "delete from lookup where component.id="
			// + "(select component_id from component where name='"
			// + componentName + "')";
			stmt.execute(sqlDelete);

			// Insert new data
			String sqlInsert = "INSERT INTO lookup (component_id, name, value, ttl, ttd) values (?, ?, ?, 0, 0)";
			pstmt = conn.prepareStatement(sqlInsert);
			Iterator iter = values.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				String value = (String) values.get(key);
				pstmt.setInt(1, componentId.intValue());
				pstmt.setString(2, key);
				pstmt.setString(3, value);
				pstmt.execute();
			}
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new CacheRefreshException("Failed writing to database",
						e1);
			}
			e.printStackTrace();
			throw new CacheRefreshException("Failed writing to database", e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new CacheRefreshException("Failed writing to database", e);
			}
		}
	}

}
