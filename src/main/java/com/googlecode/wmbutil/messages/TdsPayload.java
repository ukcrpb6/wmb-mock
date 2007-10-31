package com.googlecode.wmbutil.messages;

import java.util.List;

import com.googlecode.wmbutil.NiceMbException;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

public class TdsPayload extends Payload {

	public static TdsPayload wrap(MbMessage msg, boolean readOnly) throws MbException {
		MbElement elm = locatePayload(msg);

		if(elm == null) {
			throw new NiceMbException("Failed to find CSV payload");
		}
		
		return new TdsPayload(elm, readOnly);
	}

	/**
	 * Creates a payload as the last child, even if one already exists
	 * @param msg
	 * @return
	 * @throws MbException
	 */
	public static TdsPayload create(MbMessage msg) throws MbException {
		MbElement elm = msg.getRootElement().createElementAsLastChild("MRM");
		return new TdsPayload(elm, false);
	}
	
	public static TdsPayload wrapOrCreate(MbMessage msg) throws MbException {
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
	public static TdsPayload remove(MbMessage msg) throws MbException {
		MbElement elm = locatePayload(msg);
		
		if(elm != null) {
			elm.detach();
			return new TdsPayload(elm, true);
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
	
	private TdsPayload(MbElement elm, boolean readOnly) throws MbException {
		super(elm, readOnly);

	}

	public TdsRecord createRecord(String name) throws MbException {
		checkReadOnly();
		
		MbElement elm = getMbElement().createElementAsLastChild(MbElement.TYPE_NAME, name, null);
		
		return new TdsRecord(elm, isReadOnly());
	}
	
	public TdsRecord[] getRecords(String name) throws MbException {
		List elms = (List) getMbElement().evaluateXPath(name);
		
		TdsRecord[] records = new TdsRecord[elms.size()];
		
		for (int i = 0; i<elms.size(); i++) {
			records[i] = new TdsRecord((MbElement) elms.get(i), isReadOnly());
		}
		
		return records;
	}
	
}
