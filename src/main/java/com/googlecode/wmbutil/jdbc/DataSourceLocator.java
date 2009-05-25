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

package com.googlecode.wmbutil.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * Used for looking up JDBC data sources at runtime. Uses a file for storing the
 * data source configuration. The file is loaded from the Message Broker
 * workpath using a file that must be named "jdbc-connections.properties". For
 * each named data source, the file must contain the following properties (in a
 * typical Java properties file):
 * <ul>
 * <li>jdbc.<data source name>.class - the class name for the JDBC driver</li>
 * <li>jdbc.<data source name>.url - JDBC URL for the database</li>
 * <li>jdbc.<data source name>.username - the username used to connect to the
 * database</li>
 * <li>jdbc.<data source name>.password - the password used to connect to the
 * database</li>
 * </ul>
 */
public class DataSourceLocator {

	private static final DataSourceLocator instance = new DataSourceLocator();

	public static DataSourceLocator getInstance() {
		return instance;
	}

	private DataSourceLocator() {
		String brokerWorkpathPath = System.getProperty("broker.workpath");
		
		if (brokerWorkpathPath == null) {
			throw new IllegalStateException("System property broker.workpath must be set");
		}
		
		File brokerWorkpath = new File(brokerWorkpathPath);
		File configFile = new File(brokerWorkpath,
				"jdbc-connections.properties");

		if (configFile.exists()) {
			config = new Properties();
			try {
				FileInputStream fs = new FileInputStream(configFile);
				config.load(fs);
				fs.close();
			} catch (IOException e) {
				throw new RuntimeException(
						"Failed to read lookup connections configuration file at "
								+ configFile.getAbsolutePath()
								+ ". Missing permissions?");
			}
		} else {
			throw new RuntimeException(
					"JDBC connections configuration file does not exist at "
							+ configFile.getAbsolutePath());
		}

	}

	private Properties config;
	private Map dataSources = new HashMap();

	/**
	 * Looks up a named data source
	 * 
	 * @param dataSourceName
	 *            The name of the data source to look for
	 * @return The data source
	 * @throws RuntimeException
	 *             If the configuration can not be loaded or the data source
	 *             definition is missing
	 */
	public synchronized DataSource lookup(String dataSourceName) {
		BasicDataSource ds;
		if (dataSources.containsKey(dataSourceName)) {
			ds = (BasicDataSource) dataSources.get(dataSourceName);
			
			if (ds == null) {
				// we failed to create it earlier
				throw new RuntimeException("Failed to create data source");
			}
		} else {
			ds = new BasicDataSource();
			
			try {
				String driver = config.getProperty("jdbc." + dataSourceName
						+ ".class");
				if (driver == null || driver.trim().length() == 0) {
					throw new RuntimeException(
							"Lookup connections configuration file must contain a jdbc."
									+ dataSourceName + ".class value");
				} else {
					ds.setDriverClassName(driver);
				}
	
				String url = config.getProperty("jdbc." + dataSourceName + ".url");
				if (url == null || url.trim().length() == 0) {
					throw new RuntimeException(
							"Lookup connections configuration file must contain a jdbc."
									+ dataSourceName + ".url value");
				} else {
					ds.setUrl(url);
				}
	
				String username = config.getProperty("jdbc." + dataSourceName
						+ ".username");
				if (username == null || username.trim().length() == 0) {
					throw new RuntimeException(
							"Lookup connections configuration file must contain a jdbc."
									+ dataSourceName + ".username value");
				} else {
					ds.setUsername(username);
				}
	
				ds.setPassword(config.getProperty("jdbc." + dataSourceName
						+ ".password"));
				ds.setDefaultReadOnly(true);
				
				dataSources.put(dataSourceName, ds);
			} catch (RuntimeException e) {
				// mark failed to create
				dataSources.put(dataSourceName, null);
				throw e;
			}
		}

		return ds;

	}
}
