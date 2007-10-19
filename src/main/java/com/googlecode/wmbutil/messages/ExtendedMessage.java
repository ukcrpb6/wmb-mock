package com.googlecode.wmbutil.messages;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

public class ExtendedMessage {

	private MbMessage msg;
	private boolean readOnly;
	private Body body;

	
	public static ExtendedMessage wrap(MbMessage msg, boolean readOnly) throws MbException {
		// detect type of message;

		if (msg.getRootElement().getFirstElementByPath("/MQMD") != null) {
			return new WMQMessage(msg, readOnly);
		} else {
			return new ExtendedMessage(msg, readOnly);
		}
	}
	
	public ExtendedMessage() throws MbException {
		this(new MbMessage(), false);
	}
	
	public ExtendedMessage(MbMessage msg, boolean readOnly) throws MbException {
		this.msg = msg;
		this.readOnly = readOnly;
	}

	public MbMessage getMbMessage() {
		return msg;
	}

	protected boolean isReadOnly() {
		return readOnly;
	}


	public void copyRootElement(String name, ExtendedMessage outMessage)
			throws MbException {
		MbElement outRoot = outMessage.getMbMessage().getRootElement();

		MbElement elm = getMbMessage().getRootElement().getFirstElementByPath(name);

		if(elm != null) {
			outRoot.addAsLastChild(elm.copy());
		}
	}

	public void copyMessageHeaders(ExtendedMessage outMessage)
			throws MbException {
		MbElement outRoot = outMessage.getMbMessage().getRootElement();

		// iterate though the headers starting with the first child of the root
		// element
		MbElement header = getMbMessage().getRootElement().getFirstChild();
		// stop before the last child (body)
		while (header != null && header.getNextSibling() != null) {
			// copy the header and add it to the out message
			outRoot.addAsLastChild(header.copy());
			// move along to next header
			header = header.getNextSibling();
		}
	}

	public void copyMessage(ExtendedMessage outMessage)
			throws MbException {
		MbElement outRoot = outMessage.getMbMessage().getRootElement();

		outMessage.getMbMessage().getRootElement().copyElementTree(getMbMessage().getRootElement());
	}


	public Body getBody() {
		return body;
	}
	
	public void setBody(Body body) {
		this.body = body;
	}
}
