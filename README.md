Websphere Message Broker - Mock Framework
=========================================

Goal
----

To be able to perform unit testing of java code within the context of Message
Broker, both the testing of java libraries that manipulate messages or the
elements within; or the invocation of java compute nodes themselves.

Usage
-----

The following example is within the context of the java compute node, but the
principals are the same for testing libraries that manipulate messages.

To enable a junit test to be able to mock the invocation of a java compute node
you will need to define the junit runner as follows

```java
@RunWith(PowerMockRunner.class)
@MockPolicy(MbMockPolicy.class)
public class MyFirstJavaComputeNodeTest {
    // ... //
}
```

This will both implement mock native implementations for the JNI wrapper classes
MbMessageAssembly, MbMessage, MbElement and MbXPath, and stub out the calls to
the native logging provided by the Trace class, an IBM internal artifact.

Logging can be configured through the use of a junit rule.

```java
@Rule
public TraceRule traceRule = new TraceRule();

// ... //
```

This will configure the native tracer to log using SLF4J to the appender root
of the test class name, equivalent to _LoggerFactory.createLogger(MyFirstJavaComputeNodeTest.class)_.

Both the java compute node and any output terminals it uses should be mocked.

```java
// Mock compute node
@Mock MyFirstJavaComputeNode computeNode;

// Mock output terminals
@Mock MbOutputTerminal mockOutputTerminal;
@Mock MbOutputTerminal mockAlternateTerminal;

// ... //
```

For each test a MbMessageAssembly and MbMessage are required to be created and
updated to simulate each input that may pass through the node. To create a blank
MbMessage assembly the singleton message assembly manager can be used as follows.

```java
private MbMessageAssembly createInputAssembly() {
    // Build a sample input assembly
	MbMessageAssembly assembly = PseudoNativeMbMessageAssemblyManager.getInstance()
		.createBlankReadOnlyMessageAssembly();
	MbMessage message = new MbMessage(assembly.getMessage());

	// Modify message to simulate input
	// ... //
    return new MbMessageAssembly(assembly, message);
}

// ... //
```

A suitable mock interaction with the java compute node can be achieved thus.

```java
private LinkedListMultimap<String, MbMessageAssembly> evaluate(MbMessageAssembly assembly)
    throws MbException {
    // Mock compute node method invocations
	doReturn(mockOutputTerminal).when(computeNode).getOutputTerminal("out");
	doReturn(mockAlternateTerminal).when(computeNode).getOutputTerminal("alternate");
	doCallRealMethod().when(computeNode).evaluate(any(MbMessageAssembly.class));

	final LinkedListMultimap<String, MbMessageAssembly> propagatedAssemblies
	    = LinkedListMultimap.create();

	// Capture Propagated Messages
	doAnswer(new Answer<MbMessageAssembly>() {
		@Override
		public MbMessageAssembly answer(InvocationOnMock invocation) throws Throwable {
			MbOutputTerminal terminal = (MbOutputTerminal) invocation.getMock();
			MbMessageAssembly assembly = (MbMessageAssembly) invocation.getArguments()[0];
			propagatedAssemblies.put(terminal.getName(), assembly);
			return assembly;
		}
	}).when(mockOutputTerminal).propagate(any(MbMessageAssembly.class));

	// Setup input assembly

	computeNode.evaluate(assembly);

	// Verify expected output

	verify(computeNode).evaluate(assembly);
	verify(mockOutputTerminal).propagate(any(MbMessageAssembly.class));

	return propagatedAssemblies;
}


// ... //
```

Each test then passes calls to _evaluate(MbMessageAssembly)_ with its input and
verifies that the correct mock interactions were made and that the output matches
the expected result.

```java
@Test
public void testMyFirstJavaComputeNodeWithInput() throws MbException {
    // Create initial input assembly
    MbMessageAssembly assembly = createInputAssembly();

    // invoke against our mocked out compute node
    LinkedListMultimap<MbMessageAssembly> propagatedAssemblies = evaluate(assembly);

	// Verify expected output - for example

	Assert.assertEquals(1, propagatedAssemblies.get("out").size());
}

```

Assertions
----------

Because MbMessage and MbElement do not provide implementations of hashcode or equals methods assertion that two wrappers
are the equal _mbElementA.equals(mbElementB)_ produces unexpected results. Thus the standard junit assertions that
depend on this fail, therefore complementary assertions are provided by the _MbAssert_ class.

```java
// ... //
MbElement a = message.getRootElement().getFirstChild();
MbElement b = message.getRootElement().getFirstElementByPath('/Properties');
MbAssert.assertEquals("Expected first element to be the properties node", a, b);
// ... //
```

TODO
----

Future Features:

* Helper to remove necessary boilerplate for mocking java compute invocations
* Enhanced assertions to cover common scenarios (for example assert that tree paths equal)

Sample
------

Complete version of code detailed above.

```java
@RunWith(PowerMockRunner.class)
@MockPolicy(MbMockPolicy.class)
public class MyFirstJavaComputeNodeTest {

	@Mock MyFirstJavaComputeNode computeNode;

	@Mock MbOutputTerminal mockOutputTerminal;
	@Mock MbOutputTerminal mockAlternateTerminal;

    private MbMessageAssembly createInputAssembly() {
        // Build a sample input assembly
		MbMessageAssembly assembly = PseudoNativeMbMessageAssemblyManager.getInstance()
			.createBlankReadOnlyMessageAssembly();
		MbMessage message = new MbMessage(assembly.getMessage());

		// Modify message to simulate input
		// ... //
        return new MbMessageAssembly(assembly, message);
    }

	private LinkedListMultimap<String, MbMessageAssembly> evaluate(MbMessageAssembly assembly) 
	    throws MbException {	        
        // Mock compute node method invocations    
		doReturn(mockOutputTerminal).when(computeNode).getOutputTerminal("out");
		doReturn(mockAlternateTerminal).when(computeNode).getOutputTerminal("alternate");
		doCallRealMethod().when(computeNode).evaluate(any(MbMessageAssembly.class));

		final LinkedListMultimap<String, MbMessageAssembly> propagatedAssemblies 
		    = LinkedListMultimap.create();

		// Capture Propagated Messages
		doAnswer(new Answer<MbMessageAssembly>() {
			@Override
			public MbMessageAssembly answer(InvocationOnMock invocation) throws Throwable {
				MbOutputTerminal terminal = (MbOutputTerminal) invocation.getMock();
				MbMessageAssembly assembly = (MbMessageAssembly) invocation.getArguments()[0];
				propagatedAssemblies.put(terminal.getName(), assembly);
				return assembly;
			}
		}).when(mockOutputTerminal).propagate(any(MbMessageAssembly.class));

		// Setup input assembly

		computeNode.evaluate(assembly);

		// Verify expected output

		verify(computeNode).evaluate(assembly);
		verify(mockOutputTerminal).propagate(any(MbMessageAssembly.class));

		return propagatedAssemblies;
	}


	@Test
	public void testMyFirstJavaComputeNodeWithInput() throws MbException {
	    // Create initial input assembly
	    MbMessageAssembly assembly = createInputAssembly();

	    // invoke against our mocked out compute node
	    LinkedListMultimap<MbMessageAssembly> propagatedAssemblies = evaluate(assembly);

		// Verify expected output - for example

		Assert.assertEquals(1, propagatedAssemblies.get("out").size());
	}
}

```