package com.googlecode.wmbutil.lookup;


import java.util.List;
import java.util.Map;

public class MockDataSource implements LookupDataSource {

	private CacheRefreshException exceptionToThrow;
	private Map data;
	
	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}

	public LookupData[] lookup(String cacheName) throws CacheRefreshException {
		if(exceptionToThrow != null) {
			throw exceptionToThrow;
		}
		
		List dataList = (List) data.get(cacheName);
		
		if(dataList == null) {
			return null;
		} else {
			return (LookupData[]) dataList.toArray(new LookupData[0]);
		}
	}

	public CacheRefreshException getExceptionToThrow() {
		return exceptionToThrow;
	}

	public void setExceptionToThrow(CacheRefreshException exceptionToThrow) {
		this.exceptionToThrow = exceptionToThrow;
	}

}
