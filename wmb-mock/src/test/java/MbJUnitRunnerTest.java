import com.ibm.broker.plugin.*;
import com.ibm.broker.plugin.visitor.MbMessageVisitor;
import com.ibm.broker.plugin.visitor.MbVisitable;
import junit.framework.Assert;
import org.jaxen.XPath;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.sound.midi.SysexMessage;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.BitSet;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MbMessage.class, MbElement.class, MbXPath.class})
public class MbJUnitRunnerTest {

    @Before
    public void stubMbMessageNativeMethods() throws Exception {
        for( final Method m : NativeMbMessageManager.class.getDeclaredMethods() ) {
            PowerMockito.replace(MbMessage.class.getDeclaredMethod(m.getName(), m.getParameterTypes())).with(new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                    return m.invoke(PseudoNativeMbMessageManager.getInstance(), args);
                }
            });
        }
    }

    @Before
    public void stubMbElementNativeMethods() throws NoSuchMethodException {
        for( final Method m : NativeMbElementManager.class.getDeclaredMethods() ) {
            final Method targetMethod = MbElement.class.getDeclaredMethod(m.getName(), m.getParameterTypes());
            PowerMockito.replace(targetMethod).with(new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                    return m.invoke(PseudoNativeMbElementManager.getInstance(), args);
                }
            });
        }
    }

    @Before
    public void stubMbXPathNativeMethods() throws NoSuchMethodException {
        for( final Method m : NativeMbXPathManager.class.getDeclaredMethods() ) {
            final Method targetMethod = MbXPath.class.getDeclaredMethod(m.getName(), m.getParameterTypes());
            PowerMockito.replace(targetMethod).with(new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                    return m.invoke(PseudoNativeMbXPathManager.getInstance(), args);
                }
            });
        }
    }

    @Test
    public void testCreationOfMbMessage() throws Exception {
        MbMessage message = new MbMessage();
        MbElement e = message.getRootElement();
        Assert.assertNotNull(e);
    }

    private class MbTestObject {
        final MbMessage message;
        final MbElement child;
        final MbElement root;

        private MbTestObject(MbMessage message, MbElement child) throws MbException {
            this.message = message;
            this.root = message.getRootElement();
            this.child = child;
        }
    }

    private MbTestObject createMessageAndFirstChild() throws MbException {
        final MbMessage message = new MbMessage();
        MbElement e = message.getRootElement();
        Assert.assertNotNull(e);

        final MbElement firstChild = e.createElementAsFirstChild(MbXMLNSC.PARSER_NAME);
        Assert.assertEquals(firstChild, e.getFirstChild());
        Assert.assertEquals(firstChild, e.getLastChild());
        Assert.assertNull(firstChild.getPreviousSibling());
        Assert.assertNull(firstChild.getNextSibling());
        Assert.assertEquals(e, firstChild.getParent());
        return new MbTestObject(message, firstChild);
    }

    @Test
    public void testCreationOfFirstChild() throws Exception {
        MbTestObject initialState = createMessageAndFirstChild();
        MbElement e = initialState.root;
        MbElement firstChild = initialState.child;

        MbElement nextSibling = e.createElementAsFirstChild(MbXMLNSC.PARSER_NAME);
        Assert.assertEquals(nextSibling, e.getFirstChild());
        Assert.assertEquals(firstChild, e.getLastChild());
        Assert.assertNull(nextSibling.getPreviousSibling());
        Assert.assertEquals(firstChild, nextSibling.getNextSibling());
        Assert.assertEquals(nextSibling, firstChild.getPreviousSibling());
        Assert.assertEquals(e, nextSibling.getParent());
    }

    @Test
    public void testCreationOfLastChild() throws Exception {
        MbTestObject initialState = createMessageAndFirstChild();
        MbElement e = initialState.root;
        MbElement firstChild = initialState.child;

        MbElement nextSibling = e.createElementAsLastChild(MbXMLNSC.PARSER_NAME);
        Assert.assertEquals(firstChild, e.getFirstChild());
        Assert.assertEquals(nextSibling, e.getLastChild());
        Assert.assertNull(nextSibling.getNextSibling());
        Assert.assertEquals(nextSibling, firstChild.getNextSibling());
        Assert.assertEquals(firstChild, nextSibling.getPreviousSibling());
        Assert.assertEquals(e, nextSibling.getParent());
    }


    @Test
    public void testCreationOfPreviousSibling() throws Exception {
        MbTestObject initialState = createMessageAndFirstChild();
        MbElement e = initialState.root;
        MbElement firstChild = initialState.child;

        MbElement nextSibling = firstChild.createElementBefore(MbXMLNSC.PARSER_NAME);
        Assert.assertEquals(nextSibling, e.getFirstChild());
        Assert.assertEquals(firstChild, e.getLastChild());
        Assert.assertNull(nextSibling.getPreviousSibling());
        Assert.assertEquals(firstChild, nextSibling.getNextSibling());
        Assert.assertEquals(nextSibling, firstChild.getPreviousSibling());
        Assert.assertEquals(e, nextSibling.getParent());
    }

    @Test
    public void testCreationOfNextSibling() throws Exception {
        MbTestObject initialState = createMessageAndFirstChild();
        MbElement e = initialState.root;
        MbElement firstChild = initialState.child;

        MbElement nextSibling = firstChild.createElementAfter(MbXMLNSC.PARSER_NAME);
        Assert.assertEquals(firstChild, e.getFirstChild());
        Assert.assertEquals(nextSibling, e.getLastChild());
        Assert.assertNull(nextSibling.getNextSibling());
        Assert.assertEquals(nextSibling, firstChild.getNextSibling());
        Assert.assertEquals(firstChild, nextSibling.getPreviousSibling());
        Assert.assertEquals(e, nextSibling.getParent());
    }

    @Test
    public void testSetValue() throws Exception {
        MbTestObject initialState = createMessageAndFirstChild();
        MbElement firstChild = initialState.child;
        MbElement firstField = firstChild.createElementAsFirstChild(MbXMLNSC.FIELD);
        firstField.setValue(1);
        Assert.assertEquals(1, firstField.getValue());
        Assert.assertEquals("1", firstField.getValueAsString());
    }

    @Test
    public void testInteger() throws Exception {
        testCreateWithValue(Integer.class, 1);
    }

    @Test
    public void testLong() throws Exception {
        testCreateWithValue(Long.class, 1L);
    }

    @Test
    public void testDouble() throws Exception {
        testCreateWithValue(Double.class, 1.0d);
    }

    @Test
    public void testBigDecimal() throws Exception {
        testCreateWithValue(BigDecimal.class, new BigDecimal("10000000000000000000.01"));
    }

    @Test
    public void testBoolean() throws Exception {
        testCreateWithValue(Boolean.class, Boolean.TRUE);
    }

    @Test
    public void testString() throws Exception {
        testCreateWithValue(String.class, "XYZZY");
    }

    @Test
    public void testBitSet() throws Exception {
        BitSet b = new BitSet();
        b.set(8, true);
        testCreateWithValue(BitSet.class, b);
    }

    public <T> void createNamedValue(MbElement parent, Class<T> klass, String name,  T value) throws Exception {
        MbElement firstField = parent.createElementAsFirstChild(MbXMLNSC.FIELD, name, value);
        Assert.assertEquals(name, firstField.getName());
        Assert.assertEquals(klass, firstField.getValue().getClass());
        Assert.assertEquals(value, firstField.getValue());
        Assert.assertEquals(String.valueOf(value), firstField.getValueAsString());
    }

    public <T> void testCreateWithValue(Class<T> klass, T value) throws Exception {
        MbTestObject initialState = createMessageAndFirstChild();
        createNamedValue(initialState.child, klass, "field", value);
    }

    @Test
    public void testCreateWithMbTimestampValue() throws Exception {
        MbTestObject initialState = createMessageAndFirstChild();
        MbElement firstChild = initialState.child;
        MbTimestamp timestamp = new MbTimestamp(2012,1,1,1,1,1);
        timestamp.setTimeZone(TimeZone.getTimeZone("GMT"));
        MbElement firstField = firstChild.createElementAsFirstChild(MbXMLNSC.FIELD, "fieldName", timestamp);
        System.out.println(firstField);
        Assert.assertEquals(MbTimestamp.class, firstField.getValue().getClass());
        Assert.assertEquals(timestamp.getTimeInMillis(), ((MbTimestamp)firstField.getValue()).getTimeInMillis());
//        Assert.assertEquals(timestamp.toString(), firstField.getValueAsString());
    }

    @Test
    public void testCreateWithMbTimeValue() throws Exception {
        MbTestObject initialState = createMessageAndFirstChild();
        MbElement firstChild = initialState.child;
        MbTime time = new MbTime(1,1,1);
        time.setTimeZone(TimeZone.getTimeZone("GMT"));
        MbElement firstField = firstChild.createElementAsFirstChild(MbXMLNSC.FIELD, "fieldName", time);
        System.out.println(firstField);
        Assert.assertEquals(MbTime.class, firstField.getValue().getClass());
        Assert.assertEquals(time.getTimeInMillis(), ((MbTime)firstField.getValue()).getTimeInMillis());
//        Assert.assertEquals(timestamp.toString(), firstField.getValueAsString());
    }

    @Test
    public void testCreateWithMbDateValue() throws Exception {
        MbTestObject initialState = createMessageAndFirstChild();
        MbElement firstChild = initialState.child;
        MbDate date = new MbDate(2012,1,1);
        MbElement firstField = firstChild.createElementAsFirstChild(MbXMLNSC.FIELD, "fieldName", date);
        Assert.assertEquals(MbDate.class, firstField.getValue().getClass());
        Assert.assertEquals(date.getTimeInMillis(), ((MbDate)firstField.getValue()).getTimeInMillis());
//        Assert.assertEquals(timestamp.toString(), firstField.getValueAsString());
    }

    private MbTestObject createTestTree() throws Exception {
        MbTestObject o = createMessageAndFirstChild();
        for(int i = 0; i < 10; i++) {
            createNamedValue(o.child, Integer.class, "field-" + i, i % 2);
        }
        MbElement folder = o.child.createElementAsLastChild(MbXMLNSC.FOLDER);
        for(int i = 0; i < 10; i++) {
            createNamedValue(folder, Integer.class, "field-" + i, i % 2);
        }
        return o;
    }

    @Test
    public void testJaxenXPath() throws Exception {
        MbTestObject o = createTestTree();
        PseudoMbMessageNavigator nav = new PseudoMbMessageNavigator();
        XPath xpath = nav.parseXPath("//field-1");
        List list = xpath.selectNodes(o.message);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void testMbXPath() throws Exception {
        MbTestObject o = createTestTree();
        MbXPath x = new MbXPath("/field-1");
        List result = (List) o.root.evaluateXPath(x);
        Assert.assertEquals(1, result.size());

        x = new MbXPath("/field-1", o.root.getLastChild());
        result = (List) o.root.evaluateXPath(x);
        Assert.assertEquals(1, result.size());

        x = new MbXPath("/field-1", o.root);
        result = (List) o.root.evaluateXPath(x);
        Assert.assertEquals(0, result.size());

        MbXPath x2 = new MbXPath("//*", o.root);
        result = (List) o.root.evaluateXPath(x2);
        Assert.assertEquals(22, result.size());

        result = (List) o.root.evaluateXPath("//*");
        Assert.assertEquals(22, result.size());
    }

    @After
    public void dumpMessages() throws Exception {
        System.out.println(" *** START *** ");
        PseudoNativeMbMessageManager.getInstance().visit(new MbMessageVisitor() {
            @Override
            public void visit(PseudoNativeMbMessage message) {
                System.out.println(message.toString());
            }

            @Override
            public void visit(PseudoNativeMbElement element) {
                System.out.println(element.toString());
            }
        });
        System.out.println(" *** END *** ");
    }


}
