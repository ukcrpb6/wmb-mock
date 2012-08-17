package com.googlecode.wmbutil;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbXMLNSC;
import com.ibm.broker.plugin.MbXPath;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
@RunWith(MbJUnitRunner.class)
public class MbBaseTestCase {
    @Test
    public void testMockMessage() throws Exception {
        MbMessage message = MbMockFactory.getInstance().createMbMessage();
        MbElement element = message.getRootElement();
        System.out.println(element.toString());
        element = element.createElementAsFirstChild("Properties");

        element.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE, "name", "value");
        element.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE, "name2", "value2");
        Assert.assertEquals("value", message.getRootElement().getFirstChild().getLastChild().getValueAsString());
        element.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "name3", "value").setValue("value3");
        Assert.assertEquals("value3", message.getRootElement().getFirstChild().getLastChild().getValueAsString());
        Assert.assertEquals("value2", message.getRootElement().getFirstChild().getFirstChild().getValueAsString());

        element.getFirstChild().setSpecificType(MbXMLNSC.ATTRIBUTE);

        System.out.println(element.toString());
        System.out.println(element.getFirstChild().toString());
        System.out.println(element.getLastChild().toString());
        System.out.println(message.toString());

        MockMbNavigator nav = new MockMbNavigator();
        System.out.println("MbMessage classLoader = " + MbMessage.class.getClassLoader());
        System.out.println("MbElement classLoader = " + MbElement.class.getClassLoader());
        System.out.println("Testcase classLoader  = " + MbElementWrapperTestCase.class.getClassLoader());
        System.out.println("Context classLoader   = " + Thread.currentThread().getContextClassLoader());
        System.out.println("MbXPath classLoader   = " + MbXPath.class.getClassLoader());

        MbXPath xp = new MbXPath("/Properties");
        System.out.println(Arrays.toString(xp.getClass().getInterfaces()));

        MbJUnitRunner.GetXPathExpression gettable = (MbJUnitRunner.GetXPathExpression) xp;
        List nodesx = nav.parseXPath(gettable.getXPathExpression()).selectNodes(message);
        System.out.println(gettable.getXPathExpression() + " -- " + nodesx);

        List list = (List) message.evaluateXPath(new MbXPath("/Properties"));
        System.out.println(list);

        System.out.println("****");
        List nodes0 = nav.parseXPath("*").selectNodes(message);
        System.out.println(nodes0);

        List nodes1 = nav.parseXPath("//*").selectNodes(message);
        System.out.println(nodes1);
        List nodes = nav.parseXPath("/Properties").selectNodes(message);
        Iterator childIterator = nav.getChildAxisIterator(message);
        Assert.assertTrue(childIterator.hasNext());
        System.out.println(nodes);
        // TODO: Handle IBM ?$^ extensions to the XPATH syntax
        // TODO: Determine how to cope with inline create MbXpath objects ^_^
        // TODO: Might have to use an interceptor to catch all invocations of new MbXPath(...)
    }
}
