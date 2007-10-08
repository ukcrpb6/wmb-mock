/*
 * Created on 2007-okt-08
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.googlecode.wmbutil;

import java.util.Iterator;
import java.util.List;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbXPath;


/**
 * XPathOperation is an abstract helper class allowing a method to be
 * repeatedly applied to results of an XPath expression. The user can
 * optionally access the output message tree allowing message
 * transformations to be defined based on XPath evaluations of the input
 * tree.
 */
public abstract class XPathOperation {
	private MbXPath xpath = null;

	private MbElement outputElement = null;

	/**
	 * Constructor taking the XPath expression to evaluate. The expression
	 * must evaluate to a nodeset.
	 */
	public XPathOperation(String expression) throws MbException {
		xpath = new MbXPath(expression);
	}

	/**
	 * Must be called prior to the evaluate method if the user wishes to
	 * work with the output tree (eg for message transformation).
	 */
	public void setOutputElement(MbElement out) {
		outputElement = out;
	}

	/**
	 * Allows the user to access the output tree in the forEachElement()
	 * method
	 */
	public MbElement getOutputElement() {
		return outputElement;
	}

	/**
	 * Can be overridden by the user to do initialisation processing before
	 * iterating over the XPath nodeset results
	 */
	protected void before() throws MbException {
	}

	/**
	 * This gets called once for each element in the XPath nodeset results.
	 * The current element is passed in as the only argument. This method is
	 * abstract and must be implemented in the derived class.
	 */
	protected abstract void forEachElement(MbElement element)
			throws MbException;

	/**
	 * Can be overridden by the user to do post-processing after iterating
	 * over the XPath nodeset results
	 */
	protected void after() throws MbException {
	}

	/**
	 * Called by the user to iterate over the XPath nodeset.
	 * 
	 * @param element
	 *            The context element to which the XPath expression will be
	 *            applied.
	 */
	public void evaluate(MbElement element) throws MbException {
		Object result = element.evaluateXPath(xpath);
		if (!(result instanceof List)) {
			// error
			return;
		}

		before();

		Iterator iterator = ((List) result).iterator();
		MbElement node;
		while (iterator.hasNext()) {
			node = (MbElement) iterator.next();
			forEachElement(node);
		}

		after();
	}

}