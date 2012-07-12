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

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Map;

/**
 * Simplifies using the IBM CCSID values for charsets by
 * providing constants for common CCSIDs and methods
 * for converting to Java charsets
 */
public enum CCSID {

    UTF16_BIG_ENDIAN(1200), UTF16_LITTLE_ENDIAN(1202),
    UTF8(1208, "UTF-8"),
    ISO_8859_1(819), ISO_LATIN_1(819),
    ASCII(437, "ASCII"),
    EBCDIC_SWE_FIN(278);

    private final int value;
    private final Optional<String> charset;
    private Charset charsetInstance;

    private static Map<Integer, CCSID> cache = Maps.newHashMap();

    static {
        // Enum constants are initialised before static members - otherwise we'd populate in the constructor
        for (CCSID ccsid : values()) {
            cache.put(ccsid.value, ccsid);
        }
    }

    CCSID(int value) {
        this.value = value;
        this.charset = Optional.absent();
    }

    CCSID(int value, String charset) {
        this.value = value;
        this.charset = Optional.fromNullable(Preconditions.checkNotNull(charset));
    }

    public static CCSID valueOf(int value) {
        return cache.get(value);
    }

    /**
     * Converts a CCSID into the name used for the encoding
     * in Java. Note that this method might return charset
     * names which are not supported on the particular platform.
     *
     * @return The Java name for the encoding.
     */
    public String toCharsetString() {
        return charset.isPresent() ? charset.get() : "Cp" + value;
    }

    public Charset toCharset() throws UnsupportedCharsetException {
        if (charsetInstance == null) {
            charsetInstance = Charset.forName(toCharsetString());
        }
        return charsetInstance;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("value", value)
                .add("charset", toCharsetString()).toString();
    }
}
