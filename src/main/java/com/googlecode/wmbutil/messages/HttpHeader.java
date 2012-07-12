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

public class HttpHeader extends Header {

    private final MbHttpHeaderType type;

    HttpHeader(MbElement elm, MbHttpHeaderType type, boolean readOnly) throws MbException {
        super(elm, readOnly);
        this.type = type;
    }

    public String get(String name) throws MbException {
        return getStringValue(name);
    }

    public void set(String name, String value) throws MbException {
        setStringValue(name, value);
    }

    /**
     * Returns the {@code "Accept"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getAccept() throws MbException {
        return getStringValue(ACCEPT);
    }

    /**
     * Sets the {@code "Accept"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setAccept(String accept) throws MbException {
        setStringValue(ACCEPT, accept);
    }

    /**
     * Returns the {@code "Accept-Encoding"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getAcceptEncoding() throws MbException {
        return getStringValue(ACCEPT_ENCODING);
    }

    /**
     * Sets the {@code "Accept-Encoding"} header or {@code null} for none.
     * <p/>
     * <p>
     * By default, this is {@code "gzip"}.
     * </p>
     *
     * @since 1.5
     */
    public final void setAcceptEncoding(String acceptEncoding) throws MbException {
        setStringValue(ACCEPT_ENCODING, acceptEncoding);
    }

    /**
     * Returns the {@code "Authorization"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getAuthorization() throws MbException {
        return getStringValue(AUTHORIZATION);
    }

    /**
     * Sets the {@code "Authorization"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setAuthorization(String authorization) throws MbException {
        setStringValue(AUTHORIZATION, authorization);
    }

    /**
     * Returns the {@code "Cache-Control"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getCacheControl() throws MbException {
        return getStringValue(CACHE_CONTROL);
    }

    /**
     * Sets the {@code "Cache-Control"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setCacheControl(String cacheControl) throws MbException {
        setStringValue(CACHE_CONTROL, cacheControl);
    }

    /**
     * Returns the {@code "Content-Encoding"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getContentEncoding() throws MbException {
        return getStringValue(CONTENT_ENCODING);
    }

    /**
     * Sets the {@code "Content-Encoding"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setContentEncoding(String contentEncoding) throws MbException {
        setStringValue(CONTENT_ENCODING, contentEncoding);
    }

    /**
     * Returns the {@code "Content-Length"} header or {@code null} for none.
     * <p/>
     * <p>
     * Upgrade warning: in prior version content length was represented as a String, but now it is
     * represented as a Long.
     * </p>
     *
     * @since 1.5
     */
    public final Long getContentLength() throws MbException {
        return getLongValue(CONTENT_LENGTH);
    }

    /**
     * Sets the {@code "Content-Length"} header or {@code null} for none.
     * <p/>
     * <p>
     * Upgrade warning: in prior version content length was represented as a String, but now it is
     * represented as a Long.
     * </p>
     *
     * @since 1.5
     */
    public final void setContentLength(Long contentLength) throws MbException {
        setLongValue(CONTENT_LENGTH, contentLength);
    }

    /**
     * Returns the {@code "Content-MD5"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getContentMD5() throws MbException {
        return getStringValue(CONTENT_MD5);
    }

    /**
     * Sets the {@code "Content-MD5"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setContentMD5(String contentMD5) throws MbException {
        setStringValue(CONTENT_MD5, contentMD5);
    }

    /**
     * Returns the {@code "Content-Range"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getContentRange() throws MbException {
        return getStringValue(CONTENT_RANGE);
    }

    /**
     * Sets the {@code "Content-Range"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setContentRange(String contentRange) throws MbException {
        setStringValue(CONTENT_RANGE, contentRange);
    }

    /**
     * Returns the {@code "Content-Type"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getContentType() throws MbException {
        return getStringValue(CONTENT_TYPE);
    }

    /**
     * Sets the {@code "Content-Type"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setContentType(String contentType) throws MbException {
        setStringValue(CONTENT_TYPE, contentType);
    }

    /**
     * Returns the {@code "Cookie"} header or {@code null} for none.
     * <a href='http://tools.ietf.org/html/rfc6265'>See Cookie Specification.</a>
     *
     * @since 1.6
     */
    public final String getCookie() throws MbException {
        return getStringValue(COOKIE);
    }

    /**
     * Sets the {@code "Cookie"} header or {@code null} for none.
     *
     * @since 1.6
     */
    public final void setCookie(String cookie) throws MbException {
        setStringValue(COOKIE, cookie);
    }

