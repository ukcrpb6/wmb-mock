package com.googlecode.wmbutil.lookup;

import java.util.Map;

public class LookupRows {

	private String componentName;
	private long ttl;
	private long ttd;
	
	private Map rows;

	public LookupRows(String componentName, long ttl, long ttd, Map rows) {
		super();
		this.componentName = componentName;
		this.ttl = ttl;
		this.ttd = ttd;
		this.rows = rows;
	}

	public String getComponentName() {
		return componentName;
	}

	public long getTTL() {
		return ttl;
	}

	public long getTTD() {
		return ttd;
	}

	public Map getRows() {
		return rows;
	}
	
	
}
