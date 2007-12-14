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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	

	public LookupRows loadComponentData(String componentName) throws CacheRefreshException {
		Connection conn;
		
		long ttl = 0;
		long ttd = 0;
		
		boolean ttSet = false;
		
		try {
			conn = ds.getConnection();
		
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT lookup.name, lookup.value, component.ttl, component.ttd " +
					"FROM lookup, component " +
					"WHERE component.id = lookup.component_id AND component.name='" + componentName + "'");
			
			Map data = new HashMap();
			while(rs.next()) {
				String key = rs.getString("name");
				String value = rs.getString("value");
				
				if(!ttSet) {
					ttl = rs.getLong("ttl");
					ttd = rs.getLong("ttd");
					ttSet = true;
				}
				
				data.put(key, value);
			}
			
			
			return new LookupRows(componentName, ttl, ttd, data);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CacheRefreshException("Failed to read from database", e);
		}
	}

}
