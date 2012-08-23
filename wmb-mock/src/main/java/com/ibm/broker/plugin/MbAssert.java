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
package com.ibm.broker.plugin;

import static junit.framework.Assert.failNotEquals;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class MbAssert {

    protected MbAssert() {}

    static public void assertEquals(MbMessage expected, MbMessage actual) {
        assertEquals(null, expected, actual);
    }

    static public void assertEquals(String message, MbMessage expected, MbMessage actual) {
        if (expected == null && actual == null)
            return;
        if (expected != null && expected.getHandle() == actual.getHandle())
            return;
        failNotEquals(message, expected, actual);
    }

    static public void assertEquals(MbElement expected, MbElement actual) {
        assertEquals(null, expected, actual);
    }

    static public void assertEquals(String message, MbElement expected, MbElement actual) {
        if (expected == null && actual == null)
            return;
        if (expected != null && expected.getHandle() == actual.getHandle())
            return;
        failNotEquals(message, expected, actual);
    }

    static public void assertEquals(MbXPath expected, MbXPath actual) {
        assertEquals(null, expected, actual);
    }

    static public void assertEquals(String message, MbXPath expected, MbXPath actual) {
        if (expected == null && actual == null)
            return;
        if (expected != null && expected.getHandle() == actual.getHandle())
            return;
        failNotEquals(message, expected, actual);
    }

}