    /**
     * Returns the {@code "Date"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getDate() throws MbException {
        return getStringValue(DATE);
    }

    /**
     * Sets the {@code "Date"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setDate(String date) throws MbException {
        setStringValue(DATE, date);
    }

    /**
     * Returns the {@code "ETag"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getETag() throws MbException {
        return getStringValue(ETAG);
    }

    /**
     * Sets the {@code "ETag"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setETag(String etag) throws MbException {
        setStringValue(ETAG, etag);
    }

    /**
     * Returns the {@code "Expires"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getExpires() throws MbException {
        return getStringValue(EXPIRES);
    }

    /**
     * Sets the {@code "Expires"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setExpires(String expires) throws MbException {
        setStringValue(EXPIRES, expires);
    }

    /**
     * Returns the {@code "If-Modified-Since"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getIfModifiedSince() throws MbException {
        return getStringValue(IF_MODIFIED_SINCE);
    }

    /**
     * Sets the {@code "If-Modified-Since"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setIfModifiedSince(String ifModifiedSince) throws MbException {
        setStringValue(IF_MODIFIED_SINCE, ifModifiedSince);
    }

    /**
     * Returns the {@code "If-Match"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getIfMatch() throws MbException {
        return getStringValue(IF_MATCH);
    }

    /**
     * Sets the {@code "If-Match"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setIfMatch(String ifMatch) throws MbException {
        setStringValue(IF_MATCH, ifMatch);
    }

    /**
     * Returns the {@code "If-None-Match"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getIfNoneMatch() throws MbException {
        return getStringValue(IF_NONE_MATCH);
    }

    /**
     * Sets the {@code "If-None-Match"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setIfNoneMatch(String ifNoneMatch) throws MbException {
        setStringValue(IF_NONE_MATCH, ifNoneMatch);
    }

    /**
     * Returns the {@code "If-Unmodified-Since"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getIfUnmodifiedSince() throws MbException {
        return getStringValue(IF_UNMODIFIED_SINCE);
    }

    /**
     * Sets the {@code "If-Unmodified-Since"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setIfUnmodifiedSince(String ifUnmodifiedSince) throws MbException {
        setStringValue(IF_UNMODIFIED_SINCE, ifUnmodifiedSince);
    }

    /**
     * Returns the {@code "Last-Modified"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getLastModified() throws MbException {
        return getStringValue(LAST_MODIFIED);
    }

    /**
     * Sets the {@code "Last-Modified"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setLastModified(String lastModified) throws MbException {
        setStringValue(LAST_MODIFIED, lastModified);
    }

    /**
     * Returns the {@code "Location"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getLocation() throws MbException {
        return getStringValue(LOCATION);
    }

    /**
     * Sets the {@code "Location"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setLocation(String location) throws MbException {
        setStringValue(LOCATION, location);
    }

    /**
     * Returns the {@code "MIME-Version"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getMimeVersion() throws MbException {
        return getStringValue("MIME-Version");
    }

    /**
     * Sets the {@code "MIME-Version"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setMimeVersion(String mimeVersion) throws MbException {
        setStringValue("MIME-Version", mimeVersion);
    }

    /**
     * Returns the {@code "Range"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getRange() throws MbException {
        return getStringValue(RANGE);
    }

    /**
     * Sets the {@code "Range"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setRange(String range) throws MbException {
        setStringValue(RANGE, range);
    }

    /**
     * Returns the {@code "Retry-After"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getRetryAfter() throws MbException {
        return getStringValue(RETRY_AFTER);
    }

    /**
     * Sets the {@code "Retry-After"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setRetryAfter(String retryAfter) throws MbException {
        setStringValue(RETRY_AFTER, retryAfter);
    }

    /**
     * Returns the {@code "User-Agent"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getUserAgent() throws MbException {
        return getStringValue(USER_AGENT);
    }

    /**
     * Sets the {@code "User-Agent"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setUserAgent(String userAgent) throws MbException {
        setStringValue(USER_AGENT, userAgent);
    }

    /**
     * Returns the {@code "WWW-Authenticate"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final String getAuthenticate() throws MbException {
        return getStringValue(WWW_AUTHENTICATE);
    }

    /**
     * Sets the {@code "WWW-Authenticate"} header or {@code null} for none.
     *
     * @since 1.5
     */
    public final void setAuthenticate(String authenticate) throws MbException {
        setStringValue(WWW_AUTHENTICATE, authenticate);
    }

    /**
     * Sets the {@code authorization} header as specified in <a
     * href="http://tools.ietf.org/html/rfc2617#section-2">Basic Authentication Scheme</a>.
     *
     * @since 1.2
     */
    public final void setBasicAuthentication(String username, String password) throws MbException {
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

    public MbHttpHeaderType getType() {
        return type;
    }
}

