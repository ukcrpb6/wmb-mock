package com.googlecode.wmbutil.messages;

import java.util.Iterator;
import java.util.List;

import com.googlecode.wmbutil.NiceMbException;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

public class CsvPayload extends Payload {

	public static CsvPayload wrap(MbMessage msg, boolean readOnly) throws MbException {
		MbElement elm = locatePayload(msg);

		if(elm == null) {
			throw new NiceMbException("Failed to find CSV payload");
		}
		
		return new CsvPayload(elm, readOnly);
	}

	/**
	 * Creates a payload as the last child, even if one already exists
	 * @param msg
	 * @return
	 * @throws MbException
	 */
	public static CsvPayload create(MbMessage msg) throws MbException {
		MbElement elm = msg.getRootElement().createElementAsLastChild("MRM");
		return new CsvPayload(elm, false);
	}
	
	public static CsvPayload wrapOrCreate(MbMessage msg) throws MbException {
		if(has(msg)) {
			return wrap(msg, false);
		} else {
			return create(msg);
		}
	}

	/** 
	 * Removes the first XML payload
	 * @param msg
	 * @return
	 * @throws MbException
	 */
	public static CsvPayload remove(MbMessage msg) throws MbException {
		MbElement elm = locatePayload(msg);
		
		if(elm != null) {
			elm.detach();
			return new CsvPayload(elm, true);
		} else {
			throw new NiceMbException("Failed to find XML payload");
		}		
	}

	public static boolean has(MbMessage msg) throws MbException {
		MbElement elm = locatePayload(msg);
		return elm != null;
	}
	
	private static MbElement locatePayload(MbMessage msg) throws MbException {
		MbElement elm = msg.getRootElement().getFirstElementByPath("/MRM");

		return elm;
	}
	
	private CsvPayload(MbElement elm, boolean readOnly) throws MbException {
		super(elm, readOnly);

	}

	public CsvRecord createRecord(String name) throws MbException {
		checkReadOnly();
		
		MbElement elm = getMbElement().createElementAsLastChild(MbElement.TYPE_NAME, name, null);
		
		return new CsvRecord(elm, isReadOnly());
	}
	
	public CsvRecord[] getRecords(String name) throws MbException {
		List elms = (List) getMbElement().evaluateXPath(name);
		
		CsvRecord[] records = new CsvRecord[elms.size()];
		
		for (int i = 0; i<elms.size(); i++) {
			records[i] = new CsvRecord((MbElement) elms.get(i), isReadOnly());
		}
		
		return records;
	}
	
}
