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

package com.googlecode.wmbutil.messages;

import com.google.common.base.Preconditions;
import com.googlecode.wmbutil.NiceMbException;
import com.ibm.broker.javacompute.Base64;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

import java.io.UnsupportedEncodingException;

import static com.google.common.net.HttpHeaders.*;

public class MutableHttpHeader extends Header implements HttpHeader {

    private final MbHeaderType type;

    MutableHttpHeader(MbElement elm, MbHeaderType type) throws MbException {
        super(elm);
        this.type = type;
    }

    @Override
    public String get(String name) throws MbException {
        return getStringValue(name);
    }

    @Override
    public void set(String name, String value) throws MbException {
        setStringValue(name, value);
    }

    @Override
    public final String getAccept() throws MbException {
        return getStringValue(ACCEPT);
    }

    @Override
    public void setAccept(String accept) throws MbException {
        setStringValue(ACCEPT, accept);
    }

    @Override
    public final String getAcceptEncoding() throws MbException {
        return getStringValue(ACCEPT_ENCODING);
    }

    @Override
    public void setAcceptEncoding(String acceptEncoding) throws MbException {
        setStringValue(ACCEPT_ENCODING, acceptEncoding);
    }

    @Override
    public final String getAuthorization() throws MbException {
        return getStringValue(AUTHORIZATION);
    }

    @Override
    public void setAuthorization(String authorization) throws MbException {
        setStringValue(AUTHORIZATION, authorization);
    }

    @Override
    public final String getCacheControl() throws MbException {
        return getStringValue(CACHE_CONTROL);
    }

    @Override
    public void setCacheControl(String cacheControl) throws MbException {
        setStringValue(CACHE_CONTROL, cacheControl);
    }

    @Override
    public final String getContentEncoding() throws MbException {
        return getStringValue(CONTENT_ENCODING);
    }

    @Override
    public void setContentEncoding(String contentEncoding) throws MbException {
        setStringValue(CONTENT_ENCODING, contentEncoding);
    }

    @Override
    public final Long getContentLength() throws MbException {
        return getLongValue(CONTENT_LENGTH);
    }

    @Override
    public void setContentLength(Long contentLength) throws MbException {
        setLongValue(CONTENT_LENGTH, contentLength);
    }

    @Override
    public final String getContentMD5() throws MbException {
        return getStringValue(CONTENT_MD5);
    }

    @Override
    public void setContentMD5(String contentMD5) throws MbException {
        setStringValue(CONTENT_MD5, contentMD5);
    }

    @Override
    public final String getContentRange() throws MbException {
        return getStringValue(CONTENT_RANGE);
    }

    @Override
    public void setContentRange(String contentRange) throws MbException {
        setStringValue(CONTENT_RANGE, contentRange);
    }

    @Override
    public final String getContentType() throws MbException {
        return getStringValue(CONTENT_TYPE);
    }

    @Override
    public void setContentType(String contentType) throws MbException {
        setStringValue(CONTENT_TYPE, contentType);
    }

    @Override
    public final String getCookie() throws MbException {
        return getStringValue(COOKIE);
    }

    @Override
    public void setCookie(String cookie) throws MbException {
        setStringValue(COOKIE, cookie);
    }

    @Override
    public final String getDate() throws MbException {
        return getStringValue(DATE);
    }

    @Override
    public void setDate(String date) throws MbException {
        setStringValue(DATE, date);
    }

    @Override
    public final String getETag() throws MbException {
        return getStringValue(ETAG);
    }

    @Override
    public void setETag(String etag) throws MbException {
        setStringValue(ETAG, etag);
    }

    @Override
    public final String getExpires() throws MbException {
        return getStringValue(EXPIRES);
    }

    @Override
    public void setExpires(String expires) throws MbException {
        setStringValue(EXPIRES, expires);
    }

    @Override
    public final String getIfModifiedSince() throws MbException {
        return getStringValue(IF_MODIFIED_SINCE);
    }

    @Override
    public void setIfModifiedSince(String ifModifiedSince) throws MbException {
        setStringValue(IF_MODIFIED_SINCE, ifModifiedSince);
    }

    @Override
    public final String getIfMatch() throws MbException {
        return getStringValue(IF_MATCH);
    }

    @Override
    public void setIfMatch(String ifMatch) throws MbException {
        setStringValue(IF_MATCH, ifMatch);
    }

    @Override
    public final String getIfNoneMatch() throws MbException {
        return getStringValue(IF_NONE_MATCH);
    }

    @Override
    public void setIfNoneMatch(String ifNoneMatch) throws MbException {
        setStringValue(IF_NONE_MATCH, ifNoneMatch);
    }

    @Override
    public final String getIfUnmodifiedSince() throws MbException {
        return getStringValue(IF_UNMODIFIED_SINCE);
    }

    @Override
    public void setIfUnmodifiedSince(String ifUnmodifiedSince) throws MbException {
        setStringValue(IF_UNMODIFIED_SINCE, ifUnmodifiedSince);
    }

    @Override
    public final String getLastModified() throws MbException {
        return getStringValue(LAST_MODIFIED);
    }

    @Override
    public void setLastModified(String lastModified) throws MbException {
        setStringValue(LAST_MODIFIED, lastModified);
    }

    @Override
    public final String getLocation() throws MbException {
        return getStringValue(LOCATION);
    }

    @Override
    public void setLocation(String location) throws MbException {
        setStringValue(LOCATION, location);
    }

    @Override
    public final String getMimeVersion() throws MbException {
        return getStringValue("MIME-Version");
    }

    @Override
    public void setMimeVersion(String mimeVersion) throws MbException {
        setStringValue("MIME-Version", mimeVersion);
    }

    @Override
    public final String getRange() throws MbException {
        return getStringValue(RANGE);
    }

    @Override
    public void setRange(String range) throws MbException {
        setStringValue(RANGE, range);
    }

    @Override
    public final String getRetryAfter() throws MbException {
        return getStringValue(RETRY_AFTER);
    }

    @Override
    public void setRetryAfter(String retryAfter) throws MbException {
        setStringValue(RETRY_AFTER, retryAfter);
    }

    @Override
    public final String getUserAgent() throws MbException {
        return getStringValue(USER_AGENT);
    }

    @Override
    public void setUserAgent(String userAgent) throws MbException {
        setStringValue(USER_AGENT, userAgent);
    }

    @Override
    public final String getAuthenticate() throws MbException {
        return getStringValue(WWW_AUTHENTICATE);
    }

    @Override
    public void setAuthenticate(String authenticate) throws MbException {
        setStringValue(WWW_AUTHENTICATE, authenticate);
    }

    @Override
    public void setBasicAuthentication(String username, String password) throws MbException {
        String userPass =
                Preconditions.checkNotNull(username) + ":" + Preconditions.checkNotNull(password);
        String encoded;
        try {
            encoded = Base64.encode(userPass.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new NiceMbException(e.getMessage());
        }
        setStringValue(AUTHORIZATION, "Basic " + encoded);
    }

    @Override
    public MbHeaderType getType() {
        return type;
    }
}

