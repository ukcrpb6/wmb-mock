package com.googlecode.wmbutil;

import org.apache.log4j.Logger;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

public class ElementBuilder {

	private static final Logger LOG = Logger.getLogger(ElementBuilder.class);
	
	private MbElement currentElm;
	
	public ElementBuilder(MbElement currentElm) {
		this.currentElm = currentElm;
	}

	public ElementBuilder(MbMessage msg) throws MbException {
		this.currentElm = msg.getRootElement();
	}
	
	
	
	public ElementBuilder get(String elmName) throws MbException {
		currentElm = currentElm.getFirstElementByPath(elmName);
		
		LOG.debug("Getting, currentElement is now " + currentElm);
		
		return this;
	}
	
	public ElementBuilder createParser(String elmName) throws MbException {
		currentElm = currentElm.createElementAsLastChild(elmName);
		
		LOG.debug("createParser, currentElement is now " + currentElm);
		return this;
	}

	public ElementBuilder create(String elmName) throws MbException {
		currentElm = currentElm.createElementAsLastChild(MbElement.TYPE_NAME, elmName, null);
		
		LOG.debug("Creating, currentElement is now " + currentElm);
		return this;
	}

	public ElementBuilder create(String elmName, Object value) throws MbException {
		currentElm = currentElm.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, elmName, value);
		
		LOG.debug("Creating, currentElement is now " + currentElm);
		return this;
	}
	
	public ElementBuilder setValue(Object value) throws MbException {
		if(value instanceof MbElement ) {
			currentElm.setValue(((MbElement)value).getValue());
		} else {
			currentElm.setValue(value);
		}
		
		return this;
	}

	public Object getValue() throws MbException {
		LOG.debug("Getting value from " + currentElm);
		return currentElm.getValue();
	}
}
