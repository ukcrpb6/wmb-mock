/*
 * Copyright 2007 (C) Callista Enterprise.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *	http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package com.googlecode.wmbutil;

import com.google.common.base.Preconditions;
import com.ibm.broker.plugin.MbJavaException;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbUserException;

import static com.google.common.base.Preconditions.checkNotNull;

public class NiceMbException extends MbUserException {

    private static final long serialVersionUID = -5760540385903728797L;

    public NiceMbException(Object source, String msg) {
        super(source.getClass().getName(), "", "", "", msg, new Object[0]);
    }

    public NiceMbException(String msg) {
        super("", "", "", "", msg, new Object[0]);
    }

    public NiceMbException(String msg, Object... parameters) {
        super("", "", "", "", String.format(msg, parameters), new Object[0]);
    }

    private NiceMbException(StackTraceElement element, String msg) {
        super(element.getClassName(), element.getMethodName(), "", "", msg, new Object[0]);
    }

    public static NiceMbException propagate(Throwable t) {
        //noinspection ThrowableResultOfMethodCallIgnored
        if(checkNotNull(t).getStackTrace() != null && t.getStackTrace().length > 0) {
            StackTraceElement st = t.getStackTrace()[0];
            return new NiceMbException(st, t.getMessage());
        }

        return new NiceMbException(t.getMessage());
    }

}