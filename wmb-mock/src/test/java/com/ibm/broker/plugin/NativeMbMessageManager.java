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

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public interface NativeMbMessageManager {
    
    long _createMessage(long handle) throws MbException;

    void _createMessage(long[] paramArrayOfLong) throws MbException;

    void _clearMessage(long handle, long inputContextHandle) throws MbException;

    void _finalizeMessage(long handle, int paramInt) throws MbException;

    byte[] _getBuffer(long handle) throws MbException;

    void _writeBuffer(long handle) throws MbException;

    long _getRootElement(long handle) throws MbException;

    void _copy(long handle, long inputContextHandle) throws MbException;

    Object _evaluateXPath(long handle, long inputContextHandle, MbXPath paramMbXPath) throws MbException;

    Object _evaluateXPath(long handle, String paramString) throws MbException;
    
}
