package com.googlecode.wmbutil;

import com.googlecode.wmbutil.messages.MbElementWrapper;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbXMLNSC;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
@RunWith(MockitoJUnitRunner.class)
public class MbElementWrapperTestCase {

    @Mock private MbElement element;

    private MbElementWrapper wrapper;

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

        // TODO: //* doesn't match root?
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

    @Test
    public void testCreateElementAsFirstChild() throws Exception {
        MbMessage message = MbMockFactory.getInstance().createMbMessage();
        MbElement element = message.getRootElement();
//        element.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE, "XMLNSC", );

    }

    @Before
    public void setUp() throws Exception {
        wrapper = new MbElementWrapper(element) {};
    }

    @Test(expected = IllegalStateException.class)
    public void testSetValueWhenReadOnly() throws Exception {
        when(element.isReadOnly()).thenReturn(true);
        wrapper.setValue("value");
    }

    @Test(expected = IllegalStateException.class)
    public void testSetFieldValueWhenReadOnly() throws Exception {
        when(element.isReadOnly()).thenReturn(true);
        wrapper.setValue("field", "value");
    }

    @Test
    public void testSetValue() throws Exception {
        wrapper.setValue("field");
        verify(element).setValue("field");
    }

    @Test
    public void testSetExistingFieldValue() throws Exception {
        MbElement child = mock(MbElement.class);
        when(element.getFirstElementByPath("field")).thenReturn(child);
        wrapper.setValue("field", "value");
        verify(element).getFirstElementByPath("field");
        verify(child).setValue("value");
    }

    @Test
    public void testSetMissingFieldValue() throws Exception {
        MbElement child = mock(MbElement.class);
        when(element.getFirstElementByPath("field")).thenReturn(null);
        when(element.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "field", null)).thenReturn(child);
        wrapper.setValue("field", "value");
        verify(element).getFirstElementByPath("field");
        verify(element).createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "field", null);
        verify(child).setValue("value");
    }
}
