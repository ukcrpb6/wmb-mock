package com.test;

import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbXPath;
import com.pressassociation.bus.junit.MbJUnit4ClassRunner;
import com.pressassociation.bus.junit.MbXPathValue;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
@RunWith(MbJUnit4ClassRunner.class)
public class MbTest {

    public static MbXPathValue translate(MbXPath xpath) {
        return MbXPathValue.class.cast(xpath);
    }

    @Test
    public void testSomeXPath() throws Exception {
        MbXPath xpath = new MbXPath("/Property");
        Assert.assertEquals("/Property", translate(xpath).getValue());
    }

    @Test
    public void testSomethingElxe() throws Exception {
        MbMessage m = new MbMessage();
//        m.getRootElement();
        throw new MbException(0L, "getRootElement", 3221229850L, "Message has been cleared.", new String[]{getClass().getName()});
//        MbMessage message = MbMockFactory.getInstance().createMbMessage();
//        MbElement element = message.getRootElement();
//        System.out.println(element.toString());
//        element = element.createElementAsFirstChild("Properties");
//
//        element.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE, "name", "value");
//        element.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE, "name2", "value2");
//        Assert.assertEquals("value", message.getRootElement().getFirstChild().getLastChild().getValueAsString());
//        element.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "name3", "value").setValue("value3");
//        Assert.assertEquals("value3", message.getRootElement().getFirstChild().getLastChild().getValueAsString());
//        Assert.assertEquals("value2", message.getRootElement().getFirstChild().getFirstChild().getValueAsString());
//
//        element.getFirstChild().setSpecificType(MbXMLNSC.ATTRIBUTE);
//
//        System.out.println(element.toString());
//        System.out.println(element.getFirstChild().toString());
//        System.out.println(element.getLastChild().toString());
//        System.out.println(message.toString());
//
//        MockMbNavigator nav = new MockMbNavigator();
//        System.out.println("MbMessage classLoader = " + MbMessage.class.getClassLoader());
//        System.out.println("MbElement classLoader = " + MbElement.class.getClassLoader());
//        System.out.println("Testcase classLoader  = " + MbElementWrapperTestCase.class.getClassLoader());
//        System.out.println("Context classLoader   = " + Thread.currentThread().getContextClassLoader());
//        System.out.println("MbXPath classLoader   = " + MbXPath.class.getClassLoader());
//
//        MbXPath xp = new MbXPath("/Properties");
//        System.out.println(Arrays.toString(xp.getClass().getInterfaces()));
//
//        List nodesx = nav.parseXPath(translate(xp).getValue()).selectNodes(message);
//        System.out.println(translate(xp).getValue() + " -- " + nodesx);
//
//        List list = (List) message.evaluateXPath(new MbXPath("/Properties"));
//        System.out.println(list);
//
//        System.out.println("****");
//        List nodes0 = nav.parseXPath("*").selectNodes(message);
//        System.out.println(nodes0);
//
//        List nodes1 = nav.parseXPath("//*").selectNodes(message);
//        System.out.println(nodes1);
//        List nodes = nav.parseXPath("/Properties").selectNodes(message);
//        Iterator childIterator = nav.getChildAxisIterator(message);
//        Assert.assertTrue(childIterator.hasNext());
//        System.out.println(nodes);
//        // TODO: Handle IBM ?$^ extensions to the XPATH syntax
//        // TODO: Determine how to cope with inline create MbXpath objects ^_^
//        // TODO: Might have to use an interceptor to catch all invocations of new MbXPath(...)
    }
}
