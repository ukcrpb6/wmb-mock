package com.googlecode.wmbutil;

import com.googlecode.wmbutil.messages.MbElementWrapper;
import com.ibm.broker.plugin.MbElement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
@RunWith(MockitoJUnitRunner.class)
public class MbElementWrapperTestCase {

  @Mock private MbElement element;

  private MbElementWrapper wrapper;

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
