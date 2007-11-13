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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class JdbcLookupDataSource implements LookupDataSource {

	private DataSource ds;
	
	public JdbcLookupDataSource(DataSource ds) {
		this.ds = ds;
	}

	public JdbcLookupDataSource() {
		File userHome = new File(System.getProperty("user.home"));
		File configFile = new File(userHome, ".wmb/lookup-connection.properties");
		
		if(configFile.exists()) {
			Properties config = new Properties();
			try {
				config.load(new FileInputStream(configFile));
			} catch (IOException e) {
				throw new RuntimeException("Failed to read lookup connections configuration file at " + configFile.getAbsolutePath() + ". Incorrect access?");
			}
			
			BasicDataSource ds = new BasicDataSource();
			
			String driver = config.getProperty("lookup.class");
			if(driver == null || driver.trim().length() == 0) {
				throw new RuntimeException("Lookup connections configuration file must contain a lookup.class value");
			} else {
				ds.setDriverClassName(driver);
			}
			
			String url = config.getProperty("lookup.url");
			if(url == null || url.trim().length() == 0) {
				throw new RuntimeException("Lookup connections configuration file must contain a lookup.url value");
			} else {			
				ds.setUrl(url);
			}
			
			String username = config.getProperty("lookup.username");
			if(username == null || username.trim().length() == 0) {
				throw new RuntimeException("Lookup connections configuration file must contain a lookup.username value");
			} else {			
				ds.setUsername(username);
			}

			ds.setPassword(config.getProperty("lookup.password"));
			ds.setDefaultReadOnly(true);

			this.ds = ds;
		} else {
			throw new RuntimeException("Lookup connections configuration file does not exist at " + configFile.getAbsolutePath());
		}
		
	}
	

	public LookupData[] lookup(String componentName) throws CacheRefreshException {
		Connection conn;
		try {
			conn = ds.getConnection();
		
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT lookup.name, lookup.value, lookup.ttl, lookup.ttd " +
					"FROM lookup, component " +
					"WHERE component.id = lookup.component_id AND component.name='" + componentName + "'");
			
			List dataList = new ArrayList();
			while(rs.next()) {
				String key = rs.getString("name");
				String value = rs.getString("value");
				long ttl = rs.getLong("ttl");
				long ttd = rs.getLong("ttd");
				
				dataList.add(new LookupData(key, value, ttl, ttd));
			}
			
			return (LookupData[]) dataList.toArray(new LookupData[0]);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CacheRefreshException("Failed to read from database", e);
		}
	}

}
