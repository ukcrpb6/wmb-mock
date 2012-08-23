/**
 * Copyright 2012 Bob Browning
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm.broker.trace;

import org.junit.rules.MethodRule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class TraceRule implements MethodRule, TestRule {

    @Override
    public Statement apply(final Statement base, final Description description) {
        return configureTracer(base, description.getTestClass());
    }

    @Override
    public Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
        return configureTracer(base, target.getClass());
    }

    private Statement configureTracer(final Statement base, final Class<?> klass) {
        return new Statement() {
            @Override public void evaluate() throws Throwable {
                final Class<?> currentTestClass = LoggingNativeTracer.getInstance().getTestClass();
                LoggingNativeTracer.getInstance().setTestClass(klass);
                try {
                    base.evaluate();
                } finally {
                    LoggingNativeTracer.getInstance().setTestClass(currentTestClass);
                }
            }
        };
    }
}
